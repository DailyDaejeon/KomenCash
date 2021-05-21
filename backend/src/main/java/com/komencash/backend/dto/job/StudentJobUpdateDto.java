package com.komencash.backend.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentJobUpdateDto {
    int jobId;
    int studentId;

}
