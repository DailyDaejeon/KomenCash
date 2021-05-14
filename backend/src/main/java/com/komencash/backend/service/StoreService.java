package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountHistoryAddUpdateRequestDto;
import com.komencash.backend.dto.request.ItemAddReqAcceptUpdateRequestDto;
import com.komencash.backend.dto.request.ItemAddReqAddRequestDto;
import com.komencash.backend.dto.request.ItemAddReqFindDetailResponseDto;
import com.komencash.backend.dto.request.ItemAddReqFindResponseDto;
import com.komencash.backend.dto.store.*;
import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import com.komencash.backend.entity.store.OnlineStoreItem;
import com.komencash.backend.entity.store.OnlineStorePerchaseHistory;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    OnlineStoreItemRepository onlineStoreItemRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    OnlineStoreItemAddRequestHistoryRepository onlineStoreItemAddRequestHistoryRepository;

    @Autowired
    OnlineStorePerchaseHistoryRepository onlineStorePerchaseHistoryRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    BankService bankService;

    @Autowired
    TaxService taxService;


    public boolean addStoreItem(StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto) {
        Group group = groupRepository.findById(storeItemAddUpdateRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        OnlineStoreItem onlineStoreItem = new OnlineStoreItem(storeItemAddUpdateRequestDto, group);
        onlineStoreItemRepository.save(onlineStoreItem);
        return true;
    }


    public List<StoreItemFindResponseDto> findStoreItemList(int groupId){
        List<StoreItemFindResponseDto> storeItemFindResponseDtos = new ArrayList<>();

        List<OnlineStoreItem> onlineStoreItems = onlineStoreItemRepository.findByGroup_Id(groupId);
        onlineStoreItems.forEach(onlineStoreItem -> storeItemFindResponseDtos.add(new StoreItemFindResponseDto(onlineStoreItem)));

        return storeItemFindResponseDtos;
    }


    public boolean updateStoreItem(StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto) {
        OnlineStoreItem onlineStoreItem = onlineStoreItemRepository.findById(storeItemAddUpdateRequestDto.getId()).orElse(null);
        if(onlineStoreItem == null) return false;

        onlineStoreItem.updateStoreItem(storeItemAddUpdateRequestDto);

        onlineStoreItemRepository.save(onlineStoreItem);
        return true;
    }


    public boolean deleteStoreItem(int storeItemId) {
        OnlineStoreItem onlineStoreItem = onlineStoreItemRepository.findById(storeItemId).orElse(null);
        if(onlineStoreItem == null) return false;

        onlineStoreItemRepository.delete(onlineStoreItem);
        return true;
    }


    public List<ItemAddReqFindResponseDto> findItemAddReqList(int groupId) {
        List<ItemAddReqFindResponseDto> itemAddReqFindResponseDtos =  new ArrayList<>();
        List<OnlineStoreItemAddRequestHistory> onlineStoreItemAddRequestHistories = onlineStoreItemAddRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);

        onlineStoreItemAddRequestHistories.forEach(onlineStoreItemAddRequestHistory -> {
            if(onlineStoreItemAddRequestHistory.getAccept().equals(Accept.before_confirm)) {
                itemAddReqFindResponseDtos.add(new ItemAddReqFindResponseDto(onlineStoreItemAddRequestHistory));
            }
        });
        return itemAddReqFindResponseDtos;
    }


    public boolean updateStoreItemAddReqAccept(ItemAddReqAcceptUpdateRequestDto itemAddReqAcceptUpdateRequestDto){
        OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory =
                onlineStoreItemAddRequestHistoryRepository.findById(itemAddReqAcceptUpdateRequestDto.getId()).orElse(null);
        if(onlineStoreItemAddRequestHistory == null) return false;

        if(onlineStoreItemAddRequestHistory.getAccept().equals(Accept.before_confirm))
            onlineStoreItemAddRequestHistory.updateAccept(itemAddReqAcceptUpdateRequestDto.getAccept());
        onlineStoreItemAddRequestHistoryRepository.save(onlineStoreItemAddRequestHistory);
        return true;
    }



    public ItemAddReqFindDetailResponseDto findItemAddReq(int requestId){
        OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory = onlineStoreItemAddRequestHistoryRepository.findById(requestId).orElse(null);
        if(onlineStoreItemAddRequestHistory == null) return null;
        return new ItemAddReqFindDetailResponseDto(onlineStoreItemAddRequestHistory);
    }


    public List<StoreItemPurchaseHistoryResponseDto> findPurchaseHistoryList(int groupId) {
        List<StoreItemPurchaseHistoryResponseDto> storeItemPurchaseHistoryResponseDtos = new ArrayList<>();

        List<OnlineStorePerchaseHistory> onlineStorePurchaseHistories = onlineStorePerchaseHistoryRepository.findByStudent_Job_Group_Id(groupId);
        onlineStorePurchaseHistories.forEach(onlineStorePurchaseHistory -> {
            storeItemPurchaseHistoryResponseDtos.add(new StoreItemPurchaseHistoryResponseDto(onlineStorePurchaseHistory));
        });

        return storeItemPurchaseHistoryResponseDtos;
    }


    public List<StoreItemPurchaseHistoryResponseDto> findPurchaseHistoryListByStudentId(int studentId) {
        List<StoreItemPurchaseHistoryResponseDto> storeItemPurchaseHistoryResponseDtos = new ArrayList<>();

        List<OnlineStorePerchaseHistory> onlineStorePerchaseHistories = onlineStorePerchaseHistoryRepository.findByStudent_Id(studentId);
        onlineStorePerchaseHistories.forEach(onlineStorePerchaseHistory -> {
            storeItemPurchaseHistoryResponseDtos.add(new StoreItemPurchaseHistoryResponseDto(onlineStorePerchaseHistory));
        });

        return storeItemPurchaseHistoryResponseDtos;
    }


    public boolean addPurchaseHistory(StorePerchaseHistoryAddRequestDto storePerchaseHistoryAddRequestDto){

        OnlineStoreItem onlineStoreItem = onlineStoreItemRepository.findById(storePerchaseHistoryAddRequestDto.getItemId()).orElse(null);
        Student student = studentRepository.findById(storePerchaseHistoryAddRequestDto.getStudentId()).orElse(null);
        if(onlineStoreItem == null || student == null) return false;


        String itemName = onlineStoreItem.getName();
        int itemPrice = onlineStoreItem.getPrice();

        if(bankService.findBalance(student.getId()) < itemPrice) return false;

        String content = "온라인 스토어 물품 구매(" + student.getNickname() + ") : " + itemName;
        onlineStorePerchaseHistoryRepository.save(new OnlineStorePerchaseHistory(itemName, itemPrice, student));
        bankService.addAccountHistory(new AccountHistoryAddUpdateRequestDto(student.getId(), -itemPrice, content));
        taxService.addTaxHistory(new TaxHistoryAddUpdateRequestDto(itemPrice, content, student.getJob().getGroup().getId()));

        return true;
    }


    public boolean addItemAddReq(ItemAddReqAddRequestDto itemAddReqAddRequestDto) {
        Student student = studentRepository.findById(itemAddReqAddRequestDto.getStudentId()).orElse(null);
        if(student == null) return false;

        OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory = new OnlineStoreItemAddRequestHistory(itemAddReqAddRequestDto, student);
        onlineStoreItemAddRequestHistoryRepository.save(onlineStoreItemAddRequestHistory);
        return true;
    }
}
