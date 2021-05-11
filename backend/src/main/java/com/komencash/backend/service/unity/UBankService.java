package com.komencash.backend.service.unity;


import com.komencash.backend.dto.bank.AccountFindResponseDto;
import com.komencash.backend.dto.bank.AccountHistoryFindResponseDto;
import com.komencash.backend.dto.bank.FinancialProductHistoryAddDto;
import com.komencash.backend.dto.bank.SalaryPaymentRequestDto;
import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.financial.FinancialProductHistory;
import com.komencash.backend.entity.financial.Status;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.job.RecruitType;
import com.komencash.backend.entity.request_history.SalaryPaymentRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.repository.*;
import com.komencash.backend.service.BankService;
import com.komencash.backend.service.TaxService;
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
    @Autowired
    TaxHistoryRepository taxHistoryRepository;
    @Autowired
    SalaryPaymentRequestHistoryRepository salaryPaymentRequestHistoryRepository;

    public List<AccountHistoryFindResponseDto> getAccountHistory(int studentId) {
        List<AccountHistoryFindResponseDto> accountHistoryFindResponseDtos = new ArrayList<>();
        List<AccountHistory> accountHistories = accountHistoryRepository.findByStudent_Id(studentId);
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

    public boolean productTerminateRequest(FinancialProductHistoryAddDto request) {
        FinancialProductHistory history = financialProductHistoryRepository.findById(request.getFinancialProductDetailId()).orElse(null);
        if(history.getStatus() == Status.deposit){
            history.acceptRequest(Status.before_termination);
        }
        else{
            return false;
        }
        financialProductHistoryRepository.save(history);
        return true;
    }

    public boolean salaryRequestAllow(SalaryPaymentRequestDto request) {
        AccountHistory newAccountHistory;
        TaxHistory newTaxHistory;

        SalaryPaymentRequestHistory salaryPaymentRequestHistory = salaryPaymentRequestHistoryRepository.findById(request.getId()).orElse(null);
        Student student = studentRepository.findById(request.getStudent_id()).orElse(null);
        List<AccountHistory> accountHistory = accountHistoryRepository.findByStudent_Id(request.getStudent_id());
        int groupId = student.getJob().getGroup().getId();
        List<TaxHistory> taxHistory = taxHistoryRepository.findByGroup_Id(groupId);

        // 가장 마지막 통장 기록보기
        int lastBalance =0;
        if(accountHistory.size()!=0) {
            AccountHistory lastAccountHistory = accountHistory.get(accountHistory.size() - 1);
            lastBalance = lastAccountHistory.getBalance();
        }
        System.out.println(salaryPaymentRequestHistory.getSalary());
        System.out.println(salaryPaymentRequestHistory.getStudent().getId());
        System.out.println(salaryPaymentRequestHistory.getTaxLoss());
        System.out.println(lastBalance);
        // 세금뗀 월급 계산
        int salary = salaryPaymentRequestHistory.getSalary() - salaryPaymentRequestHistory.getTaxLoss();


        // 선거로 뽑혔을 경우
        if(student.getJob().getRecruitType() == RecruitType.vote){
            int tax_change = taxHistory.get(taxHistory.size()-1).getBalance()-salary;
            // 세금에서 월급을 줌
            TaxHistoryAddUpdateRequestDto dto = new TaxHistoryAddUpdateRequestDto(salary*(-1), student.getJob().getName()+" Salary", groupId);
            newTaxHistory = new TaxHistory(dto, student.getJob().getGroup(), tax_change);
            taxHistoryRepository.save(newTaxHistory);

        }
        else{
            // 소득세 떼가기
            int tax_change = taxHistory.get(taxHistory.size()-1).getBalance()+ salaryPaymentRequestHistory.getTaxLoss();
            TaxHistoryAddUpdateRequestDto dto = new TaxHistoryAddUpdateRequestDto(salaryPaymentRequestHistory.getTaxLoss(), student.getJob().getName()+" Salary Tax", groupId);
            newTaxHistory = new TaxHistory(dto, student.getJob().getGroup(), tax_change);
            taxHistoryRepository.save(newTaxHistory);
        }

        lastBalance += salary;

        newAccountHistory = new AccountHistory(salary, lastBalance, "Salary", student);
        accountHistoryRepository.save(newAccountHistory);

        salaryPaymentRequestHistoryRepository.delete(salaryPaymentRequestHistory);
        return true;
    }
}
