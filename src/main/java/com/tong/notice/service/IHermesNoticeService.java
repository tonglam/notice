package com.tong.notice.service;

import com.tong.notice.domian.NoticeData;

import java.util.List;

/**
 * Create by tong on 2022/12/30
 */
public interface IHermesNoticeService {

    void createHermesNotice();

    List<NoticeData> queryHermesInfoList();

}
