package com.komencash.backend.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartTimeAddUpdateRequestDto {

    private int id;
    private String name;
    private String role;
    private int salary;
    private int groupId;

}
