package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.LicenseHistory;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.SearchOperator;
import co.jp.nej.earth.service.LicenseHistoryService;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/licenseHistory")
public class LicenseHistoryController extends BaseController {

    @Autowired
    private LicenseHistoryService licenseHistoryService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String initScreen(Model model, HttpServletRequest request) throws EarthException {
        HttpSession session = request.getSession();
        SearchByColumnsForm searchByColumnsForm = new SearchByColumnsForm();
        String screenName = Constant.ScreenKey.LICENSE_HISTORY;
        SessionUtil.clearAllSearchCondition(session, screenName);
        if (session.getAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM) != null) {
            searchByColumnsForm = (SearchByColumnsForm) session.getAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
        }
        List<StrCal> strCals = licenseHistoryService.search(searchByColumnsForm);
        model.addAttribute("strCals", strCals);
        model.addAttribute("searchByColumnsForm", searchByColumnsForm);
        model.addAttribute("searchOperators", SearchOperator.values());
        session.setAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchByColumnsForm);
        session.setAttribute(Constant.Session.SCREEN_NAME, screenName);
        return LicenseHistory.LICENSEHISTORY;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    @ResponseBody
    public List<StrCal> initScreenByColumn(SearchByColumnsForm searchByColumnsForm, Model model,
                                           HttpServletRequest request) throws EarthException {
        List<StrCal> strCals = licenseHistoryService.search(searchByColumnsForm);
        request.getSession().setAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchByColumnsForm);
        return strCals;
    }
}