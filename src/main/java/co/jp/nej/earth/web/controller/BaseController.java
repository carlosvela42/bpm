package co.jp.nej.earth.web.controller;

import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import co.jp.nej.earth.web.form.SearchClientForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class BaseController {
    @Autowired
    protected EMessageResource messageSource;

    protected String redirectToList() {
        return "redirect:/" + getObjectName();
    }

    protected String listView() {
        return getObjectName();
    }

    protected String redirectToList(String url) {
        return "redirect:/" + url;
    }

    protected String getObjectName() {
        String[] parts = this.getClass().getName().split("\\.");
        String objectName = StringUtils.removeEnd(parts[parts.length - 1], "Controller");
        return WordUtils.uncapitalize(objectName);
    }

    protected String getListUrl() {
        return getObjectName();
    }

    /**
     * Message when record not found
     * @param redirectAttributes
     * @return
     */
    protected String recordNotFound(RedirectAttributes redirectAttributes) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Constant.ErrorCode.E0009, messageSource.get(
            Constant.ErrorCode.E0009,
            new String[]{ getObjectName()})));
        redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
        return redirectToList(getListUrl());
    }

    protected String deleteFail(RedirectAttributes redirectAttributes) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Constant.ErrorCode.E0033, messageSource.get(
            Constant.ErrorCode.E0033,
            new String[]{ getObjectName()})));
        redirectAttributes.addFlashAttribute(Constant.Session.MESSAGES, messages);
        return redirectToList(getListUrl());
    }

    protected List<Message> updateFailed() {
        return updateFailed(getObjectName());
    }

    protected List<Message> updateFailed(String target) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(Constant.ErrorCode.E0032, messageSource.get(
            Constant.ErrorCode.E0032, new String[]{target})));
        return messages;
    }

    private SearchClientForm getSearchConditionValue(HttpSession session, String screenKey) {
        SearchClientForm searchClientForm = null;
        if (!(EStringUtil.equals((String) session.getAttribute(Constant.Session.SCREEN_NAME), screenKey))) {
            session.removeAttribute(Constant.Session.SEARCH_CLIENT_FORM);
        }

        searchClientForm = (SearchClientForm) session.getAttribute(Constant.Session.SEARCH_CLIENT_FORM);
        if (searchClientForm == null) {
            searchClientForm = new SearchClientForm();
        }
        session.setAttribute(Constant.Session.SEARCH_CLIENT_FORM, searchClientForm);
        session.setAttribute(Constant.Session.SCREEN_NAME, screenKey);
        return searchClientForm;
    }

    /**
     * Get search condition value for client search
     */
    protected SearchClientForm getSearchConditionValueByScope(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return getSearchConditionValue(session, getObjectName());
    }

    /**
     * Get search condition value for client search
     */
    protected SearchClientForm getSearchConditionValueByScope(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return getSearchConditionValue(session, key);
    }

    private SearchByColumnsForm getSearchServerConditionValue(HttpSession session, String screenKey) {
        SearchByColumnsForm searchClientForm = null;
        if (!(EStringUtil.equals((String) session.getAttribute(Constant.Session.SCREEN_NAME), screenKey))) {
            session.removeAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
            session.removeAttribute(Constant.Session.WORK_ITEM_IDS);

        }

        searchClientForm = (SearchByColumnsForm) session.getAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM);
        if (searchClientForm == null) {
            searchClientForm = new SearchByColumnsForm();
        }
        session.setAttribute(Constant.Session.SEARCH_BY_COLUMNS_FORM, searchClientForm);
        session.setAttribute(Constant.Session.SCREEN_NAME, screenKey);
        return searchClientForm;
    }

    /**
     * Get search condition value for client search
     */
    protected SearchByColumnsForm getSearchServerConditionValueByScope(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return getSearchServerConditionValue(session, getObjectName());
    }

    /**
     * Get search condition value for client search
     */
    protected SearchByColumnsForm getSearchServerConditionValueByScope(HttpServletRequest request, String key) {
        HttpSession session = request.getSession();
        return getSearchServerConditionValue(session, key);
    }
}
