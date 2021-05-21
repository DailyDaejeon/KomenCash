import { loginUser, 
    updateMyInfo } from '@/api/user.js'
import jwtDecode from 'jwt-decode'

const state = {
    adminAuth: 0,
    token: sessionStorage.getItem('auth-token'),
    isLogin: sessionStorage.getItem('auth-token') === null ? false : true,
    isLoginError: false,
    userInfo: sessionStorage.getItem('auth-token') === null ? {
    "email": null,
    "id": null,
    "nickname": null,
    "password": null,
    "phoneNumber": null
    } : jwtDecode(sessionStorage.getItem('auth-token')),
    isPasswordConfirmed: false,
    currentTab: 0,
    v: {},
};

const mutations = {
    setToken(state, token) {
        state.token = token
        sessionStorage.setItem('auth-token', token)
        state.isLogin = true
        state.isLoginError = false
        state.userInfo = jwtDecode(token)
    },
    fetchInfo(state, userData) {
        state.userInfo = userData
    },
    setEmail(state, email) {
        state.userInfo.email = email
    },
    setPhonenum(state, phone) {
        state.userInfo.phoneNumber = phone

    },
    logout(state) {
        console.log('여기들어왔음')
        state.token = ''
        state.isLogin = false
        state.isLoginError = false
        sessionStorage.clear()
    },
    loginError(state) {
        state.isLoginError = true
    },
    confirmComplete(state) {
        state.isPasswordConfirmed = true
    },
    enteredAccount(state) {
        state.isPasswordConfirmed = false
    },
    setModifyUserInfo(state, userData) {
        state.userInfo.nickname = userData.nickname
        state.userInfo.phoneNumber = userData.phoneNumber
    },
    setCurrentTab (newValue) {
        this.state.currentTab = newValue;
    },
    setValidation(newValue){
      this.state.v = newValue;
    }
}

const actions = {
    async LOGIN({ commit }, userData) {
        const response = await loginUser(userData);
        if (response.data['auth-token']) {
            commit('setToken', response.data['auth-token'])
        } else {
            commit('loginError')
        }
        return response
    },
    async MODIFY({ commit }, userData) {
        const response = await updateMyInfo(userData);
        commit('setToken',response.data['auth-token'])
    },
    async PASSWORDCONFIRM({ commit }, userData) {
        const response = await loginUser(userData)
        if (response.data['auth-token']) {
            commit('confirmComplete')
            commit('setToken', response.data['auth-token'])
        } else {
            commit('loginError')
        }
        return response
    },


};

const getters = {
    
    fetchedUserInfo(state) {
        return state.userInfo
    },
}

export default {
    state,
    mutations,
    getters,
    actions
}
