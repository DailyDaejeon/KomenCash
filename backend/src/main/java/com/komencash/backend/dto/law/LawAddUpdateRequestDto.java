package com.komencash.backend.dto.law;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawAddUpdateRequestDto {

    private int id;
    private String lawType;
    private int article;
    private int paragraph;
    private String content;
    private int groupId;

    public LawAddUpdateRequestDto(String lawType, int article, int paragraph, String content, int groupId){
        this.lawType = lawType;
        this.article = article;
        this.paragraph = paragraph;
        this.content = content;
        this.groupId = groupId;
    }
}
