package com.komencash.backend.service;

import com.komencash.backend.dto.statistic.StatisticListAddRequestDto;
import com.komencash.backend.entity.group.Group;
import com.komencash.backend.entity.statistic.StatisticList;
import com.komencash.backend.repository.GroupRepository;
import com.komencash.backend.repository.StatisticListDetailRepository;
import com.komencash.backend.repository.StatisticListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

    @Autowired
    StatisticListRepository statisticListRepository;

    @Autowired
    StatisticListDetailRepository statisticListDetailRepository;

    @Autowired
    GroupRepository groupRepository;

    public boolean addStatisticList(StatisticListAddRequestDto statisticListAddRequestDto){

        Group group = groupRepository.findById(statisticListAddRequestDto.getGroupId()).orElse(null);
        if(group == null) return false;

        StatisticList statisticList = new StatisticList(statisticListAddRequestDto, group);
        statisticListRepository.save(statisticList);
        return true;
    }
}
