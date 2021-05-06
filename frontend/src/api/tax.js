import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 세금 상세 조회
function fetchTaxList(groupId) {
  return instance.get(`tax/detail/${groupId}`)
}

// 세금 내역 추가
function addTaxData(taxData) {
  return instance.post(`tax`,taxData)
}

// 세금 내역 삭제
function deleteTaxData(taxId) {
  return instance.delete(`tax/${taxId}`)
}

// 세금 내역 수정
function modifyTaxData(taxData) {
  return instance.put(`tax/history`,taxData)
}

// 인플레이션 비율 수정
function modifyInfRate(taxData) {
  return instance.put(`tax/inflation-rate`,taxData)
}

// 소득세율 수정
function modifyTaxRate(taxData) {
  return instance.put(`tax/rate`,taxData)
}

export {
  fetchTaxList,
  addTaxData,
  deleteTaxData,
  modifyTaxData,
  modifyInfRate,
  modifyTaxRate
}