package com.komencash.backend.entity.store;

import com.komencash.backend.dto.store.StoreItemAddUpdateRequestDto;
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
@Table(name = "`online_store_item`")
public class OnlineStoreItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public void updateStoreItem(StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto) {
        this.name = storeItemAddUpdateRequestDto.getName();
        this.price = storeItemAddUpdateRequestDto.getPrice();
    }

    public OnlineStoreItem(StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto, Group group) {
        this.name = storeItemAddUpdateRequestDto.getName();
        this.price = storeItemAddUpdateRequestDto.getPrice();
        this.group = group;
    }
}
