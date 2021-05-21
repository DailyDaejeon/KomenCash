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


    public boolean addStock(StockAddUpdateRequestDto stockAddUpdateRequestDto){
        Group group = groupRepository.findById(stockAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        Stock stock = new Stock(stockAddUpdateRequestDto, group);
        stockRepository.save(stock);
        return true;
    }


    public boolean addStockHistory(StockHistoryAddRequestDto stockHistoryAddRequestDto){
        Stock stock = stockRepository.findById(stockHistoryAddRequestDto.getStockId()).orElse(null);
        if(stock == null) return false;

        StockHistory stockHistory = new StockHistory(stockHistoryAddRequestDto.getPrice(), stock);
        stockHistoryRepository.save(stockHistory);
        return true;
    }


    public List<StockFindResponseDto> findStockList(int groupId){
        List<StockFindResponseDto> stockFindResponseDtos = new ArrayList<>();

        List<Stock> stocks = stockRepository.findByGroup_Id(groupId);
        stocks.forEach(stock -> {
            List<StockHistory> stockHistories = stockHistoryRepository.findByStock_Id(stock.getId());
            int price = stockHistories.size() == 0 ? 0 : stockHistories.get(stockHistories.size() - 1).getPrice();
            int prePrice = stockHistories.size() < 2 ? 0 : stockHistories.get(stockHistories.size() - 2).getPrice();
            stockFindResponseDtos.add(new StockFindResponseDto(stock, price, prePrice));
        });

        return stockFindResponseDtos;
    }


    public List<StockHistoryFindListResponseDto> findStockHistoryList(int stockId) {
        List<StockHistoryFindListResponseDto> stockHistoryFindListResponseDtos = new ArrayList<>();

        List<StockHistory> stockHistories = stockHistoryRepository.findByStock_Id(stockId);
        stockHistories.forEach(stockHistory -> stockHistoryFindListResponseDtos.add(new StockHistoryFindListResponseDto(stockHistory)));

        return stockHistoryFindListResponseDtos;
    }


    public List<StockDealHistoryFindListResponseDto> findStockDealHistoryList(int studentId){
        List<StockDealHistoryFindListResponseDto> stockDealHistoryFindListResponseDtos = new ArrayList<>();

        List<StockDealHistory> stockDealHistories = stockDealHistoryRepository.findByStudent_Id(studentId);
        stockDealHistories.forEach(stockDealHistory -> stockDealHistoryFindListResponseDtos.add(new StockDealHistoryFindListResponseDto(stockDealHistory)));

        return stockDealHistoryFindListResponseDtos;
    }


    public boolean updateStock (StockAddUpdateRequestDto stockAddUpdateRequestDto) {
        Stock stock = stockRepository.findById(stockAddUpdateRequestDto.getId()).orElse(null);
        Group group = groupRepository.findById(stockAddUpdateRequestDto.getGroupId()).orElse(null);
        if(stock == null || group == null) return false;

        stock.update(stockAddUpdateRequestDto, group);
        stockRepository.save(stock);
        return true;
    }


    public boolean deleteStock(int stockId){
        Stock stock = stockRepository.findById(stockId).orElse(null);
        if(stock == null) return false;

        stockRepository.delete(stock);
        return true;
    }


    public boolean addStockDealHistory(StockDealHistoryAddRequestDto stockDealHistoryAddRequestDto) {
        Stock stock = stockRepository.findById(stockDealHistoryAddRequestDto.getStockId()).orElse(null);
        Student student = studentRepository.findById(stockDealHistoryAddRequestDto.getStudentId()).orElse(null);
        if(stock == null || student == null) return false;

        // 사는 경우에는 거래 후 남는 계좌 잔액은 음수일 수 없다.
        int price = stockDealHistoryAddRequestDto.getPrice();
        int amount = stockDealHistoryAddRequestDto.getAmount();
        int changeBalance = price * amount;
        if(bankService.findBalance(student.getId()) < changeBalance
                && stockDealHistoryAddRequestDto.getAmount() > 0) return false;

        // 거래 후 남는 주식 수량은 음수일 수 없다.
        int remainAmount = 0;
        List<StockDealHistory> stockDealHistories = stockDealHistoryRepository.findByStudent_IdAndStock_Id(student.getId(),stock.getId());
        for(int i=0; i<stockDealHistories.size(); i++){
            remainAmount += stockDealHistories.get(i).getAmount();
        }
        remainAmount += stockDealHistoryAddRequestDto.getAmount();
        if(remainAmount < 0) return false;


        String content = "주식 거래(" + student.getNickname() + ") : " + stock.getName();
        bankService.addAccountHistory(new AccountHistoryAddUpdateRequestDto(student.getId(), -(price * amount), content));

        stockDealHistoryRepository.save(new StockDealHistory(stockDealHistoryAddRequestDto, stock, student));
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
