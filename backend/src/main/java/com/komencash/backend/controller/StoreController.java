package com.komencash.backend.controller;

import com.komencash.backend.dto.law.LawInsertUpdateRequest;
import com.komencash.backend.dto.store.StoreItemInsertUpdateRequest;
import com.komencash.backend.dto.store.StoreItemResponse;
import com.komencash.backend.service.StoreService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreService storeService;


    @ApiOperation(value = "온라인스토어 물품 목록 조회", notes = "입력받은 그룹 아이디로 그룹 내의 모든 온라인스토어 물품 목록을 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
     public List<StoreItemResponse> selectStoreItem(@PathVariable("group-id") int groupId){
        return storeService.selectStoreItem(groupId);
    }

    
    @ApiOperation(value = "온라인스토어 물품 정보 수정", notes = "입력받은 온라인스토어 물품 정보로 수정하고 결과를 반환")
    @PutMapping("")
    public boolean updateStoreItem(@RequestBody StoreItemInsertUpdateRequest storeItemInsertUpdateRequest) {
        return storeService.updateStoreItem(storeItemInsertUpdateRequest);
    }


}
