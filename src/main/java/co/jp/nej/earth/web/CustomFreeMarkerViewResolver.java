package co.jp.nej.earth.web;

import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by daopq on 8/21/2017.
 */
public class CustomFreeMarkerViewResolver extends FreeMarkerViewResolver {
    public CustomFreeMarkerViewResolver() {
        setViewClass(requiredViewClass());
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Class requiredViewClass() {
        return CustomFreeMarkerView.class;
    }
}
