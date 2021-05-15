package com.komencash.backend.dto.law;

import com.komencash.backend.entity.law.Law;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawFindByLawTypeResponseDto {

    private int id;
    private String lawType;
    private int article;
    private int paragraph;
    private String content;

    public LawFindByLawTypeResponseDto(Law law){
        this.id = law.getId();
        this.lawType = law.getLawType();
        this.article = law.getArticle();
        this.paragraph = law.getParagraph();
        this.content = law.getContent();
    }
}
