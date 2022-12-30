package com.tong.notice.task;

import com.tong.notice.domian.Notice;
import com.tong.notice.service.IHermesNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create by tong on 2022/12/31
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshTask {

    private final IHermesNoticeService hermesNoticeService;

    @Scheduled(cron = "0 */5 0-23 * * *")
    public void refreshHermes() {
        log.info("start running refreshHermes!");
        List<Notice> oldList = this.hermesNoticeService.queryHermesInfoList();
        String updateTime = oldList.get(0).getUpdateTime();
        LocalDateTime localDateTime = LocalDateTime.now();
        // refresh every hour
        if (localDateTime.minusHours(1).isAfter(LocalDateTime.parse(updateTime.replaceAll(" ", "T")))) {
            log.info("start refresh hermes info!");
            this.hermesNoticeService.createHermesNotice();
        }
        log.info("last update in one hour, do not need to refresh!");
    }

}


