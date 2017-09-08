package co.jp.nej.earth.service;

import co.jp.nej.earth.util.NumberUtil;
import org.junit.Test;

public class TemplateFieldSizeTest {

    @Test
    public void sizeField()  {
        System.out.println("1, "+NumberUtil.sizeField(1).toString());
        System.out.println("15, "+NumberUtil.sizeField(16).toString());
        System.out.println("20, "+NumberUtil.sizeField(20).toString());
        System.out.println("180, "+NumberUtil.sizeField(269).toString());
        System.out.println("255, "+NumberUtil.sizeDeField(255).toString());
        System.out.println("44, "+NumberUtil.sizeDeField(44).toString());
        System.out.println("24, "+NumberUtil.sizeDeField(24).toString());
    }

}
