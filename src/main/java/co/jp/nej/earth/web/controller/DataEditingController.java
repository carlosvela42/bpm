package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.Field;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.Action;
import co.jp.nej.earth.model.enums.EventType;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.model.enums.Type;
import co.jp.nej.earth.service.DocumentService;
import co.jp.nej.earth.service.FolderItemService;
import co.jp.nej.earth.service.LayerService;
import co.jp.nej.earth.service.ProcessService;
import co.jp.nej.earth.service.TaskService;
import co.jp.nej.earth.service.TemplateService;
import co.jp.nej.earth.service.WorkItemService;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.DataEditingTemplateForm;
import co.jp.nej.earth.web.form.EditWorkItemForm;
import co.jp.nej.earth.web.form.TaskForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Edit work item data screen
 *
 * @author DaoPQ
 * @version 1.0
 */
@Controller
@RequestMapping("/workItem")
public class DataEditingController extends BaseController {

    @Autowired
    private WorkItemService workItemService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private FolderItemService folderItemService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private LayerService layerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EMessageResource eMessageResource;

    /**
     * And character string
     */
    private static final String AND_CHARACTER = "&";

    /**
     * Initial view of work item
     */
    private static final String EDIT_SCREEN = "workitem/workItemEdit";

    /**
     * Redirect work item list
     */
    private static final String WORKITEM_LIST = "workItem";

    /**
     * Partial view for template in work item screen
     */
    private static final String PARTIAL_TEMPLATE = "workitem/partial/template";

    /**
     * Partial view for template in work item screen
     */
    private static final String PARTIAL_TEMPLATE_FIELD = "workitem/partial/templateField";

    /**
     * Error message page
     */
    private static final String ERROR_MESSAGE_PAGE = "workitem/partial/messages";

    /**
     * Key: Template list to display on template screen
     */
    private static final String KEY_TEMPLATE_LIST = "templateList";

    /**
     * Key: Task list to display on template screen
     */
    private static final String KEY_TASK_LIST = "tasks";

    /**
     * Key: Work item to display on screen
     */
    private static final String KEY_WORK_ITEM = "workItem";

    /**
     * Key: Set item type on screen
     */
    private static final String KEY_ITEM_TYPE = "type";

    /**
     * Key: Current template id
     */
    private static final String KEY_CURRENT_TEMPLATE_ID = "currentTemplateId";

    /**
     * Key: Process Id
     */
    private static final String KEY_PROCESS_ID = "processId";

    /**
     * Key: Current task id
     */
    private static final String KEY_CURRENT_TASK_ID = "currentTaskId";

    /**
     * Key: work item id
     */
    private static final String KEY_WORK_ITEM_ID = "workItemId";

    /**
     * Key: Folder Item No
     */
    private static final String KEY_FOLDER_ITEM_NO = "folderItemNo";

    /**
     * Key: Document no
     */
    private static final String KEY_DOCUMENT_NO = "documentNo";

    /**
     * Key: Layer no
     */
    private static final String KEY_LAYER_NO = "layerNo";

    /**
     * Key: OwnerID
     */
    private static final String KEY_OWNER_ID = "ownerId";

    /**
     * Key: Workspace id
     */
    private static final String KEY_WORKSPACE_ID = "workspaceId";

    /**
     * Key: Fields
     */
    private static final String KEY_FIELDS = "fields";

    /**
     * Key: Template data (value)
     */
    private static final String KEY_TEMPLATE_DATA = "mapTemplate";

    /**
     * Key to store template on session
     */
    private static final String KEY_TEMPLATE_SESSION = "templateSession";

    /**
     * Show edit screen
     *
     * @param model   Model model
     * @param request HttpServletRequest
     * @return edit page
     * @throws EarthException Throw EarthException when has error
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEdit(@ModelAttribute("workItemId") String workItemId, Model model, HttpServletRequest request)
        throws EarthException {

        HttpSession session = request.getSession();
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);

        WorkItem workItem = workItemService.getWorkItemStructureSession(session, workspaceId, workItemId);

        // WorkItem is not exist
        if (workItem == null) {
            throw new EarthException("Invalid WorkItem");
        }

        // Get task list
        List<MgrTask> tasks = new ArrayList<>();
        Integer processId = workItem.getDataProcess().getProcessId();
        if (processId != null) {
            tasks = taskService.getTaskByProcess(workspaceId, processId);
        }

        // Set data to model
        model.addAttribute(KEY_TASK_LIST, tasks);
        if (!StringUtils.isEmpty(workItem.getTaskId())) {
            model.addAttribute(KEY_CURRENT_TASK_ID, workItem.getTaskId());
        }
        model.addAttribute(KEY_WORK_ITEM, workItem);

        UserInfo userInfo = (UserInfo) session.getAttribute(Session.USER_INFO);
        model.addAttribute(Session.TOKEN, userInfo.getLoginToken());

        // Set enums
        model.addAttribute("enums",
            (new BeansWrapperBuilder(Configuration.VERSION_2_3_21)).build().getEnumModels());

        // Save value of first template to session
        initialTemplateSession(session);

        return EDIT_SCREEN;
    }

    /**
     * Load partial template by Id to display on work item screen
     *
     * @param model   Model object
     * @param request HttpServletRequest object
     * @return Partial view template if finish normal, otherwise null
     * @throws EarthException Throw EarthException when has error
     */
    @RequestMapping(value = "/showTemplate", method = RequestMethod.POST)
    public String showTemplate(@Valid @ModelAttribute("EditWorkItemForm") EditWorkItemForm editWorkItemForm,
                               Model model, HttpServletRequest request) throws EarthException {
        String workspaceId = editWorkItemForm.getWorkspaceId();
        String type = editWorkItemForm.getType();

        // Get accessible template by login user
        HttpSession session = request.getSession();

        // Set display template
        String workItemId = editWorkItemForm.getWorkItemId();
        TemplateType templateType = TemplateType.getByValue(EStringUtil.parseInt(type));
        if (templateType == null) {
            throw new EarthException("Invalid item type");
        }
        switch (templateType) {
            case PROCESS:
                // Process type
                Integer processId = Integer.valueOf(editWorkItemForm.getProcessId());
                setTemplateOfProcess(model, workspaceId, session, processId, workItemId);
                break;
            case WORKITEM:
                setTemplateOfWorkItem(model, session, workspaceId, workItemId);
                break;
            case FOLDERITEM:
                setTemplateOfFolderItem(model, session, workspaceId, workItemId, editWorkItemForm.getFolderItemNo());
                break;
            case DOCUMENT:
                setTemplateOfDocument(model, session, workspaceId, workItemId,
                    editWorkItemForm.getFolderItemNo(), editWorkItemForm.getDocumentNo());
                break;
            default:
                break;
        }

        // Setting template list
        List<String> templateIdList = TemplateUtil.getAccessibleTemplates(session, workspaceId);
        List<MgrTemplate> templateList = templateService.getByIdsAndType(workspaceId, templateIdList, templateType);
        model.addAttribute(KEY_TEMPLATE_LIST, templateList);
        model.addAttribute(KEY_WORKSPACE_ID, workspaceId);
        model.addAttribute(KEY_ITEM_TYPE, type);

        return PARTIAL_TEMPLATE;
    }

    /**
     * Update process
     *
     * @param form    ProcessForm object
     * @param result  BindingResult object
     * @param model   Model object
     * @param request HttpServletRequest object
     * @return list work item screen if ok, otherwise system error screen
     */
    @RequestMapping(value = "/updateTemplate", method = RequestMethod.POST)
    public String updateTemplate(@Valid @ModelAttribute("DataEditingTemplateForm") DataEditingTemplateForm form,
                                BindingResult result, Model model, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            // Validate request parameter
            // Validate common
            form.validateForm();
            AccessRight accessRight = AccessRight.fromTitle(form.getAccessRight());
            String workItemId = form.getWorkItemId();
            String workspaceId = form.getWorkspaceId();
            HttpSession session = request.getSession();
            TemplateType templateType = TemplateType.getByValue(EStringUtil.parseInt(form.getTemplateType()));

            // Only save to session when user has permission RW or FULL
            if ((AccessRight.FULL == accessRight) || (AccessRight.RW == accessRight)) {
                String resultValidate = validateUpdateTemplate(result, model, response, form);
                if (!StringUtils.isEmpty(resultValidate)) {
                    return resultValidate;
                }

                boolean updateStatus = false;
                if (templateType != null) {
                    switch (templateType) {

                        // Update process
                        case PROCESS:
                            updateStatus = updateProcess(form, session);
                            break;

                        // Update WorkItem
                        case WORKITEM:
                            updateStatus = updateWorkItem(form, session);
                            break;

                        // Update FolderItem
                        case FOLDERITEM:
                            updateStatus = updateFolderItem(form, session);
                            break;

                        // Update Document
                        case DOCUMENT:
                            updateStatus = updateDocument(form, session);
                            break;
                        default:
                            break;
                    }
                } else {
                    updateStatus = true;
                }

                // Set error response
                if (!updateStatus) {
                    return setUpdateError(response, model);
                }
            }

            // Close and save data
            closeAndSave(form.getEventType(), request.getSession(), workItemId, workspaceId);

        } catch (Exception ex) {
            return setExceptionResponse(response, model, ex);
        }

        return null;
    }

    /**
     * Update taskId to workItem
     *
     * @param taskForm Task Form
     * @param request HttpServletRequest object
     * @param response HttpServletResponse response
     * @param model Model object
     * @return view to display
     */
    @RequestMapping(value = "/updateTask", method = RequestMethod.GET)
    public String updateTask(@Valid @ModelAttribute("TaskForm") TaskForm taskForm, HttpServletRequest request,
                             HttpServletResponse response, Model model) {
        try {

            // Set Template manager
            WorkItem workItem = new WorkItem();
            workItem.setTaskId(taskForm.getTaskId());
            workItem.setWorkitemId(taskForm.getWorkItemId());

            // Update data to session
            HttpSession session = request.getSession();
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);
            WorkItem workItemSession = workItemService.updateTaskToWorkItemSession(session, workspaceId, workItem);

            // Set error response
            if (workItemSession == null) {
                return setUpdateError(response, model);
            }
        } catch (Exception ex) {
            return setExceptionResponse(response, model, ex);
        }
        return null;
    }

    /**
     * Load template field
     *
     * @param model    Model object
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @return Partial template field
     */
    @RequestMapping(value = "/setTemplateField", method = RequestMethod.POST)
    public String setTemplateField(Model model, HttpServletRequest request, HttpServletResponse response) {
        try {
            String field = request.getParameter("templateField");
            String type = request.getParameter("type");
            String templateId = request.getParameter("templateId");
            String processId = request.getParameter("processId");
            String workItemId = request.getParameter("workItemId");
            String folderItemNo = request.getParameter("folderItemNo");
            String documentNo = request.getParameter("documentNo");

            // validate request parameter
            if (StringUtils.isEmpty(field)) {
                response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                List<Message> messages = new ArrayList<>();
                messages.add(new Message("field", "Invalid parameter templateField = null"));
                model.addAttribute(Constant.Session.MESSAGES, messages);
                return ERROR_MESSAGE_PAGE;
            }

            // Get template from session
            Map<String, Map<String, Object>> templateSession = getTemplateFromSession(request.getSession());
            TemplateType templateType = TemplateType.getByValue(Integer.valueOf(type));
            if (templateType == null) {
                throw new EarthException("Invalid template type");
            }
            StringBuilder templateKey = new StringBuilder(templateType.getTitle()).append(AND_CHARACTER);

            // Set key to template session for template
            switch (templateType) {
                case PROCESS:
                    templateKey.append(processId)
                               .append(AND_CHARACTER)
                               .append(templateId);
                    break;
                case WORKITEM:
                    templateKey.append(workItemId)
                               .append(AND_CHARACTER)
                               .append(templateId);
                    break;
                case FOLDERITEM:
                    templateKey.append(folderItemNo)
                               .append(AND_CHARACTER)
                               .append(templateId);
                    break;
                case DOCUMENT:
                    templateKey.append(folderItemNo)
                               .append(AND_CHARACTER)
                               .append(documentNo)
                               .append(AND_CHARACTER)
                               .append(templateId);
                    break;
                default:
                    break;
            }

            String strTemplateKey = templateKey.toString();
            if ((templateSession != null) && (templateSession.containsKey(strTemplateKey))) {
                model.addAttribute(KEY_TEMPLATE_DATA, templateSession.get(strTemplateKey));
            }
            model.addAttribute(KEY_FIELDS, convertJsonToList(field));

            return PARTIAL_TEMPLATE_FIELD;
        } catch (Exception ex) {
            return setExceptionResponse(response, model, ex);
        }
    }

    /**
     * Close edit work item
     *
     * @param request HttpServletRequest object
     * @return Partial template field
     */
    @RequestMapping(value = "/closeEdit", method = RequestMethod.POST)
    public String close(HttpServletRequest request) {
        try {
            String workItemId = request.getParameter(KEY_WORK_ITEM_ID);
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());

            if (EStringUtil.isEmpty(workItemId) || (EStringUtil.isEmpty(workspaceId))) {
                throw new EarthException("Invalid parameter workItemId or workspaceId is null");
            }

            workItemService.closeWorkItem(request.getSession(), workspaceId, workItemId);
            return redirectToList(WORKITEM_LIST);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // =========================================================//
    // Private method
    // =========================================================//

    /**
     * Set response when has exception
     *
     * @param response HttpServletResponse response
     * @param model    Model model
     * @param ex       Exception
     * @return Error page
     */
    private String setExceptionResponse(HttpServletResponse response, Model model, Exception ex) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(ex.toString(), ex.getMessage()));
        model.addAttribute(Constant.Session.MESSAGES, messages);

        return ERROR_MESSAGE_PAGE;
    }

    /**
     * Get Template of process
     *
     * @param workspaceId Workspace ID
     * @param session     HttpSession object
     * @param workItemId  WorkItem ID
     * @throws EarthException Throw this exception when has any error
     */
    private void setTemplateOfProcess(Model model, String workspaceId, HttpSession session, Integer processId, String
        workItemId) throws EarthException {
        DatProcess data = processService.getProcessSession(session, workspaceId, workItemId, processId);

        String templateId = data.getTemplateId();
        if (!StringUtils.isEmpty(templateId)) {
            model.addAttribute(KEY_CURRENT_TEMPLATE_ID, templateId);
            if (data.getProcessData() != null && (data.getProcessData().getDataMap() != null)) {
                model.addAttribute(KEY_TEMPLATE_DATA, data.getProcessData().getDataMap());
                String key = new StringBuilder(TemplateType.PROCESS.toString())
                    .append(AND_CHARACTER)
                    .append(processId)
                    .append(AND_CHARACTER)
                    .append(templateId).toString();
                storeTemplateToSession(session, key, data.getProcessData().getDataMap());
            }
            if (data.getMgrTemplate() != null) {
                model.addAttribute(KEY_FIELDS, data.getMgrTemplate().getTemplateFields());
            }
        }

        model.addAttribute(KEY_PROCESS_ID, processId);
        model.addAttribute(KEY_WORK_ITEM_ID, workItemId);
    }

    /**
     * Get Template of process
     *
     * @param workspaceId Workspace ID
     * @param session     HttpSession object
     * @param workItemId  WorkItem ID
     * @throws EarthException Throw this exception when has any error
     */
    private void setTemplateOfWorkItem(Model model, HttpSession session, String workspaceId, String workItemId)
        throws EarthException {
        WorkItem data = workItemService.getWorkItemSession(session, workspaceId, workItemId);
        if (data == null) {
            return;
        }

        String templateId = data.getTemplateId();
        if (!StringUtils.isEmpty(templateId)) {
            model.addAttribute(KEY_CURRENT_TEMPLATE_ID, templateId);

            // Store this data to session
            if (data.getWorkItemData() != null && (data.getWorkItemData().getDataMap() != null)) {
                model.addAttribute(KEY_TEMPLATE_DATA, data.getWorkItemData().getDataMap());
                String key = new StringBuilder(TemplateType.WORKITEM.toString())
                    .append(AND_CHARACTER)
                    .append(workItemId)
                    .append(AND_CHARACTER)
                    .append(templateId).toString();
                storeTemplateToSession(session, key, data.getWorkItemData().getDataMap());
            }
            if (data.getMgrTemplate() != null) {
                model.addAttribute(KEY_FIELDS, data.getMgrTemplate().getTemplateFields());
            }
        }
        model.addAttribute(KEY_WORK_ITEM_ID, workItemId);
    }


    /**
     * Get Template of FolderItem
     *
     * @param workspaceId  Workspace ID
     * @param session      HttpSession object
     * @param workItemId   WorkItem ID
     * @param folderItemNo Folder Item No
     * @throws EarthException Throw this exception when has any error
     */
    private void setTemplateOfFolderItem(Model model, HttpSession session, String workspaceId, String workItemId,
                                         String folderItemNo) throws EarthException {
        FolderItem data = folderItemService.getFolderItemSession(session, workspaceId, workItemId, folderItemNo);
        if (data == null) {
            return;
        }

        String templateId = data.getTemplateId();
        if (!StringUtils.isEmpty(templateId)) {
            model.addAttribute(KEY_CURRENT_TEMPLATE_ID, templateId);
            if (data.getFolderItemData() != null && (data.getFolderItemData().getDataMap() != null)) {
                model.addAttribute(KEY_TEMPLATE_DATA, data.getFolderItemData().getDataMap());
                String key = new StringBuilder(TemplateType.FOLDERITEM.toString())
                    .append(AND_CHARACTER)
                    .append(folderItemNo)
                    .append(AND_CHARACTER)
                    .append(templateId).toString();
                storeTemplateToSession(session, key, data.getFolderItemData().getDataMap());
            }
            if (data.getMgrTemplate() != null) {
                model.addAttribute(KEY_FIELDS, data.getMgrTemplate().getTemplateFields());
            }
        }

        model.addAttribute(KEY_WORK_ITEM_ID, workItemId);
        model.addAttribute(KEY_FOLDER_ITEM_NO, folderItemNo);
    }

    /**
     * Get Template of Document
     *
     * @param workspaceId  Workspace ID
     * @param session      HttpSession object
     * @param workItemId   WorkItem ID
     * @param folderItemNo Folder Item No
     * @param documentNo   Document NO!
     * @throws EarthException Throw this exception when has any error
     */
    private void setTemplateOfDocument(Model model, HttpSession session, String workspaceId, String workItemId,
                                       String folderItemNo, String documentNo) throws EarthException {
        Document data = documentService.getDocumentSession(session, workspaceId, workItemId, folderItemNo, documentNo);
        if (data == null) {
            return;
        }

        String templateId = data.getTemplateId();
        if (!StringUtils.isEmpty(templateId)) {
            model.addAttribute(KEY_CURRENT_TEMPLATE_ID, templateId);
            if (data.getDocumentData() != null && (data.getDocumentData().getDataMap() != null)) {
                model.addAttribute(KEY_TEMPLATE_DATA, data.getDocumentData().getDataMap());
                String key = new StringBuilder(TemplateType.DOCUMENT.toString())
                    .append(AND_CHARACTER)
                    .append(folderItemNo)
                    .append(AND_CHARACTER)
                    .append(documentNo)
                    .append(AND_CHARACTER)
                    .append(templateId).toString();
                storeTemplateToSession(session, key, data.getDocumentData().getDataMap());
            }
            if (data.getMgrTemplate() != null) {
                model.addAttribute(KEY_FIELDS, data.getMgrTemplate().getTemplateFields());
            }
        }

        model.addAttribute(KEY_WORK_ITEM_ID, workItemId);
        model.addAttribute(KEY_FOLDER_ITEM_NO, folderItemNo);
        model.addAttribute(KEY_DOCUMENT_NO, documentNo);
    }

    /**
     * Get Template of Layer
     *
     * @param workspaceId  Workspace ID
     * @param session      HttpSession object
     * @param workItemId   WorkItem ID
     * @param folderItemNo Folder Item No
     * @param documentNo   Document NO
     * @param layerNo      Layer NO
     * @param ownerId      Owner ID
     * @throws EarthException Throw this exception when has any error
     */
    private void setTemplateOfLayer(Model model, HttpSession session, String workspaceId, String workItemId,
                                    String folderItemNo, String documentNo, String layerNo, String ownerId) throws
        EarthException {
        Layer data = layerService.getLayerSession(session, workspaceId, workItemId, folderItemNo, documentNo, layerNo);
        if (data == null) {
            return;
        }

        String templateId = data.getTemplateId();
        if (!StringUtils.isEmpty(templateId)) {
            model.addAttribute(KEY_CURRENT_TEMPLATE_ID, templateId);
            if (data.getLayerData() != null && (data.getLayerData().getDataMap() != null)) {
                model.addAttribute(KEY_TEMPLATE_DATA, data.getLayerData().getDataMap());
                storeTemplateToSession(session, TemplateType.LAYER + "&" + templateId, data.getLayerData()
                    .getDataMap());
            }
            if (data.getMgrTemplate() != null) {
                model.addAttribute(KEY_FIELDS, data.getMgrTemplate().getTemplateFields());
            }
        }

        model.addAttribute(KEY_WORK_ITEM_ID, workItemId);
        model.addAttribute(KEY_FOLDER_ITEM_NO, folderItemNo);
        model.addAttribute(KEY_DOCUMENT_NO, documentNo);
        model.addAttribute(KEY_LAYER_NO, layerNo);
        model.addAttribute(KEY_OWNER_ID, ownerId);
    }

    /**
     * Convert String with json format to List
     *
     * @param jsonValue JSONValue of object that need to cast to
     * @return List of Field
     * @throws EarthException Throw this exception when has any error
     */
    private List<Field> convertJsonToList(String jsonValue) throws EarthException {
        try {
            return new ObjectMapper().readValue(jsonValue, new TypeReference<List<Field>>() {
            });
        } catch (IOException e) {
            throw new EarthException(e);
        }
    }

    /**
     * Copy data template from form to entity
     *
     * @param form DataEditingTemplateForm
     * @return MgrTemplate object
     * @throws EarthException Throw this exception when has any error
     */
    private MgrTemplate copyFormToMgrTemplate(DataEditingTemplateForm form) throws
        EarthException {
        if (form == null) {
            return null;
        }
        MgrTemplate mgrTemplate = new MgrTemplate();
        mgrTemplate.setTemplateId(form.getTemplateId());
        mgrTemplate.setTemplateName(form.getTemplateName());
        mgrTemplate.setTemplateTableName(form.getTemplateTableName());
        mgrTemplate.setTemplateType(form.getTemplateType());

        String templateField = form.getTemplateField();
        List<Field> templateFields = null;
        if (templateField != null) {
            templateFields = convertJsonToList(templateField);
        }
        mgrTemplate.setTemplateField(templateField);
        if (templateFields != null) {
            mgrTemplate.setTemplateFields(templateFields);
        }

        return mgrTemplate;
    }

    /**
     * Set data to Template Data
     *
     * @param form DataEditingTemplateForm
     * @return TemplateData object
     * @throws EarthException Throw this exception when has any error
     */
    @SuppressWarnings("unchecked")
    private TemplateData copyDataToTemplateData(DataEditingTemplateForm form) throws
        EarthException {
        if (form == null) {
            return null;
        }
        TemplateData templateData = new TemplateData();
        List<?> listDataMap = null;
        try {
            listDataMap = new ObjectMapper().readValue(form.getTemplateData(), new TypeReference<List<?>>() {
            });
        } catch (IOException e) {
            throw new EarthException(e);
        }
        Map<String, Object> dataMap = new HashMap();
        for (Object object : listDataMap) {
            if (object instanceof LinkedHashMap) {
                dataMap.putAll((LinkedHashMap) object);
            }
        }

        templateData.setDataMap(dataMap);

        return templateData;
    }

    /**
     * Validate form before save template
     *
     * @param result   BindingResult object
     * @param model    Model object
     * @param response HttpServletResponse object
     * @return True if validate success, otherwise false
     */
    @SuppressWarnings("unchecked")
    private String validateUpdateTemplate(BindingResult result, Model model, HttpServletResponse response,
                                          DataEditingTemplateForm form) throws IOException, EarthException {
        if (form == null || (EStringUtil.isEmpty(form.getTemplateId()))) {
            return EStringUtil.EMPTY;
        }

        // Validate common
        List<Message> messages = validatorUtil.validate(result);

        List<Field> templateFields = null;
        if (form.getTemplateField() != null) {
            templateFields = convertJsonToList(form.getTemplateField());
        }
        List<?> listDataMap = new ObjectMapper().readValue(form.getTemplateData(), new TypeReference<List<?>>() {
        });
        Map<String, Object> dataMap = new HashMap<>();
        for (Object object : listDataMap) {
            if (object instanceof LinkedHashMap) {
                dataMap.putAll((LinkedHashMap) object);
            }
        }

        if (CollectionUtils.isEmpty(templateFields)) {
            return EStringUtil.EMPTY;
        }

        String fieldName;
        String fieldValue;
        int size = 0;
        for (Field field : templateFields) {
            if (field == null) {
                continue;
            }
            fieldName = field.getName();
            if (fieldName == null) {
                continue;
            }
            fieldValue = String.valueOf(dataMap.get(fieldName));
            if (field.getSize() != null) {
                size = field.getSize();
            }
            if ((field.getRequired()) && (EStringUtil.isEmpty(fieldValue))) {
                messages.add(new Message(fieldName,
                    eMessageResource.get(Constant.ErrorCode.E0001, new String[]{fieldName})));
            } else if (fieldValue.length() > size) {
                messages.add(new Message(fieldName,
                    eMessageResource.get(Constant.ErrorCode.E0026, new String[]{fieldName, String.valueOf(size)})));
            } else if ((Type.isNumber(field.getType())) && (!EStringUtil.isEmpty(fieldValue))
                && (!org.apache.commons.lang3.StringUtils.isNumeric(String.valueOf(dataMap.get(fieldName))))) {
                messages.add(new Message(fieldName, eMessageResource.get(Constant.ErrorCode.E0008,
                    new String[]{fieldName})));
            }
        }

        if (!CollectionUtils.isEmpty(messages)) {
            model.addAttribute(Constant.Session.MESSAGES, messages);
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return ERROR_MESSAGE_PAGE;
        }
        return EStringUtil.EMPTY;
    }

    /**
     * Initial template on session
     *
     * @param session HttpSession object
     */
    private void initialTemplateSession(HttpSession session) {
        Map<String, Map<String, Object>> mapTemplate = new HashMap<>();
        session.setAttribute(KEY_TEMPLATE_SESSION, mapTemplate);
    }

    /**
     * Initial template on session
     *
     * @param session HttpSession object
     */
    private void removeTemplateSession(HttpSession session) {
        session.removeAttribute(KEY_TEMPLATE_SESSION);
    }

    /**
     * Store Template to session
     *
     * @param session       HttpSession object
     * @param key           Session key
     * @param templateValue Value of template
     */
    @SuppressWarnings("unchecked")
    private void storeTemplateToSession(HttpSession session, String key, Map<String, Object> templateValue) {
        if (session.getAttribute(KEY_TEMPLATE_SESSION) instanceof Map) {
            Map<String, Map<String, Object>> mapTemplate = (Map<String, Map<String, Object>>) session.getAttribute(
                KEY_TEMPLATE_SESSION);
            if ((templateValue != null) && (!mapTemplate.containsKey(key))) {
                mapTemplate.put(key, templateValue);
            }
        }
    }

    /**
     * Get Template data from session
     *
     * @param session HttpSession object
     * @return Session data
     */
    @SuppressWarnings("unchecked")
    private Map<String, Map<String, Object>> getTemplateFromSession(HttpSession session) {
        if (session.getAttribute(KEY_TEMPLATE_SESSION) instanceof Map) {
            return (Map<String, Map<String, Object>>) session.getAttribute(KEY_TEMPLATE_SESSION);
        }
        return null;
    }

    /**
     * Close and save data to database when click button save
     *
     * @param eventType   Event type when call action
     * @param session     HttpSession object
     * @param workItemId  WorkItem ID
     * @param workSpaceId Workspace ID
     * @throws EarthException Throw this exception when has any error
     */
    private void closeAndSave(String eventType, HttpSession session, String workItemId, String workSpaceId)
        throws EarthException {
        // Close and save data, remove template data on session
        if (EventType.isButtonClick(eventType)) {
            workItemService.closeAndSaveWorkItem(session, workItemId, workSpaceId);
            removeTemplateSession(session);
        }
    }

    /**
     * Set error response when error occur
     *
     * @param response HttpServletResponse object
     * @return Error page
     */
    private String setUpdateError(HttpServletResponse response, Model model) {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Update", "Update Process fail"));
        model.addAttribute(Constant.Session.MESSAGES, messages);
        return ERROR_MESSAGE_PAGE;
    }

    /**
     * Update process
     *
     * @param form DataEditingTemplateForm
     * @param session HttpSession
     * @return True if update successfully, otherwise false
     * @throws EarthException Common Earth exception
     */
    private boolean updateProcess(DataEditingTemplateForm form, HttpSession session) throws EarthException {
        DatProcess processMap = new DatProcess();
        processMap.setProcessId(EStringUtil.parseInt(form.getProcessId()));
        processMap.setWorkitemId(form.getWorkItemId());

        // Set template
        String templateId = form.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            processMap.setTemplateId(null);
            processMap.setMgrTemplate(null);
            processMap.setProcessData(null);
        } else {
            processMap.setTemplateId(form.getTemplateId());
            processMap.setMgrTemplate(copyFormToMgrTemplate(form));

            // Set Template Data
            processMap.setProcessData(copyDataToTemplateData(form));
        }

        // Update data to session
        return processService.updateProcessSession(session, form.getWorkspaceId(), processMap);
    }

    /**
     * Update WorkItem
     *
     * @param form DataEditingTemplateForm
     * @param session HttpSession
     * @return True if update successfully, otherwise false
     * @throws EarthException Common Earth exception
     */
    private boolean updateWorkItem(DataEditingTemplateForm form, HttpSession session) throws EarthException {
        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId(form.getWorkItemId());

        // Set template
        String templateId = form.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            workItem.setTemplateId(null);
            workItem.setMgrTemplate(null);
            workItem.setWorkItemData(null);
        } else {
            workItem.setTemplateId(form.getTemplateId());
            workItem.setMgrTemplate(copyFormToMgrTemplate(form));

            // Set Template Data
            workItem.setWorkItemData(copyDataToTemplateData(form));
        }

        // Update data to session
        return workItemService.updateWorkItemSession(session, form.getWorkspaceId(), workItem);
    }

    /**
     * Update Folder Item
     *
     * @param form DataEditingTemplateForm
     * @param session HttpSession
     * @return True if update successfully, otherwise false
     * @throws EarthException Common Earth exception
     */
    private boolean updateFolderItem(DataEditingTemplateForm form, HttpSession session) throws EarthException {
        FolderItem folderItem = new FolderItem();
        folderItem.setFolderItemNo(form.getFolderItemNo());
        folderItem.setWorkitemId(form.getWorkItemId());
        folderItem.setAction(Action.UPDATE.getAction());

        // Set template
        String templateId = form.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            folderItem.setTemplateId(null);
            folderItem.setMgrTemplate(null);
            folderItem.setFolderItemData(null);
        } else {
            folderItem.setTemplateId(form.getTemplateId());
            folderItem.setMgrTemplate(copyFormToMgrTemplate(form));

            // Set Template Data
            folderItem.setFolderItemData(copyDataToTemplateData(form));
        }

        // Update data to session
        return folderItemService.updateFolderItemSession(session, form.getWorkspaceId(),
            form.getWorkItemId(),folderItem);
    }

    /**
     * Update Document
     *
     * @param form DataEditingTemplateForm
     * @param session HttpSession
     * @return True if update successfully, otherwise false
     * @throws EarthException Common Earth exception
     */
    private boolean updateDocument(DataEditingTemplateForm form, HttpSession session) throws EarthException {
        Document document = new Document();
        document.setDocumentNo(form.getDocumentNo());
        document.setAction(Action.UPDATE.getAction());

        // Set template
        String templateId = form.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            document.setTemplateId(null);
            document.setMgrTemplate(null);
            document.setDocumentData(null);
        } else {
            document.setTemplateId(form.getTemplateId());
            document.setMgrTemplate(copyFormToMgrTemplate(form));

            // Set Template Data
            document.setDocumentData(copyDataToTemplateData(form));
        }

        // Update data to session
        return documentService.updateDocumentSession(session, form.getWorkspaceId(), form.getWorkItemId(),
            form.getFolderItemNo(), document);
    }
}
