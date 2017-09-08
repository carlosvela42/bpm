package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.form.TemplateAuthorityForm;
import co.jp.nej.earth.service.MstCodeService;
import co.jp.nej.earth.service.ProfileService;
import co.jp.nej.earth.service.TemplateService;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.SessionUtil;
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
import java.util.Map;

/**
 * @author p-tvo-thuynd
 */

@Controller
@RequestMapping("/templateAccessRight")
public class TemplateAuthorityController extends BaseController {

    public static final String URL = "templateAccessRight";
    @Autowired
    private TemplateService templateService;

    @Autowired
    private MstCodeService mstCodeService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    protected String getListUrl() {
        return "templateAccessRight";
    }

    /**
     * @param model to hold model attribute
     * @return path of screen
     * @throws EarthException
     * @author p-tvo-thuynd this method is to get list of template based on
     * workspace id
     */
    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showList(Model model, HttpServletRequest request) throws EarthException {
        if (SessionUtil.loadWorkspacesWithMessage(workspaceService, model, request, messageSource)) {
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());

            SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
            model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);

            model.addAttribute("mgrTemplates", templateService.getTemplateListInfo(workspaceId));
        }
        return "templateAccessRight/templateList";
    }

    /**
     * @param model      to hold model attribute
     * @param templateId id of choosen template
     * @return path of screen
     * @throws EarthException
     * @author p-tvo-thuynd this method is to show detail info of an template
     */
    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(Model model, String templateId, HttpServletRequest request,
        final RedirectAttributes redirectAttributes) throws EarthException {
        SessionUtil.loadWorkspaces(workspaceService, model, request);
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        return loadView(templateId, model, workspaceId, redirectAttributes, null);
    }

    /**
     * @param model to hold model attribute
     * @return path of screen
     * @author p-tvo-thuynd this method is to update info of a template into DB
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(TemplateAuthorityForm templateAuthorityForm, HttpServletRequest request,
                            Model model, final RedirectAttributes redirectAttributes) throws EarthException {
        List<Message> messages = new ArrayList<>();
        SessionUtil.loadWorkspaces(workspaceService, model, request);
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        try {
            TemplateKey templateKey = new TemplateKey();
            templateKey.setTemplateId(templateAuthorityForm.getTemplateId());
            templateKey.setWorkspaceId(workspaceId);

            List<UserAccessRight> userAccessRights = new ArrayList<>();
            List<UserAccessRight> userAccessRights1 = templateAuthorityForm.getUserAccessRights();
            if (userAccessRights1 != null) {
                for (UserAccessRight userAccessRight : userAccessRights1) {
                    if (userAccessRight.getUserId() != null) {
                        userAccessRights.add(userAccessRight);
                    }
                }
            }
            List<ProfileAccessRight> profileAccessRights = new ArrayList<>();
            List<ProfileAccessRight> profileAccessRights1 = templateAuthorityForm.getProfileAccessRights();
            if (profileAccessRights1 != null) {
                for (ProfileAccessRight profileAccessRight : profileAccessRights1) {
                    if (profileAccessRight.getProfileId() != null) {
                        profileAccessRights.add(profileAccessRight);
                    }
                }
            }

            if(templateService.getById(templateKey) == null){
                messages.addAll(updateFailed());
                model.addAttribute(Constant.Session.MESSAGES, messages);
                return loadView(templateKey.getTemplateId(), model, workspaceId, redirectAttributes,
                    templateAuthorityForm);
            }
            // save user authority to template to CTL_TEMPLATE table
            templateService.saveAuthority(templateKey, userAccessRights, profileAccessRights);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redirectToList(URL);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }

    private String loadView(String templateId, Model model, String workspaceId,
        final RedirectAttributes redirectAttributes, TemplateAuthorityForm templateAuthorityFormIn)
        throws EarthException {
        model.addAttribute("templateId", templateId);

        model.addAttribute("mgrUsers", userService.getAll());
        model.addAttribute("mgrProfiles", profileService.getAll());

        // Get access right info from Database.
        Map<String, String> accessRights = mstCodeService
            .getMstCodesBySection(Constant.MstCode.TEMPLATE_AUTHORITY_SECTION);
        model.addAttribute("accessRights", accessRights);

        TemplateKey templateKey = new TemplateKey();
        templateKey.setTemplateId(templateId);
        templateKey.setWorkspaceId(workspaceId);
        TemplateAuthorityForm templateAuthorityForm = templateAuthorityFormIn;
        if(templateAuthorityFormIn == null) {
            MgrTemplate mgrTemplate = templateService.getById(templateKey);
            if(mgrTemplate == null) {
                return recordNotFound(redirectAttributes);
            }
            List<UserAccessRight> userAccessRights = templateService.getUserAuthority(templateKey);
            List<ProfileAccessRight> profileAccessRights = templateService.getProfileAuthority(templateKey);

            templateAuthorityForm = new TemplateAuthorityForm(mgrTemplate.getTemplateId(),
                mgrTemplate.getTemplateName(), userAccessRights, profileAccessRights);
        }
        model.addAttribute("templateAuthorityForm", templateAuthorityForm);
        return "templateAccessRight/editTemplateAccessRight";

    }
}
