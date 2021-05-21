import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 사건경위서 목록 조회
function fetchCaseList(groupId) {
  return instance.get(`case/list/${groupId}`)
}

// 사건경위서 상세 조회
function fetchCaseDetail(caseId) {
  return instance.get(`case/${caseId}`)
}

// 사건경위서 사건 처리
function acceptCase(caseData) {
  return instance.put(`case/accept`,caseData)
}

// 사건경위서 사건 처리
function createCase(caseData) {
  return instance.post(`case`, caseData)
}


export {
  fetchCaseList,
  fetchCaseDetail,
  acceptCase,
  createCase
}