package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.CreateSchemaException;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.StrageDb;
import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DocumentDataSavePath;
import co.jp.nej.earth.model.form.DeleteProcessForm;
import co.jp.nej.earth.model.form.ProcessForm;
import co.jp.nej.earth.service.MstCodeService;
import co.jp.nej.earth.service.ProcessService;
import co.jp.nej.earth.service.SiteService;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author cuong
 */
@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController {

    public static final String URL = "process";

    static final List<String> fieldsOrder
        = Arrays.asList(new String[] {"processName","processVersion", "description",
            "schemaName","dbUser","dbPassword","owner","dbServer", "siteId"});

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    @Autowired
    private MstCodeService mstCodeService;

    @Autowired
    private EMessageResource eMessageResource;
    /**
     * get processes and workspaces from db.
     *
     * @param model
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = { "", "/" }, method = { RequestMethod.GET, RequestMethod.POST })
    public String showList(Model model, HttpServletRequest request) throws EarthException {
        if (SessionUtil.loadWorkspacesWithMessage(workspaceService, model, request, messageSource)) {
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
            SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
            model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM,searchClientForm);
            model.addAttribute("processes", processService.getAllByWorkspace(workspaceId));
            model.addAttribute("documentSaveDataPaths",
                    mstCodeService.getMstCodesBySection(Constant.MstCode.DOCUMENT_DATA_SAVE_PATH));
            model.addAttribute("messages", model.asMap().get("messages"));
        }
        return "process/processList";
    }

    /**
     * get list site id
     *
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = "/addNew", method = { RequestMethod.GET, RequestMethod.POST })
    public String addNew(Model model, HttpServletRequest request) throws EarthException {
        ProcessForm processForm = new ProcessForm();
        loadInfo(request, model, processForm, true);
        return "process/addProcess";
    }

    /**
     * insert process to db
     *
     * @param processForm
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("processForm") ProcessForm processForm, BindingResult bindResult,
                            Model model, HttpServletRequest request) throws EarthException {

        validateProcess(processForm, bindResult);
        List<Message> messages = validatorUtil.validate(bindResult, fieldsOrder);
        if (CollectionUtils.isEmpty(messages)) {
            processForm.setWorkspaceId(SessionUtil.getSearchConditionWorkspaceId(request.getSession()));
            boolean result = false;
            try {
                result = processService.insertOne(processForm, request.getSession().getId());
            } catch (EarthException exception) {
                if (exception instanceof CreateSchemaException) {
                    messages.add(
                        new Message(Constant.ErrorCode.E0038, eMessageResource.get(Constant.ErrorCode.E0038)));
                } else {
                    throw new EarthException(exception);
                }
            }

            if(result) {
                return redirectToList(URL);
            }
        }

        loadInfo(request, model, processForm, true);
        model.addAttribute("messages", messages);
        return "process/addProcess";

    }

    /**
     * show process detail
     *
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = "/showDetail", method = { RequestMethod.GET, RequestMethod.POST })
    public String showDetail(String processId, HttpServletRequest request, Model model,
        final RedirectAttributes redirectAttributes) throws EarthException {
        try {
            String workspaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
            Map<String, Object> result = processService.getDetail(workspaceId, processId);
            if(result.get("process") == null) {
                return recordNotFound(redirectAttributes);
            }
            ProcessForm processForm = new ProcessForm();
            processForm.setProcess((MgrProcess) result.get("process"));
            processForm.setStrageDb((StrageDb) result.get("strageDb"));
            processForm.setStrageFile((StrageFile) result.get("strageFile"));
            loadInfo(request, model, processForm, false);

            return "process/addProcess";
        } catch (EarthException ex) {
            return redirectToList();
        }
    }

    /**
     * update process
     *
     * @param processForm
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("processForm") ProcessForm processForm, BindingResult result,
            HttpServletRequest request, Model model) throws EarthException {
        validateProcess(processForm, result);
        List<Message> messages = validatorUtil.validate(result);

        if(CollectionUtils.isEmpty(messages)) {
            if (processService.updateOne(processForm)) {
                return redirectToList(URL);
            } else {
                messages.addAll(updateFailed());
            }
        }

        loadInfo(request, model, processForm, false);
        model.addAttribute("messages", messages);
        return "process/addProcess";
    }

    /**
     * delete list process
     *
     * @param deleteProcessForm
     * @return
     * @throws EarthException
     */
    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(DeleteProcessForm deleteProcessForm, final RedirectAttributes redirectAttrs,
            HttpServletRequest request) throws EarthException {
        if (!processService.deleteList(deleteProcessForm)){
            return deleteFail(redirectAttrs);
        }
        return redirectToList();
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList(URL);
    }

    /**
     * download file
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) throws IOException {
        // TODO
        File file = new File("C:\\temp.txt");
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    private void loadInfo(HttpServletRequest request, Model model, ProcessForm processForm, boolean isUpdate) throws
        EarthException {
        String workSpaceId = SessionUtil.getSearchConditionWorkspaceId(request.getSession());
        model.addAttribute("siteIds", siteService.getAllSiteIds());
        model.addAttribute("workspaceId", workSpaceId);
        model.addAttribute("isUpdate", isUpdate);

        MgrProcess process = processForm.getProcess();
        if (process == null) {
            process = new MgrProcess();
            process.setDocumentDataSavePath(String.valueOf(DocumentDataSavePath.DATABASE.getId()));
            processForm.setProcess(process);
        }
        model.addAttribute("process", process);

        StrageDb strageDb = processForm.getStrageDb();
        if (strageDb == null) {
            strageDb = new StrageDb();
            processForm.setStrageDb(strageDb);
        }
        model.addAttribute("strageDb", strageDb);

        StrageFile strageFile = processForm.getStrageFile();
        if (strageFile == null) {
            strageFile = new StrageFile();
            processForm.setStrageFile(strageFile);
        }
        model.addAttribute("strageFile", strageFile);
    }

    private void validateProcess(ProcessForm processForm, BindingResult result) {
        validator.validate(processForm.getProcess(), result);
        if (processForm.getProcess().isSavePathDatabaseType()) {
            validator.validate(processForm.getStrageDb(), result);
        } else {
            validator.validate(processForm.getStrageFile(), result);
        }
    }
}
