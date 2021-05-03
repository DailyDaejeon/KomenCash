package com.komencash.backend.service;

import com.komencash.backend.dto.store.StoreItemInsertUpdateRequest;
import com.komencash.backend.dto.store.StoreItemResponse;
import com.komencash.backend.entity.store.OnlineStoreItem;
import com.komencash.backend.repository.OnlineStoreItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    OnlineStoreItemRepository onlineStoreItemRepository;


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
}
