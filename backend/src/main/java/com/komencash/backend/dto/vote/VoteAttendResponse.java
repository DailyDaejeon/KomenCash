package com.komencash.backend.dto.vote;

import com.komencash.backend.entity.vote.VoteAttend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteAttendResponse {
    private int choiceItenNum;
    private int studentId;
    private String studentNickname;

    public VoteAttendResponse(VoteAttend voteAttend) {
        this.choiceItenNum = voteAttend.getChoiceItemNum();
        this.studentId = voteAttend.getStudent().getId();
        this.studentNickname = voteAttend.getStudent().getNickname();
    }
}
