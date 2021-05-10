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
}
