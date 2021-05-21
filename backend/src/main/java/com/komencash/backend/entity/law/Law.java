package com.komencash.backend.entity.law;

import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
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
@Table(name = "law")
public class Law {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "law_type")
    private String lawType;

    @Column(name = "article")
    private int article;

    @Column(name = "paragraph")
    private int paragraph;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;



    public Law(LawAddUpdateRequestDto lawAddUpdateRequestDto, Group group) {
        this.lawType = lawAddUpdateRequestDto.getLawType();
        this.article = lawAddUpdateRequestDto.getArticle();
        this.paragraph = lawAddUpdateRequestDto.getParagraph();
        this.content = lawAddUpdateRequestDto.getContent();
        this.group = group;
    }


    public void updateLaw(LawAddUpdateRequestDto lawAddUpdateRequestDto, Group group) {
        this.id = lawAddUpdateRequestDto.getId();
        this.lawType = lawAddUpdateRequestDto.getLawType();
        this.article = lawAddUpdateRequestDto.getArticle();
        this.paragraph = lawAddUpdateRequestDto.getParagraph();
        this.content = lawAddUpdateRequestDto.getContent();
        this.group = group;
    }
}
