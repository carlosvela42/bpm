package co.jp.nej.earth.web.restcontroller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.model.ws.UnlockEventRequest;
import co.jp.nej.earth.service.EventControlService;
import co.jp.nej.earth.util.ValidatorUtil;
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
public class EventRestController extends BaseRestController {
    @Autowired
    private EventControlService eventControlService;

    @Autowired
    private ValidatorUtil validatorUtil;

    @RequestMapping(value = "/unlockEventControl", method = RequestMethod.POST)
    public Response unlockEventControl(@Valid @RequestBody UnlockEventRequest request, BindingResult bindingResult)
        throws EarthException {
        List<Message> messages = validatorUtil.validate(bindingResult);
        if (messages.size() > 0) {
            return new Response(messages);
        }
        String eventId = eventControlService.getEventIdByWorkItemId(request.getWorkspaceId(), request.getWorkItemId());
        if (eventId != "") {
            return getRestResponse(request, bindingResult, () -> {
                boolean result = eventControlService.unlockEventControl(request.getWorkspaceId(), eventId);
                return new Response(result);
            });
        }
        return getRestResponse(request, bindingResult, () -> {
            return new Response(false);
        });
    }
}
