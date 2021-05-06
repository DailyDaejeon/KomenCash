package com.komencash.backend.service;

import com.komencash.backend.dto.stock.StockDealHistoryResponse;
import com.komencash.backend.dto.stock.StockHistoryInsertRequest;
import com.komencash.backend.dto.stock.StockInsertUpdateRequest;
import com.komencash.backend.dto.stock.StockSelectResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.stock.Stock;
import com.komencash.backend.entity.stock.StockDealHistory;
import com.komencash.backend.entity.stock.StockHistory;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StockDealHistoryRepository;
import com.komencash.backend.repository.StockHistoryRepository;
import com.komencash.backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Group group = groupRepository.findById(stockInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        Stock stock = new Stock(stockInsertUpdateRequest, group);
        stockRepository.save(stock);
        return true;
    }


    public List<StockSelectResponse> selectStockList(int groupId){
        List<StockSelectResponse> stockSelectResponses = new ArrayList<>();

        List<Stock> stocks = stockRepository.findByGroup_Id(groupId);
        for(Stock stock : stocks) {
            List<StockHistory> stockHistories = stockHistoryRepository.findByStock_Id(stock.getId());
            int price = stockHistories.size() == 0 ? 0 : stockHistories.get(stockHistories.size() - 1).getPrice();
            int prePrice = stockHistories.size() < 2 ? 0 : stockHistories.get(stockHistories.size() - 2).getPrice();
            stockSelectResponses.add(new StockSelectResponse(stock, price, prePrice));
        }

        return stockSelectResponses;
    }

    public List<StockDealHistoryResponse> selectStockDealHistory(int studentId){
        List<StockDealHistoryResponse> stockDealHistoryResponses = new ArrayList<>();

        List<StockDealHistory> stockDealHistories = stockDealHistoryRepository.findByStudent_Id(studentId);
        for(StockDealHistory stockDealHistory : stockDealHistories)
            stockDealHistoryResponses.add(new StockDealHistoryResponse(stockDealHistory));

        return stockDealHistoryResponses;
    }


    public boolean updateStock (StockInsertUpdateRequest stockInsertUpdateRequest) {
        Stock stock = stockRepository.findById(stockInsertUpdateRequest.getId()).orElse(null);
        if(stock == null) return false;

        Group group = groupRepository.findById(stockInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        stock.update(stockInsertUpdateRequest, group);
        stockRepository.save(stock);
        return true;
    }

    public boolean deleteStock(int stockId){
        Stock stock = stockRepository.findById(stockId).orElse(null);
        if(stock == null) return false;

        stockRepository.delete(stock);
        return true;
    }


    public boolean saveStockHistory(StockHistoryInsertRequest stockHistoryInsertRequest){
        Stock stock = stockRepository.findById(stockHistoryInsertRequest.getStockId()).orElse(null);
        if(stock == null) return false;

        StockHistory stockHistory = new StockHistory(stockHistoryInsertRequest.getPrice(), stock);
        stockHistoryRepository.save(stockHistory);
        return true;
    }

}
