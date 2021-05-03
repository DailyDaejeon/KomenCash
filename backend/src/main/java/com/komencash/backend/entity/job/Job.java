package com.komencash.backend.entity.job;

import com.komencash.backend.dto.job.JobInsertUpdateRequest;
import com.komencash.backend.entity.group.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private int salary;

    @Column(name = "role")
    private String role;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "personnel")
    private int personnel;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_type")
    private RecruitType recruitType;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;


    public Job(JobInsertUpdateRequest jobInsertUpdateRequest, Group group) {
        this.id = jobInsertUpdateRequest.getId();
        this.name = jobInsertUpdateRequest.getName();
        this.salary = jobInsertUpdateRequest.getSalary();
        this.role = jobInsertUpdateRequest.getRole();
        this.personnel = jobInsertUpdateRequest.getPersonnel();
        this.recruitType = jobInsertUpdateRequest.getRecruitType();
        this.group = group;
    }

    public void updateJob(JobInsertUpdateRequest jobInsertUpdateRequest) {
        this.name = jobInsertUpdateRequest.getName();
        this.salary = jobInsertUpdateRequest.getSalary();
        this.role = jobInsertUpdateRequest.getRole();
        this.personnel = jobInsertUpdateRequest.getPersonnel();
        this.recruitType = jobInsertUpdateRequest.getRecruitType();
    }
}
