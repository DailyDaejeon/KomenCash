import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 주식종목 목록 조회
function fetchStockList(groupId) {
  return instance.get(`stock/list/${groupId}`)
}

// 주식종목 추가
function addStock(stockData) {
  return instance.post(`stock`,stockData)
}

// 주식종목 수정
function modifyStock(stockData) {
  return instance.put(`stock`,stockData)
}

// 주식종목 삭제
function deleteStock(stockId) {
  return instance.delete(`stock/${stockId}`)
}

// 주식 세부 데이터 추가
function addStockData(stockData) {
  return instance.post(`stock/history`,stockData)
}

// 주식 종목 가격변동 내역 조회
function fetchStockHistory(stockId) {
  return instance.get(`stock/history/${stockId}`)
}





export {
  fetchStockList,
  addStock,
  modifyStock,
  deleteStock,
  addStockData,
  fetchStockHistory
}