package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountResponseDto;
import com.komencash.backend.entity.bank.AccountHistory;
import com.komencash.backend.entity.financial.FinancialProduct;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.AccountHistoryRepository;
import com.komencash.backend.repository.FinancialProuctRepository;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
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
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    public List<AccountResponseDto> getAccounts(int groupId) {
        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);
        List<AccountResponseDto> accounts = new ArrayList<>();
        students.forEach(s ->{
            List<AccountHistory> history = accountHistoryRepository.findAllByStudent_Id(s.getId());
            AccountResponseDto dto = new AccountResponseDto(s.getId(), s.getNickname(), history);
            accounts.add(dto);
        });
        return accounts;
    }

    public void createFinancialProduct(int groupId, String name) {
        Group g = groupRepository.findById(groupId).orElse(null);
        FinancialProduct financialProduct = new FinancialProduct(name, g);
        financialProuctRepository.save(financialProduct);
    }
}
