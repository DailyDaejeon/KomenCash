package com.komencash.backend.dto.credit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditHistoryAddRequestDto {

    private String content;
    private int pointChange;
    private int studentId;

}
