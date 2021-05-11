package com.komencash.backend.dto.credit;

import com.komencash.backend.entity.credit.CreditGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditGradeFindListResponseDto {

    private int id;
    private int creditGrade;
    private int maxPoint;
    private int minPoint;

    public CreditGradeFindListResponseDto(CreditGrade creditGrade){
        this.id = creditGrade.getId();
        this.creditGrade = creditGrade.getGrade();
        this.maxPoint = creditGrade.getMaxPoint();
        this.minPoint = creditGrade.getMinPoint();
    }
}
