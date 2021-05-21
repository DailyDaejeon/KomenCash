package com.komencash.backend.entity.group;

import com.komencash.backend.dto.group.GroupAddUpdateRequestDto;
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


    public Group(GroupAddUpdateRequestDto groupAddUpdateRequestDto, Teacher teacher) {
        this.code = teacher.getNickname() + teacher.getId() + groupAddUpdateRequestDto.getName();
        this.name = groupAddUpdateRequestDto.getName();
        this.monetaryUnitName = groupAddUpdateRequestDto.getMonetaryUnitName();
        this.taxRate = groupAddUpdateRequestDto.getTaxRate();
        this.inflationRate = groupAddUpdateRequestDto.getInflationRate();
        this.teacher = teacher;
    }


    public void updateGroup(GroupAddUpdateRequestDto groupAddUpdateRequestDto){
        this.name = groupAddUpdateRequestDto.getName();
        this.monetaryUnitName = groupAddUpdateRequestDto.getMonetaryUnitName();
        this.taxRate = groupAddUpdateRequestDto.getTaxRate();
        this.inflationRate = groupAddUpdateRequestDto.getInflationRate();
    }

    public void updateTaxRate(double taxRate){ this.taxRate = taxRate; }

    public void updateInflationRate(double inflationRate){ this.inflationRate = inflationRate; }


}
