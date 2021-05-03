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
function addFinancial(groupId) {
  return instance.post(`bank/${groupId}/financial_product`)
}

// 은행 금융상품 상세생성
function detailFinancial(financialId) {
  return instance.post(`bank/${financialId}/financial_product_detail`)
}


export {
  fetchGroupMemberAccountList,
  fetchFinancialList,
  addFinancial,
  detailFinancial,
}
