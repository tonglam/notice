package com.tong.notice.webmagic.exchange;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Create by tong on 2022/12/30
 */
public class ExchangePageProcessor implements PageProcessor {

    private final Site site = Site.me().setRetryTimes(5).setSleepTime(100).setUserAgent("Chrome 108.0.0.0");

    @Override
    public void process(Page page) {
        String request = page.getRequest().toString();
        String exchangeArea = StringUtils.substringBetween(request, "/zh-CN/", "',");
        page.putField("from", StringUtils.substringBefore(exchangeArea, "/"));
        page.putField("to", StringUtils.substringAfter(exchangeArea, "/"));
        page.putField("exchange", page.getHtml().xpath("//div[@class='tab-panel selected']").$("output").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

}
