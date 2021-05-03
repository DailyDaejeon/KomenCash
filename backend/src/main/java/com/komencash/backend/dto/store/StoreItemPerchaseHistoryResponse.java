package com.komencash.backend.dto.store;

import com.komencash.backend.entity.store.OnlineStorePerchaseHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemPerchaseHistoryResponse {

    private int id;
    private String name;
    private int price;
    private Date perchaseDate;
    private int studentId;
    private String studentNickname;

    public StoreItemPerchaseHistoryResponse(OnlineStorePerchaseHistory onlineStorePerchaseHistory) {
        this.id = onlineStorePerchaseHistory.getId();
        this.name = onlineStorePerchaseHistory.getName();
        this.price = onlineStorePerchaseHistory.getPrice();
        this.perchaseDate = onlineStorePerchaseHistory.getPerchaseDatetime();
        this.studentId = onlineStorePerchaseHistory.getStudent().getId();
        this.studentNickname = onlineStorePerchaseHistory.getStudent().getNickname();
    }
}
