package com.komencash.backend.service;

import com.komencash.backend.dto.bank.*;
import com.komencash.backend.dto.credit.CreditFindGradeAndPointResponseDto;
import com.komencash.backend.dto.student.StudentFindFinancialInfoDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.*;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.request_history.SalaryPaymentRequestHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BankService {

    @Autowired
    AccountHistoryRepository accountHistoryRepository;

    @Autowired
    FinancialProuctRepository financialProuctRepository;

    @Autowired
    FinancialProductDetailRepository financialProductDetailRepository;

    @Autowired
    FinancialProductHistoryRepository financialProductHistoryRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    CreditService creditService;

    @Autowired
    SalaryPaymentRequestHistoryRepository salaryPaymentRequestHistoryRepository;


    public List<AccountFindResponseDto> findAccountByGroupId(int groupId) {
        List<AccountFindResponseDto> accountFindResponseDtos = new ArrayList<>();

        List<Student> students = studentRepository.findByJob_Group_Id(groupId);
        students.forEach(student ->{
            List<AccountHistoryFindResponseDto> accountHistoryFindResponseDtos = new ArrayList<>();

            List<AccountHistory> accountHistories = accountHistoryRepository.findByStudent_Id(student.getId());
            accountHistories.forEach(accountHistory ->
                    accountHistoryFindResponseDtos.add(
                            new AccountHistoryFindResponseDto(
                                    accountHistory.getBalance(),
                                    accountHistory.getBalanceChange(),
                                    accountHistory.getContent())));
            accountFindResponseDtos.add(new AccountFindResponseDto(student.getId(), student.getNickname(), accountHistoryFindResponseDtos));
        });
        return accountFindResponseDtos;
    }


    public int findBalance(int studentId){
        List<AccountHistory> accountHistories = accountHistoryRepository.findByStudent_Id(studentId);
        int balance = accountHistories.size() == 0 ? 0 : accountHistories.get(accountHistories.size() - 1).getBalance();
        return balance;
    }


    public int addFinancialProduct(FinancialProductAddUpdateRequestDto financialProductAddUpdateRequestDto) {
        Group group = groupRepository.findById(financialProductAddUpdateRequestDto.getGroupId()).orElse(null);
        if (group == null) return 0;

        int productId = financialProuctRepository.save(new FinancialProduct(financialProductAddUpdateRequestDto.getName(), group)).getId();
        return productId;
    }


    public boolean addFinancialProductDetail(int financialProductId, FinancialProductDetailAddUpdateRequestDto financialProductDetailAddUpdateRequestDto) {
        FinancialProduct financialProduct = financialProuctRepository.findById(financialProductId).orElse(null);
        if(financialProduct == null) return false;

        financialProductDetailRepository.save(new FinancialProductDetail(financialProductDetailAddUpdateRequestDto, financialProduct));
        return true;
    }


    public List<FinancialProductFindResponseDto> findFinancialProductList(int groupId) {
        List<FinancialProductFindResponseDto> financialProductFindResponseDtos = new ArrayList<>();

        List<FinancialProduct> financialProducts = financialProuctRepository.findByGroup_Id(groupId);
        financialProducts.forEach(financialProduct -> {
            financialProductFindResponseDtos.add(
                    new FinancialProductFindResponseDto(financialProduct.getId(), financialProduct.getName())
            );
        });

        return financialProductFindResponseDtos;
    }


    public boolean deleteFinancialProduct(int productId) {
        FinancialProduct financialProduct = financialProuctRepository.findById(productId).orElse(null);
        if(financialProduct == null) return false;

        financialProuctRepository.delete(financialProduct);
        return true;
    }


    public boolean addSalaryPaymentRequest(int groupId){
        List<Student> students = studentRepository.findByJob_Group_Id(groupId);

        students.forEach(student -> {
            Job job = student.getJob();
            int salary = job.getSalary();
            int taxLoss = (int) (job.getGroup().getTaxRate() * salary);
            SalaryPaymentRequestHistory salaryPaymentRequestHistory = new SalaryPaymentRequestHistory(salary, taxLoss, student);
            salaryPaymentRequestHistoryRepository.save(salaryPaymentRequestHistory);
        });

        return true;
    }


    public boolean addAccountHistory(AccountHistoryAddUpdateRequestDto accountHistoryAddUpdateRequestDto) {
        List<AccountHistory> accountHistories = accountHistoryRepository.findAll();
        int preBalance = accountHistories.size() == 0 ? 0 : accountHistories.get(accountHistories.size() - 1).getBalance();

        int balanceChange = accountHistoryAddUpdateRequestDto.getBalanceChange();
        int balance = preBalance + balanceChange;
        String content = accountHistoryAddUpdateRequestDto.getContent();
        Student student = studentRepository.findById(accountHistoryAddUpdateRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        AccountHistory accountHistory = new AccountHistory(balanceChange, balance, content, student);
        accountHistoryRepository.save(accountHistory);
        return true;
    }


    public FinancialProductFindDetailResponseDto findFinancialProductListDetail(int productId){
        FinancialProduct financialProduct = financialProuctRepository.findById(productId).orElse(null);
        if(financialProduct == null) return null;


        List<FinancialProductDetailFindByProductDto> financialProductDetailResponse = new ArrayList<>();
        List<FinancialProductDetail> financialProductDetails = financialProductDetailRepository.findByFinancialProduct_IdOrderByCreditGrade(productId);
        financialProductDetails.forEach(financialProductDetail ->
                financialProductDetailResponse.add(new FinancialProductDetailFindByProductDto(financialProductDetail)));


        List<StudentFindFinancialInfoDto> studentFindFinancialInfoDtos = new ArrayList<>();
        List<FinancialProductHistory> financialProductHistories =
                financialProductHistoryRepository.findByFinancialProductDetail_FinancialProduct_Id(productId);
        financialProductHistories.forEach(financialProductHistory -> {
            if(financialProductHistory.getStatus().equals(Status.deposit)) {
                Student student = financialProductHistory.getStudent();

                CreditFindGradeAndPointResponseDto creditFindGradeAndPointResponseDto = creditService.findCreditGrade(student.getId());
                int grade = creditFindGradeAndPointResponseDto.getCreditGrade();
                Date startDate = financialProductHistory.getStartDate();
                int principal = financialProductHistory.getPrincipal();

                studentFindFinancialInfoDtos.add(new StudentFindFinancialInfoDto(student.getId(), student.getNickname(), grade, startDate, principal));
            }
        });

        return new FinancialProductFindDetailResponseDto(financialProduct, financialProductDetailResponse, studentFindFinancialInfoDtos);
    }
    // Scheduler 함수
    public void updateFinancialProductHistory(){
        Date currentDate = new Date();
        List<FinancialProductHistory> list = financialProductHistoryRepository.findAll();
        list.forEach(s ->{
            // 상태가 예금 상태이고, 현재 시간이 end Date 보다 최신이면
            // 내 통장에 + 이율 해서 넣기
            if (s.getStatus()== Status.deposit && currentDate.compareTo(s.getEndDate()) == 1) {
                //상태를 만료로 저장
                FinancialProductHistory history = new FinancialProductHistory(
                        s.getId(),
                        s.getPrincipal(),
                        s.getStartDate(),
                        s.getEndDate(),
                        Status.maturity,
                        s.getStudent(),
                        s.getFinancialProductDetail()
                );
                financialProductHistoryRepository.save(history);

                // 내 통장에 추가
                List<AccountHistory> accountHistory = accountHistoryRepository.findByStudent_Id(s.getStudent().getId());
                AccountHistory lastAccount= accountHistory.get(accountHistory.size()-1);
                int lastBalance = (int) (lastAccount.getBalance()+(s.getPrincipal()* s.getFinancialProductDetail().getRate() * 0.01));
                int balanceChange = (int)(s.getPrincipal()* s.getFinancialProductDetail().getRate());
                AccountHistory newHistory = new AccountHistory(balanceChange, lastBalance, "예금 만기, 이율+"+s.getFinancialProductDetail().getRate(), s.getStudent());
                accountHistoryRepository.save(newHistory);
            }
        });
    }
    public List<FinancialProductHistoryFindResponseDto> findFinancialProductHistoryByStudentId(int studentId){
        List<FinancialProductHistoryFindResponseDto> financialProductHistoryFindResponsDtos = new ArrayList<>();

        List<FinancialProductHistory> financialProductHistories = financialProductHistoryRepository.findByStudent_Id(studentId);
        financialProductHistories.forEach(financialProductHistory ->
                financialProductHistoryFindResponsDtos.add(new FinancialProductHistoryFindResponseDto(financialProductHistory, financialProductHistory.getFinancialProductDetail().getRate())));

        return financialProductHistoryFindResponsDtos;
    }


    public List<FinancialProductApplyListResponseDto> findFinancialProductApplyList(int productId) {
        List<FinancialProductHistory> financialProductHistories =financialProductHistoryRepository.findByFinancialProductDetail_FinancialProduct_Id(productId);
        List<FinancialProductApplyListResponseDto> financialProductApplyListResponseDtos = new ArrayList<>();
        financialProductHistories.forEach(financialProductHistory -> {
            Status status = financialProductHistory.getStatus();
            if(status == Status.before_deposit || status == Status.before_termination){
                financialProductApplyListResponseDtos.add(
                        new FinancialProductApplyListResponseDto(
                                financialProductHistory.getId(),
                                financialProductHistory.getStudent().getNickname(),
                                financialProductHistory.getStatus()));
            }
        });
        return financialProductApplyListResponseDtos;
    }



    public boolean updateFinancialProduct (FinancialProductAddUpdateRequestDto financialProductAddUpdateRequestDto){
        FinancialProduct financialProduct = financialProuctRepository.findById(financialProductAddUpdateRequestDto.getId()).orElse(null);
        if(financialProduct == null) return false;

        financialProduct.updateName(financialProductAddUpdateRequestDto.getName());
        financialProuctRepository.save(financialProduct);
        return true;
    }


    public boolean updateFinancialProductDetail (FinancialProductDetailUpdateRequestDto financialProductDetailUpdateRequestDto) {
        FinancialProductDetail financialProductDetail = financialProductDetailRepository.findById(financialProductDetailUpdateRequestDto.getId()).orElse(null);
        if(financialProductDetail == null) return false;

        financialProductDetail.updateFinancialProductDetail(financialProductDetailUpdateRequestDto);
        financialProductDetailRepository.save(financialProductDetail);
        return true;
    }

    public boolean updateFinancialStatusAccept(int financialProductHistoryId) {
        FinancialProductHistory history = financialProductHistoryRepository.findById(financialProductHistoryId).orElse(null);
        if(history.getStatus() == Status.before_deposit) {
            history.acceptRequest(Status.deposit);
            String content = "금융 상품[" + history.getFinancialProductDetail().getFinancialProduct().getName() + "] 가입";
            addAccountHistory(new AccountHistoryAddUpdateRequestDto(history.getStudent().getId(), -history.getPrincipal(), content));
            financialProductHistoryRepository.save(history);
        }
        else if(history.getStatus() == Status.before_termination){
            String content = "금융 상품[" + history.getFinancialProductDetail().getFinancialProduct().getName() + "] 중도 해지";
            addAccountHistory(new AccountHistoryAddUpdateRequestDto(history.getStudent().getId(), history.getPrincipal(), content));
            financialProductHistoryRepository.delete(history);
        }
        
        return true;
    }

}
