package com.tong.notice.controller;

import com.tong.notice.domian.Notice;
import com.tong.notice.service.IHermesNoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by tong on 2022/12/30
 */
@Slf4j
@RestController
@RequestMapping("/hermes")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {

    private final IHermesNoticeService hermesNoticeService;

    @GetMapping("/queryHermesInfoList")
    public List<Notice> queryHermesInfoList() {
        return this.hermesNoticeService.queryHermesInfoList();
    }

}
