package com.komencash.backend.service;

import com.komencash.backend.dto.credit.CreditFindGradeAndPointResponseDto;
import com.komencash.backend.dto.credit.CreditFindResponseDto;
import com.komencash.backend.dto.statistic.StatisticListFindResponseDto;
import com.komencash.backend.entity.credit.CreditGrade;
import com.komencash.backend.entity.credit.CreditHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.CreditGradeRepository;
import com.komencash.backend.repository.CreditHistoryRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditService {

    @Autowired
    CreditHistoryRepository creditHistoryRepository;
    @Autowired
    CreditGradeRepository creditGradeRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    StatisticService statisticService;

    public List<CreditFindResponseDto>  findCreditList(int groupId){

        List<CreditFindResponseDto> creditFindResponsDtos = new ArrayList<>();

        List<Student> students = studentRepository.findByJob_Group_Id(groupId);
        students.forEach(student -> {
            CreditFindGradeAndPointResponseDto creditFindGradeAndPointResponseDto = findCreditGrade(student.getId());
            int grade = creditFindGradeAndPointResponseDto.getCreditGrade();
            int point = creditFindGradeAndPointResponseDto.getPoint();
            creditFindResponsDtos.add(new CreditFindResponseDto(student, grade, point));
        });

        return creditFindResponsDtos;
    }

    public CreditFindGradeAndPointResponseDto findCreditGrade(int studentId){
        List<CreditHistory> creditHistories = creditHistoryRepository.findByStudent_Id(studentId);
        CreditHistory creditHistory = creditHistories.size() == 0 ? new CreditHistory() : creditHistories.get(creditHistories.size() - 1);

        int point = creditHistory.getPoint();
        CreditGrade creditGrade = creditGradeRepository.findByPoint(point);
        int grade =creditGrade.getGrade();

        return new CreditFindGradeAndPointResponseDto(grade, point);
    }


    public List<StatisticListFindResponseDto> findSubmittedStatisticList(int groupId){
        return statisticService.findStatisticListList(groupId, true);
    }
}
