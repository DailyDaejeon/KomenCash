package com.komencash.backend.dto.bank;

import com.komencash.backend.entity.financial.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialProductApplyDto {
    private int id;
    private String nickname;
    private Status status;

}
