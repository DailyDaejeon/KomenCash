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

    public TaxDetailResponse findTaxDetail(int groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return null;

        List<TaxHistoryResponse> taxHistoryResponses = new ArrayList<>();
        List<TaxHistory> taxHistories = taxHistoryRepository.findAll();
        for(TaxHistory taxHistory : taxHistories) taxHistoryResponses.add(new TaxHistoryResponse(taxHistory));

        TaxDetailResponse taxDetailResponse = new TaxDetailResponse(group.getTax(), taxHistoryResponses);
        return taxDetailResponse;
    }


    public boolean insertTaxHistory(TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        group.updateTaxBalance(taxHistoryInsertUpdateRequest.getBalanceChange());
        groupRepository.save(group);

        TaxHistory taxHistory = new TaxHistory(taxHistoryInsertUpdateRequest, group);
        taxHistoryRepository.save(taxHistory);
        return true;
    }


    public boolean updateTaxHistory(TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest){
        TaxHistory taxHistory = taxHistoryRepository.findById(taxHistoryInsertUpdateRequest.getId()).orElse(null);
        if(taxHistory == null) return false;

        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        int updatedBalance = taxHistoryInsertUpdateRequest.getBalanceChange() - taxHistory.getBalanceChange();
        group.updateTaxBalance(updatedBalance);
        groupRepository.save(group);

        taxHistory.updateTaxHistory(taxHistoryInsertUpdateRequest, group);
        taxHistoryRepository.save(taxHistory);
        return true;
    }


    public boolean deleteTaxHistory(int taxHistoryId){
        TaxHistory taxHistory = taxHistoryRepository.findById(taxHistoryId).orElse(null);
        if(taxHistory == null) return false;

        Group group = groupRepository.findById(taxHistory.getGroup().getId()).orElse(null);
        if(group == null) return false;

        group.updateTaxBalance(-taxHistory.getBalanceChange());
        groupRepository.save(group);

        taxHistoryRepository.delete(taxHistory);
        return true;
    }


    public boolean updateTaxRate(TaxRateUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        group.updateTaxRate(taxHistoryInsertUpdateRequest.getTaxRate());
        groupRepository.save(group);
        return true;
    }

}
