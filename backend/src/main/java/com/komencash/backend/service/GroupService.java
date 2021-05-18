package com.komencash.backend.service;

import com.komencash.backend.dto.certificate.CertificateAddUpdateRequestDto;
import com.komencash.backend.dto.group.GroupAddUpdateRequestDto;
import com.komencash.backend.dto.group.GroupFindResponseDto;
import com.komencash.backend.dto.job.JobAddUpdateRequestDto;
import com.komencash.backend.dto.law.LawAddUpdateRequestDto;
import com.komencash.backend.dto.store.StoreItemAddUpdateRequestDto;
import com.komencash.backend.dto.tax.TaxHistoryAddUpdateRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.job.RecruitType;
import com.komencash.backend.entity.tax.TaxHistory;
import com.komencash.backend.entity.teacher.Teacher;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StudentRepository;
import com.komencash.backend.repository.TaxHistoryRepository;
import com.komencash.backend.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TaxHistoryRepository taxHistoryRepository;

    @Autowired
    JobService jobService;

    @Autowired
    LawService lawService;

    @Autowired
    CertificateService certificateService;

    @Autowired
    StoreService storeService;


    public int addGroup(GroupAddUpdateRequestDto groupAddUpdateRequestDto){
        Teacher teacher = teacherRepository.findById(groupAddUpdateRequestDto.getTeacherId()).orElse(null);
        if(teacher == null) return 0;

        Group group = new Group(groupAddUpdateRequestDto, teacher);
        group = groupRepository.save(group);
        taxHistoryRepository.save(new TaxHistory(new TaxHistoryAddUpdateRequestDto(0, "그룹 생성",group.getId()), group, 0));
        return group.getId();
    }


    public List<GroupFindResponseDto> findGroup(int teacherId){
        List<GroupFindResponseDto> groupFindResponseDtos = new ArrayList<>();

        List<Group> groups = groupRepository.findByTeacher_Id(teacherId);
        groups.forEach(group -> {
            int studentsSize = studentRepository.countByJob_Group_Id(group.getId());
            groupFindResponseDtos.add(new GroupFindResponseDto(group, studentsSize));
        });

        return groupFindResponseDtos;
    }


    public boolean updateGroup(GroupAddUpdateRequestDto groupAddUpdateRequestDto) {
        Group group = groupRepository.findById(groupAddUpdateRequestDto.getId()).orElse(null);
        if(group == null) return false;

        group.updateGroup(groupAddUpdateRequestDto);
        groupRepository.save(group);
        return true;
    }


    public boolean deleteGroup(int groupId){
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) return false;

        groupRepository.delete(group);
        return true;
    }

    public boolean initializeGroup(int groupId){

        jobService.addJob(new JobAddUpdateRequestDto("국회의장", 310, "투표 및 회의 진행, 법률 제안", "없음", 1, RecruitType.vote, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("국무총리", 300, "투표 및 회의 진행, 법률 제안", "없음", 1, RecruitType.vote, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("은행원", 300, "학생 월급 최종 지급, 금융 상품 관리", "없음", 2, RecruitType.resume, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("경찰", 250, "법률 위반 행위 감시 및 신고 접수", "없음", 1, RecruitType.resume, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("국세청 직원", 260, "세금 사용 내역 기업", "없음", 1, RecruitType.resume, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("통계청 직원", 290, "그룹 내 제출물 관리", "없음", 1, RecruitType.resume, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("신용 평가 위원", 250, "학생 신용 등급 관리", "없음", 1, RecruitType.resume, groupId));
        jobService.addJob(new JobAddUpdateRequestDto("무직", 0, "구직 활동 등", "없음", 1000, RecruitType.vote, groupId));

        lawService.addLaw(new LawAddUpdateRequestDto("도로법", 1, 1, "복도를 뛰어다니면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("도로법", 1, 2, "우측 통행해야 한다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("질서법", 1, 1, "공용 물건이나 타인의 물건을 파손하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("교육법", 1, 1, "수업에 방해되는 언행을 하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("교육법", 1, 2, "사전 통보나 합당한 이유 없이 지각은 금지한다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("형사법", 1, 1, "타인에게 폭력을 행사하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("형사법", 1, 2, "타인에게 욕설하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("형사법", 1, 3, "타인에 대한 소문이나 싫어하는 말을 하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("형사법", 2, 1, "타인의 물건을 훔치면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("형사법", 3, 1, "교내에 허락되지 않은 물건을 반입하면 안된다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("환경법", 1, 1, "쓰레기는 정해진 곳에 버려야한다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("환경법", 1, 2, "분리수거를 제대로 해야 한다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("환경법", 1, 3, "음식물쓰레기를 제대로 처리해야 한다.", groupId));
        lawService.addLaw(new LawAddUpdateRequestDto("금융법", 1, 1, "학급 경제 활동의 문서를 위조하면 안된다.", groupId));

        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 1급", "20권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 2급", "16권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 3급", "14권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 4급", "12권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 5급", "10권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 6급", "8권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 7급", "6권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 8급", "4권 이상 도서 대출", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("독서왕 9급", "2권 이상 도서 대출", groupId));

        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 1급", "교과서 노래 10곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 2급", "교과서 노래 8곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 3급", "교과서 노래 7곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 4급", "교과서 노래 6곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 5급", "교과서 노래 5곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 6급", "교과서 노래 4곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 7급", "교과서 노래 3곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 8급", "교과서 노래 2곡 통과", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("음악의 신 9급", "교과서 노래 1곡 통과", groupId));

        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 1급", "95점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 2급", "85점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 3급", "80점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 4급", "75점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 5급", "70점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 6급", "65점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 7급", "60점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 8급", "55점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("수학왕 9급", "50점 이상", groupId));

        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 1급", "95점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 2급", "85점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 3급", "80점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 4급", "75점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 5급", "70점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 6급", "65점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 7급", "60점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 8급", "55점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("사회왕 9급", "50점 이상", groupId));

        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 1급", "95점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 2급", "85점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 3급", "80점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 4급", "75점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 5급", "70점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 6급", "65점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 7급", "60점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 8급", "55점 이상", groupId));
        certificateService.addCertificate(new CertificateAddUpdateRequestDto("과학왕 9급", "50점 이상", groupId));

        storeService.addStoreItem(new StoreItemAddUpdateRequestDto("방학숙제 1회 면제권", 120, groupId));
        storeService.addStoreItem(new StoreItemAddUpdateRequestDto("일기 1회 면제권", 80, groupId));
        storeService.addStoreItem(new StoreItemAddUpdateRequestDto("급식 우선권", 70, groupId));
        storeService.addStoreItem(new StoreItemAddUpdateRequestDto("노래 신청권", 30, groupId));
        storeService.addStoreItem(new StoreItemAddUpdateRequestDto("선생님 물건 대여권", 200, groupId));

        return true;
    }
}
