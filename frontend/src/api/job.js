import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 직업전체조회
function fetchJobList(groupId) {
  return instance.get(`job/${groupId}`)
}

// 직업상세조회
function fetchJobDetail(jobId) {
  return instance.get(`job/detail/${jobId}`)
}

// 직업추가
function addJob(jobData) {
  return instance.post(`job`,jobData)
}

// 직업수정
function modifyJob(jobData) {
  return instance.put(`job`,jobData)
}

// 직업삭제
function deleteJob(jobId) {
  return instance.delete(`job/${jobId}`)
}

// 미확인 직업 추가 요청 리스트 조회
function fetchJobRequestList(groupId) {
  return instance.get(`job/add-request-list/${groupId}`)
}

// 미확인 이력서 리스트 조회
function fetchJobResumeList(groupId) {
  return instance.get(`job/resume-list/${groupId}`)
}

// 이력서 상세정보 조회
function fetchJobResumeDetail(resumeId) {
  return instance.get(`job/resume-detail/${resumeId}`)
}

// 아르바이트추가
function addPartTime(jobData) {
  return instance.post(`job/part-time`,jobData)
}

// 아르바이트 정보조회
function fetchPartTimeList(groupId) {
  return instance.get(`job/part-time-list/${groupId}`)
}

// 아르바이트 정보 삭제
function deletePartTime(partTimeId) {
  return instance.delete(`job/part-time/${partTimeId}`)
}

// 직업 추가 요청 승인/거절
function acceptJobRequest(requestData) {
  return instance.put(`job/add-request/accept`,requestData)
}

// 이력서 제출내역 승인/거절
function acceptJobResumeRequest(requestData) {
  return instance.put(`job/resume-request/accept`,requestData)
}


export {
  fetchJobList,
  fetchJobDetail,
  addJob,
  modifyJob,
  deleteJob,
  fetchJobRequestList,
  fetchJobResumeList,
  fetchJobResumeDetail,
  addPartTime,
  fetchPartTimeList,
  deletePartTime,
  acceptJobRequest,
  acceptJobResumeRequest
}