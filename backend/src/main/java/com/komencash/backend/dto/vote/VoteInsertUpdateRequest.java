package com.komencash.backend.dto.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteInsertUpdateRequest {

    private int id;
    private String title;
    private String content;
    private int StudentId;
    private List<VoteItemInsertUpdateRequest> voteItemList;
}
