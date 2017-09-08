/**
 * Created by thanhnt on 6/8/2017.
 */
package co.jp.nej.earth.model.entity;

import java.util.ArrayList;
import java.util.List;

public class TemplateBusinessCollection {
    public List<TemplateBusinessData> getTemplateBusinessDataEntityList() {
        return templateBusinessDataEntityList;
    }

    public void setTemplateBusinessDataEntityList(List<TemplateBusinessData> templateBusinessDataEntityList) {
        this.templateBusinessDataEntityList = templateBusinessDataEntityList;
    }

    private List<TemplateBusinessData> templateBusinessDataEntityList;

    public TemplateBusinessCollection() {
        this.templateBusinessDataEntityList = new ArrayList<TemplateBusinessData>();
    }

    public void addItem(TemplateBusinessData templateBusinessDataEntity) {
        this.templateBusinessDataEntityList.add(templateBusinessDataEntity);
    }

    public void creteData() {
        List<String> imageListTemp = new ArrayList<String>();
        imageListTemp.add("imageViewer.png");
        imageListTemp.add("bg_icon_global_nav_01.png");
        imageListTemp.add("bg_icon_global_nav_01_active.png");
        imageListTemp.add("bg_icon_global_nav_authority.png");
        imageListTemp.add("bg_icon_global_nav_authority_active.png");
        imageListTemp.add("bg_icon_global_nav_data.png");
        imageListTemp.add("bg_icon_global_nav_data_active.png");
        imageListTemp.add("bg_icon_global_nav_main.png");

        TemplateBusinessData item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/01");
        item.setPageNumber("9");
        item.setListItem(imageListTemp);
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/03");
        item.setPageNumber("12");
        item.setListItem(imageListTemp);
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/05");
        item.setPageNumber("7");
        item.setListItem(imageListTemp);
        this.addItem(item);
    }

    public void createDataPopup() {
        List<String> listItemTemp = new ArrayList<String>();
        listItemTemp.add("11.送付状");
        listItemTemp.add("12.同意書");
        listItemTemp.add("21.振込先指示書");
        listItemTemp.add("22.交通費明細書");
        listItemTemp.add("23.診療報酬明細");
        listItemTemp.add("24.施術証明書");
        listItemTemp.add("25.請求書");
        listItemTemp.add("26.領収書");
        listItemTemp.add("31.回答書");
        listItemTemp.add("41.事故証明書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        listItemTemp.add("41.事故証明書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        listItemTemp.add("41.事故証明書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        listItemTemp.add("41.事故証明書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        listItemTemp.add("41.事故証明書");
        listItemTemp.add("42.職業証明書");
        listItemTemp.add("91.承諾書");
        listItemTemp.add("92.承諾書");
        TemplateBusinessData item = new TemplateBusinessData();
        item.setListItem(listItemTemp);
        this.addItem(item);
    }

    public void createDataTablePopup() {
        TemplateBusinessData item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/01");
        item.setPageNumber("9");
        item.setTemplateName("nissho01");
        item.setStatus("KiMoChi");
        item.setUnknowName("unknowName");
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/02");
        item.setPageNumber("6");
        item.setTemplateName("nissho02");
        item.setStatus("Active");
        item.setUnknowName("unknowName02");
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setCreatedDate("2017/04/03");
        item.setPageNumber("7");
        item.setTemplateName("nissho03");
        item.setStatus("Active");
        item.setUnknowName("unknowName03");
        this.addItem(item);
    }

    public void createDocumentInfo() {
        TemplateBusinessData item = new TemplateBusinessData();
        item.setFormTypeCode("12");
        item.setFormTypeName("同意書");
        item.setReceiptNumber("12-34567890-1-23");
        item.setDocumentType("01");
        item.setDocumentNumber("6");
        item.setUserChangeCode("Nissho");
        item.setApplicationMatter("申送り事項①");
        item.setDocumentStatus("処理中");
        this.addItem(item);
    }

    public void createComboxItem() {
        TemplateBusinessData item = new TemplateBusinessData();
        item.setStatusId("statusId1");
        item.setStatusName("処理中");
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setStatusId("statusId2");
        item.setStatusName("Kimochi");
        this.addItem(item);

        item = new TemplateBusinessData();
        item.setStatusId("statusId3");
        item.setStatusName("Kuta-tasoa");
        this.addItem(item);
    }
}

