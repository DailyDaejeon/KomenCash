package com.komencash.backend.dto.student;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentJobRequestDto {
    private String title;
    private String content;
    private int jobId;
    private int studentId;

}
