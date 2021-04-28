package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.Request.GroupMemberAddRequestDto;
import com.komencash.backend.entity.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "group_member_add_request_history")
public class GroupMemberAddRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;

    public void updateAccept(){
        this.accept = Accept.accept;
    }

    public void updateReject() { this.accept = Accept.reject;
    }
}
