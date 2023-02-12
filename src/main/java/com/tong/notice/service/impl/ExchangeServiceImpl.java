package com.tong.notice.service.impl;

import com.google.common.collect.Maps;
import com.tong.notice.constant.Constant;
import com.tong.notice.service.IExchangeService;
import com.tong.notice.webmagic.exchange.ExchangePageProcessor;
import com.tong.notice.webmagic.exchange.ExchangePipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.Map;

/**
 * Created by tong on 2023/01/11
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeServiceImpl implements IExchangeService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void createExchangeNotice() {
        // get fetch list
        Map<String, String> fetchMap = Maps.newHashMap();
        String key = Constant.EXCHANGE_FETCH_KEY;
        this.redisTemplate.opsForHash().entries(key).forEach((k, v) -> fetchMap.put((String) k, (String) v));
        // spider
        fetchMap.forEach((from, to) ->
                Spider
                        .create(new ExchangePageProcessor())
                        .addUrl(String.format("https://themoneyconverter.com/zh-CN/%s/%s", from, to))
                        .addPipeline(new ExchangePipeline())
                        .thread(1)
                        .run());
    }

}
