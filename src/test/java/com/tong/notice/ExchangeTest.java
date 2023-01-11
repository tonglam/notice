package com.tong.notice;

import com.tong.notice.service.IExchangeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by tong on 2022/12/30
 */
public class ExchangeTest extends NoticeApplicationTests {

    @Autowired
    private IExchangeService exchangeService;

    @Test
    void createExchangeNotice() {
        this.exchangeService.createExchangeNotice();
    }

}
