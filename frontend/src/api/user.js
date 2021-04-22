import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

//회원가입 API
function registerUser(userData) {
    return instance.post('member/join', userData);
}

//로그인 API
function loginUser(userData) {
    return instance.post('member/login', userData);
}

// 내 정보 가져오는 API
function fetchMyInfo(userId) {
    return instance.get(`member/info`,{
        params:{
            u_id: userId
        }
    })
}

// 내 정보 수정
function updateMyInfo(userData) {
    return instance.put('member/update', userData)
}

// 회원탈퇴
function deleteMyInfo(userId) {
    return instance.delete(`member/delete`, {
        params: { u_id: userId }
    })
}

// 이메일 인증
function emailAuth(userId) {
    //비밀번호 찾기
    return instance.get(`check/sendEmail`, userId)
}

//아이디 가입 여부 확인
function userIdChk(userId) {
    return instance.get(`member/chk_id`, {
        params: { u_email: userId }
    })
}

//아이디 중복 확인
function dupIdChk(userId) {
    return instance.get(`member/dup_id_chk`, {
        params: { u_email: userId }
    })
}

//닉네임 중복 확인
function dupNickNameChk(userNickName) {
    return instance.get(`member/dup_nickname_chk`, {
        params: { u_nickname: userNickName }
    })
}

//비밀번호 찾기 - 새 비밀번호 변경
function changePw(userData) {
    return instance.put(`member/change_pw`,userData)
}

// 휴대폰 인증
function phoneAuth(userPhonenum) {
    //아이디찾기
    return instance.get(`member/find_id`, {
        params: { u_phone_number: userPhonenum }
    }) 
}


export {
    registerUser,
    loginUser,
    fetchMyInfo,
    updateMyInfo,
    deleteMyInfo,
    emailAuth,
    phoneAuth,
    userIdChk,
    dupNickNameChk,
    changePw,
    dupIdChk   
}