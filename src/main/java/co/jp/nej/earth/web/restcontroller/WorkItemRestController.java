package co.jp.nej.earth.web.restcontroller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.session.EarthSessionManager;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.ws.GetDocumentResponse;
import co.jp.nej.earth.model.ws.GetLayerResponse;
import co.jp.nej.earth.model.ws.GetWorkItemRequest;
import co.jp.nej.earth.model.ws.GetWorkItemResponse;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.model.ws.SearchWorkItemResponse;
import co.jp.nej.earth.model.ws.WorkItemUpdateRequest;
import co.jp.nej.earth.service.WorkItemService;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/WS")
public class WorkItemRestController extends BaseRestController {
    @Autowired
    private WorkItemService workItemService;

    @RequestMapping(value = "/getWorkItem", method = RequestMethod.GET)
    public Response getWorkItem(@Valid GetWorkItemRequest request, BindingResult result)
            throws EarthException {
        return getRestResponse(request, result, () -> {
            WorkItem workItem = workItemService.getWorkItemSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId());

            if (workItem == null) {
                return new GetLayerResponse(messageSource.get(ErrorCode.E0029, new String[] { "workItem" }));
            }

            return new GetWorkItemResponse(true, workItem);
        });

    }

    @RequestMapping(value = "/updateWorkItem", method = RequestMethod.POST)
    public Response updateWorkItem(@Valid @RequestBody WorkItemUpdateRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = workItemService.updateWorkItemSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItem());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/getWorkItemStructure", method = RequestMethod.GET)
    public Response getWorkItemStructure(@Valid GetWorkItemRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            WorkItem workItem = workItemService.getWorkItemStructureSession(
                    EarthSessionManager.find(request.getToken()), request.getWorkspaceId(),
                    request.getWorkItemId());
            if (workItem == null) {
                return new GetLayerResponse(messageSource.get(ErrorCode.E0029, new String[] { "workItem" }));
            }

            return new GetWorkItemResponse(true, workItem);
        });
    }

    @RequestMapping(value = "/searchWorkItems", method = RequestMethod.POST)
    public Response searchWorkItems(@Valid @RequestBody SearchByColumnsForm searchByColumnsForm,
        BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(searchByColumnsForm, bindingResult, () -> {
            Map<String, Object> workItemSearchResult = workItemService.searchWorkItems(EarthSessionManager.find(
                searchByColumnsForm.getToken()),
                searchByColumnsForm, searchByColumnsForm.getWorkspaceId());
            if (workItemSearchResult.size() == 0) {
                return new GetDocumentResponse(messageSource.get(ErrorCode.E0029, new String[] { "workItem" }));
            }

            boolean isSuccess = EStringUtil.isEmpty(workItemSearchResult.get("error"));
            return new SearchWorkItemResponse(isSuccess, workItemSearchResult);
        });
    }
}