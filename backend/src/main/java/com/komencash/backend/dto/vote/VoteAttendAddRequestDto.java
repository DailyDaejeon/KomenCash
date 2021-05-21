package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteAttendAddRequestDto {

    private int voteItemNum;
    private int voteId;
    private int studentId;
}
