package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Column;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.Row;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.WorkItemListDTO;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.SearchOperator;
import co.jp.nej.earth.model.form.DeleteListForm;
import co.jp.nej.earth.service.EventControlService;
import co.jp.nej.earth.service.MstCodeService;
import co.jp.nej.earth.service.TemplateService;
import co.jp.nej.earth.service.WorkItemService;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.web.form.SearchByColumnForm;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import co.jp.nej.earth.web.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workItem")
public class WorkItemController extends BaseController {

    public static final String URL = "workItem";

    @Autowired
    private WorkItemService workItemService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private MstCodeService mstCodeService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EventControlService eventControlService;


    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String showList(Model model, HttpServletRequest request, final RedirectAttributes redirectAttributes)
        throws EarthException {
        if (SessionUtil.loadWorkspacesWithMessage(workspaceService, model, request, messageSource)) {
            HttpSession session = request.getSession();
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);

            List<SearchForm> searchForms = new ArrayList<>();
            UserInfo userInfo = (UserInfo) session.getAttribute(Constant.Session.USER_INFO);
            String userId = userInfo.getUserId();
            List<MgrTemplate> templates = templateService.getAllByWorkspace(workspaceId, userId);
            for (MgrTemplate mgrTemplate : templates) {
                SearchForm searchForm = new SearchForm();
                searchForm.setTemplateName(mgrTemplate.getTemplateName());
                searchForm.setTemplateId(mgrTemplate.getTemplateId());
                searchForm.setTemplateTableName(mgrTemplate.getTemplateTableName());
                searchForms.add(searchForm);
            }
            // Get access right info from Database.
            Map<String, String> templateTypes = mstCodeService
                .getMstCodesBySection(Constant.MstCode.TEMPLATE_TYPE);
            model.addAttribute("templateTypes", templateTypes);
            List<String> workItemIds = new ArrayList<>();

            String screenName = Constant.ScreenKey.WORK_ITEM_LIST + workspaceId;
            SearchByColumnsForm searchByColumnsForm = getSearchServerConditionValueByScope(request, screenName);
            workItemIds = ConversionUtil.castList(session.getAttribute(Constant.Session.WORK_ITEM_IDS), String.class);

            List<WorkItemListDTO> workItems = new ArrayList<>();
            if (!CollectionUtils.isEmpty(workItemIds)) {
                workItems = workItemService.getWorkItemsByWorkspace(workspaceId, workItemIds, userId,
                    searchByColumnsForm.getTemplateId());
            }
            model.addAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchByColumnsForm);
            model.addAttribute("workItems", workItems);
            model.addAttribute("searchByColumnForm", new SearchByColumnForm());
            model.addAttribute("searchOperators", SearchOperator.values());
            session.setAttribute("initWorkItemList", true);
            if (redirectAttributes.getFlashAttributes().get(Constant.Session.MESSAGES) == null) {
                model.addAttribute(Constant.Session.MESSAGES, model.asMap().get("messages"));
            } else {
                model.addAttribute(Constant.Session.MESSAGES, redirectAttributes.getFlashAttributes()
                    .get(Constant.Session.MESSAGES));
            }
            request.getSession().setAttribute("workspaceId", workspaceId);
        }
        return "workitem/workItemList";
    }

    @RequestMapping(value = {"/unlock"}, method = RequestMethod.POST)
    @ResponseBody
    public List<WorkItemListDTO> unlock(DeleteListForm deleteListForm, Model model, HttpServletRequest request)
        throws EarthException {

        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        List<String> eventIds = deleteListForm.getListIds();
        eventControlService.unlockEventControls(workspaceId, eventIds);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constant.Session.USER_INFO);
        List<String> workItemIds = ConversionUtil.castList(request.getSession().getAttribute(
            Constant.Session.WORK_ITEM_IDS), String.class) ;

        List<WorkItemListDTO> workItems=new ArrayList<>();
        SearchByColumnsForm searchByColumnsForm = (SearchByColumnsForm)
            request.getSession().getAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
        if (workItemIds != null) {
            workItems = workItemService.getWorkItemsByWorkspace(workspaceId, workItemIds, userInfo.getUserId(),
                searchByColumnsForm.getTemplateId());
        }
        return workItems;
    }

    @RequestMapping(value = "/showDetail", method = RequestMethod.GET)
    public String showDetail(@ModelAttribute("workItemId") String workItemId, Model model,
                             HttpServletRequest request, final RedirectAttributes redirectAttributes)
        throws EarthException {
        HttpSession session = request.getSession();
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);

        List<Message> messages = workItemService.openWorkItemFromScreen(session, workspaceId, workItemId);
        if (messages.size() > 0) {
            model.addAttribute(Constant.Session.MESSAGES, messages);
            redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
            return redirectToList(URL);
        }
        return redirectToList("workItem/edit?workItemId=" + workItemId);
    }

    @RequestMapping(value = "/searchColumn", method = RequestMethod.POST)
    @ResponseBody
    public SearchForm searchColumn(SearchByColumnsForm searchByColumnsForm, HttpServletRequest request)
        throws EarthException, IOException {
        HttpSession session = request.getSession();
        boolean initWorkItemList = (boolean) session.getAttribute("initWorkItemList");
        SearchForm searchForm = new SearchForm();
        if (initWorkItemList) {
            session.setAttribute("initWorkItemList", false);
            return new SearchForm();
        }

        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);
        Map<String, Object> map = workItemService.searchWorkItems(session, searchByColumnsForm,
            workspaceId);
        searchForm.setColumns(ConversionUtil.castList(map.get("columns"), Column.class));
        searchForm.setRows(ConversionUtil.castList(map.get("rows"), Row.class));
        session.setAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchByColumnsForm);
        session.setAttribute("workspaceId", workspaceId);
        return searchForm;
    }

    @RequestMapping(value = {"/getList"}, method = RequestMethod.POST)
    @ResponseBody
    public List<WorkItemListDTO> getList(DeleteListForm deleteListForm, HttpServletRequest request)
        throws EarthException, IOException {
        HttpSession session = request.getSession();
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);

        List<String> workItemIds = deleteListForm.getListIds();
        session.setAttribute(Constant.Session.WORK_ITEM_IDS, workItemIds);
        List<WorkItemListDTO> workItems = new ArrayList<>();
        request.getSession().setAttribute("workspaceId", workspaceId);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constant.Session.USER_INFO);
        String userId = userInfo.getUserId();
        if (!EStringUtil.isEmpty(workItemIds.get(0))) {
            SearchByColumnsForm searchByColumnsForm = (SearchByColumnsForm)
                session.getAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
            workItems = workItemService.getWorkItemsByWorkspace(workspaceId, workItemIds, userId, searchByColumnsForm
                .getTemplateId());
        }
        return workItems;
    }

    @RequestMapping(value = {"/getTemplateName"}, method = RequestMethod.POST)
    @ResponseBody
    public List<MgrTemplate> getTemplateName(SearchByColumnsForm searchByColumnsForm, HttpServletRequest request)
        throws EarthException, IOException {
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        request.getSession().setAttribute("workspaceId", workspaceId);
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constant.Session.USER_INFO);
        return templateService.getTemplateByType(workspaceId, searchByColumnsForm.getTemplateType(), userInfo
            .getUserId());
    }

}
