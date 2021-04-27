package com.komencash.backend.service;

import com.komencash.backend.dto.teacher.*;
import com.komencash.backend.entity.teacher.Teacher;
import com.komencash.backend.repository.TeacherRepository;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Random;

@Service
public class TeacherService{

    @Autowired
    JwtService jwtService;

    @Autowired
    TeacherRepository teacherRepository;


    public boolean saveTeacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        teacherRepository.save(new Teacher(teacherInsertUpdateRequest));
        return true;
    }


    public TeacherDupCheckByEmailResponse dupCheckByEmail(String email){
        return new TeacherDupCheckByEmailResponse(teacherRepository.findByEmail(email).orElseGet(Teacher::new));
    }


    public boolean dupCheckByNickname(String nickname){
        return teacherRepository.findByNickname(nickname).orElse(null) == null ? true : false;
    }


    public boolean loginTeacher(TeacherLoginRequest teacherLoginRequest, HttpServletResponse response) {
        Teacher loginResult = teacherRepository.findByEmailAndPassword(teacherLoginRequest.getEmail(), teacherLoginRequest.getPassword()).orElseGet(Teacher::new);

        if(loginResult.getId() == 0) return false;

        response.setHeader("auth-token", jwtService.create(loginResult));
        return true;

    }


    public TeacherSelectResponse findTeacher(int teacher_id) {
        return new TeacherSelectResponse(teacherRepository.findById(teacher_id).orElseGet(Teacher::new));
    }

    public boolean updateTeacher(TeacherInsertUpdateRequest teacherInsertUpdateRequest) {
        Teacher teacher = teacherRepository.findById(teacherInsertUpdateRequest.getId()).orElseGet(Teacher::new);
        teacher.updateTeacher(teacherInsertUpdateRequest);
        teacherRepository.save(teacher);
        return true;
    }


    public boolean deleteTeacher(int teacher_id) {
        teacherRepository.deleteById(teacher_id);
        return true;
    }


    public boolean updateTeacherPassword(TeacherPasswordUpdateRequest teacherPasswordUpdateRequest) {
        Teacher teacher = teacherRepository.findById(teacherPasswordUpdateRequest.getId()).orElse(null);

        if(teacher == null) return false;

        Teacher updateTeacher = Teacher.builder()
                .id(teacher.getId())
                .email(teacher.getEmail())
                .password(teacherPasswordUpdateRequest.getPassword())
                .nickname(teacher.getNickname())
                .phoneNumber(teacher.getPhoneNumber())
                .build();

        teacherRepository.save(updateTeacher);
        return true;
    }


    public TeacherAuthByPhoneResponse authTeacherByPhone(String phoneNum){

        Teacher resultTeacher = teacherRepository.findByPhoneNumber(phoneNum).orElseGet(Teacher::new);

        if(resultTeacher.getId() == 0) return new TeacherAuthByPhoneResponse(0, null);

        // 인증 번호 생성
       int authNum = makeAuthNumber();

        // 메세지 전송
        sendMessage(authNum, phoneNum);

        return new TeacherAuthByPhoneResponse(authNum, resultTeacher.getEmail());
    }

    public int makeAuthNumber(){
        Random rand = new Random();
        String authNumber = "";
        for(int i=0; i<4; i++){
            String ran = Integer.toString(rand.nextInt(10));
            authNumber += ran;
        }
        return Integer.parseInt(authNumber);
    }

    public void sendMessage(int authNum, String phoneNum) {
//        String api_key = "NCSBCNFO2FFJM0BV";
//        String api_secret = "B1FFZEW61CGA5LW8MYV35WM7XYXZJNCB";
        String api_key = "";
        String api_secret = "";
        net.nurigo.java_sdk.api.Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNum);    // 수신전화번호
        params.put("from", "01021147305");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "Komenta : 인증번호는 " + "[ "+authNum+" ]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version
    }
}
