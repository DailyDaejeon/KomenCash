package com.komencash.backend.dto.job;

import com.komencash.backend.entity.job.PartTimeJob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartTimeFindResponseDto {

    private int id;
    private String name;
    private String role;
    private int salary;


    public PartTimeFindResponseDto(PartTimeJob partTimeJob) {
        this.id = partTimeJob.getId();
        this.name = partTimeJob.getName();
        this.role = partTimeJob.getRole();
        this.salary = partTimeJob.getSalary();
    }

}
