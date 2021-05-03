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
    private String monetaryUnitName;
    private double taxRate;
    private int teacherId;

    public GroupInsertUpdateRequest(String name, String monetaryUnitName, double taxRate){
        this.name = name;
        this.monetaryUnitName = monetaryUnitName;
        this.taxRate = taxRate;

    }
}
