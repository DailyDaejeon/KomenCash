package com.komencash.backend.dto.law;

import com.komencash.backend.entity.law.Law;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LawSelectResponse {

    private int id;
    private String lawTyepe;
    private int article;
    private int paragraph;
    private String content;

    public LawSelectResponse(Law law){
        this.id = law.getId();
        this.lawTyepe = law.getLawTyepe();
        this.article = law.getArticle();
        this.paragraph = law.getParagraph();
        this.content = law.getContent();
    }
}
