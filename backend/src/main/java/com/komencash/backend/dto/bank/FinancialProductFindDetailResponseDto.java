package com.komencash.backend.dto.bank;


import com.komencash.backend.dto.student.StudentFindFinancialInfoDto;
import com.komencash.backend.entity.financial.FinancialProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductFindDetailResponseDto {

    private int id;
    private String name;
    private List<FinancialProductDetailFindByProductDto> financialProductDetailResponse;
    private List<StudentFindFinancialInfoDto> studentFindFinancialInfoResponse;

    public FinancialProductFindDetailResponseDto(FinancialProduct financialProduct, List<FinancialProductDetailFindByProductDto> financialProductDetailResponse,
                                                 List<StudentFindFinancialInfoDto> studentFindFinancialInfoResponse){
        this.id = financialProduct.getId();
        this.name = financialProduct.getName();
        this.financialProductDetailResponse = financialProductDetailResponse;
        this.studentFindFinancialInfoResponse = studentFindFinancialInfoResponse;
    }
}
