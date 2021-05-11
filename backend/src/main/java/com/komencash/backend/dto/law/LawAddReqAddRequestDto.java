package com.komencash.backend.dto.law;

import com.komencash.backend.entity.request_history.Accept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LawAddReqAddRequestDto {

    private String title;
    private String content;
    private int voteId;
    private int studentId;
}
