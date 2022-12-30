package com.tong.notice;

import com.tong.notice.service.IHermesNoticeService;
import com.tong.notice.domian.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Create by tong on 2022/12/30
 */
public class HermesTest extends NoticeApplicationTests {

    @Autowired
    private IHermesNoticeService hermesService;

    @Test
    void createHermesNotice() {
        this.hermesService.createHermesNotice();
    }

    @Test
    void queryHermesInfoList() {
        List<Notice> list = this.hermesService.queryHermesInfoList();
        System.out.println(1);
    }

}
