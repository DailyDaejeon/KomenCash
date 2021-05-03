package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAddReqDetailResponse {

    private int id;
    private String name;
    private Accept accept;
    private String content;
    private int studentId;
    private String studentNickname;

    public ItemAddReqDetailResponse(OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory) {
        this.id = onlineStoreItemAddRequestHistory.getId();
        this.name = onlineStoreItemAddRequestHistory.getName();
        this.accept = onlineStoreItemAddRequestHistory.getAccept();
        this.content = onlineStoreItemAddRequestHistory.getContent();
        this.studentId = onlineStoreItemAddRequestHistory.getStudent().getId();
        this.studentNickname = onlineStoreItemAddRequestHistory.getStudent().getNickname();
    }
}
