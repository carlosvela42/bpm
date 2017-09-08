package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrCal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LicenseHistoryServiceTest extends BaseTest {
    @Autowired
    private LicenseHistoryService licenseHistoryService;

    @Autowired
    private PreparingDataService data;

    private static boolean setUpfinished = false;

    private static final int AVAILABLE_LICENSE_COUNT = 20;
    private static final int USE_LICENSE_COUNT = 20;
    private static final int TEST_CASE_1_RESULT = 15;
    private static final int TEST_CASE_2_RESULT = 8;
    private static final int TEST_CASE_34_RESULT = 8;

    @Before
    public void setUp() throws EarthException {
        if (setUpfinished) {
            return;
        }

        data.deleteAllLicenseHistory();

        StrCal strCal1 = new StrCal();
        strCal1.setDivision("division1");
        strCal1.setProcessTime("2017/04/19 12:33:44.182");
        strCal1.setProfileId("profile_1");
        strCal1.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal1.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal1);

        StrCal strCal2 = new StrCal();
        strCal2.setDivision("division1");
        strCal2.setProcessTime("2017/04/19 12:33:44.182");
        strCal2.setProfileId("profile_2");
        strCal2.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal2.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal2);

        StrCal strCal3 = new StrCal();
        strCal3.setDivision("division1");
        strCal3.setProcessTime("2017/04/19 12:33:44.182");
        strCal3.setProfileId("profile_3");
        strCal3.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal3.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal3);

        StrCal strCal4 = new StrCal();
        strCal4.setDivision("division1");
        strCal4.setProcessTime("2017/04/19 12:33:44.182");
        strCal4.setProfileId("profile_4");
        strCal4.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal4.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal4);

        StrCal strCal5 = new StrCal();
        strCal5.setDivision("division1");
        strCal5.setProcessTime("2017/04/19 12:33:44.585");
        strCal5.setProfileId("profile_1");
        strCal5.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal5.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal5);

        StrCal strCal6 = new StrCal();
        strCal6.setDivision("division1");
        strCal6.setProcessTime("2017/04/19 12:33:44.585");
        strCal6.setProfileId("profile_2");
        strCal6.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal6.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal6);

        StrCal strCal7 = new StrCal();
        strCal7.setDivision("division1");
        strCal7.setProcessTime("2017/04/19 12:33:44.585");
        strCal7.setProfileId("profile_3");
        strCal7.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal7.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal7);

        StrCal strCal8 = new StrCal();
        strCal8.setDivision("division1");
        strCal8.setProcessTime("2017/04/19 12:33:44.585");
        strCal8.setProfileId("profile_4");
        strCal8.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal8.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal8);

        StrCal strCal9 = new StrCal();
        strCal9.setDivision("division1");
        strCal9.setProcessTime("2017/04/19 12:33:53.720");
        strCal9.setProfileId("profile_1");
        strCal9.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal9.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal9);

        StrCal strCal10 = new StrCal();
        strCal10.setDivision("division1");
        strCal10.setProcessTime("2017/04/19 12:33:53.720");
        strCal10.setProfileId("profile_2");
        strCal10.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal10.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal10);

        StrCal strCal11 = new StrCal();
        strCal11.setDivision("division1");
        strCal11.setProcessTime("2017/04/19 12:33:53.720");
        strCal11.setProfileId("profile_3");
        strCal11.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal11.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal11);

        StrCal strCal12 = new StrCal();
        strCal12.setDivision("division1");
        strCal12.setProcessTime("2017/04/19 12:33:53.720");
        strCal12.setProfileId("profile_4");
        strCal12.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal12.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal12);

        StrCal strCal13 = new StrCal();
        strCal13.setDivision("division1");
        strCal13.setProcessTime("2017/04/19 12:33:54.349");
        strCal13.setProfileId("profile_1");
        strCal13.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal13.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal13);

        StrCal strCal14 = new StrCal();
        strCal14.setDivision("division1");
        strCal14.setProcessTime("2017/04/19 12:33:54.349");
        strCal14.setProfileId("profile_2");
        strCal14.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal14.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal14);

        StrCal strCal15 = new StrCal();
        strCal15.setDivision("division1");
        strCal15.setProcessTime("2017/04/19 12:33:54.349");
        strCal15.setProfileId("profile_3");
        strCal15.setAvailableLicenseCount(AVAILABLE_LICENSE_COUNT);
        strCal15.setUseLicenseCount(USE_LICENSE_COUNT);
        data.insertLicenseHistory(strCal15);

        setUpfinished = true;
    }

    @Test
    public void testSearch1() throws EarthException {
        List<StrCal> strCals = licenseHistoryService.search(null, null);
        Assert.assertTrue(strCals != null && strCals.size() == 15);
    }

    @Test
    public void testSearch2() throws EarthException {
        List<StrCal> strCals = licenseHistoryService.search(7L, 9L);
        Assert.assertTrue(strCals != null && strCals.size() == 6L);
    }

    @Test
    public void testSearch3() throws EarthException {
        List<StrCal> strCals = licenseHistoryService.search(3L, null);
        Assert.assertTrue(strCals != null && strCals.size() == 3L);
    }

    @Test
    public void testSearch4() throws EarthException {
        List<StrCal> strCals = licenseHistoryService.search(null, 10L);
        Assert.assertTrue(strCals != null && strCals.size() == 5L);
    }

    @Test
    public void testXDelete() throws EarthException {
        data.deleteAllLicenseHistory();
    }
}