import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 그룹생성
function saveGroup(groupData) {
    return instance.get(`group`, groupData) 
}

// 그룹 수정
function modifyGroup(groupData) {
    return instance.put(`group`, groupData) 
}

// 그룹 삭제
function deleteGroup(groupId) {
    return instance.delete(`group/${groupId}`) 
}

// 그룹조회
function fetchGroupList() {
    return instance.get(`group/group_list`) 
}

// 그룹상세조회
function fetchGroup(teacherId) {
    return instance.get(`group/group_list/${teacherId}`) 
}

//그룹원 상세 조회
function fetchGroupMember(groupId) {
    return instance.get(`student/${groupId}`)
}

export {
  saveGroup,
  modifyGroup,
  deleteGroup,
  fetchGroupList,
  fetchGroup,
  fetchGroupMember
}