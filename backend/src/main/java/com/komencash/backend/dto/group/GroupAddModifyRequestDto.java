package com.komencash.backend.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupAddModifyRequestDto {
    private int id;
    private String code;
    private String name;
    private String monetaryUnitName;
    private double taxRate;
    private double inflationRate;
    private int teacherId;
}
