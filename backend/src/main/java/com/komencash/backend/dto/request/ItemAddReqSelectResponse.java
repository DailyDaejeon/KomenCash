package com.komencash.backend.dto.request;

import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAddReqSelectResponse {

    private int id;
    private String name;
    private Accept accept;
    private int studentId;
    private String studentNickname;

    public ItemAddReqSelectResponse(OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory) {
        this.id = onlineStoreItemAddRequestHistory.getId();
        this.name = onlineStoreItemAddRequestHistory.getName();
        this.accept = onlineStoreItemAddRequestHistory.getAccept();
        this.studentId = onlineStoreItemAddRequestHistory.getStudent().getId();
        this.studentNickname = onlineStoreItemAddRequestHistory.getStudent().getNickname();
    }

}
