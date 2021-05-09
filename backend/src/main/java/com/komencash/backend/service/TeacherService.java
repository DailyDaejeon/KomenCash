package com.komencash.backend.service;

import com.komencash.backend.dto.teacher.*;
import com.komencash.backend.entity.teacher.Teacher;
import com.komencash.backend.repository.TeacherRepository;
import net.nurigo.java_sdk.api.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class TeacherService{

    @Autowired
    JwtService jwtService;

    @Autowired
    TeacherRepository teacherRepository;


    public boolean addTeacher(TeacherAddUpdateRequestDto teacherAddUpdateRequestDto) {
        teacherRepository.save(new Teacher(teacherAddUpdateRequestDto));
        return true;
    }


    public TeacherFindByEmailResponseDto findTeacherByEmail(String email){
        return new TeacherFindByEmailResponseDto(teacherRepository.findByEmail(email).orElse(null));
    }


    public boolean findTeacherByNickname(String nickname){
        return teacherRepository.findByNickname(nickname).orElse(null) == null ? true : false;
    }


    public TeacherFindResponseDto teacherLogin(TeacherLoginRequestDto teacherLoginRequestDto) {

        String email = teacherLoginRequestDto.getEmail();
        String password = teacherLoginRequestDto.getPassword();
        Teacher teacher = teacherRepository.findByEmailAndPassword(email, password).orElse(null);
        if(teacher == null) return null;

        TeacherFindResponseDto teacherFindResponseDto = new TeacherFindResponseDto(teacher);
        return teacherFindResponseDto;
    }


    public TeacherFindResponseDto findTeacher(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(teacher == null) return null;

        return new TeacherFindResponseDto(teacher);
    }

    public boolean updateTeacher(TeacherAddUpdateRequestDto teacherAddUpdateRequestDto) {
        Teacher teacher = teacherRepository.findById(teacherAddUpdateRequestDto.getId()).orElse(null);
        if(teacher == null) return false;

        teacher.updateTeacher(teacherAddUpdateRequestDto);
        teacherRepository.save(teacher);
        return true;
    }


    public boolean deleteTeacher(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if(teacher == null) return false;

        teacherRepository.delete(teacher);
        return true;
    }


    public boolean updateTeacherPassword(TeacherUpdatePasswordRequestDto teacherUpdatePasswordRequestDto) {
        Teacher teacher = teacherRepository.findById(teacherUpdatePasswordRequestDto.getId()).orElse(null);
        if(teacher == null) return false;

        teacher.updatePasswordTeacher(teacherUpdatePasswordRequestDto);
        teacherRepository.save(teacher);
        return true;
    }


    public TeacherFindByPhoneNumberResponseDto findTeacherByPhoneNumber(String phoneNum){

        Teacher resultTeacher = teacherRepository.findByPhoneNumber(phoneNum).orElseGet(Teacher::new);

        if(resultTeacher.getId() == 0) return new TeacherFindByPhoneNumberResponseDto(null, null);

        // 인증 번호 생성
       String authNum = makeAuthNumber();

        // 메세지 전송
        sendMessage(authNum, phoneNum);

        return new TeacherFindByPhoneNumberResponseDto(authNum, resultTeacher.getEmail());
    }

    public String makeAuthNumber(){
        Random rand = new Random();
        String authNumber = "";
        for(int i=0; i<4; i++){
            String ran = Integer.toString(rand.nextInt(10));
            authNumber += ran;
        }
        return authNumber;
    }

    public void sendMessage(String authNum, String phoneNum) {
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
