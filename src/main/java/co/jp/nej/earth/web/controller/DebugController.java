package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.service.EventControlService;
import co.jp.nej.earth.service.WorkItemService;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/debug")
public class DebugController extends BaseController {

    @Autowired
    private DatabaseType databaseType;

    @Autowired
    private WorkItemService workItemService;

    @Autowired
    private EventControlService eventControlService;

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,String> info(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("session", request.getRequestedSessionId());
        map.put("dataType", databaseType.name());
        return map;
    }

    @RequestMapping(value = "/updateProcess", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map<String,String> updateProcess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String workspaceId = SessionUtil.getSearchConditionWorkspaceId(session);
        UserInfo userInfo = ConversionUtil.castObject(session.getAttribute(Constant.Session.USER_INFO), UserInfo.class);
        Map<String, String> map = new HashMap<>();
        try {
            Integer processServiceId = 1;
            workItemService.insertOrUpdateWorkItemToDbFromEvent(workspaceId, Thread.currentThread().getId(),
                request.getServerName(), processServiceId, userInfo.getUserId(), request.getSession().getId());
            map.put("message", "Cheer, let's drink beer!");
        } catch (EarthException e) {
            map.put("error EarthException", e.getMessage());
        } catch (IOException e) {
            map.put("error IOException", e.getMessage());
        }
        return map;
    }
}
