package com.komencash.backend.dto.job;

import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.job.RecruitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDetailResponse {

    private int id;
    private String name;
    private int salary;
    private String role;
    private int personnel;
    private RecruitType recruitType;
    private List<JobStudentResponse> jobStudentResponses;


    public JobDetailResponse(Job job, List<JobStudentResponse> jobStudentResponses) {
        this.id = job.getId();
        this.name = job.getName();
        this.salary = job.getSalary();
        this.role = job.getRole();
        this.personnel = job.getPersonnel();
        this.recruitType = job.getRecruitType();
        this.jobStudentResponses = jobStudentResponses;
    }
}
