package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EAutoIncrease;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.form.DeleteProcessForm;
import co.jp.nej.earth.service.DirectoryService;
import co.jp.nej.earth.util.DirectoryUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.ValidatorUtil;
import co.jp.nej.earth.web.form.DirectoryForm;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/directory")
public class DirectoryController extends BaseController {

    @Autowired
    private DirectoryService directoryService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EAutoIncrease eAutoIncrease;

    private static final String ADD_URL = "directory/addDirectory";

    @RequestMapping(value = {"", "/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String showList(Model model, HttpServletRequest request) throws EarthException {
        SearchClientForm searchClientForm = getSearchConditionValueByScope(request);
        model.addAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        model.addAttribute("directorys", directoryService.getAllDirectories());
        return "directory/directoryList";
    }

    @RequestMapping(value = "/deleteList", method = RequestMethod.POST)
    public String deleteList(DeleteProcessForm deleteForm, final RedirectAttributes redirectAttributes) {
        try {
            List<Integer> intDataDirectoryIds = deleteForm.getProcessIds();
            List<Message> messages = directoryService.validateDelete(intDataDirectoryIds, Constant.EARTH_WORKSPACE_ID);
            if (!CollectionUtils.isEmpty(messages)) {
                redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
            }else{
                directoryService.deleteDirectories(intDataDirectoryIds, Constant.EARTH_WORKSPACE_ID);
            }
            return redirectToList();
        } catch (Exception ex) {
            return redirectToList();
        }
    }

    @RequestMapping(value = "/addNew", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNew(Model model) throws EarthException {
        DirectoryForm directoryForm = new DirectoryForm();
        directoryForm.setDataDirectoryId(Constant.ID_DEFAULT);
        directoryForm.setNewCreateFile(String.valueOf(Constant.Directory.YES));
        model.addAttribute("directoryForm", directoryForm);
        return ADD_URL;
    }

    @RequestMapping(value = "/insertOne", method = RequestMethod.POST)
    public String insertOne(@Valid @ModelAttribute("directoryForm") DirectoryForm directoryForm, BindingResult result,
                            HttpServletRequest request, Model model) throws EarthException {
        Set<String> checkedWarnings = Message.getListWarning(directoryForm.getMessages());
        List<Message> messages = validatorUtil.validate(result);
        directoryForm.setLastUpdateTime(null);
        if (messages.size() > 0) {
            model.addAttribute("directoryForm", directoryForm);
            model.addAttribute(Session.MESSAGES, messages);
            return ADD_URL;
        }
        Directory directory = new Directory();
        directory.setDataDirectoryId(
            Integer.parseInt(eAutoIncrease.getAutoId(EarthId.DIRECTORY, request.getSession().getId())));
        directory.setNewCreateFile(Integer.valueOf(directoryForm.getNewCreateFile()));
        directory.setDiskVolSize(directoryForm.getDiskVolSize());
        directory.setReservedDiskVolSize(directoryForm.getReservedDiskVolSize());
        directory.setFolderPath(directoryForm.getFolderPath());

        messages = directoryService.validate(directory, Constant.EARTH_WORKSPACE_ID);
        Message.updateCheckedWarning(messages, checkedWarnings);
        if(CollectionUtils.isEmpty(messages) || Message.isAllWarningChecked(messages)) {
            directory.setDataDirectoryId(
                Integer.parseInt(eAutoIncrease.getAutoId(EarthId.DIRECTORY, request.getSession().getId())));
            directoryService.insertOne(directory, Constant.EARTH_WORKSPACE_ID);
            return redirectToList();
        } else {
            model.addAttribute(Session.MESSAGES, messages);
            model.addAttribute("directory", directory);
            return ADD_URL;
        }
    }

    @RequestMapping(value = "/showDetail", method = {RequestMethod.GET, RequestMethod.POST})
    public String showDetail(@ModelAttribute("dataDirectoryId") String dataDirectoryId, Model model,
        final RedirectAttributes redirectAttributes) throws EarthException {
        DirectoryForm directoryForm = new DirectoryForm();
        if (!EStringUtil.isEmpty(dataDirectoryId)) {
            Directory directory = directoryService.getById(dataDirectoryId, Constant.EARTH_WORKSPACE_ID);
            if(directory == null) {
                return recordNotFound(redirectAttributes);
            }
            directoryForm.setDataDirectoryId(String.valueOf(directory.getDataDirectoryId()));
            directoryForm.setNewCreateFile(String.valueOf(directory.getNewCreateFile()));
            directoryForm.setReservedDiskVolSize(directory.getReservedDiskVolSize());
            directoryForm.setDiskVolSize(directory.getDiskVolSize());
            directoryForm.setFolderPath(directory.getFolderPath());
            directoryForm.setLastUpdateTime(directory.getLastUpdateTime());
            model.addAttribute("directoryForm", directoryForm);
        }
        return ADD_URL;
    }

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    public String updateOne(@Valid @ModelAttribute("directoryForm") DirectoryForm directoryForm, BindingResult result,
                            HttpServletRequest request, Model model) throws EarthException {
        Set<String> checkedWarnings = Message.getListWarning(directoryForm.getMessages());
        List<Message> messages = validatorUtil.validate(result);

        if(CollectionUtils.isEmpty(messages)) {
            Directory directory = new Directory();
            directory.setDataDirectoryId(Integer.valueOf(directoryForm.getDataDirectoryId()));
            directory.setNewCreateFile(Integer.valueOf(directoryForm.getNewCreateFile()));
            directory.setDiskVolSize(directoryForm.getDiskVolSize());
            directory.setReservedDiskVolSize(directoryForm.getReservedDiskVolSize());
            directory.setFolderPath(directoryForm.getFolderPath());

            messages = directoryService.validate(directory, Constant.EARTH_WORKSPACE_ID);
            Message.updateCheckedWarning(messages, checkedWarnings);
            if(CollectionUtils.isEmpty(messages) || Message.isAllWarningChecked(messages)) {
                if(directoryService.updateDirectory(directory, Constant.EARTH_WORKSPACE_ID)){
                    return redirectToList();
                } else {
                    messages.addAll(updateFailed());
                }
            }
        }

        model.addAttribute(Session.MESSAGES, messages);
        model.addAttribute("directoryForm", directoryForm);
        return ADD_URL;
    }

    @RequestMapping(value = "/getSizeFolder", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeFolder(@ModelAttribute("folderPath") String folderPath, Model model, HttpServletRequest request)
        throws EarthException {
        return DirectoryUtil.getSizeFolder(folderPath);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancel() {
        return redirectToList();
    }
}
