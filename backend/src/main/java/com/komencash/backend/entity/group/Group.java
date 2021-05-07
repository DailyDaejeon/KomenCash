package com.komencash.backend.entity.group;

import com.komencash.backend.dto.group.GroupAddModifyRequestDto;
import com.komencash.backend.entity.teacher.Teacher;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`group`")
public class Group{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name =  "monetary_unit_name")
    private String monetaryUnitName;

    @Column(name = "tax_rate")
    private double taxRate;

    @Column(name = "inflation_rate")
    private double inflationRate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    public Group(GroupAddModifyRequestDto groupAddModifyRequestDto, Teacher teacher) {
        this.code = teacher.getNickname() + teacher.getId() + groupAddModifyRequestDto.getName();
        this.name = groupAddModifyRequestDto.getName();
        this.monetaryUnitName = groupAddModifyRequestDto.getMonetaryUnitName();
        this.taxRate = groupAddModifyRequestDto.getTaxRate();
        this.inflationRate = groupAddModifyRequestDto.getInflationRate();
        this.teacher = teacher;
    }


    public void updateGroup(GroupAddModifyRequestDto groupAddModifyRequestDto){
        this.name = groupAddModifyRequestDto.getName();
        this.monetaryUnitName = groupAddModifyRequestDto.getMonetaryUnitName();
        this.taxRate = groupAddModifyRequestDto.getTaxRate();
        this.inflationRate = groupAddModifyRequestDto.getInflationRate();
    }

    public void updateTaxRate(double taxRate){ this.taxRate = taxRate; }

    public void updateInflationRate(double inflationRate){ this.inflationRate = inflationRate; }


}
