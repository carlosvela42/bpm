package co.jp.nej.earth.web;

import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by daopq on 8/21/2017.
 */
public class CustomFreeMarkerView extends FreeMarkerView {
    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        super.exposeHelpers(model, request);
        model.put("enums", (new BeansWrapperBuilder(Configuration.VERSION_2_3_21)).build().getEnumModels());
    }
}
