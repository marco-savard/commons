package com.marcosavard.commons.time;

import junit.framework.Assert;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class DateUtilTest {

    @Test
    public void givenDate_whenJanuaryToMarch_thenGivesQuarter1() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        int quarter = DateUtil.findQuarter(date);
        Assert.assertEquals(1, quarter);

        date = LocalDate.of(2020, 3, 31);
        quarter = DateUtil.findQuarter(date);
        Assert.assertEquals(1, quarter);
    }

    @Test
    public void givenDate_whenOctoberToDecember_thenGivesQuarter4() {
        LocalDate date = LocalDate.of(2020, 10, 1);
        int quarter = DateUtil.findQuarter(date);
        Assert.assertEquals(4, quarter);

        date = LocalDate.of(2020, 12, 31);
        quarter = DateUtil.findQuarter(date);
        Assert.assertEquals(4, quarter);
    }


}
