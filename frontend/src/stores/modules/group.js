
const state = {
  groupInfo :  JSON.parse(sessionStorage.getItem('groupInfo')),
  groupMemberCnt : JSON.parse(sessionStorage.getItem('groupMemberCnt'))
};

const mutations = {
  setGroupInfo(state, groupData) {
    state.groupInfo = groupData
    sessionStorage.setItem('groupInfo',JSON.stringify(groupData))
  },
  setGroupMemberCnt(state,groupData) {
    state.groupMemberCnt = groupData.studentCnt
    sessionStorage.setItem('groupMemberCnt',JSON.stringify(groupData.studentCnt))
  }
}

const actions = {

};

const getters = {
    
}

export default {
    state,
    mutations,
    getters,
    actions
}
