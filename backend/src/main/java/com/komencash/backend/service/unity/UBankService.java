package com.komencash.backend.service.unity;


import com.komencash.backend.dto.bank.AccountHistoryFindResponseDto;
import com.komencash.backend.dto.bank.FinancialProductHistoryAddDto;
import com.komencash.backend.dto.bank.FinancialProductReqResponseDto;
import com.komencash.backend.dto.bank.SalaryPaymentRequestDto;
import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.FinancialProduct;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.financial.FinancialProductHistory;
import com.komencash.backend.entity.financial.Status;
import com.komencash.backend.entity.job.RecruitType;
import com.komencash.backend.entity.request_history.SalaryPaymentRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.repository.*;
import com.komencash.backend.service.CreditService;
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
    @Autowired
    CreditService creditService;


    public List<AccountHistoryFindResponseDto> getAccountHistory(int studentId) {
        List<AccountHistoryFindResponseDto> accountHistoryFindResponseDtos = new ArrayList<>();
        List<AccountHistory> accountHistories = accountHistoryRepository.findByStudent_Id(studentId);
        accountHistories.forEach(accountHistory ->
                accountHistoryFindResponseDtos.add(
                        new AccountHistoryFindResponseDto(
                                accountHistory.getBalance(),
                                accountHistory.getBalanceChange(),
                                accountHistory.getContent(),
                                accountHistory.getCreatedDate()
                        )
                )
        );
        return accountHistoryFindResponseDtos;

    }


    public boolean productRegist(FinancialProductHistoryAddDto request) {
        FinancialProductDetail financialProductDetail = financialProductDetailRepository.findById(request.getFinancialProductDetailId()).orElse(null);

        Student student = studentRepository.findById(request.getStudentId()).orElse(null);
        List<AccountHistory> accountHistories = accountHistoryRepository.findByStudent_Id(request.getStudentId());
        AccountHistory temp;
        if(accountHistories.size()!= 0) {
             temp = accountHistories.get(accountHistories.size() - 1);

        }
        else{
            return false;
        }
        if(temp.getBalance() < request.getPrincipal()){
            return false;
        }
        Date date = new Date();
        long time = date.getTime();
        long endTime = (long) date.getTime() +  ((long) financialProductDetail.getPeriod() * 24 * 60 * 60 * 1000L);
        Timestamp startDate = new Timestamp(time);
        Timestamp endDate = new Timestamp(endTime);
        System.out.println("?????? : " + startDate + "~" + endDate);
        // Status ??????
        FinancialProductHistory financialProductHistory = new FinancialProductHistory(request.getPrincipal(), startDate, endDate, Status.before_deposit, student, financialProductDetail);
        financialProductHistoryRepository.save(financialProductHistory);

        return true;
    }


    public boolean productTerminateRequest(FinancialProductHistoryAddDto request) {
        FinancialProductHistory history = financialProductHistoryRepository.findById(request.getId()).orElse(null);
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

        // ?????? ????????? ?????? ????????????
        int lastBalance =0;
        if(accountHistory.size()!=0) {
            AccountHistory lastAccountHistory = accountHistory.get(accountHistory.size() - 1);
            lastBalance = lastAccountHistory.getBalance();
        }
        System.out.println(salaryPaymentRequestHistory.getSalary());
        System.out.println(salaryPaymentRequestHistory.getStudent().getId());
        System.out.println(salaryPaymentRequestHistory.getTaxLoss());
        System.out.println(lastBalance);
        // ????????? ?????? ??????
        int salary = salaryPaymentRequestHistory.getSalary() - salaryPaymentRequestHistory.getTaxLoss();


        // ????????? ????????? ??????
        if(student.getJob().getRecruitType() == RecruitType.vote){

            int tax_change;
            if(taxHistory.size() != 0){
                tax_change = taxHistory.get(taxHistory.size()-1).getBalance()-salary;
            }
            else{
                tax_change = -1*salary;
            }
            // ???????????? ????????? ???
            TaxHistoryAddUpdateRequestDto dto = new TaxHistoryAddUpdateRequestDto(salary*(-1), student.getJob().getName()+" Salary", groupId);
            newTaxHistory = new TaxHistory(dto, student.getJob().getGroup(), tax_change);
            taxHistoryRepository.save(newTaxHistory);

        }
        else{
            int tax_change;
            // ????????? ?????????
            if(taxHistory.size() != 0) {
                tax_change = taxHistory.get(taxHistory.size() - 1).getBalance() + salaryPaymentRequestHistory.getTaxLoss();
            }
            else{
                tax_change = salaryPaymentRequestHistory.getTaxLoss();
            }
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


    public List<FinancialProductReqResponseDto> getFinancialProductRequestList(int groupId){
        List<FinancialProductReqResponseDto> financialProductReqResponseDtos = new ArrayList<>();

        List<FinancialProductHistory> financialProductHistories = financialProductHistoryRepository.findByStudent_Job_Group_Id(groupId);
        financialProductHistories.forEach(financialProductHistory -> {

            if(financialProductHistory.getStatus() == Status.before_deposit || financialProductHistory.getStatus() == Status.before_termination){
                FinancialProductDetail financialProductDetail = financialProductHistory.getFinancialProductDetail();
                FinancialProduct financialProduct = financialProductDetail.getFinancialProduct();
                Student student = financialProductHistory.getStudent();
                int creditGrade = creditService.findCreditGrade(student.getId()).getCreditGrade();

                financialProductReqResponseDtos.add(
                        new FinancialProductReqResponseDto(
                                financialProduct.getId(),
                                financialProduct.getName(),
                                financialProductHistory.getId(),
                                financialProductHistory.getPrincipal(),
                                financialProductDetail.getRate(),
                                financialProductHistory.getStartDate(),
                                financialProductHistory.getEndDate(),
                                financialProductHistory.getStatus(),
                                student.getId(),
                                student.getNickname(),
                                creditGrade
                        )
                );
            }
        });

        return financialProductReqResponseDtos;
    }


    public List<SalaryPaymentRequestDto> getSalaryRequestList(int groupId) {
        List<SalaryPaymentRequestHistory> list = salaryPaymentRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);
        List<SalaryPaymentRequestDto> dtos = new ArrayList<>();
        list.forEach(s ->{
            dtos.add(new SalaryPaymentRequestDto(s.getId(), s.getSalary(), s.getTaxLoss(), s.getAccept(),s.getStudent().getId(), s.getStudent().getNickname()));
        });
        return dtos;
    }
}
