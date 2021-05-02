package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountHistoryDto;
import com.komencash.backend.dto.bank.AccountResponseDto;
import com.komencash.backend.dto.bank.FinancialProductDetailResponseDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.FinancialProduct;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    @Autowired
    AccountHistoryRepository accountHistoryRepository;

    @Autowired
    FinancialProuctRepository financialProuctRepository;

    @Autowired
    FinancialProductDetailRepository financialProductDetailRepository;

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

    public void createFinancialProductDetail(int financialProductId, FinancialProductDetailResponseDto dto) {
        FinancialProduct financialProduct = financialProuctRepository.findById(financialProductId).orElse(null);
        FinancialProductDetail financialProductDetail = new FinancialProductDetail(dto,financialProduct);
        financialProductDetailRepository.save(financialProductDetail);
    }


    public List<FinancialProductDetailResponseDto> getFinancialProductDetail(int groupId) {
        List<FinancialProductDetail> f = financialProductDetailRepository.findByFinancialProduct_Group_Id(groupId);
        List<FinancialProductDetailResponseDto> result = new ArrayList<>();
        f.forEach(s -> {
            result.add(new FinancialProductDetailResponseDto(s.getId(), s.getPeriod(), s.getCreditGrade(), s.getRate(), s.getFinancialProduct()));
        });
        return result;

    }

    public void deleteFinancialProduct(int productId) {
        financialProuctRepository.deleteById(productId);
    }
}
