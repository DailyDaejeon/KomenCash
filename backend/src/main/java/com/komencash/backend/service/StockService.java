package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountHistoryAddUpdateRequestDto;
import com.komencash.backend.dto.stock.*;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.stock.Stock;
import com.komencash.backend.entity.stock.StockDealHistory;
import com.komencash.backend.entity.stock.StockHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
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
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BankService bankService;


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


    public List<StockHistorySelectResponse> selectStockHistory(int stockId) {
        List<StockHistorySelectResponse> stockHistorySelectResponses = new ArrayList<>();

        List<StockHistory> stockHistories = stockHistoryRepository.findByStock_Id(stockId);
        for(StockHistory stockHistory : stockHistories) stockHistorySelectResponses.add(new StockHistorySelectResponse(stockHistory));

        return stockHistorySelectResponses;
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


    public boolean addStockDealHistory(StockDealHistoryAddRequestDto stockDealHistoryAddRequestDto) {
        Stock stock = stockRepository.findById(stockDealHistoryAddRequestDto.getStockId()).orElse(null);
        Student student = studentRepository.findById(stockDealHistoryAddRequestDto.getStudentId()).orElse(null);
        if(stock == null || student == null) return false;



        int price = stockDealHistoryAddRequestDto.getPrice();
        int amount = stockDealHistoryAddRequestDto.getAmount();
        if(bankService.findBalance(student.getId()) < (price * amount)) return false;

        String content = "주식 거래(" + student.getNickname() + ") : " + stock.getName();
        bankService.addAccountHistory(new AccountHistoryAddUpdateRequestDto(student.getId(), -(price * amount), content));

        StockDealHistory stockDealHistory = new StockDealHistory(stockDealHistoryAddRequestDto, stock, student);
        stockDealHistoryRepository.save(stockDealHistory);
        return true;
    }


    public List<StockDealHistoryFindHoldingStatusDto> findStockHoldingStatus(int studentId){
        List<StockDealHistoryFindHoldingStatusDto> stockDealHistoryFindHoldingStatusDtos = new ArrayList<>();

        Student student = studentRepository.findById(studentId).orElse(null);
        List<Stock> stocks = stockRepository.findByGroup_Id(student.getJob().getGroup().getId());
        List<StockDealHistory> stockDealHistories = stockDealHistoryRepository.findByStudent_Id(studentId);
        if(student == null) return null;

        for (Stock stock : stocks){

            double sumDealPrice = 0;
            int amount = 0;
            for(StockDealHistory stockDealHistory : stockDealHistories) {
                if (stockDealHistory.getStock() == stock) {
                    amount += stockDealHistory.getAmount();
                    sumDealPrice += (stockDealHistory.getPrice() * stockDealHistory.getAmount());
                }
            }

            if(amount > 0) {
                List<StockHistory> stockHistories = stockHistoryRepository.findByStock_Id(stock.getId());

                int stockId = stock.getId();
                String stockName = stock.getName();
                int curPrice = stockHistories.size() > 0 ? stockHistories.get(stockHistories.size() - 1).getPrice() : 0;
                double avgDealPrice = sumDealPrice / amount;
                double changePercent = (curPrice / avgDealPrice - 1) * 100;
                stockDealHistoryFindHoldingStatusDtos
                        .add(new StockDealHistoryFindHoldingStatusDto(stockId, stockName, curPrice, avgDealPrice, amount, changePercent));
            }
        }
        return stockDealHistoryFindHoldingStatusDtos;
    }
}
