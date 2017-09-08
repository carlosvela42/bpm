package co.jp.nej.earth.web.restcontroller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.session.EarthSessionManager;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.ws.CloseAndSaveWorkItemRequest;
import co.jp.nej.earth.model.ws.CloseProcessRequest;
import co.jp.nej.earth.model.ws.GetProcessRequest;
import co.jp.nej.earth.model.ws.GetProcessResponse;
import co.jp.nej.earth.model.ws.OpenProcessRequest;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.model.ws.UpdateProcessRequest;
import co.jp.nej.earth.service.ProcessService;
import co.jp.nej.earth.service.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/WS")
public class ProcessRestController extends BaseRestController {
    @Autowired
    private ProcessService processService;

    @Autowired
    private WorkItemService workItemService;

    @RequestMapping(value = "/openProcess", method = RequestMethod.POST)
    public Response openProcess(@Valid @RequestBody OpenProcessRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            List<Message> result = workItemService.openWorkItem(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(),
                    Integer.parseInt(request.getMode()));
            if (result.size() > 0) {
                return new Response(result);
            }

            return new Response(true);
        });
    }

    @RequestMapping(value = "/closeProcess", method = RequestMethod.POST)
    public Response closeProcess(@Valid @RequestBody CloseProcessRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            List<Message> result = workItemService.closeWorkItem(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId());

            if (result.size() > 0) {
                return new Response(result);
            }

            return new Response(true);
        });
    }

    @RequestMapping(value = "/getProcess", method = RequestMethod.GET)
    public Response getProcess(@Valid GetProcessRequest request, BindingResult bindingResult)
            throws EarthException {
        return (Response) getRestResponse(request, bindingResult, () -> {

            DatProcess process = processService.getProcessSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), Integer.parseInt(request.getProcessId()));

            GetProcessResponse respone = new GetProcessResponse(true);
            respone.setProcessResult(process);

            return respone;
        });
    }

    @RequestMapping(value = "/updateProcess", method = RequestMethod.POST)
    public Response updateProcess(@Valid @RequestBody UpdateProcessRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = processService.updateProcessSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getDatProcess());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/closeAndSaveProcess", method = RequestMethod.POST)
    public Response closeAndSaveProcess(@Valid @RequestBody CloseAndSaveWorkItemRequest request,
            BindingResult bindingResult) throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = workItemService.closeAndSaveWorkItem(EarthSessionManager.find(request.getToken()),
                    request.getWorkItemId(), request.getWorkspaceId());
            return new Response(result);
        });
    }
}