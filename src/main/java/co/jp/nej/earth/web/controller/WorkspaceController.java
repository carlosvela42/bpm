package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.model.form.DeleteWorkspaceListForm;
import co.jp.nej.earth.model.form.WorkspaceForm;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * @author longlt
 */

@Controller
@RequestMapping("/workspace")
public class WorkspaceController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(WorkspaceController.class);

    public static final String URL = "workspace";

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String displayWorkspace(Model model, HttpServletRequest request) throws EarthException {
        List<MgrWorkspace> mgrWorkspaces = workspaceService.getAll();
        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM,searchClientForm);
        model.addAttribute("mgrWorkspaces", mgrWorkspaces);
        return "workspace/workspaceList";
    }

    @RequestMapping(value = "/addNew", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNew(Model model, HttpServletRequest request) throws EarthException {
        WorkspaceForm workspaceForm = new WorkspaceForm();
        workspaceForm.setWorkspaceId(Integer.parseInt(Constant.ID_DEFAULT));
        workspaceForm.setDbType(databaseType.name());
        model.addAttribute("workspaceForm", workspaceForm);
        return "workspace/addWorkspace";
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("workspaceForm") WorkspaceForm workspaceForm, BindingResult result,
                            Model model, HttpServletRequest request) {
        try {
            MgrWorkspaceConnect mgrWorkspaceConnect = setMgrWorkspaceConnect(workspaceForm);
            List<Message> messages = validatorUtil.validate(result);
            workspaceForm.setLastUpdateTime(null);
            if (!CollectionUtils.isEmpty(messages)) {
                return displayAddOrEditWorkspaceWithError(messages, model, workspaceForm);
            }

            messages.addAll(workspaceService.validateInsert(mgrWorkspaceConnect,true));


            if (!CollectionUtils.isEmpty(messages)) {
                return displayAddOrEditWorkspaceWithError(messages, model, workspaceForm);
            } else {
                Integer workspaceId =
                    Integer.parseInt(eAutoIncrease.getAutoId(EarthId.WORKSPACE, request.getSession().getId()));
                mgrWorkspaceConnect.setWorkspaceId(workspaceId);
                mgrWorkspaceConnect.getMgrWorkspace().setWorkspaceId(workspaceId);
                workspaceService.insertOne(mgrWorkspaceConnect);

                // Save All Workspaces information into session.
                List<MgrWorkspace> mgrWorkspaces = workspaceService.getAll();
                request.getSession().setAttribute(Session.WORKSPACES, mgrWorkspaces);

                return redirectToList(URL);
            }
        } catch (EarthException e) {
            LOG.error(e.getMessage(), e);
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(null, e.getMessage()));
            return displayAddOrEditWorkspaceWithError(messages, model, workspaceForm);
        }
    }

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("workspaceForm") WorkspaceForm workspaceForm, BindingResult result,
                            Model model, HttpServletRequest request) throws EarthException {
        MgrWorkspaceConnect mgrWorkspaceConnect = setMgrWorkspaceConnect(workspaceForm);
        List<Message> messages = validatorUtil.validate(result);
        if (!CollectionUtils.isEmpty(messages)) {
            return displayAddOrEditWorkspaceWithError(messages, model, workspaceForm);
        }

        messages.addAll(workspaceService.validateInsert(mgrWorkspaceConnect, false));
        if (CollectionUtils.isEmpty(messages)) {
            if(workspaceService.updateOne(mgrWorkspaceConnect)) {
                // Save All Workspaces information into session.
                List<MgrWorkspace> mgrWorkspaces = workspaceService.getAll();
                request.getSession().setAttribute(Session.WORKSPACES, mgrWorkspaces);

                return redirectToList();
            } else {
                messages.addAll(updateFailed());
            }
        }
        model.addAttribute(Session.MESSAGES, messages);
        model.addAttribute("workspaceForm", workspaceForm);
        return "workspace/addWorkspace";
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(String workspaceId, Model model, HttpServletRequest request,
        RedirectAttributes redirectAttributes) throws EarthException {
        WorkspaceForm workspaceForm = new WorkspaceForm();
        MgrWorkspaceConnect mgrWorkspaceConnect = workspaceService.getDetail(workspaceId);
        if(mgrWorkspaceConnect == null) {
            return recordNotFound(redirectAttributes);
        }
        workspaceForm.setWorkspaceId(mgrWorkspaceConnect.getWorkspaceId());
        workspaceForm.setDbType(databaseType.name());
        workspaceForm.setWorkspaceName(mgrWorkspaceConnect.getMgrWorkspace().getWorkspaceName());
        workspaceForm.setSchemaName(mgrWorkspaceConnect.getSchemaName());
        workspaceForm.setDbUser(mgrWorkspaceConnect.getDbUser());
        workspaceForm.setDbPassword(mgrWorkspaceConnect.getDbPassword());
        workspaceForm.setOwner(mgrWorkspaceConnect.getOwner());
        workspaceForm.setDbServer(mgrWorkspaceConnect.getDbServer());
        workspaceForm.setLastUpdateTime(mgrWorkspaceConnect.getLastUpdateTime());

        model.addAttribute("workspaceForm", workspaceForm);
        return "workspace/addWorkspace";
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(DeleteWorkspaceListForm form, Model model) throws EarthException {
        List<Message> messages = workspaceService.deleteList(form.getListIds());
        model.addAttribute(Session.MESSAGES, messages);
        return redirectToList(URL);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }

    private MgrWorkspaceConnect setMgrWorkspaceConnect(WorkspaceForm workspaceForm) {
        MgrWorkspaceConnect mgrWorkspaceConnect = new MgrWorkspaceConnect();
        mgrWorkspaceConnect.setWorkspaceId(workspaceForm.getWorkspaceId());
        mgrWorkspaceConnect.setSchemaName(workspaceForm.getSchemaName());
        mgrWorkspaceConnect.setDbUser(workspaceForm.getDbUser());
        mgrWorkspaceConnect.setDbPassword(workspaceForm.getDbPassword());
        mgrWorkspaceConnect.setOwner(workspaceForm.getOwner());
        mgrWorkspaceConnect.setDbServer(workspaceForm.getDbServer());
        mgrWorkspaceConnect.setDbType(workspaceForm.getDbType());
        mgrWorkspaceConnect.setChangePassword(workspaceForm.isChangePassword());

        MgrWorkspace mgrWorkspace = new MgrWorkspace();
        mgrWorkspace.setWorkspaceId(workspaceForm.getWorkspaceId());
        mgrWorkspace.setWorkspaceName(workspaceForm.getWorkspaceName());
        mgrWorkspaceConnect.setMgrWorkspace(mgrWorkspace);
        return mgrWorkspaceConnect;
    }

    private String displayAddOrEditWorkspaceWithError(List<Message> messages, Model model, WorkspaceForm form) {
        model.addAttribute(Session.MESSAGES, messages);
        form.setWorkspaceId(Integer.parseInt(Constant.ID_DEFAULT));
        model.addAttribute("workspaceForm", form);
        return "workspace/addWorkspace";
    }

}
