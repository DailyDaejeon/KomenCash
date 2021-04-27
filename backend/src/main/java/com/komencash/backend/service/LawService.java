package com.komencash.backend.service;

import com.komencash.backend.dto.law.LawSelectResponse;
import com.komencash.backend.entity.law.Law;
import com.komencash.backend.repository.LawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LawService {

    @Autowired
    LawRepository lawRepository;

    public List<LawSelectResponse> findLawByGroupId(int group_id) {

        List<Law> lawList =  lawRepository.findByGroup_Id(group_id).orElseGet(ArrayList::new);

        List<LawSelectResponse> resultList = new ArrayList();
        for(Law law : lawList) {
            resultList.add(new LawSelectResponse(law));
        }

        return resultList;
    }
}
