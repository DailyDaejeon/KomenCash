package com.komencash.backend.repository;

import com.komencash.backend.entity.store.OnlineStoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineStoreItemRepository extends JpaRepository<OnlineStoreItem, Integer> {
}