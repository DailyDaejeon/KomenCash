package com.komencash.backend.entity.request_history;

import com.komencash.backend.dto.request.ItemAddReqAddRequestDto;
import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "online_store_item_add_request_history")
public class OnlineStoreItemAddRequestHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "accept")
    private Accept accept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public OnlineStoreItemAddRequestHistory(ItemAddReqAddRequestDto itemAddReqAddRequestDto, Student student) {
        this.name = itemAddReqAddRequestDto.getItemName();
        this.content = itemAddReqAddRequestDto.getContent();
        this.accept = Accept.before_confirm;
        this.student = student;
    }

    public void updateAccept(Accept accept){
        this.accept = accept;
    }
}
