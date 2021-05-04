package com.komencash.backend.entity.stock;

import com.komencash.backend.dto.stock.StockInsertUpdateRequest;
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
@Table(name = "`stock`")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "hint")
    private String hint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public void update(StockInsertUpdateRequest stockInsertUpdateRequest, Group group) {
        this.name = stockInsertUpdateRequest.getName();
        this.hint = stockInsertUpdateRequest.getHint();
        this.group = group;
    }


    public Stock(StockInsertUpdateRequest stockInsertUpdateRequest, Group group) {
        this.name = stockInsertUpdateRequest.getName();
        this.hint = stockInsertUpdateRequest.getHint();
        this.group = group;
    }
}
