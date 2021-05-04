import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 온라인 스토어 물품 정보 추가
function addStoreProduct(productData) {
  return instance.post(`store`,productData)
}

// 온라인 스토어 물품 정보 수정
function modifyStoreProduct(productData) {
  return instance.put(`store`,productData)
}

// 온라인 스토어 물품 정보 삭제
function deleteStoreProduct(storeItemId) {
  return instance.delete(`store/${storeItemId}`)
}

// 온라인 스토어 물품 목록 조회
function fetchStoreList(groupId) {
  return instance.get(`store/list/${groupId}`)
}

// 온라인 스토어 거래내역 조회
function fetchStoreHistory(groupId) {
  return instance.get(`store/history/${groupId}`)
}

// 미확인 온라인 스토어 상품추가 요청 조회
function fetchStoreRequestList(groupId) {
  return instance.get(`store/add-request-list/${groupId}`)
}

// 온라인 스토어 상품추가 요청 상세정보 조회
function fetchStoreRequestDetail(requestId) {
  return instance.get(`store/add-request/${requestId}`)
}


export {
  addStoreProduct,
  modifyStoreProduct,
  deleteStoreProduct,
  fetchStoreList,
  fetchStoreHistory,
  fetchStoreRequestList,
  fetchStoreRequestDetail
}