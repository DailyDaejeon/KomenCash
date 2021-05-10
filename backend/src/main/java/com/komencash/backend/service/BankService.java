package com.komencash.backend.service;

import com.komencash.backend.dto.bank.*;
import com.komencash.backend.dto.credit.CreditFindGradeAndPointResponseDto;
import com.komencash.backend.dto.student.StudentFindFinancialInfoDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.*;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public List<AccountFindResponseDto> findAccountByGroupId(int groupId) {
        List<AccountFindResponseDto> accountFindResponseDtos = new ArrayList<>();

        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);
        students.forEach(student ->{

            List<AccountHistoryFindResponseDto> accountHistoryFindResponseDtos = new ArrayList<>();

            List<AccountHistory> accountHistories = accountHistoryRepository.findAllByStudent_Id(student.getId());
            accountHistories.forEach(accountHistory ->
                    accountHistoryFindResponseDtos.add(
                            new AccountHistoryFindResponseDto(
                                    accountHistory.getBalance(),
                                    accountHistory.getBalanceChange(),
                                    accountHistory.getContent()
                            )
                    )
            );

            accountFindResponseDtos.add(new AccountFindResponseDto(student.getId(), student.getNickname(), accountHistoryFindResponseDtos));
        });

        return accountFindResponseDtos;
    }


    public int getBalance(int studentId){
        List<AccountHistory> accountHistories = accountHistoryRepository.findAllByStudent_Id(studentId);
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


    public List<FinancialProductDetailAddUpdateRequestDto> findFinancialProductDetailList(int groupId) {
        List<FinancialProductDetailAddUpdateRequestDto> financialProductDetailAddUpdateRequestDtos = new ArrayList<>();

        List<FinancialProductDetail> financialProductDetails = financialProductDetailRepository.findByFinancialProduct_Group_Id(groupId);
        financialProductDetails.forEach(financialProductDetail -> {
            financialProductDetailAddUpdateRequestDtos.add(
                    new FinancialProductDetailAddUpdateRequestDto(
                            financialProductDetail.getId(),
                            financialProductDetail.getPeriod(),
                            financialProductDetail.getCreditGrade(),
                            financialProductDetail.getRate(),
                            financialProductDetail.getFinancialProduct()));
        });

        return financialProductDetailAddUpdateRequestDtos;
    }


    public boolean deleteFinancialProduct(int productId) {
        FinancialProduct financialProduct = financialProuctRepository.findById(productId).orElse(null);
        if(financialProduct == null) return false;

        financialProuctRepository.delete(financialProduct);
        return true;
    }


    public boolean insertAccountHistory(AccountHistoryAddUpdateRequestDto accountHistoryAddUpdateRequestDto) {
        List<AccountHistory> accountHistories = accountHistoryRepository.findAll();
        int preBalance = accountHistories.size() == 0 ? 0 : accountHistories.get(accountHistories.size() - 1).getBalance();

        int balance_change = accountHistoryAddUpdateRequestDto.getBalance_change();
        int balance = preBalance + balance_change;
        String content = accountHistoryAddUpdateRequestDto.getContent();
        Student student = studentRepository.findById(accountHistoryAddUpdateRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        AccountHistory accountHistory = new AccountHistory(balance_change, balance, content, student);
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
                int point = creditFindGradeAndPointResponseDto.getPoint();

                studentFindFinancialInfoDtos.add(new StudentFindFinancialInfoDto(student.getId(), student.getNickname(), grade, point));
            }
        });

        return new FinancialProductFindDetailResponseDto(financialProduct, financialProductDetailResponse, studentFindFinancialInfoDtos);
    }


    public List<FinancialProductHistoryFindResponseDto> findFinancialProductHistoryByStudentId(int studentId){
        List<FinancialProductHistoryFindResponseDto> financialProductHistoryFindResponsDtos = new ArrayList<>();

        List<FinancialProductHistory> financialProductHistories = financialProductHistoryRepository.findByStudent_Id(studentId);
        financialProductHistories.forEach(financialProductHistory ->
                financialProductHistoryFindResponsDtos.add(new FinancialProductHistoryFindResponseDto(financialProductHistory)));

        return financialProductHistoryFindResponsDtos;
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
        history.acceptDeposit();
        financialProductHistoryRepository.save(history);
        return true;
    }
}
