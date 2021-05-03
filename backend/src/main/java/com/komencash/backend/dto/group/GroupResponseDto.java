package com.komencash.backend.dto.group;

import com.komencash.backend.entity.group.Group;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GroupResponseDto {
//    private int id;
//    private String code;
//    private String name;
//    private String monetary_unit_name;
//    private double tax_rate;
//    private int tax;
    private Group group;
    private int studentCnt;

    public GroupResponseDto(Group group, int studentCnt){
        this.group = group;
        this.studentCnt = studentCnt;
    }
//    public GroupResponseDto(Group group){
//        this.id = group.getId();
//        this.code = group.getCode();
//        this.name = group.getName();
//        this.monetary_unit_name = group.getMonetary_unit_name();
//        this.tax_rate = group.getTax_rate();
//        this.tax = group.getTax();
//    }

}
