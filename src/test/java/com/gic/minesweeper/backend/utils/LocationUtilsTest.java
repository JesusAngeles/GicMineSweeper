package com.gic.minesweeper.backend.utils;

import org.junit.Assert;
import org.junit.Test;

public class LocationUtilsTest {

    @Test
    public void getLocation() {
        Assert.assertNull(LocationUtils.getLocation(null));
        Assert.assertNull(LocationUtils.getLocation(""));
        Assert.assertNull(LocationUtils.getLocation("a"));
        Assert.assertNull(LocationUtils.getLocation("aa"));
        Assert.assertNull(LocationUtils.getLocation("aaa"));
        Assert.assertNull(LocationUtils.getLocation("aa11"));
        Assert.assertNull(LocationUtils.getLocation("a11"));

        Assert.assertNotNull(LocationUtils.getLocation("a1"));
        Assert.assertEquals(1, LocationUtils.getLocation("a1").row());
        Assert.assertEquals(1, LocationUtils.getLocation("a1").column());

        Assert.assertNotNull(LocationUtils.getLocation("b3"));
        Assert.assertEquals(2, LocationUtils.getLocation("b3").row());
        Assert.assertEquals(3, LocationUtils.getLocation("b3").column());

        Assert.assertNotNull(LocationUtils.getLocation("c3"));
        Assert.assertEquals(3, LocationUtils.getLocation("c3").row());
        Assert.assertEquals(3, LocationUtils.getLocation("c3").column());
    }

    @Test
    public void getRowNumber() {
        Assert.assertEquals(1, LocationUtils.getRowNumber(1, 4));
        Assert.assertEquals(1, LocationUtils.getRowNumber(4, 4));
        Assert.assertEquals(2, LocationUtils.getRowNumber(5, 4));
        Assert.assertEquals(2, LocationUtils.getRowNumber(8, 4));
    }

    @Test
    public void getColumnNumber() {
        Assert.assertEquals(1, LocationUtils.getColumnNumber(1, 4));
        Assert.assertEquals(4, LocationUtils.getColumnNumber(4, 4));
        Assert.assertEquals(1, LocationUtils.getColumnNumber(5, 4));
        Assert.assertEquals(4, LocationUtils.getColumnNumber(8, 4));
    }
}
