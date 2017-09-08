package co.jp.nej.earth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.TemplateBusinessCollection;


@Controller
@RequestMapping("/templateBusiness")
public class TemplateBusinessController extends BaseController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() throws EarthException {
        return "templateBusiness/menuList";
    }

    @RequestMapping(value = {"/imageSearch"}, method = RequestMethod.GET)
    public String imageSearch() throws EarthException {
        return "templateBusiness/imageSearch";
    }

    @RequestMapping(value = {"/imageDisplay"}, method = RequestMethod.GET)
    public String imageDisplay(Model model) throws EarthException {

        TemplateBusinessCollection templateCollection = new TemplateBusinessCollection();
        templateCollection.creteData();

        model.addAttribute("templateCollection", templateCollection);

        TemplateBusinessCollection popupData = new TemplateBusinessCollection();
        popupData.createDataPopup();

        TemplateBusinessCollection popupTableData = new TemplateBusinessCollection();
        popupTableData.createDataTablePopup();

        model.addAttribute("popupTableData", popupTableData);

        model.addAttribute("popupData", popupData);

        // Load documentInfo
        TemplateBusinessCollection documentInfo = new TemplateBusinessCollection();
        documentInfo.createDocumentInfo();
        model.addAttribute("documentInfo", documentInfo);

        // Load Combox status
        TemplateBusinessCollection comboxStatus = new TemplateBusinessCollection();
        comboxStatus.createComboxItem();
        model.addAttribute("comboxStatus", comboxStatus);

        return "templateBusiness/imageDisplay";
    }

    @RequestMapping(value = {"/indexRegistrationList"}, method = RequestMethod.GET)
    public String indexRegistrationList() throws EarthException {
        return "templateBusiness/indexRegistrationList";
    }

    @RequestMapping(value = {"/indexRegistration"}, method = RequestMethod.GET)
    public String indexRegistration(Model model) throws EarthException {
        TemplateBusinessCollection templateCollection = new TemplateBusinessCollection();
        templateCollection.creteData();

        model.addAttribute("templateCollection", templateCollection);

        // Load documentInfo
        TemplateBusinessCollection documentInfo = new TemplateBusinessCollection();
        documentInfo.createDocumentInfo();
        model.addAttribute("documentInfo", documentInfo);

        return "templateBusiness/indexRegistration";
    }

    @RequestMapping(value = {"/progressManagement"}, method = RequestMethod.GET)
    public String progressManagement() throws EarthException {
        return "templateBusiness/progressManagement";
    }

    @RequestMapping(value = {"/insertOne"}, method = RequestMethod.POST)
    public String insertOne(String tempId, String selectedTableIds, String checkedIds) {
        String id = tempId;
        String tableIds = selectedTableIds;
        String checkId = checkedIds;
        return redirectToList("templateBusiness/imageDisplay");
    }
}



