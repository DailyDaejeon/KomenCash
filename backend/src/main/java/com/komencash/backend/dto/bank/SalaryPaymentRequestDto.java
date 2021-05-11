package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryPaymentRequestDto {
    private int id;
    private int salary;
    private int tax_loss;
    private Accept accept;
    private int student_id;

    public SalaryPaymentRequestDto(int id, int student_id) {
        this.id = id;
        this.student_id = student_id;
    }

    public SalaryPaymentRequestDto(int id) {
        this.id = id;
    }
}

