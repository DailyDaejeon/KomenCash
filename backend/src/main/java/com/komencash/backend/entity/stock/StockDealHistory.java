package com.komencash.backend.entity.stock;

import com.komencash.backend.dto.stock.StockDealHistoryAddRequestDto;
import com.komencash.backend.entity.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "stock_deal_history")
public class StockDealHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public StockDealHistory(StockDealHistoryAddRequestDto stockDealHistoryAddRequestDto, Stock stock, Student student){
        this.price = stockDealHistoryAddRequestDto.getPrice();
        this.amount = stockDealHistoryAddRequestDto.getAmount();
        this.createdDate = new Date();
        this.stock = stock;
        this.student = student;
    }
}
