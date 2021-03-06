package com.komencash.backend.controller;

import com.komencash.backend.dto.request.ItemAddReqAcceptUpdateRequestDto;
import com.komencash.backend.dto.request.ItemAddReqAddRequestDto;
import com.komencash.backend.dto.request.ItemAddReqFindDetailResponseDto;
import com.komencash.backend.dto.request.ItemAddReqFindResponseDto;
import com.komencash.backend.dto.store.*;
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


    @ApiOperation(value = "온라인 스토어 상품 정보 생성", notes = "입력받은 온라인 스토어 상품 정보를 생성하고 결과를 반환")
    @PostMapping
    public boolean addStoreItem(@RequestBody StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto) {
        return storeService.addStoreItem(storeItemAddUpdateRequestDto);
    }


    @ApiOperation(value = "온라인 스토어 상품 목록 조회", notes = "입력받은 그룹 아이디로 그룹 내의 모든 온라인스토어 물품 목록을 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/list/{group-id}")
     public List<StoreItemFindResponseDto> getStoreItemList(@PathVariable("group-id") int groupId){
        return storeService.findStoreItemList(groupId);
    }


    @ApiOperation(value = "온라인 스토어 상품 정보 수정", notes = "입력받은 온라인스토어 물품 정보로 수정하고 결과를 반환")
    @PutMapping
    public boolean updateStoreItem(@RequestBody StoreItemAddUpdateRequestDto storeItemAddUpdateRequestDto) {
        return storeService.updateStoreItem(storeItemAddUpdateRequestDto);
    }


    @ApiOperation(value = "온라인스토어 상품 정보 삭제", notes = "입력받은 store-item-id의 물품 정보를 삭제하고 결과를 반환")
    @ApiImplicitParam(name = "store-item-id", value = "store_item-id(물품 아이디)", dataType = "int", required = true)
    @DeleteMapping("/{store-item-id}")
    public boolean deleteStoreItem(@PathVariable("store-item-id") int storeItemId) {
        return storeService.deleteStoreItem(storeItemId);
    }


    @ApiOperation(value = "미확인된 상품 추가 요청 목록 조회", notes = "입력받은 그룹 아이디로 그룹 내 미확인된 상품 추가 요청 목록을 반환")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request-list/{group-id}")
    public List<ItemAddReqFindResponseDto> getItemAddReqList(@PathVariable("group-id") int groupId){
        return storeService.findItemAddReqList(groupId);
    }


    @ApiOperation(value = "온라인 스토어 상품 추가 요청 승인/거절", notes = "입력받은 상품 추가 요청 정보로 update하고 결과를 반환")
    @PutMapping("/add-request/accept")
    public boolean updateStoreItemAddReqAccept(@RequestBody ItemAddReqAcceptUpdateRequestDto itemAddReqAcceptUpdateRequestDto) {
        return storeService.updateStoreItemAddReqAccept(itemAddReqAcceptUpdateRequestDto);
    }


    @ApiOperation(value = "상품 추가 요청 상세정보 조회", notes = "입력받은 상품 추가 요청 아이디로 상세정보 반환")
    @ApiImplicitParam(name = "request-id", value = "request-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/add-request/{request-id}")
    public ItemAddReqFindDetailResponseDto getItemAddReq(@PathVariable("request-id") int requestId){
        return storeService.findItemAddReq(requestId);
    }


    @ApiOperation(value = "온라인 스토어 거래내역 조회", notes = "입력받은 그룹아이디의 그룹 내 모든 거래 내역을 최신순으로 조회")
    @ApiImplicitParam(name = "group-id", value = "group-id(그룹 아이디)", dataType = "int", required = true)
    @GetMapping("/history/{group-id}")
    public List<StoreItemPurchaseHistoryResponseDto> getPurchaseHistoryList(@PathVariable("group-id") int groupId){
        return storeService.findPurchaseHistoryList(groupId);
    }


    @ApiOperation(value = "학생별 온라인 스토어 거래내역 조회", notes = "입력받은 학생아이디의 온라인스토어 거래 내역을 최신순으로 조회")
    @ApiImplicitParam(name = "student-id", value = "student-id(학생 아이디)", dataType = "int", required = true)
    @GetMapping("/history/student/{student-id}")
    public List<StoreItemPurchaseHistoryResponseDto> getPurchaseHistoryListByStudentId(@PathVariable("student-id") int studentId){
        return storeService.findPurchaseHistoryListByStudentId(studentId);
    }


    @ApiOperation(value = "아이템 구입", notes = "입력받은 학생 아이디가 입력받은 아이템 아이디를 구입")
    @PostMapping("/purchase")
    public boolean addPurchaseHistory(@RequestBody StorePerchaseHistoryAddRequestDto storePerchaseHistoryAddRequestDto) {
        return storeService.addPurchaseHistory(storePerchaseHistoryAddRequestDto);
    }


    @ApiOperation(value = "상품 추가 요청", notes = "입력받은 상품의 추가 요청을 생성하고 결과를 반환")
    @PostMapping("/add-request")
    public boolean addItemAddReq(@RequestBody ItemAddReqAddRequestDto itemAddReqAddRequestDto) {
        return storeService.addItemAddReq(itemAddReqAddRequestDto);
    }
}
