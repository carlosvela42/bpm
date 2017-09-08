package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.form.DeleteListForm;
import co.jp.nej.earth.model.form.ProfileForm;
import co.jp.nej.earth.service.ProfileService;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    public static final String PROFILE_LIST = "profile/profileList";
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorUtil validatorUtil;

    private static final String URL = "profile";

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showList(Model model, HttpServletRequest request) throws EarthException {
        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        model.addAttribute("mgrProfiles", profileService.getAll());
        return PROFILE_LIST;
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(Model model, String profileId,
                             final RedirectAttributes redirectAttributes) throws
        EarthException {
        Map<String, Object> profileDetail = profileService.getDetail(profileId);
        if (profileDetail.get("mgrProfile") == null) {
            return recordNotFound(redirectAttributes);
        }

        MgrProfile mgrProfile = ConversionUtil.castObject(profileDetail.get("mgrProfile"), MgrProfile.class);
        List<UsersProfile> usersProfiles = ConversionUtil.castList(profileDetail.get("usersProfiles"),
            UsersProfile.class);
        model.addAttribute("usersProfiles", usersProfiles);
        model.addAttribute("mgrProfile", mgrProfile);
        return "profile/addProfile";

    }

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult result
        , Model model) throws EarthException {
        List<Message> messages = validatorUtil.validate(result);
        MgrProfile mgrProfile = setMgrProfile(profileForm);
        List<UsersProfile> usersProfiles = profileForm.getUsersProfiles();

        messages.addAll(profileService.validate(mgrProfile, false));
        List<String> userIds = getUserIds(usersProfiles);

        if (CollectionUtils.isEmpty(messages)) {
            if (profileService.updateAndAssignUsers(mgrProfile, userIds)) {
                return redirectToList();
            } else {
                messages.addAll(updateFailed());
            }
        }
        return getView(model, mgrProfile, usersProfiles, messages);
    }

    @RequestMapping(value = "/addNew", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNew(Model model, HttpServletRequest request) throws EarthException {
        List<UsersProfile> usersProfiles = userService.getUsersProfileId("");
        model.addAttribute("usersProfiles", usersProfiles);
        model.addAttribute("mgrProfile", new MgrProfile());
        model.addAttribute("userIds", new ArrayList<String>());
        return "profile/addProfile";
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("profileForm") ProfileForm profileForm, BindingResult result,
                            Model model) throws EarthException {
        List<Message> messages = validatorUtil.validate(result);
        MgrProfile mgrProfile = setMgrProfile(profileForm);
        List<UsersProfile> usersProfiles = profileForm.getUsersProfiles();
        mgrProfile.setLastUpdateTime(null);
        if (messages != null && messages.size() > 0) {
            return getView(model, mgrProfile, usersProfiles, messages);
        } else {
            messages.addAll(profileService.validate(mgrProfile, true));
            List<String> userIds = getUserIds(usersProfiles);
            if (messages != null && messages.size() > 0) {
                return getView(model, mgrProfile, usersProfiles, messages);
            } else {
                boolean insertProfile = profileService.insertAndAssignUsers(mgrProfile, userIds);
                if (insertProfile) {
                    return redirectToList(URL);
                } else {
                    return getView(model, mgrProfile, usersProfiles, messages);
                }
            }
        }
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(DeleteListForm form, HttpServletRequest request) throws EarthException {
        List<String> profileId = form.getListIds();
        profileService.deleteList(profileId);
        return redirectToList(URL);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }

    private String getView(Model model, MgrProfile mgrProfile, List<UsersProfile> usersProfiles,
                           List<Message> messages) throws EarthException {
        model.addAttribute(Constant.Session.MESSAGES, messages);
        model.addAttribute("mgrProfile", mgrProfile);
        model.addAttribute("usersProfiles", usersProfiles);

        return "profile/addProfile";
    }

    private MgrProfile setMgrProfile(ProfileForm profileForm) {
        return new MgrProfile(profileForm.getProfileId(), Integer.parseInt("0"),
            profileForm.getDescription(), profileForm.getLdapIdentifier(), profileForm.getLastUpdateTime());
    }

    private List<String> getUserIds(List<UsersProfile> usersProfiles) {
        List<String> userIds = new ArrayList<>();
        for (UsersProfile usersProfile : usersProfiles) {
            if (usersProfile.getUserIdChoose() != null) {
                userIds.add(usersProfile.getUserId());
            }
        }
        return userIds;
    }

}
