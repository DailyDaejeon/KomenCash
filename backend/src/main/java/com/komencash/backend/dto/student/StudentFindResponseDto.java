package com.komencash.backend.dto.student;

import com.komencash.backend.entity.job.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFindResponseDto {
    private int id;
    private String nickname;
    private Job job;

}

