package com.komencash.backend.entity.job;

import com.komencash.backend.dto.job.PartTimeAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "part_time_job")
public class PartTimeJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "salary")
    private int salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public PartTimeJob(PartTimeAddUpdateRequestDto partTimeAddUpdateRequestDto, Group group) {
        this.id = partTimeAddUpdateRequestDto.getId();
        this.name = partTimeAddUpdateRequestDto.getName();
        this.role = partTimeAddUpdateRequestDto.getRole();
        this.salary = partTimeAddUpdateRequestDto.getSalary();
        this.group = group;
    }
}
