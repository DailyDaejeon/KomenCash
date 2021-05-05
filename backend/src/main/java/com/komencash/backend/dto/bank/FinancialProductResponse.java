package com.komencash.backend.dto.bank;


import com.komencash.backend.dto.student.StudentInfoResponse;
import com.komencash.backend.entity.financial.FinancialProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductResponse {

    private int id;
    private String name;
    private List<FinancialProductDetailResponse> financialProductDetailResponses;
    private List<StudentInfoResponse> studentInfoResponses;

    public FinancialProductResponse(FinancialProduct financialProduct, List<FinancialProductDetailResponse> financialProductDetailResponses,
                                    List<StudentInfoResponse> studentInfoResponses){
        this.id = financialProduct.getId();
        this.name = financialProduct.getName();
        this.financialProductDetailResponses = financialProductDetailResponses;
        this.studentInfoResponses = studentInfoResponses;
    }
}
