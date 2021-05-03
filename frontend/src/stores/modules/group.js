
const state = {
  groupInfo :  JSON.parse(sessionStorage.getItem('groupInfo'))
};

const mutations = {
  setGroupInfo(state, groupData) {
      state.groupInfo = groupData
      sessionStorage.setItem('groupInfo',JSON.stringify(groupData))
    },
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
