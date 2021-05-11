package com.komencash.backend.service;

import com.komencash.backend.dto.statistic.StatisticListAddRequestDto;
import com.komencash.backend.dto.statistic.StatisticListFindResponseDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.statistic.StatisticList;
import com.komencash.backend.entity.statistic.StatisticListDetail;
import com.komencash.backend.entity.student.Student;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StatisticListDetailRepository;
import com.komencash.backend.repository.StatisticListRepository;
import com.komencash.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    StatisticListRepository statisticListRepository;

    @Autowired
    StatisticListDetailRepository statisticListDetailRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;


    public boolean addStatisticList(StatisticListAddRequestDto statisticListAddRequestDto){

        Group group = groupRepository.findById(statisticListAddRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        StatisticList statisticList = new StatisticList(statisticListAddRequestDto, group);
        StatisticList createdStatisticList = statisticListRepository.save(statisticList);

        List<Student> students = studentRepository.findByJob_Group_Id(statisticListAddRequestDto.getGroupId());
        students.forEach(student -> statisticListDetailRepository.save(new StatisticListDetail(student, createdStatisticList)));
        return true;
    }


    public List<StatisticListFindResponseDto> findStatisticListList(int groupId) {

        List<StatisticListFindResponseDto> statisticListFindResponseDtos = new ArrayList<>();

        List<StatisticList> statisticLists = statisticListRepository.findByGroup_Id(groupId);
        statisticLists.forEach(statisticList -> {
            if(!statisticList.isSend()) statisticListFindResponseDtos.add(new StatisticListFindResponseDto(statisticList));
        });

        return statisticListFindResponseDtos;
    }


    public boolean deleteStatistidList(int statisticListId){
        StatisticList statisticList = statisticListRepository.findById(statisticListId).orElse(null);
        if(statisticList == null) return false;

        statisticListRepository.delete(statisticList);
        return true;
    }
}
