package com.irunninglog.api;

import com.irunninglog.api.Unit;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UnitTest {

    @Test
    public void sanity() {
        assertNotNull(Unit.English);
        assertNotNull(Unit.Metric);
    }

}