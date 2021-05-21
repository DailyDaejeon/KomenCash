import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

//그룹원 목록 조회
function fetchGroupMemberList(groupId) {
    return instance.get(`student/group/${groupId}`)
}

//그룹원 추가 요청 리스트 보기
function fetchGroupRequest(groupId) {
    return instance.get(`student/${groupId}/add-request`)
}

//그룹원 상세 조회
function fetchGroupMemberDetail(studentId) {
    return instance.get(`student/${studentId}`)
}

//그룹원 삭제
function deleteGroupMember(studentId) {
    return instance.delete(`student/${studentId}`)
}

//그룹원 추가요청 수락
function acceptGroupMember(requestId) {
  return instance.put(`student/accept`, { requestId: requestId})
}

//그룹원 추가요청 거절
function rejectGroupMember(requestId) {
  return instance.put(`student/reject`, { requestId: requestId})
}

//그룹원 자격증 취득 목록 조회
function fetchGroupMemberCertificate(studentId) {
  return instance.get(`certificate/student/${studentId}`)
}

//그룹원 자격증 변경 수락
function modifyGroupMemberCertificate(studentData) {
    return instance.put(`student/certificate`, studentData)
}

//그룹원 전과조회
function fetchGroupMemeberCase(studentId) {
  return instance.get(`case/student/${studentId}`)
}

//그룹원 직업변경
function modifyGroupMemberJob(studentData) {
  return instance.put(`job/job-change`, studentData)
}

// 그룹원 직업 무직으로 변경
function modifyGroupMemberJobFire(studentId) {
  return instance.put(`student/job/fire`, { studentId: studentId})
}

//그룹원 비밀번호초기화
function resetGroupMemberPw(studentId) {
    return instance.put(`student/reset-pw`,  {
      studentId :studentId
    })
}

// 그룹원 신용등급  조회
function fetchMemberCredit(studentId) {
  return instance.get(`credit/student/${studentId}`)
}

// 그룹원 신용점수 반영
function addMemberCreditPoint(studentData) {
  return instance.post(`credit/point`, studentData)
}

// 그룹원 잔액 조회 API
function fetchMemberBalance(studentId) {
  return instance.get(`bank/balance/${studentId}`)
}
	
// 그룹원 금융상품 조회 API
function fetchMemberFinancial(studentId) {
  return instance.get(`bank/financial-product/student/${studentId}`)
}

// 그룹원 주식 구매내역 조회 API
function fetchMemberStockDeal(studentId) {
  return instance.get(`stock/deal/student/${studentId}`)
}

// 학생별 주식 보유현황 조회
function fetchMemberStockDealStatus(stockId) {
  return instance.get(`stock/deal/holding-status/${stockId}`)
}

// 그룹원 제출 내역 수정
function acceptStatisDetail(statisData) {
  return instance.put(`statistic/detail`, statisData)
}

// 그룹원 스토어 거래내역 조회
function fetchGroupMemeberStoreHistory(studentId) {
  return instance.get(`store/history/student/${studentId}`)
}


// 그룹원 월급 지급 요청 생성
function salaryPaymentRequest(groupId) {
  return instance.post(`bank/salary-payment-request`, {
    data: groupId})
}

// 그룹원 월급 지급 요청 생성
function downloadUnity() {
  return instance.get(`download`)
}

export {
  fetchGroupMemberList,
  fetchGroupRequest,
  fetchGroupMemberDetail,
  deleteGroupMember,
  acceptGroupMember,
  rejectGroupMember,
  modifyGroupMemberCertificate,
  modifyGroupMemberJob,
  resetGroupMemberPw,
  fetchGroupMemberCertificate,
  fetchMemberCredit,
  addMemberCreditPoint,
  fetchGroupMemeberCase,
  fetchMemberBalance,
  fetchMemberFinancial,
  fetchMemberStockDeal,
  fetchMemberStockDealStatus,
  modifyGroupMemberJobFire,
  acceptStatisDetail,
  fetchGroupMemeberStoreHistory,
  salaryPaymentRequest,
  downloadUnity
}