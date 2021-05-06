package com.komencash.backend.controller;

import com.komencash.backend.dto.request.ItemAddReqDetailResponse;
import com.komencash.backend.dto.request.ItemAddReqSelectResponse;
import com.komencash.backend.dto.store.StoreItemInsertUpdateRequest;
import com.komencash.backend.dto.store.StoreItemPerchaseHistoryResponse;
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


    @ApiOperation(value = "온라인스토어 물품 정보 삭제", notes = "입력받은 store-item-id의 물품 정보를 삭제하고 결과를 반환")
    @ApiImplicitParam(name = "store-item-id", value = "store_item-id(물품 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{store-item-id}")
    public boolean deleteStoreItem(@PathVariable("store-item-id") int storeItemId) {
        return storeService.deleteStoreItem(storeItemId);
    }


    @ApiOperation(value = "온라인스토어 물품 정보 추가", notes = "입력받은 온라인스토어 물품 정보를 추가하고 결과를 반환")
    @PostMapping("")
    public boolean insertStoreItem(@RequestBody StoreItemInsertUpdateRequest storeItemInsertUpdateRequest) {
        return storeService.insertStoreItem(storeItemInsertUpdateRequest);
    }


    @ApiOperation(value = "미확인된 상품 추가 요청 목록 조회", notes = "입력받은 그룹 아이디로 그룹 내 미확인된 상품 추가 요청 목록을 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request-list/{group-id}")
    public List<ItemAddReqSelectResponse> selectItemAddReqList(@PathVariable("group-id") int groupId){
        return storeService.selectItemAddReqList(groupId);
    }


    @ApiOperation(value = "상품 추가 요청 상세정보 조회", notes = "입력받은 상품 추가 요청 아이디로 상세정보 반환")
    @ApiImplicitParam(name = "request-id", value = "request-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request/{request-id}")
    public ItemAddReqDetailResponse selectItemAddReq(@PathVariable("request-id") int requestId){
        return storeService.selectItemAddReq(requestId);
    }

    @ApiOperation(value = "온라인 스토어 거래내역 조회", notes = "입력받은 그룹아이디의 그룹 내 모든 거래 내역을 최신순으로 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/history/{group-id}")
    public List<StoreItemPerchaseHistoryResponse> selectPerchaseHistoryList(@PathVariable("group-id") int groupId){
        return storeService.selectPerchaseHistoryList(groupId);
    }


    @ApiOperation(value = "온라인 스토어 거래내역 조회", notes = "입력받은 학생아이디의 온라인스토어 거래 내역을 최신순으로 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/history/student/{student-id}")
    public List<StoreItemPerchaseHistoryResponse> selectPerchaseHistoryListByStudent(@PathVariable("student-id") int studentId){
        return storeService.selectPerchaseHistoryListByStudent(studentId);
    }

}
