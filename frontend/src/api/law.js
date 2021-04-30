import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 헌법리스트조회
function fetchLawList(groupId) {
  return instance.get(`law/${groupId}`)
}

// 헌법 정보 수정
function modifyLawItem(lawItemData) {
  return instance.get(`law`, lawItemData)
}

// 법률추가/수정 요청리스트 조회
function fetchLawRequest(groupId) {
  return instance.get(`law/add_request/${groupId}`)
}


export {
  fetchLawList,
  modifyLawItem,
  fetchLawRequest
}