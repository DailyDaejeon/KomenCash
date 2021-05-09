package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDetailFindResponseDto {

    private int id;
    private String title;
    private String content;
    private int studentId;
    private String studentNickname;
    private List<VoteItemFindResponseDto> voteItemResultResponses;
    private List<VoteAttendFindResponseDto> voteAttendFindResponsDtos;


    public VoteDetailFindResponseDto(VoteFindResponseDto voteFindResponseDto, List<VoteAttendFindResponseDto> voteAttendFindResponsDtos) {
        this.id = voteFindResponseDto.getId();
        this.title = voteFindResponseDto.getTitle();
        this.content = voteFindResponseDto.getContent();
        this.studentId = voteFindResponseDto.getStudentId();
        this.studentNickname = voteFindResponseDto.getStudentNickname();
        this.voteItemResultResponses = voteFindResponseDto.getVoteItemResultResponses();
        this.voteAttendFindResponsDtos = voteAttendFindResponsDtos;
    }
}
