package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.constant.Constant.View;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.Channel;
import co.jp.nej.earth.model.enums.SearchOperator;
import co.jp.nej.earth.model.form.SearchForm;
import co.jp.nej.earth.service.LoginStatusService;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.util.LoginUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.LoginForm;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by minhtv on 3/21/2017. Login System
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private LoginStatusService loginStatusService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model, HttpServletRequest request) {
        if (LoginUtil.isLogin(request.getSession())) {
            return "redirect:/";
        }

        return View.LOGIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginSubmit(@Valid @ModelAttribute("LoginForm") LoginForm loginForm, BindingResult result,
        Model model, HttpServletRequest request,
        final RedirectAttributes redirectAttributes) throws EarthException {
        List<Message> messages = validatorUtil.validate(result);
        if (messages.size() > 0) {
            model.addAttribute(Session.MESSAGES, messages);
            return View.LOGIN;
        }

        messages = userService.login(loginForm.getUserId(), loginForm.getPassword(), request.getSession(),
            Channel.INTERNAL);
        if (!Message.isAllWarningOrEmpty(messages)) {
            model.addAttribute(Session.MESSAGES, messages);
            return View.LOGIN;
        }

        redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@ModelAttribute("User") MgrUser mgrUser, Model model, HttpServletRequest request)
        throws EarthException {
        userService.logout(request.getSession());
        return "redirect:login";
    }

    @RequestMapping(value = "/loginView", method = RequestMethod.GET)
    public String evidentLog(SearchForm searchForm, Model model, HttpServletRequest request) throws EarthException {
        request.getSession().removeAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
        model.addAttribute("searchForm", searchForm);
//        model.addAttribute("templateTypes", TemplateType.getTempateTypes());
        model.addAttribute("searchOperators", SearchOperator.values());
        model.addAttribute("ctlLogins", loginStatusService.getAll(searchForm.getSkip(), searchForm.getLimit(), null));
        return "loginStatus/loginStatus";
    }

    @RequestMapping(value = "/loginView", method = RequestMethod.POST)
    @ResponseBody
    public List<CtlLogin> evidentLogSearch(SearchByColumnsForm searchByColumnsForm, HttpServletRequest request) throws
        EarthException {
        List<CtlLogin> ctlLogins = loginStatusService.getAllByColumn(searchByColumnsForm, null);
        request.getSession().setAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchByColumnsForm);
        return ctlLogins;
    }
}
