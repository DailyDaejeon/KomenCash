package com.komencash.backend.service;

import com.komencash.backend.dto.store.StoreItemFindResponseDto;
import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.dto.tax.TaxHistoryFindResponseDto;
import com.komencash.backend.dto.tax.TaxRateUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.store.OnlineStoreItem;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.OnlineStoreItemRepository;
import com.komencash.backend.repository.TaxHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaxService {

    @Autowired
    TaxHistoryRepository taxHistoryRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StoreService storeService;

    @Autowired
    OnlineStoreItemRepository onlineStoreItemRepository;


    public List<TaxHistoryFindResponseDto> findTaxHistoryList(int groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return null;

        List<TaxHistoryFindResponseDto> taxHistoryFindResponsDtos = new ArrayList<>();
        List<TaxHistory> taxHistories = taxHistoryRepository.findByGroup_Id(group.getId());
        taxHistories.forEach(taxHistory -> taxHistoryFindResponsDtos.add(new TaxHistoryFindResponseDto(taxHistory)));

        return taxHistoryFindResponsDtos;
    }


    public boolean addTaxHistory(TaxHistoryAddUpdateRequestDto taxHistoryAddUpdateRequestDto){
        Group group = groupRepository.findById(taxHistoryAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        List<TaxHistory> taxHistories = taxHistoryRepository.findByGroup_Id(group.getId());
        int preBalance = taxHistories.size() < 1 ? 0 : taxHistories.get(taxHistories.size() - 1).getBalance();
        int balance = preBalance + taxHistoryAddUpdateRequestDto.getBalanceChange();

        TaxHistory taxHistory = new TaxHistory(taxHistoryAddUpdateRequestDto, group, balance);
        taxHistoryRepository.save(taxHistory);
        return true;
    }



    public boolean updateTaxRate(TaxRateUpdateRequestDto taxRateUpdateRequestDto){
        Group group = groupRepository.findById(taxRateUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        group.updateTaxRate(taxRateUpdateRequestDto.getTaxRate());
        groupRepository.save(group);
        return true;
    }


    public boolean updateInflationRate(TaxRateUpdateRequestDto taxRateUpdateRequestDto){
        Group group = groupRepository.findById(taxRateUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;


        List<StoreItemFindResponseDto> storeItemFindResponseDtos = storeService.findStoreItemList(taxRateUpdateRequestDto.getGroupId());
        storeItemFindResponseDtos.forEach(storeItemFindResponseDto -> {
                OnlineStoreItem onlineStoreItem =
                        new OnlineStoreItem(
                                storeItemFindResponseDto.getId(),
                                storeItemFindResponseDto.getName(),
                                (int) (storeItemFindResponseDto.getPrice() * (1 + 0.01 * taxRateUpdateRequestDto.getTaxRate())),
                                group);
                onlineStoreItemRepository.save(onlineStoreItem);
        });

        group.updateInflationRate(taxRateUpdateRequestDto.getTaxRate());
        groupRepository.save(group);
        return true;
    }

}
