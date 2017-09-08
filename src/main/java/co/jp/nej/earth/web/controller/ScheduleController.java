package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.entity.MgrProcessService;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.form.DeleteListForm;
import co.jp.nej.earth.model.form.ScheduleForm;
import co.jp.nej.earth.service.ScheduleService;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.SessionUtil;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends BaseController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    @RequestMapping(value = { "", "/" }, method = {RequestMethod.GET, RequestMethod.POST})
    public String switchWorkspace(Model model, HttpServletRequest request) throws EarthException {
        if (SessionUtil.loadWorkspacesWithMessage(workspaceService, model, request, messageSource)) {
            SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
            model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
            model.addAttribute("mgrSchedules", scheduleService.getSchedulesByWorkspaceId(workspaceId));
            model.addAttribute("messages", model.asMap().get("messages"));
        }
        return "schedule/scheduleList";
    }

    @RequestMapping(value = "/addNew", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNew(Model model, HttpServletRequest request) throws EarthException {
        SessionUtil.loadWorkspaces(workspaceService, model, request);
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        Map<String, Object> info = scheduleService.getInfo(workspaceId);
        model.addAttribute("mgrProcesses", ConversionUtil.castList(info.get("mgrProcesses"), MgrProcess.class));
        model.addAttribute("mgrTasks", ConversionUtil.castList(info.get("mgrTasks"), MgrTask.class));
        model.addAttribute("mgrProcessServices",
            ConversionUtil.castList(info.get("mgrProcessServices"), MgrProcessService.class));
        MgrSchedule mgrSchedule = new MgrSchedule();
        mgrSchedule.setScheduleId((String) info.get("scheduleId"));
        model.addAttribute("mgrSchedule", mgrSchedule);
        return "schedule/addSchedule";
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("scheduleForm") ScheduleForm scheduleForm, BindingResult result,
        Model model, HttpServletRequest request) throws EarthException {
        SessionUtil.loadWorkspaces(workspaceService, model, request);
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        List<Message> messages = validatorUtil.validate(result);
        MgrSchedule mgrSchedule = setMgrSchedule(scheduleForm);
        mgrSchedule.setLastUpdateTime(null);
        messages.addAll(scheduleService.validate(workspaceId, mgrSchedule, true));

        if (CollectionUtils.isEmpty(messages)) {
            mgrSchedule.setScheduleId(eAutoIncrease.getAutoId(EarthId.SCHEDULE, request.getSession().getId()));
            boolean insertSchedule = scheduleService.insertOne(workspaceId, mgrSchedule);
            if (insertSchedule) {
                return redirectToList();
            } else {
                messages.addAll(updateFailed());
            }
        }
        return getView(model, messages, workspaceId, mgrSchedule);
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(String scheduleId, HttpServletRequest request, Model model,
        final RedirectAttributes redirectAttributes) throws EarthException {
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        Map<String, Object> info = scheduleService.showDetail(workspaceId, scheduleId);
        if (info.get("mgrSchedule") == null) {
            return recordNotFound(redirectAttributes);
        }

        model.addAttribute("mgrProcesses", ConversionUtil.castList(info.get("mgrProcesses"), MgrProcess.class));
        model.addAttribute("mgrTasks", ConversionUtil.castList(info.get("mgrTasks"), MgrTask.class));
        model.addAttribute("mgrProcessServices",
            ConversionUtil.castList(info.get("mgrProcessServices"), MgrProcessService.class));
        model.addAttribute("mgrSchedule", ConversionUtil.castObject(info.get("mgrSchedule"), MgrSchedule.class));
        model.addAttribute("workspaceId", workspaceId);
        return "schedule/addSchedule";

    }

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("addScheduleForm") ScheduleForm addScheduleForm,
        BindingResult result, Model model, HttpServletRequest request) throws EarthException {
        SessionUtil.loadWorkspaces(workspaceService, model, request);
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        MgrSchedule mgrSchedule = setMgrSchedule(addScheduleForm);

        List<Message> messages = validatorUtil.validate(result);
        messages.addAll(scheduleService.validate(workspaceId, mgrSchedule, false));
        if (CollectionUtils.isEmpty(messages)) {
            boolean updateResult = scheduleService.updateOne(workspaceId, mgrSchedule);
            if (updateResult) {
                return redirectToList();
            } else {
                messages.addAll(updateFailed());
            }
        }
        return getView(model, messages, workspaceId, mgrSchedule);
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(DeleteListForm deleteForm, Model model, HttpServletRequest request,
        RedirectAttributes redirectAttributes) throws
        Exception {
        List<String> scheduleIds = deleteForm.getListIds();
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        List<Message> messages = scheduleService.validateDelete(workspaceId, scheduleIds);
        if(CollectionUtils.isEmpty(messages)) {
            scheduleService.deleteList(deleteForm.getWorkspaceId(), scheduleIds);
        }
        redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
        return redirectToList();
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList();
    }

    private String getView(Model model, List<Message> messages, String workspaceId, MgrSchedule schedule)
        throws EarthException {
        model.addAttribute(Constant.Session.MESSAGES, messages);
        Map<String, Object> info = scheduleService.getInfo(workspaceId);
        model.addAttribute("mgrSchedule", schedule);
        model.addAttribute("mgrProcesses", info.get("mgrProcesses"));
        model.addAttribute("mgrTasks", info.get("mgrTasks"));
        model.addAttribute("mgrProcessServices", info.get("mgrProcessServices"));

        return "schedule/addSchedule";
    }

    private MgrSchedule setMgrSchedule(ScheduleForm addScheduleForm) {
        MgrSchedule schedule = new MgrSchedule(addScheduleForm.getScheduleId(), addScheduleForm.getTaskId(),
            addScheduleForm.getHostName(), addScheduleForm.getProcessIServiceId(),
            addScheduleForm.getEnableDisable(), addScheduleForm.getStartTime(), addScheduleForm.getEndTime(),
            addScheduleForm.getStartTime(), addScheduleForm.getRunIntervalDay(),
            addScheduleForm.getRunIntervalHour(), addScheduleForm.getRunIntervalMinute(),
            addScheduleForm.getRunIntervalSecond(), addScheduleForm.getLastUpdateTime());
        MgrTask task = new MgrTask();
        task.setTaskId(addScheduleForm.getTaskId());
        task.setProcessId(addScheduleForm.getProcessId());
        schedule.setTask(task);
        return schedule;
    }
}
