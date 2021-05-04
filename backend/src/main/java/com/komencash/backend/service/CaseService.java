package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountHistoryInsertUpdateRequest;
import com.komencash.backend.dto.request.CaseAcceptRequest;
import com.komencash.backend.dto.request.CaseSelectResponse;
import com.komencash.backend.dto.tax.TaxHistoryInsertUpdateRequest;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.CaseRequestHistory;
import com.komencash.backend.repository.CaseRequestHistoryRepository;
import com.komencash.backend.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseService {

    @Autowired
    CaseRequestHistoryRepository caseRequestHistoryRepository;
    @Autowired
    BankService bankService;
    @Autowired
    TaxService taxService;
    @Autowired
    GroupRepository groupRepository;


    public List<CaseSelectResponse> selectCaseList(int groupId){
        List<CaseSelectResponse> caseSelectResponses = new ArrayList<>();

        List<CaseRequestHistory> caseRequestHistories = caseRequestHistoryRepository.findByPolice_Job_Group_Id(groupId);
        for(CaseRequestHistory caseRequestHistory : caseRequestHistories) caseSelectResponses.add(new CaseSelectResponse(caseRequestHistory));

        return caseSelectResponses;
    }


    public CaseSelectResponse selectCase(int caseId){
        CaseRequestHistory caseRequestHistory = caseRequestHistoryRepository.findById(caseId).orElse(null);
        if(caseRequestHistory == null) return null;

        return new CaseSelectResponse(caseRequestHistory);
    }


    public boolean updateCaseAccept(CaseAcceptRequest caseAcceptRequest) {
        CaseRequestHistory caseRequestHistory = caseRequestHistoryRepository.findById(caseAcceptRequest.getCaseId()).orElse(null);
        if(caseRequestHistory == null) return false;

        if(caseAcceptRequest.getAccept().equals(Accept.accept)) {

            // 사건 처리 수락이면 accpet를 accpet로 바꾸고 저장
            caseRequestHistory.updateCaseAccept(caseAcceptRequest.getAccept());
            caseRequestHistoryRepository.save(caseRequestHistory);

            // 학생 계좌 히스토리에 벌금 빼고 저장
            int studentId = caseRequestHistory.getStudent().getId();
            int balance_change = -caseRequestHistory.getFine();
            String content = "사건 번호 - " + caseRequestHistory.getId() + " : " +
                    "경찰 - " + caseRequestHistory.getPolice().getNickname() + ", 대상 - " + caseRequestHistory.getStudent().getNickname();
            if(!bankService.insertAccountHistory(new AccountHistoryInsertUpdateRequest(studentId, balance_change, content))) return false;


            // 세금 히스토리에 세금 넣고 저장
            Group group = caseRequestHistory.getPolice().getJob().getGroup();
            if(!taxService.insertTaxHistory(new TaxHistoryInsertUpdateRequest(-balance_change, content, group.getId()))) return false;

            // 세금 잔액에 더해준다.
            group.updateTaxBalance(-balance_change);
            groupRepository.save(group);
            return true;
        }

        // 사건 처리 거절이면 accept만 reject로 바꾸고 저장
        else if(caseAcceptRequest.getAccept().equals(Accept.reject)){
            caseRequestHistory.updateCaseAccept(caseAcceptRequest.getAccept());
            caseRequestHistoryRepository.save(caseRequestHistory);
        }
        return true;
    }
}
