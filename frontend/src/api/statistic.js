import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 제출 목록 조회
function fetchStatisticList(groupId) {
  return instance.get(`statistic/list/${groupId}`)
}

// 제출 목록 상세 조회
function fetchStatisticDetail(statisId) {
  return instance.get(`statistic/detail/${statisId}`)
}

// 제출 목록 리스트 조회
function fetchStatisticCredit(groupId) {
  return instance.get(`credit/statistic-doc/list/${groupId}`)
}


// 제출 목록 생성
function addStatisticItem(statisData) {
  return instance.post(`statistic`, statisData)
}

// 제출 목록 삭제
function deleteStatisticItem(statisId) {
  return instance.delete(`statistic/financial-product/${statisId}`)
}

// 제출 내역 신용등급관리위원회로 제출
function submitStatisticList(statisId) {
  return instance.put(`statistic/submit`,{
    statisId: statisId
  })
}



export {
  fetchStatisticList,
  fetchStatisticDetail,
  addStatisticItem,
  fetchStatisticCredit,
  deleteStatisticItem,
  submitStatisticList
}