package com.komencash.backend.service.unity;

import com.komencash.backend.dto.bank.FinancialProductAddDto;
import com.komencash.backend.entity.financial.FinancialProductDetail;
import com.komencash.backend.entity.financial.FinancialProductHistory;
import com.komencash.backend.entity.financial.Status;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.FinancialProductDetailRepository;
import com.komencash.backend.repository.FinancialProductHistoryRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UBankService {
    @Autowired
    FinancialProductHistoryRepository financialProductHistoryRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FinancialProductDetailRepository financialProductDetailRepository;
    public boolean productRegist(FinancialProductAddDto request) {
        FinancialProductDetail financialProductDetail = financialProductDetailRepository.findById(request.getFinancialProductDetailId()).orElse(null);

        Student student = studentRepository.findById(request.getStudentId()).orElse(null);

        Date date= new Date();
        long time = date.getTime();
        Timestamp startDate = new Timestamp(time);
        Timestamp endDate = new Timestamp(time + financialProductDetail.getPeriod() * 24 * 60 * 60 * 1000);
        System.out.println("기간 : "+startDate + "~" +endDate);

        FinancialProductHistory financialProductHistory = new FinancialProductHistory(request.getPrincipal(), startDate, endDate, Status.deposit, student, financialProductDetail);
        financialProductHistoryRepository.save(financialProductHistory);

        return true;
    }
}
