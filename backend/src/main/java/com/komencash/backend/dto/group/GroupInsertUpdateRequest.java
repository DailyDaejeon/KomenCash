package com.komencash.backend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInsertUpdateRequest {
    private int id;
    private String code;
    private String name;
    private String monetary_unit_name;
    private double tax_rate;
    private int teacher_id;
}
