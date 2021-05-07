package com.komencash.backend.entity.tax;

import com.komencash.backend.dto.tax.TaxHistoryInsertUpdateRequest;
import com.komencash.backend.entity.group.Group;
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
@Table(name = "tax_history")
public class TaxHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "balance_change")
    private int balanceChange;

    @Column(name = "balance")
    private int balance;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


    public TaxHistory(TaxHistoryInsertUpdateRequest taxHistoryInsertUpdateRequest, Group group, int balance){
        this.balanceChange = taxHistoryInsertUpdateRequest.getBalanceChange();
        this.content = taxHistoryInsertUpdateRequest.getContent();
        this.createdDate = new Date();
        this.balance = balance;
        this.group = group;
    }
}
