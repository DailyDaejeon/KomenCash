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
    private String lawTyepe;

    @Column(name = "article")
    private int article;

    @Column(name = "paragraph")
    private int paragraph;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;



    public void updateLaw(LawAddUpdateRequestDto lawAddUpdateRequestDto, Group group) {
        if(lawAddUpdateRequestDto.getId() != 0) this.id = lawAddUpdateRequestDto.getId();
        if(lawAddUpdateRequestDto.getLawTyepe() != null) this.lawTyepe = lawAddUpdateRequestDto.getLawTyepe();
        if(lawAddUpdateRequestDto.getArticle() != 0) this.article = lawAddUpdateRequestDto.getArticle();
        if(lawAddUpdateRequestDto.getParagraph() != 0) this.paragraph = lawAddUpdateRequestDto.getParagraph();
        if(lawAddUpdateRequestDto.getContent() != null) this.content = lawAddUpdateRequestDto.getContent();
        if(group != null) this.group = group;
    }
}
