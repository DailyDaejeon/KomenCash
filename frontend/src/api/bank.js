import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 그룹원 계좌 조회
function fetchGroupMemberAccountList(groupId) {
  return instance.get(`bank/${groupId}`)
}

// 은행 금융상품 조회
function fetchFinancialList(groupId) {
  return instance.get(`bank/${groupId}/financial-product`)
}

// 은행 금융상품 추가
function addFinancial(financialData) {
  return instance.post(`bank/financial-product`, financialData)
}

// 은행 금융상품 상세생성
function detailFinancial(financialData) {
  return instance.post(`bank/${financialData.financialProduct.id}/financial-product-detail`, financialData)
}

// 은행 금융상품 삭제
function deleteFinancial(financialId) {
  return instance.delete(`bank/financial-product/${financialId}`)
}

// 은행 금융상품 수정
function modifyFinancial(financialData) {
  return instance.put(`bank/financial-product`,financialData)
}

// 은행 금융상품 상세수정
function modifyDetailFinancial(financialData) {
  return instance.put(`bank/financial-product-detail`,financialData)
}

// 은행 금융상품 상세 정보조회
function fetchDetailFinancial(financialId) {
  return instance.get(`bank/financial-product/product/${financialId}`)
}

// 그룹원 월급 지급 요청 생성
function createSalaryRequest(groupId) {
  return instance.post(`bank/salary-payment-request`,{
    groupId: groupId
  })
}

// 금융상품 신청 목록 조회
function fetchFinancialRequest(financialId) {
  return instance.get(`bank/financial-product/apply/${financialId}`)
}

// 금융상품 신청 상태 수정
function acceptFinancialRequest(finHisId) {
  return instance.put(`bank/financial-status-accept/${finHisId}`)
}


export {
  fetchGroupMemberAccountList,
  fetchFinancialList,
  addFinancial,
  detailFinancial,
  deleteFinancial,
  modifyFinancial,
  modifyDetailFinancial,
  fetchDetailFinancial,
  createSalaryRequest,
  fetchFinancialRequest,
  acceptFinancialRequest
}
