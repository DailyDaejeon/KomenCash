package com.komencash.backend.service;

import com.komencash.backend.dto.stock.StockInsertUpdateRequest;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.stock.Stock;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StockDealHistoryRepository;
import com.komencash.backend.repository.StockHistoryRepository;
import com.komencash.backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    StockHistoryRepository stockHistoryRepository;
    @Autowired
    StockDealHistoryRepository stockDealHistoryRepository;
    @Autowired
    GroupRepository groupRepository;


    public boolean saveStock(StockInsertUpdateRequest stockInsertUpdateRequest){
        Group group = groupRepository.findById(stockInsertUpdateRequest.getGroup_id()).orElse(null);
        if(group == null) return false;

        Stock stock = new Stock(stockInsertUpdateRequest, group);
        stockRepository.save(stock);
        return true;
    }

}
