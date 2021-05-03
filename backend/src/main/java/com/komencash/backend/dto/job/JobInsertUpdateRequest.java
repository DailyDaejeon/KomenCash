package com.komencash.backend.dto.job;

import com.komencash.backend.entity.job.RecruitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInsertUpdateRequest {

    private int id;
    private String name;
    private int salary;
    private String role;
    private int personnel;
    private RecruitType recruitType;
    private int groupId;

    public JobInsertUpdateRequest(String name, int salary, String role, int personnel, RecruitType recruitType, int groupId) {
        this.name = name;
        this.salary = salary;
        this.role = role;
        this.personnel = personnel;
        this.recruitType = recruitType;
        this.groupId = groupId;
    }
}
