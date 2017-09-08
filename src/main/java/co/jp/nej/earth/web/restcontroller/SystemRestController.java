package co.jp.nej.earth.web.restcontroller;

import javax.validation.Valid;

import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.model.ws.UpdateSystemDateRequest;
import co.jp.nej.earth.service.SystemConfigurationService;
import org.springframework.web.bind.annotation.RestController;

import static co.jp.nej.earth.model.constant.Constant.ErrorCode.E0011;

@RestController
@RequestMapping("/WS")
public class SystemRestController extends BaseRestController {
    @Autowired
    private SystemConfigurationService systemConfigurationService;

    @Autowired
    private EMessageResource messageResource;

    @RequestMapping(value = "/updateSystemDate", method = RequestMethod.POST)
    public Response updateSystemDate(@Valid @RequestBody UpdateSystemDateRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            if (!DateUtil.isDate(request.getDateInput(),Constant.DatePattern.YYYYMMDD)) {
                return new Response(messageResource.get(E0011, new String[]{"date.input"}));
            }

            boolean result = systemConfigurationService.updateSystemConfig(request.getDateInput());
            return new Response(result);
        });
    }

}
