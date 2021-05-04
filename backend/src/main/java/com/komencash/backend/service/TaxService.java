package com.komencash.backend.service;

import com.komencash.backend.dto.tax.TaxHistoryInsertUpdateRequest;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.TaxHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxService {

    @Autowired
    TaxHistoryRepository taxHistoryRepository;
    @Autowired
    GroupRepository groupRepository;


    public boolean insertTaxHistory(TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest){
        Group group = groupRepository.findById(taxHistoryInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        TaxHistory taxHistory = new TaxHistory(taxHistoryInsertUpdateRequest, group);
        taxHistoryRepository.save(taxHistory);
        return true;
    }

}