import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 그룹원 계좌 조회
function fetchGroupMemberAccountList(groupId) {
  return instance.get(`bank/${groupId}`)
}

export {
  fetchGroupMemberAccountList,
}
