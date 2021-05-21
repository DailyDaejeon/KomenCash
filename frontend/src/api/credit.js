import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 신용등급 전체 조회
function fetchCreditList() {
  return instance.get(`credit/grade-list`)
}

// 그룹원 별 신용등급 목록 조회
function fetchMemeberCreditList(groupId) {
  return instance.get(`credit/list/${groupId}`)
}

// 그룹원 별 신용등급 목록 조회
function addMemeberCreditPoint(creditData) {
  return instance.post(`credit/point`, creditData)
}




export {
  fetchCreditList,
  fetchMemeberCreditList,
  addMemeberCreditPoint
}