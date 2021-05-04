package com.komencash.backend.dto.tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxDetailResponse {

    private int tax;
    private List<TaxHistoryResponse> taxHistoryResponses;

}
