package com.komencash.backend.dto.job;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAddRequestAcceptRequest {

    private int id;
    private Accept accept;
}
