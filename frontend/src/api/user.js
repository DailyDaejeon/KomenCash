import { setInterceptors } from './config/interceptors'
const instance = setInterceptors()

// 회원가입 API
function registerUser(teacherData) {
    return instance.post('teacher', teacherData);
}

// 로그인 API
function loginUser(teacherData) {
    return instance.post('teacher/login', teacherData);
}

// 내 정보 가져오는 API
function fetchMyInfo(teacherId) {
    return instance.get(`teacher/${teacherId}`)
}

// 내 정보 수정
function updateMyInfo(userData) {
    return instance.put('teacher', userData)
}

// 회원탈퇴
function deleteMyInfo(teacherId) {
    return instance.delete(`teacher/${teacherId}`)
}

//아이디 가입 여부 확인 선생님 회원정보에 존재하는 경우 그 id와 email을 반환
function userIdChk(email) {
    return instance.get(`teacher/dup_chk_email/${email}`)
}

//닉네임 중복 확인
function dupNickNameChk(nickname) {
    return instance.get(`teacher/dup_chk_nickname/${nickname}`)
}

//비밀번호 찾기 - 새 비밀번호 변경
function changePw(teacherData) {
    return instance.put(`teacher/change_pw`,teacherData)
}

// 휴대폰 인증
function phoneAuth(userPhonenum) {
    // 이메일과 인증번호를 반환
    return instance.get(`teacger/phone_auth`, userPhonenum) 
}


export {
    registerUser,
    loginUser,
    fetchMyInfo,
    updateMyInfo,
    deleteMyInfo,
    phoneAuth,
    userIdChk,
    dupNickNameChk,
    changePw,
}