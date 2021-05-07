import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 그룹원 계좌 조회
function fetchGroupMemberAccountList(groupId) {
  return instance.get(`bank/${groupId}`)
}

// 은행 금융상품 조회
function fetchFinancialList(groupId) {
  return instance.get(`bank/${groupId}/financial_product`)
}

// 은행 금융상품 추가
function addFinancial(groupId,name) {
  return instance.post(`bank/${groupId}/financial_product`,{
    name:name
  })
}

// 은행 금융상품 상세생성
function detailFinancial(financialId) {
  return instance.post(`bank/${financialId}/financial_product_detail`)
}

// 은행 금융상품 삭제
function deleteFinancial(financialId) {
  return instance.delete(`bank/financial_product/${financialId}`)
}

// 은행 금융상품 수정
function modifyFinancial(financialData) {
  return instance.put(`bank/financial_product`,financialData)
}

// 은행 금융상품 상세수정
function modifyDetailFinancial(financialData) {
  return instance.put(`bank/financial_product-detail`,financialData)
}

// 은행 금융상품 상세조회
function fetchDetailFinancial(financialId) {
  return instance.get(`bank/financial_product/product/${financialId}`)
}

export {
  fetchGroupMemberAccountList,
  fetchFinancialList,
  addFinancial,
  detailFinancial,
  deleteFinancial,
  modifyFinancial,
  modifyDetailFinancial,
  fetchDetailFinancial
}
