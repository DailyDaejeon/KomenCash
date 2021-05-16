
const state = {
  groupInfo :  JSON.parse(sessionStorage.getItem('groupInfo')),
  groupMemberCnt : JSON.parse(sessionStorage.getItem('groupMemberCnt'))
};

const mutations = {
  deleteGroupInfo(state) {
    console.log('여기들어왔음')
    state.groupInfo = ''
    state.groupMemberCnt = 0
    sessionStorage.clear()
  },
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
