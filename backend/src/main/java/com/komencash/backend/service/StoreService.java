package com.komencash.backend.service;

import com.komencash.backend.dto.request.ItemAddReqSelectResponse;
import com.komencash.backend.dto.store.StoreItemInsertUpdateRequest;
import com.komencash.backend.dto.store.StoreItemResponse;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.request_history.Accept;
import com.komencash.backend.entity.request_history.OnlineStoreItemAddRequestHistory;
import com.komencash.backend.entity.store.OnlineStoreItem;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.OnlineStoreItemAddRequestHistoryRepository;
import com.komencash.backend.repository.OnlineStoreItemRepository;
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

    public List<StoreItemResponse> selectStoreItem(int groupId){
        List<StoreItemResponse> storeItemResponses = new ArrayList<>();

        List<OnlineStoreItem> onlineStoreItems = onlineStoreItemRepository.findByGroup_Id(groupId);
        for(OnlineStoreItem onlineStoreItem : onlineStoreItems) storeItemResponses.add(new StoreItemResponse(onlineStoreItem));

        return storeItemResponses;
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

}
