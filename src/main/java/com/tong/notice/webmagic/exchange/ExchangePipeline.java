package com.tong.notice.webmagic.exchange;

import com.google.common.collect.Maps;
import com.tong.notice.constant.Constant;
import com.tong.notice.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Create by tong on 2022/12/30
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangePipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        String from = resultItems.get("from");
        String to = resultItems.get("to");
        String output = StringUtils.substringBetween(resultItems.get("exchange").toString(), "<output>", "</output>");
        String currency = StringUtils.substringBetween(output, "=", to).trim();
        // redis cache
        String key = String.format(Constant.EXCHANGE_KEY, from, to);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATETIME));
        Map<String, Map<String, Object>> cacheMap = Maps.newHashMap();
        Map<String, Object> valueMap = Maps.newHashMap();
        valueMap.put(date, currency);
        cacheMap.put(key, valueMap);
        RedisUtils.pipelineHashCache(cacheMap, -1, null);
    }

}
