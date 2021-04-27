package com.komencash.backend.entity.law;

import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.entity.Group;
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

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;



    public void updateLaw(LawInsertUpdateRequest lawInsertUpdateRequest, Group group) {
        if(lawInsertUpdateRequest.getId() != 0) this.id = lawInsertUpdateRequest.getId();
        if(lawInsertUpdateRequest.getLawTyepe() != null) this.lawTyepe = lawInsertUpdateRequest.getLawTyepe();
        if(lawInsertUpdateRequest.getArticle() != 0) this.article = lawInsertUpdateRequest.getArticle();
        if(lawInsertUpdateRequest.getParagraph() != 0) this.paragraph = lawInsertUpdateRequest.getParagraph();
        if(lawInsertUpdateRequest.getContent() != null) this.content = lawInsertUpdateRequest.getContent();
        if(group != null) this.group = group;
    }
}
