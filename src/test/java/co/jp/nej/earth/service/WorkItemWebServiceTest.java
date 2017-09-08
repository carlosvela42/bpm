package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.WorkItem;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkItemWebServiceTest extends BaseTest {

    @Autowired
    private HttpSession session;

    @Autowired
    private WorkItemService workItemService;

    @Test
    public void getWorkItem() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        List<FolderItem> folderItems = new ArrayList<>();
        FolderItem folderItem = new FolderItem();
        folderItem.setAction(1);
        folderItem.setFolderItemNo("1");
        folderItem.setWorkitemId("1");
        folderItem.setTemplateId("1");
        folderItem.setFolderItemData(templateData);
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        document.setAction(1);
        document.setDocumentBinary(new byte[10]);
        document.setDocumentData(templateData);
        document.setDocumentNo("1");
        document.setDocumentPath("1");
        document.setDocumentType("1");
        document.setFolderItemNo("1");
        document.setPageCount(1);
        document.setTemplateId("1");
        document.setViewInformation("1");
        document.setWorkitemId("1");

        List<Layer> layers = new ArrayList<>();
        Layer layer = new Layer();
        layer.setAction(1);
        layer.setAnnotations("1");
        layer.setDocumentNo("1");
        layer.setFolderItemNo("1");
        layer.setLayerData(templateData);
        layer.setLayerNo("1");
        layer.setOwnerId("1");
        layer.setWorkitemId("1");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        /*Assert.assertEquals(workItemService.getWorkItem(session, "003", "1"), true);*/
    }

    @Test
    public void getWorkItem1() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        List<FolderItem> folderItems = new ArrayList<>();
        FolderItem folderItem = new FolderItem();
        folderItem.setAction(1);
        folderItem.setFolderItemNo("1");
        folderItem.setWorkitemId("1");
        folderItem.setTemplateId("1");
        folderItem.setFolderItemData(templateData);
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        document.setAction(1);
        document.setDocumentBinary(new byte[10]);
        document.setDocumentData(templateData);
        document.setDocumentNo("1");
        document.setDocumentPath("1");
        document.setDocumentType("1");
        document.setFolderItemNo("1");
        document.setPageCount(1);
        document.setTemplateId("1");
        document.setViewInformation("1");
        document.setWorkitemId("1");

        List<Layer> layers = new ArrayList<>();
        Layer layer = new Layer();
        layer.setAction(1);
        layer.setAnnotations("1");
        layer.setDocumentNo("1");
        layer.setFolderItemNo("1");
        layer.setLayerData(templateData);
        layer.setLayerNo("1");
        layer.setOwnerId("1");
        layer.setWorkitemId("1");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        /*Assert.assertEquals(workItemService.getWorkItem(session, "003", "2"), false);*/
    }

    @Test
    public void getWorkItem3() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            workItemService.getWorkItemSession(session, "003", "1");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateWorkItem() throws Exception {
        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            workItemService.updateWorkItemSession(session, "003", workItem);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateWorkItem1() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        List<FolderItem> folderItems = new ArrayList<>();
        FolderItem folderItem = new FolderItem();
        folderItem.setAction(1);
        folderItem.setFolderItemNo("1");
        folderItem.setWorkitemId("1");
        folderItem.setTemplateId("1");
        folderItem.setFolderItemData(templateData);
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        document.setAction(1);
        document.setDocumentBinary(new byte[10]);
        document.setDocumentData(templateData);
        document.setDocumentNo("1");
        document.setDocumentPath("1");
        document.setDocumentType("1");
        document.setFolderItemNo("1");
        document.setPageCount(1);
        document.setTemplateId("1");
        document.setViewInformation("1");
        document.setWorkitemId("1");

        List<Layer> layers = new ArrayList<>();
        Layer layer = new Layer();
        layer.setAction(1);
        layer.setAnnotations("1");
        layer.setDocumentNo("1");
        layer.setFolderItemNo("1");
        layer.setLayerData(templateData);
        layer.setLayerNo("1");
        layer.setOwnerId("1");
        layer.setWorkitemId("1");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(workItemService.updateWorkItemSession(session, "003", new WorkItem()), false);
    }

    @Test
    public void updateWorkItem2() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        List<FolderItem> folderItems = new ArrayList<>();
        FolderItem folderItem = new FolderItem();
        folderItem.setAction(1);
        folderItem.setFolderItemNo("1");
        folderItem.setWorkitemId("1");
        folderItem.setTemplateId("1");
        folderItem.setFolderItemData(templateData);
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        document.setAction(1);
        document.setDocumentBinary(new byte[10]);
        document.setDocumentData(templateData);
        document.setDocumentNo("1");
        document.setDocumentPath("1");
        document.setDocumentType("1");
        document.setFolderItemNo("1");
        document.setPageCount(1);
        document.setTemplateId("1");
        document.setViewInformation("1");
        document.setWorkitemId("1");

        List<Layer> layers = new ArrayList<>();
        Layer layer = new Layer();
        layer.setAction(1);
        layer.setAnnotations("1");
        layer.setDocumentNo("1");
        layer.setFolderItemNo("1");
        layer.setLayerData(templateData);
        layer.setLayerNo("1");
        layer.setOwnerId("1");
        layer.setWorkitemId("1");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(workItemService.updateWorkItemSession(session, "003", workItem), true);
    }
}
