package com.greak;

import com.greak.common.utils.DateUtils;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateUnitTest {

    @Test
    public void dateConversionTest() throws Exception {
        long expectedValue = 1500490389431L;
        long dateInMillis = DateUtils.getTimeInMillis("2017-07-19T18:51:15.114431Z");

        assertEquals(expectedValue, dateInMillis);
    }
}