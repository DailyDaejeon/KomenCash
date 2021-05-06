import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 신용등급 전체 조회
function fetchCreditList(groupId) {
  return instance.get(`credit/list/${groupId}`)
}




export {
  fetchCreditList
}