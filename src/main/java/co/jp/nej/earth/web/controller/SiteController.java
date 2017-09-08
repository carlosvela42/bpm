package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.service.DirectoryService;
import co.jp.nej.earth.service.SiteService;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.SearchClientForm;
import co.jp.nej.earth.web.form.SiteForm;
import com.google.common.base.Joiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/site")
public class SiteController extends BaseController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    @Autowired
    private ValidatorUtil validatorUtil;

    private static final String URL = "site";

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showList(Model model, HttpServletRequest request) throws EarthException {

        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);

        model.addAttribute("siteIds", siteService.getAllSiteIds());
        model.addAttribute("messages", model.asMap().get("messages"));
        return "site/siteList";
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(@ModelAttribute("siteIds") String siteIds, Model model,
                             final RedirectAttributes redirectAttributes) throws EarthException {
        if (!EStringUtil.isEmpty(siteIds)) {
            List<Integer> siteIdInteger = new ArrayList<>();
            List<String> siteIdList = Arrays.asList(siteIds.split("\\s*,\\s*"));
            for (String siteId : siteIdList) {
                siteIdInteger.add(Integer.valueOf(siteId));
            }
            List<Message> messages = siteService.validateDelete(siteIdInteger);
            if (!CollectionUtils.isEmpty(messages)) {
                model.addAttribute(Session.MESSAGES, messages);
                redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
            } else {
                siteService.deleteSites(siteIdInteger, Constant.EARTH_WORKSPACE_ID);
            }
        }
        return redirectToList();
    }

    @RequestMapping(value = "/addNew", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNew(Model model, HttpServletRequest request) throws EarthException {
        List<Directory> directories = directoryService.getAllDirectories();
        SiteForm siteForm = new SiteForm();
        siteForm.setSiteId(Constant.ID_DEFAULT);
        siteForm.setDirectories(directories);
        model.addAttribute("siteForm", siteForm);
        model.addAttribute("disableSaveButton", (directories.size() == 0));
        return "site/addSite";
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("siteForm") SiteForm siteForm, BindingResult result,
                            HttpServletRequest request, Model model) throws EarthException {
        List<Message> messages = validatorUtil.validate(result);
        if (messages.size() > 0) {
            List<Directory> directories = directoryService.getAllDirectories();
            String directoryBySites = siteForm.getDirectoryIds();
            if (!StringUtils.isEmpty(directoryBySites)) {
                List<String> directoryIdList = Arrays.asList(directoryBySites.split("\\s*,\\s*"));
                markCheckedDirectory(directoryIdList, directories);
            }
            siteForm.setDirectories(directories);
            siteForm.setLastUpdateTime(null);
            model.addAttribute("siteForm", siteForm);
            model.addAttribute(Session.MESSAGES, messages);
            return "site/addSite";
        }

        siteForm.setSiteId(eAutoIncrease.getAutoId(EarthId.SITE, request.getSession().getId()));
        List<String> directoryIdList = Arrays.asList(siteForm.getDirectoryIds().split("\\s*,\\s*"));
        siteService.insertOne(siteForm.getSiteId(), directoryIdList, Constant.EARTH_WORKSPACE_ID);
        return redirectToList("site");
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(@ModelAttribute("siteId") String siteId, Model model, HttpServletRequest request,
                             final RedirectAttributes redirectAttributes) throws EarthException {
        List<Directory> directories = directoryService.getAllDirectories();
        List<Directory> directoryBySites = directoryService.getAllDirectoriesBySite(siteId,
            Constant.EARTH_WORKSPACE_ID);
        if (CollectionUtils.isEmpty(directoryBySites)) {
            return recordNotFound(redirectAttributes);
        }
        SiteForm siteForm = new SiteForm();

        siteForm.setLastUpdateTime(DateUtil.getCurrentDateString());

        siteForm.setSiteId(siteId);
        siteForm.setDirectories(directories);

        List<String> directoryIdList = siteDirectoryToIds(directoryBySites);
        markCheckedDirectory(directoryIdList, directories);

        model.addAttribute("directoryIds", Joiner.on(",").join(directoryIdList));
        model.addAttribute("siteForm", siteForm);
        return "site/addSite";
    }

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("siteForm") SiteForm siteForm, BindingResult result,
                            @ModelAttribute("directoryIds") String directoryIds, Model model,
                            HttpServletRequest request)
        throws EarthException {

        List<Message> messages = validatorUtil.validate(result);
        if (messages.size() > 0) {
            List<Directory> directories = directoryService.getAllDirectories();
            String directoryBySites = siteForm.getDirectoryIds();
            if (!StringUtils.isEmpty(directoryBySites)) {
                List<String> directoryIdList = Arrays.asList(directoryBySites.split("\\s*,\\s*"));
                markCheckedDirectory(directoryIdList, directories);
            }
            siteForm.setDirectories(directories);

            model.addAttribute("siteForm", siteForm);
            model.addAttribute(Session.MESSAGES, messages);
            return "site/addSite";
        }
        List<String> directoryIdList = Arrays.asList(directoryIds.split("\\s*,\\s*"));
        siteService.updateSite(siteForm.getSiteId(), directoryIdList, Constant.EARTH_WORKSPACE_ID);
        return redirectToList("site");
    }

    private void markCheckedDirectory(List<String> directoryIdList, List<Directory> directories) {
        for (String directoryId : directoryIdList) {
            for (Directory directory : directories) {
                if (directory.getDataDirectoryId() == Integer.valueOf(directoryId)) {
                    directory.setChecked(true);
                }
            }
        }
    }

    private List<String> siteDirectoryToIds(List<Directory> directories) {
        return directories.stream().map(d -> String.valueOf(d.getDataDirectoryId())).collect(Collectors.toList());
    }

}
