package com.tong.notice.webmagic.hermes;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Create by tong on 2022/12/30
 */
public class HermesPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(5).setSleepTime(100).setUserAgent("Chrome 108.0.0.0");

    @Override
    public void process(Page page) {
        page.putField("id", page.getHtml().xpath("//div[@class='product-item']").$("a", "id").all());
        page.putField("href", page.getHtml().xpath("//div[@class='product-item']").$("a", "href").all());
        page.putField("img", page.getHtml().xpath("//div[@class='product-item']").$("img", "src").all());
        page.putField("name", page.getHtml().xpath("//span[@class='product-item-name']//tidyText()").all());
        page.putField("price", page.getHtml().xpath("//span[@class='price medium']/tidyText()").all());
    }

    @Override
    public Site getSite() {
        return site;
    }

}
