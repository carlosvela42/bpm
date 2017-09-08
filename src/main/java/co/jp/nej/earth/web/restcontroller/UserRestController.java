package co.jp.nej.earth.web.restcontroller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.session.EarthSessionManager;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.Channel;
import co.jp.nej.earth.model.ws.LoginRequest;
import co.jp.nej.earth.model.ws.LoginResponse;
import co.jp.nej.earth.model.ws.Request;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.util.ValidatorUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/WS")
public class UserRestController extends BaseRestController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseRestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ValidatorUtil validatorUtil;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response login(@Valid LoginRequest loginRequest, BindingResult bindingResult, HttpServletRequest req)
            throws EarthException {
        try {
            LOG.info("Earth Spring Rest Webservice:");
            LOG.info("Request Content:" + new ObjectMapper().writeValueAsString(loginRequest));

            List<Message> messages = new ArrayList<>();
            // Validation data input.
            messages = validatorUtil.validate(bindingResult);
            if (messages.size() > 0) {
                return new LoginResponse(false, messages);
            }

            messages = userService.login(loginRequest.getUserId(), loginRequest.getPassword(), req.getSession(),
                    Channel.WEB_SERVICE);
            if (!Message.isAllWarningOrEmpty(messages)) {
                return new LoginResponse(false, messages);
            }

            UserInfo userInfo = (UserInfo) req.getSession().getAttribute(Constant.Session.USER_INFO);
            return new LoginResponse(true, userInfo.getLoginToken());
        } catch (JsonProcessingException e) {
            LOG.error(e.getMessage(), e);
            return new LoginResponse(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Response logout(@Valid Request logoutRequest, BindingResult bindingResult) throws EarthException {

        return getRestResponse(logoutRequest, bindingResult, () -> {
            boolean result = userService.logout(EarthSessionManager.find(logoutRequest.getToken()));
            return new Response(result);
        });
    }
}