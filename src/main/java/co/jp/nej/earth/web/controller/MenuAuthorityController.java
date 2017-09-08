package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.form.MenuAuthorityForm;
import co.jp.nej.earth.service.MenuService;
import co.jp.nej.earth.service.MstCodeService;
import co.jp.nej.earth.service.ProfileService;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/menuAccessRight")
public class MenuAuthorityController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MstCodeService mstCodeService;

    private static final String URL = "menuAccessRight";

    protected String getListUrl() {
        return "menuAccessRight";
    }

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showList(Model model, HttpServletRequest request) throws EarthException {
        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        model.addAttribute("mgrMenus", menuService.getAll());
        return "menuAccessRight/menuList";
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(String functionId, Model model, HttpServletRequest request,
        final RedirectAttributes redirectAttributes) throws EarthException {
        MgrMenu mgrMenu = menuService.getDetail(functionId);
        if(mgrMenu == null) {
            return recordNotFound(redirectAttributes);
        }
        model.addAttribute("mgrUsers", userService.getAll());
        model.addAttribute("mgrProfiles", profileService.getAll());
        model.addAttribute("templateAuthoritySection",
            mstCodeService.getMstCodesBySection(Constant.MstCode.TEMPLATE_AUTHORITY_SECTION));
        MenuAuthorityForm menuAuthorityForm = new MenuAuthorityForm(mgrMenu.getFunctionId(), mgrMenu.getFunctionName(),
            mgrMenu.getFunctionCategoryId(), mgrMenu.getFunctionCategoryName(),
            menuService.getUserAuthority(functionId), menuService.getProfileAuthority(functionId));

        model.addAttribute("menuAuthorityForm", menuAuthorityForm);

        return "menuAccessRight/editMenuAccessRight";
    }

    @RequestMapping(value = "/updateMenuAccessRight", method = RequestMethod.POST)
    public String updateOne(MenuAuthorityForm menuAuthorityForm) throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<>();
        List<UserAccessRight> userAccessRights1 = menuAuthorityForm.getUserAccessRights();
        if (userAccessRights1 != null) {
            for (UserAccessRight userAccessRight : userAccessRights1) {
                if (userAccessRight.getUserId() != null) {
                    userAccessRights.add(userAccessRight);
                }
            }
        }
        List<ProfileAccessRight> profileAccessRights = new ArrayList<>();
        List<ProfileAccessRight> profileAccessRights1 = menuAuthorityForm.getProfileAccessRights();
        if (menuAuthorityForm.getProfileAccessRights() != null) {
            for (ProfileAccessRight profileAccessRight : profileAccessRights1) {
                if (profileAccessRight.getProfileId() != null) {
                    profileAccessRights.add(profileAccessRight);
                }
            }
        }
        menuService.saveAuthority(menuAuthorityForm.getFunctionId(), userAccessRights, profileAccessRights);
        return redirectToList(URL);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }
}
