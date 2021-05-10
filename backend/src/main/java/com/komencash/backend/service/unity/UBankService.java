package com.komencash.backend.service.unity;


import com.komencash.backend.dto.bank.AccountFindResponseDto;
import com.komencash.backend.dto.bank.AccountHistoryFindResponseDto;
import com.komencash.backend.dto.bank.FinancialProductHistoryAddDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.financial.FinancialProductHistory;
import com.komencash.backend.entity.financial.Status;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.AccountHistoryRepository;
import com.komencash.backend.repository.FinancialProductDetailRepository;
import com.komencash.backend.repository.FinancialProductHistoryRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UBankService {

    @Autowired
    FinancialProductHistoryRepository financialProductHistoryRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FinancialProductDetailRepository financialProductDetailRepository;
    @Autowired
    AccountHistoryRepository accountHistoryRepository;


    public List<AccountHistoryFindResponseDto> getAccountHistory(int studentId) {
        List<AccountHistoryFindResponseDto> accountHistoryFindResponseDtos = new ArrayList<>();
        List<AccountHistory> accountHistories = accountHistoryRepository.findAllByStudent_Id(studentId);
        accountHistories.forEach(accountHistory ->
                accountHistoryFindResponseDtos.add(
                        new AccountHistoryFindResponseDto(
                                accountHistory.getBalance(),
                                accountHistory.getBalanceChange(),
                                accountHistory.getContent()
                        )
                )
        );
        return accountHistoryFindResponseDtos;

    }

    public boolean productRegist(FinancialProductHistoryAddDto request) {
        FinancialProductDetail financialProductDetail = financialProductDetailRepository.findById(request.getFinancialProductDetailId()).orElse(null);

        Student student = studentRepository.findById(request.getStudentId()).orElse(null);

        Date date = new Date();
        long time = date.getTime();
        Timestamp startDate = new Timestamp(time);
        Timestamp endDate = new Timestamp(time + financialProductDetail.getPeriod() * 24 * 60 * 60 * 1000);
        System.out.println("기간 : " + startDate + "~" + endDate);
        // Status 수정
        FinancialProductHistory financialProductHistory = new FinancialProductHistory(request.getPrincipal(), startDate, endDate, Status.before_deposit, student, financialProductDetail);
        financialProductHistoryRepository.save(financialProductHistory);

        return true;
    }
}
