package com.komencash.backend.service;

import com.komencash.backend.dto.credit.CreditSelectResponse;
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

    public List<CreditSelectResponse>  findCreditList(int groupId){
        List<CreditSelectResponse> creditSelectResponses = new ArrayList<>();
        List<Student> students = studentRepository.findAllByJob_Group_Id(groupId);

        for(Student student : students) {
            List<CreditHistory> creditHistories = creditHistoryRepository.findByStudent_Id(student.getId());
            CreditHistory creditHistory = creditHistories.size() == 0 ? new CreditHistory() : creditHistories.get(creditHistories.size() - 1);

            int point = creditHistory.getPoint();
            CreditGrade creditGrade = creditGradeRepository.findByPoint(point);

            creditSelectResponses.add(new CreditSelectResponse(student, creditGrade.getGrade(), point));
        }

        return creditSelectResponses;
    }
}
