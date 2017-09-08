package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SiteServiceTest extends BaseTest {

    @Autowired
    private SiteService siteService;

    @Test
    public void getAllSiteIdsTest() throws EarthException {
        Assert.assertTrue(siteService.getAllSiteIds().size() > 0);
    }

    @Test
    public void getAllSitesTest() throws EarthException {
        Assert.assertTrue(siteService.getAllSites().size() > 0);
    }
}
