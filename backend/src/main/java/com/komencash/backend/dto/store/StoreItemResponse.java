package com.komencash.backend.dto.store;

import com.komencash.backend.entity.store.OnlineStoreItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreItemResponse {

    private int id;
    private String name;
    private int price;

    public StoreItemResponse(OnlineStoreItem onlineStoreItem) {
        this.id = onlineStoreItem.getId();
        this.name = onlineStoreItem.getName();
        this.price = onlineStoreItem.getPrice();
    }
}
