package com.komencash.backend.dto.student;

import com.komencash.backend.dto.certificate.CertificateSelectResponse;
import com.komencash.backend.entity.job.Job;
import com.komencash.backend.entity.student.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentDetailResponseDto {

    private int id;
    private String nickname;
    private String password;
    private Job job;
    private List<CertificateSelectResponse> certificateSelectResponseList;

    public StudentDetailResponseDto(Student student, List<CertificateSelectResponse> certificateSelectResponseList){
        this.id = student.getId();
        this.nickname = student.getNickname();
        this.password = student.getPassword();
        this.job = student.getJob();
        this.certificateSelectResponseList = certificateSelectResponseList;
    }
}

