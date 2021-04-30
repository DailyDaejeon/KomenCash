import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 투표리스트조회
function fetchVoteList(groupId) {
  return instance.get(`vote/list/${groupId}`)
}

// 투표생성
function addVote(voteData) {
  return instance.post(`vote`,voteData)
}

// 투표상세조회
function fetchVoteList(voteId) {
  return instance.get(`vote/detail/${voteId}`)
}

// 투표삭제
function deleteVote(voteId) {
  return instance.delete(`vote/${voteId}`)
}

export {
  fetchVoteList,
  addVote,
  fetchVoteList,
  deleteVote
}