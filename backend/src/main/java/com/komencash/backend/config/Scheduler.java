package com.komencash.backend.config;

import com.komencash.backend.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Autowired
    BankService bankService;

    @Scheduled(cron = "0 * 9 * * ?")
    public void cronJobSch() throws Exception {
        System.out.println("Scheduler 시작");
        bankService.updateFinancialProductHistory();
        System.out.println("예금 만기 업데이트 완료");
    }

}
