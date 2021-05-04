package com.komencash.backend.service;

import com.komencash.backend.dto.request.CaseSelectResponse;
import com.komencash.backend.entity.request_history.CaseRequestHistory;
import com.komencash.backend.repository.CaseRequestHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseService {

    @Autowired
    CaseRequestHistoryRepository caseRequestHistoryRepository;


    public List<CaseSelectResponse> selectCaseList(int groupId){
        List<CaseSelectResponse> caseSelectResponses = new ArrayList<>();

        List<CaseRequestHistory> caseRequestHistories = caseRequestHistoryRepository.findByPolice_Job_Group_Id(groupId);
        for(CaseRequestHistory caseRequestHistory : caseRequestHistories) caseSelectResponses.add(new CaseSelectResponse(caseRequestHistory));

        return caseSelectResponses;
    }
}
