package com.komencash.backend.dto.job;

import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.job.RecruitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobFindResponseDto {

    private int id;
    private String name;
    private String role;
    private String qualification;
    private int salary;
    private int personnel;
    private RecruitType recruitType;

    public JobFindResponseDto(Job job) {
        this.id = job.getId();
        this.name = job.getName();
        this.role = job.getRole();
        this.qualification = job.getQualification();
        this.salary = job.getSalary();
        this.personnel = job.getPersonnel();
        this.recruitType = job.getRecruitType();
    }
}
