package com.komencash.backend.dto.student;

import com.komencash.backend.dto.bank.AccountHistoryDto;
import com.komencash.backend.entity.bank.AccountHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentState {
    //내아이디
    //내가 속한 그룹명
    //내가 가진 돈
    //나의 직업
    //내 자격증

    private int studentId;
    private String nickname;
    private int balance;
    private String jobName;
    private int jobSalary;
    private List<String> certificateName;



}
