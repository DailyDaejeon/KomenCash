import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 자격증 정보 목록 조회
function fetchCertiList(groupId) {
  return instance.get(`certificate/group/${groupId}`)
}

// 자격증 정보 추가
function addCertificate(certiData) {
  return instance.post(`certificate`,certiData)
}

// 자격증 정보 수정
function modifyCertificate(certiData) {
  return instance.put(`certificate`,certiData)
}

// 자격증 정보 삭제
function deleteCertificate(certiId) {
  return instance.delete(`certificate/${certiId}`)
}

// 자격증 발급 정보 생성
function addCertiIssue(certiData) {
  return instance.post(`certificate/issue`, certiData)
}

// 자격증 발급 요청 생성
function addCertiIssueRequest(certiData) {
  return instance.post(`certificate/issue-request`, certiData)
}

// 자격증 발급 요청 수락/거절
function requestCertificate(certiData) {
  return instance.put(`certificate/issue-request`,certiData)
}

// 자격증 발급 요청 내역 삭제
function deleteMemberCertificate(certiData) {
  return instance.delete(`certificate/issue-request`, certiData)
}

export {
  fetchCertiList,
  addCertificate,
  modifyCertificate,
  deleteCertificate,
  addCertiIssue,
  addCertiIssueRequest,
  requestCertificate,
  deleteMemberCertificate
}