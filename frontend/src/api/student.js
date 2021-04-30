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

//그룹원 비밀번호초기화
function resetGroupMemberPw(studentId) {
    return instance.put(`student/reset-pw`,  {
      student_id:studentId
    })
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
  resetGroupMemberPw
}