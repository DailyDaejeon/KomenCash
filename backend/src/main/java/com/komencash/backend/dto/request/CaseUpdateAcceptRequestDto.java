package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseUpdateAcceptRequestDto {

    private int caseId;
    private Accept accept;

}
