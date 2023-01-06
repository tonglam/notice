package com.tong.notice.webmagic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tong.notice.constant.Constant;
import com.tong.notice.domain.NoticeData;
import com.tong.notice.domain.TelegramNoticeData;
import com.tong.notice.utils.HttpUtils;
import com.tong.notice.utils.JsonUtils;
import com.tong.notice.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * Create by tong on 2022/12/30
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HermesPipeline implements Pipeline {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<NoticeData> list = Lists.newArrayList();

        List<String> idList = (List<String>) resultItems.getAll().get("id");
        List<String> nameList = (List<String>) resultItems.getAll().get("name");
        List<String> priceList = (List<String>) resultItems.getAll().get("price");
        List<String> imgList = (List<String>) resultItems.getAll().get("img");
        List<String> hrefList = (List<String>) resultItems.getAll().get("href");

        for (int i = 0; i < nameList.size(); i++) {
            list.add(
                    new NoticeData()
                            .setId(StringUtils.substringAfter(idList.get(i), "-link-"))
                            .setName(nameList.get(i))
                            .setPrice(priceList.get(i))
                            .setImg("https://" + StringUtils.substringBetween(imgList.get(i), "//", "?"))
                            .setHref("https://www.hermes.com" + hrefList.get(i))
                            .setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATETIME)))
            );
        }
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        String key = Constant.REDIS_KEY;
        // if new entry
        List<String> oldIdList = Lists.newArrayList();
        this.redisTemplate.opsForHash().keys(key).forEach(id -> oldIdList.add(id.toString()));
        list.forEach(o -> {
            if (!oldIdList.contains(o.getId())) {
                o.setNewEntry(true);
                // push publication
                List<String> userList = Lists.newArrayList();
                RedisUtils.getSetByKey(Constant.NOTIFICATION_KEY).ifPresent(i -> i.stream().map(k -> (String) k).forEach(userList::add));
                String caption = "[NEW]" + o.getName() + " - " + o.getPrice() + "\r\n" + o.getHref();
                TelegramNoticeData data = new TelegramNoticeData()
                        .setImgUrl(o.getImg())
                        .setImgCaption(caption)
                        .setNewEntry(true)
                        .setUserList(userList);
                try {
                    HttpUtils.httpPost(Constant.NOTIFICATION_IMAGE, JsonUtils.obj2json(data));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // redis cache
        Map<String, Map<String, Object>> cacheMap = Maps.newHashMap();
        Map<String, Object> valueMap = Maps.newHashMap();
        list.forEach(o -> valueMap.put(o.getId(), o));
        cacheMap.put(key, valueMap);
        RedisUtils.removeCacheByKey(key);
        RedisUtils.pipelineHashCache(cacheMap, -1, null);
    }

}
