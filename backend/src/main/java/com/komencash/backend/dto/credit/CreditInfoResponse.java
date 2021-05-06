package com.komencash.backend.dto.credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditInfoResponse {

    private int creditGrade;
    private int point;
}
