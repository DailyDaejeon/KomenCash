package com.komencash.backend.dto;

import com.komencash.backend.entity.Group;
import com.komencash.backend.entity.Teacher;
import com.komencash.backend.repository.GroupRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class GroupResponseDto {
//    private int id;
//    private String code;
//    private String name;
//    private String monetary_unit_name;
//    private double tax_rate;
//    private int tax;
    private List<Group> group;
    public GroupResponseDto(List<Group> group){
        this.group = group;
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
