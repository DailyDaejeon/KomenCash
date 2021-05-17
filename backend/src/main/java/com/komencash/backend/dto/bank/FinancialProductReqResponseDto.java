package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.financial.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductReqResponseDto {

    private int productId;
    private String productName;
    private int historyId;
    private int principal;
    private double rate;
    private Date startDate;
    private Date endDate;
    private Status status;
    private int studentId;
    private String studentNickname;
    private int studentCreditGrade;

}
