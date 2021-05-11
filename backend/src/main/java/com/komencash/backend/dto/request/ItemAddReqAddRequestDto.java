package com.komencash.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAddReqAddRequestDto {

    private String itemName;
    private String content;
    private int studentId;
}
