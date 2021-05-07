package com.komencash.backend.service;

import com.komencash.backend.dto.tax.TaxDetailResponse;
import com.komencash.backend.dto.tax.TaxHistoryInsertUpdateRequest;
import com.komencash.backend.dto.tax.TaxHistoryResponse;
import com.komencash.backend.dto.tax.TaxRateUpdateRequest;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.repository.GroupRepository;
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

    public List<TaxHistoryResponse> findTaxDetail(int groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return null;

        List<TaxHistoryResponse> taxHistoryResponses = new ArrayList<>();
        List<TaxHistory> taxHistories = taxHistoryRepository.findAll();
        for(TaxHistory taxHistory : taxHistories) taxHistoryResponses.add(new TaxHistoryResponse(taxHistory));

        return taxHistoryResponses;
    }


    public boolean insertTaxHistory(TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        List<TaxHistory> taxHistories = taxHistoryRepository.findAll();
        int preBalance = taxHistories.size() < 1 ? 0 : taxHistories.get(taxHistories.size() - 1).getBalance();
        int balance = preBalance + taxHistoryInsertUpdateRequest.getBalanceChange();

        TaxHistory taxHistory = new TaxHistory(taxHistoryInsertUpdateRequest, group, balance);
        taxHistoryRepository.save(taxHistory);
        return true;
    }



    public boolean updateTaxRate(TaxRateUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        group.updateTaxRate(taxHistoryInsertUpdateRequest.getTaxRate());
        groupRepository.save(group);
        return true;
    }


    public boolean updateInflationRate(TaxRateUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        group.updateInflationRate(taxHistoryInsertUpdateRequest.getTaxRate());
        groupRepository.save(group);
        return true;
    }

}
