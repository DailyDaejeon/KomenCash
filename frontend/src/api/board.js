import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 공지사항 전체 조회
function fetchBoardList(groupId) {
  return instance.get(`board/list/${groupId}`)
}

// 공지사항 세부 조회
function fetchBoardDetail(boardId) {
  return instance.get(`board/${boardId}`)
}

// 공지사항 추가
function addBoard(boardData) {
  return instance.post(`board`,boardData)
}

// 공지사항 수정
function modifyBoard(boardData) {
  return instance.put(`board`,boardData)
}

// 공지사항 삭제
function deleteBoard(boardData) {
  return instance.delete(`board`,boardData)
}



export {
  fetchBoardList,
  fetchBoardDetail,
  addBoard,
  modifyBoard,
  deleteBoard
}