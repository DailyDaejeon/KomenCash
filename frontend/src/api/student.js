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
function acceptGroupMember(studentId) {
    return instance.put(`student/accept`, {
      student_id:studentId
    })
}

//그룹원 추가요청 거절
function rejectGroupMember(studentId) {
    return instance.put(`student/reject`, {
      student_id:studentId
    })
}

//그룹원 자격증 변경 수락
function modifyGroupMemberCertificate(studentData) {
    return instance.put(`student/certificate`, studentData)
}

//그룹원 직업변경
function modifyGroupMemberJob(studentData) {
    return instance.put(`student/job`, studentData)
}

// 그룹원 직업 무직으로 변경
function modifyGroupMemberJobFire(studentId) {
    return instance.put(`student/job/fire`, {
        studentId :studentId
    })
}

//그룹원 비밀번호초기화
function resetGroupMemberPw(studentId) {
    return instance.put(`student/reset-pw`,  {
      student_id:studentId
    })
}

// 그룹원 신용등급  조회
function fetchMemberCredit(studentId) {
  return instance.get(`credit/student/${studentId}`)
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

// 그룹원 제출 내역 수정
function acceptStatisDetail(statisData) {
  return instance.put(`statistic/detail`, statisData)
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
  fetchMemberCredit,
  fetchMemberBalance,
  fetchMemberFinancial,
  fetchMemberStockDeal,
  modifyGroupMemberJobFire,
  acceptStatisDetail
}