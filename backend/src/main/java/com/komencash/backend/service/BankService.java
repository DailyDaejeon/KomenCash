package com.komencash.backend.service;

import com.komencash.backend.dto.bank.*;
import com.komencash.backend.dto.student.StudentInfoResponse;
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
    FinancialProductPurchaseRepository financialProductPurchaseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;


    public List<AccountResponseDto> getAccounts(int groupId) {
        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);
        List<AccountResponseDto> accounts = new ArrayList<>();
        students.forEach(s ->{
            List<AccountHistory> history = accountHistoryRepository.findAllByStudent_Id(s.getId());
            List<AccountHistoryDto> historyList = new ArrayList<>();
            history.forEach(ss -> {
                AccountHistoryDto a = new AccountHistoryDto(ss.getBalanceChange(), ss.getContent());
                historyList.add(a);
            });
            AccountResponseDto dto = new AccountResponseDto(s.getId(), s.getNickname(), historyList);
            accounts.add(dto);
        });
        return accounts;
    }

    public void createFinancialProduct(int groupId, String name) {
        Group g = groupRepository.findById(groupId).orElse(null);
        FinancialProduct financialProduct = new FinancialProduct(name, g);
        financialProuctRepository.save(financialProduct);
    }

    public void createFinancialProductDetail(int financialProductId, FinancialProductDetailRequest dto) {
        FinancialProduct financialProduct = financialProuctRepository.findById(financialProductId).orElse(null);
        FinancialProductDetail financialProductDetail = new FinancialProductDetail(dto,financialProduct);
        financialProductDetailRepository.save(financialProductDetail);
    }


    public List<FinancialProductDetailRequest> getFinancialProductDetail(int groupId) {
        List<FinancialProductDetail> f = financialProductDetailRepository.findByFinancialProduct_Group_Id(groupId);
        List<FinancialProductDetailRequest> result = new ArrayList<>();
        f.forEach(s -> {
            result.add(new FinancialProductDetailRequest(s.getId(), s.getPeriod(), s.getCreditGrade(), s.getRate(), s.getFinancialProduct()));
        });
        return result;

    }

    public void deleteFinancialProduct(int productId) {
        financialProuctRepository.deleteById(productId);
    }


    public boolean insertAccountHistory(AccountHistoryInsertUpdateRequest accountHistoryInsertUpdateRequest) {
        List<AccountHistory> accountHistories = accountHistoryRepository.findAll();
        int preBalance = accountHistories.size() == 0 ? 0 : accountHistories.get(accountHistories.size() - 1).getBalance();

        int balance_change = accountHistoryInsertUpdateRequest.getBalance_change();
        int balance = preBalance + balance_change;
        String content = accountHistoryInsertUpdateRequest.getContent();
        Student student = studentRepository.findById(accountHistoryInsertUpdateRequest.getStudentId()).orElse(null);
        if(student == null) return false;

        AccountHistory accountHistory = new AccountHistory(balance_change, balance, content, student);
        accountHistoryRepository.save(accountHistory);
        return true;
    }


    public FinancialProductResponse getFinancialProduct(int productId){

        FinancialProduct financialProduct = financialProuctRepository.findById(productId).orElse(null);
        if(financialProduct == null) return null;

        List<FinancialProductDetailResponse> financialProductDetailResponses = new ArrayList<>();
        List<FinancialProductDetail> financialProductDetails = financialProductDetailRepository.findByFinancialProduct_Id(productId);
        for(FinancialProductDetail financialProductDetail : financialProductDetails)
            financialProductDetailResponses.add(new FinancialProductDetailResponse(financialProductDetail));

        List<StudentInfoResponse> studentInfoResponses = new ArrayList<>();
        List<FinancialProductPurchase> financialProductPurchases =
                financialProductPurchaseRepository.findByFinancialProductHistory_FinancialProduct_Id(productId);
        for(FinancialProductPurchase financialProductPurchase : financialProductPurchases) {
            if(!financialProductPurchase.getFinancialProductHistory().getStatus().equals(Status.deposit)) continue;
            Student student = financialProductPurchase.getStudent();
            studentInfoResponses.add(new StudentInfoResponse(student.getId(), student.getNickname()));
        }

        return new FinancialProductResponse(financialProduct, financialProductDetailResponses, studentInfoResponses);
    }

    public boolean updateFinancialProduct (FinancialProductUpdateRequest financialProductUpdateRequest){
        FinancialProduct financialProduct = financialProuctRepository.findById(financialProductUpdateRequest.getId()).orElse(null);
        if(financialProduct == null) return false;

        financialProduct.updateName(financialProductUpdateRequest.getName());
        financialProuctRepository.save(financialProduct);
        return true;
    }
}
