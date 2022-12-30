package com.tong.notice;

import com.tong.notice.service.IHermesNoticeService;
import com.tong.notice.domian.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create by tong on 2022/12/30
 */
public class HermesTest extends NoticeApplicationTests {

    @Autowired
    private IHermesNoticeService hermesNoticeService;

    @Test
    void createHermesNotice() {
        this.hermesNoticeService.createHermesNotice();
    }

    @Test
    void queryHermesInfoList() {
        List<Notice> list = this.hermesNoticeService.queryHermesInfoList();
        System.out.println(1);
    }

    @Test
    void test(){
        List<Notice> oldList = this.hermesNoticeService.queryHermesInfoList();
        String updateTime = oldList.get(0).getUpdateTime();
        LocalDateTime localDateTime = LocalDateTime.now();
        // refresh every hour
        if (localDateTime.minusHours(1).isAfter(LocalDateTime.parse(updateTime.replaceAll(" ", "T")))) {
            System.out.println(1);
        }
        System.out.println(1);
    }

}
