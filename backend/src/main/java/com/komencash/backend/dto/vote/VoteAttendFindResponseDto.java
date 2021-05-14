package com.komencash.backend.dto.vote;

import com.komencash.backend.entity.vote.VoteAttend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteAttendFindResponseDto {
    private int choiceItemNum;
    private String choiceItemContent;
    private int studentId;
    private String studentNickname;

    public VoteAttendFindResponseDto(VoteAttend voteAttend, String content) {
        this.choiceItemNum = voteAttend.getChoiceItemNum();
        this.studentId = voteAttend.getStudent().getId();
        this.studentNickname = voteAttend.getStudent().getNickname();
        this.choiceItemContent = content;
    }
}
