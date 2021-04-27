package com.komencash.backend.entity.law;

import com.komencash.backend.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "law")
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "law_type")
    private String lawTyepe;

    @Column(name = "article")
    private int article;

    @Column(name = "paragraph")
    private int paragraph;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
}
