package com.komencash.backend.service;

import com.komencash.backend.dto.bank.AccountHistoryAddUpdateRequestDto;
import com.komencash.backend.dto.request.ItemAddReqAddRequestDto;
import com.komencash.backend.dto.request.ItemAddReqDetailResponse;
import com.komencash.backend.dto.request.ItemAddReqSelectResponse;
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

    public List<StoreItemFindResponseDto> findStoreItemList(int groupId){
        List<StoreItemFindResponseDto> storeItemFindResponseDtos = new ArrayList<>();

        List<OnlineStoreItem> onlineStoreItems = onlineStoreItemRepository.findByGroup_Id(groupId);
        onlineStoreItems.forEach(onlineStoreItem -> storeItemFindResponseDtos.add(new StoreItemFindResponseDto(onlineStoreItem)));

        return storeItemFindResponseDtos;
    }


    public boolean updateStoreItem(StoreItemInsertUpdateRequest storeItemInsertUpdateRequest) {
        OnlineStoreItem onlineStoreItem = onlineStoreItemRepository.findById(storeItemInsertUpdateRequest.getId()).orElse(null);
        if(onlineStoreItem == null) return false;

        onlineStoreItem.updateStoreItem(storeItemInsertUpdateRequest);

        onlineStoreItemRepository.save(onlineStoreItem);
        return true;
    }


    public boolean deleteStoreItem(int storeItemId) {
        OnlineStoreItem onlineStoreItem = onlineStoreItemRepository.findById(storeItemId).orElse(null);
        if(onlineStoreItem == null) return false;

        onlineStoreItemRepository.delete(onlineStoreItem);
        return true;
    }


    public boolean insertStoreItem(StoreItemInsertUpdateRequest storeItemInsertUpdateRequest) {
        Group group = groupRepository.findById(storeItemInsertUpdateRequest.getGroupId()).orElse(null);
        if(group == null) return false;

        OnlineStoreItem onlineStoreItem = new OnlineStoreItem(storeItemInsertUpdateRequest, group);
        onlineStoreItemRepository.save(onlineStoreItem);
        return true;
    }

    public List<ItemAddReqSelectResponse> selectItemAddReqList(int groupId) {
        List<ItemAddReqSelectResponse> itemAddReqSelectResponses =  new ArrayList<>();
        List<OnlineStoreItemAddRequestHistory> onlineStoreItemAddRequestHistories = onlineStoreItemAddRequestHistoryRepository.findByStudent_Job_Group_Id(groupId);

        for(OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory : onlineStoreItemAddRequestHistories){
            if(!onlineStoreItemAddRequestHistory.getAccept().equals(Accept.before_confirm)) continue;
            itemAddReqSelectResponses.add(new ItemAddReqSelectResponse(onlineStoreItemAddRequestHistory));
        }
        return itemAddReqSelectResponses;
    }


    public boolean updateStoreItemAddRequestAccept(StoreItemAddRequestAcceptUpdateRequest storeItemAddRequestAcceptUpdateRequest){
        OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory =
                onlineStoreItemAddRequestHistoryRepository.findById(storeItemAddRequestAcceptUpdateRequest.getId()).orElse(null);
        if(onlineStoreItemAddRequestHistory == null) return false;

        if(onlineStoreItemAddRequestHistory.getAccept().equals(Accept.before_confirm))
            onlineStoreItemAddRequestHistory.updateAccept(storeItemAddRequestAcceptUpdateRequest.getAccept());
        onlineStoreItemAddRequestHistoryRepository.save(onlineStoreItemAddRequestHistory);
        return true;
    }



    public ItemAddReqDetailResponse selectItemAddReq(int requestId){
        OnlineStoreItemAddRequestHistory onlineStoreItemAddRequestHistory = onlineStoreItemAddRequestHistoryRepository.findById(requestId).orElse(null);
        if(onlineStoreItemAddRequestHistory == null) return null;
        return new ItemAddReqDetailResponse(onlineStoreItemAddRequestHistory);
    }


    public List<StoreItemPerchaseHistoryResponse> selectPerchaseHistoryList(int groupId) {
        List<StoreItemPerchaseHistoryResponse> responses = new ArrayList<>();

        List<OnlineStorePerchaseHistory> onlineStorePerchaseHistories = onlineStorePerchaseHistoryRepository.findByStudent_Job_Group_Id(groupId);
        for(OnlineStorePerchaseHistory history : onlineStorePerchaseHistories) responses.add(new StoreItemPerchaseHistoryResponse(history));

        return responses;
    }

    public List<StoreItemPerchaseHistoryResponse> selectPerchaseHistoryListByStudent(int studentId) {
        List<StoreItemPerchaseHistoryResponse> responses = new ArrayList<>();

        List<OnlineStorePerchaseHistory> onlineStorePerchaseHistories = onlineStorePerchaseHistoryRepository.findByStudent_Id(studentId);
        for(OnlineStorePerchaseHistory history : onlineStorePerchaseHistories) responses.add(new StoreItemPerchaseHistoryResponse(history));

        return responses;
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
