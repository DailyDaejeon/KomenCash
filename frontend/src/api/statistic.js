import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 제출 목록 조회
function fetchStatisticList(groupId) {
  return instance.get(`statistic/list/${groupId}`)
}

// 제출 목록 생성
function addStatisticItem(statisData) {
  return instance.post(`statistic`, statisData)
}

// 제출 목록 삭제
function deleteStatisticItem(statisId) {
  return instance.delete(`statistic/financial-product/${statisId}`)
}


export {
  fetchStatisticList,
  addStatisticItem,
  deleteStatisticItem
}