package com.komencash.backend.entity.group;

import com.komencash.backend.dto.group.GroupInsertUpdateRequest;
import com.komencash.backend.entity.teacher.Teacher;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
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
    private String monetary_unit_name;

    @Column(name = "tax_rate")
    private double tax_rate;

    @Column(name = "tax")
    private int tax;

    @Column(name = "inflation_rate")
    private double inflationRate;

    // 여기부분 잘 모름
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Group(GroupInsertUpdateRequest groupInsertUpdateRequest, Teacher teacher) {
        this.code = teacher.getNickname()+teacher.getId()+groupInsertUpdateRequest.getName();
        this.name = groupInsertUpdateRequest.getName();
        this.monetary_unit_name = groupInsertUpdateRequest.getMonetaryUnitName();
        this.tax_rate = groupInsertUpdateRequest.getTaxRate();
        this.inflationRate = groupInsertUpdateRequest.getInflationRate();
        this.teacher = teacher;
    }

    public void updateGroup(String name, String monetary_unit_name, double tax_rate, double inflationRate){
        this.setName(name);
        this.setMonetary_unit_name(monetary_unit_name);
        this.setTax_rate(tax_rate);
        this.setInflationRate(inflationRate);
    }


//     public static Group createGroup(GroupResponseDto groupResponseDto, Teacher teacher) {
//        Group group = new Group();
//        group.id = groupResponseDto.getId();
//        group.code = groupResponseDto.getCode();
//        group.name = groupResponseDto.getName();
//        group.monetary_unit_name = groupResponseDto.getMonetary_unit_name();
//        group.tax_rate = groupResponseDto.getTax_rate();
//        group.tax = groupResponseDto.getTax();
//        group.teacher = teacher;
//
//        return group;
//    }
}
