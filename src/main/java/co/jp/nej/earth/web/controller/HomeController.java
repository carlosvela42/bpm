package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.View;
import co.jp.nej.earth.web.form.SearchClientForm;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController extends BaseController {
    @GetMapping("/")
    public String index(Model model,HttpServletRequest request) {
        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        model.addAttribute("sessionId", request.getRequestedSessionId());
        return View.HOME;
    }

    @RequestMapping(value = {"/searchClientForm"}, method = RequestMethod.POST)
    @ResponseBody
    public String searchClientForm(SearchClientForm searchClientForm, HttpServletRequest request)
        throws EarthException {
        request.getSession().setAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        return "";
    }

    @RequestMapping(value = {"/session"}, method = RequestMethod.GET)
    public String session(Model model, HttpServletRequest request) {
        Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .serializeNulls()
            .create();
        Map<String, Object> map = new HashMap<>();
        Enumeration keys = request.getSession().getAttributeNames();
        String parameterKey = request.getParameter("key") ;
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            boolean shouldTake = StringUtils.isEmpty(parameterKey) || parameterKey.equals(key);
            if(shouldTake) {
                map.put(key, request.getSession().getAttribute(key));
            }
        }

        String sessionJson = gson.toJson(map);
        sessionJson = sessionJson.replaceAll("\\\\\"", "\\\\\\\\\\\"");
        sessionJson = sessionJson.replace("\\n", "");
        model.addAttribute("sessionJson", sessionJson);

        return "home/session";
    }
}
