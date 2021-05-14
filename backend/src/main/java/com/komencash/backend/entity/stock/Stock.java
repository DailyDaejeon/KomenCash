package com.komencash.backend.entity.stock;

import com.komencash.backend.dto.stock.StockAddUpdateRequestDto;
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


    public void update(StockAddUpdateRequestDto stockAddUpdateRequestDto, Group group) {
        this.name = stockAddUpdateRequestDto.getName();
        this.hint = stockAddUpdateRequestDto.getHint();
        this.group = group;
    }


    public Stock(StockAddUpdateRequestDto stockAddUpdateRequestDto, Group group) {
        this.name = stockAddUpdateRequestDto.getName();
        this.hint = stockAddUpdateRequestDto.getHint();
        this.group = group;
    }
}
