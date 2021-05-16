import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 헌법리스트조회
function fetchLawList(groupId) {
  return instance.get(`law/${groupId}`)
}

// 법률 추가
function addLawItem(lawData) {
  return instance.post(`law`, lawData)
}

// 헌법 정보 수정
function modifyLawItem(lawItemData) {
  return instance.put(`law`, lawItemData)
}

// 법률추가/수정 요청리스트 조회
function fetchLawRequest(groupId) {
  return instance.get(`law/add-request-list/${groupId}`)
}

// 법률추가/수정 요청 상세조회
function fetchLawRequestDetail(requestId) {
  return instance.get(`law/add-request/${requestId}`)
}

// 법률추가/수정 요청 승인/거절
function acceptLawRequest(requestData) {
  return instance.put(`law/add-request/accept`,requestData)
}

export {
  fetchLawList,
  addLawItem,
  modifyLawItem,
  fetchLawRequest,
  fetchLawRequestDetail,
  acceptLawRequest
}