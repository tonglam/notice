package com.tong.notice.service.impl;

import com.google.common.collect.Lists;
import com.tong.notice.service.IHermesNoticeService;
import com.tong.notice.constant.Constant;
import com.tong.notice.domian.Notice;
import com.tong.notice.webmagic.HermesPageProcessor;
import com.tong.notice.webmagic.HermesPipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * Create by tong on 2022/12/30
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HermesNoticeService implements IHermesNoticeService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void createHermesNotice() {
        Spider
                .create(new HermesPageProcessor())
                .addUrl("https://www.hermes.com/uk/en/category/women/bags-and-small-leather-goods/bags-and-clutches/#")
                .addPipeline(new HermesPipeline())
                .thread(1)
                .run();
    }

    @Override
    public List<Notice> queryHermesInfoList() {
        List<Notice> list = Lists.newArrayList();
        String key = Constant.REDIS_KEY;
        this.redisTemplate.opsForHash().entries(key).forEach((k, v) -> list.add((Notice) v));
        return list;
    }

}