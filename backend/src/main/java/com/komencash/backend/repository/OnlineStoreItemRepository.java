package com.komencash.backend.repository;

import com.komencash.backend.entity.store.OnlineStoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnlineStoreItemRepository extends JpaRepository<OnlineStoreItem, Integer> {

    public List<OnlineStoreItem> findByGroup_Id(int groupId);
}