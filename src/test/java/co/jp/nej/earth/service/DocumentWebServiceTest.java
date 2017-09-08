package co.jp.nej.earth.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.WorkItem;

public class DocumentWebServiceTest extends BaseTest {

    @Autowired
    private HttpSession session;

    @Autowired
    private DocumentService documentService;

    @Test
    public void getDocument() throws Exception {

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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), true);*/
    }

    @Test
    public void getDocument1() throws Exception {

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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "2", 1, 1), false);*/
    }

    @Test
    public void getDocument2() throws Exception {

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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 2, 1), false);*/
    }

    @Test
    public void getDocument3() throws Exception {

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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 2), false);*/
    }

    @Test
    public void getDocument6() throws Exception {

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
        folderItem.setDocuments(null);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), false);*/
    }

    @Test
    public void getDocument7() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        workItem.setFolderItems(null);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), false);*/
    }

    @Test
    public void getDocument8() throws Exception {

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
        folderItem.setWorkitemId("2");
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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), false);*/
    }

    @Test
    public void getDocument9() throws Exception {

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
        document.setFolderItemNo("2");
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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), false);*/
    }

    @Test
    public void getDocument10() throws Exception {

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
        document.setWorkitemId("2");

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
        /*Assert.assertEquals(documentService.getDocument(session, "003", "1", 1, 1), false);*/
    }

    @Test
    public void getDocument14() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            documentService.getDocumentSession(session, "003", "1", "1", "1");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateDocument() throws Exception {
        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            documentService.updateDocumentSession(session, "003", "1", "1", new Document());
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateDocument1() throws Exception {

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
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "2", "1", new Document()), false);
    }

    @Test
    public void updateDocument2() throws Exception {

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
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "2", new Document()), false);
    }

    @Test
    public void updateDocument3() throws Exception {

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
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument6() throws Exception {

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
        folderItem.setDocuments(null);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument7() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        TemplateData templateData = new TemplateData();
        templateData.setDataMap(new HashMap<>());
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        
        workItem.setLastHistoryNo(0);
        workItem.setWorkItemData(templateData);
        workItem.setFolderItems(null);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument11() throws Exception {

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
        folderItem.setWorkitemId("2");
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
        layer.setWorkitemId("2");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument12() throws Exception {

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
        document.setWorkitemId("2");

        List<Layer> layers = new ArrayList<>();
        Layer layer = new Layer();
        layer.setAction(1);
        layer.setAnnotations("1");
        layer.setDocumentNo("1");
        layer.setFolderItemNo("1");
        layer.setLayerData(templateData);
        layer.setLayerNo("1");
        layer.setOwnerId("1");
        layer.setWorkitemId("2");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument13() throws Exception {

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
        document.setFolderItemNo("2");
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
        layer.setWorkitemId("2");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", new Document()), false);
    }

    @Test
    public void updateDocument14() throws Exception {

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
        Assert.assertEquals(documentService.updateDocumentSession(session, "003", "1", "1", document), true);
    }

//    @Test
//    public void getImagesByFolderItem() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        WorkItem workItem2 = new WorkItem();
//        workItem2.setWorkitemId("1");
//        TemplateData templateData2 = new TemplateData();
//        templateData2.setDataMap(new HashMap<>());
//        workItem2.setTemplateId("1");
//        workItem2.setTaskId("1");
//        workItem2.setEventStatus(1);
//        workItem2.setLastHistoryNo(0);
//        workItem2.setWorkItemData(templateData2);
//        List<FolderItem> folderItems2 = new ArrayList<>();
//        FolderItem folderItem2 = new FolderItem();
//        folderItem2.setAction(1);
//        folderItem2.setFolderItemNo("1");
//        folderItem2.setWorkitemId("1");
//        folderItem2.setTemplateId("1");
//        folderItem2.setFolderItemData(templateData2);
//        List<Document> documents2 = new ArrayList<>();
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData2);
//        document2.setDocumentNo("1");
//        document2.setDocumentPath("1");
//        document.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        List<Layer> layers2 = new ArrayList<>();
//        Layer layer2 = new Layer();
//        layer2.setAction(1);
//        layer2.setAnnotations("1");
//        layer2.setDocumentNo("1");
//        layer2.setFolderItemNo("1");
//        layer2.setLayerData(templateData);
//        layer2.setLayerNo("1");
//        layer2.setOwnerId("1");
//        layer2.setWorkitemId("1");
//        layer2.setTemplateId("1");
//        layers2.add(layer2);
//        document2.setLayers(layers2);
//        documents2.add(document2);
//        folderItem2.setDocuments(documents2);
//        folderItems2.add(folderItem2);
//        workItem2.setFolderItems(folderItems2);
//        session.setAttribute("TMP003&" + workItem2.getWorkitemId(), workItem2);
//        Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), true);
//    }
//
//    @Test
//    public void displayImage2() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "2", 1, 1), false);
//    }
//
//    @Test
//    public void displayImage3() throws Exception {
//        session.setAttribute("ORIGIN003&1", new ArrayList<>());
//        try {
//            documentService.getImagesByFolderItem(session, "003", "1", 1, 1);
//        } catch (Exception e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void displayImage4() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", null);
//        try {
//            documentService.getImagesByFolderItem(session, "003", "1", 1, 1);
//        } catch (Exception e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void displayImage5() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", new ArrayList<>());
//        try {
//            documentService.getImagesByFolderItem(session, "003", "1", 1, 1);
//        } catch (Exception e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void displayImage6() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        folderItem.setDocuments(null);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), false);
//    }
//
//    @Test
//    public void displayImage7() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", null);
//        File fileInput = new File("src\\test\\resources\\fileData.xml");
//        this.excuteTest("003", fileInput, () -> {
//            try {
//                Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }
//
//    @Test
//    public void displayImage8() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", null);
//        File fileInput = new File("src\\test\\resources\\fileData2.xml");
//        this.excuteTest("003", fileInput, () -> {
//            try {
//                Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }
//
//    @Test
//    public void displayImage9() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", null);
//        File fileInput = new File("src\\test\\resources\\fileData3.xml");
//        this.excuteTest("003", fileInput, () -> {
//            try {
//                Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }
//
//    @Test
//    public void displayImage10() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        List<Layer> layers = new ArrayList<>();
//        Layer layer = new Layer();
//        layer.setAction(1);
//        layer.setAnnotations("1");
//        layer.setDocumentNo("1");
//        layer.setFolderItemNo("1");
//        layer.setLayerData(templateData);
//        layer.setLayerNo("1");
//        layer.setOwnerId("1");
//        layer.setWorkitemId("1");
//        layer.setTemplateId("1");
//        layers.add(layer);
//        document.setLayers(layers);
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
//
//        session.setAttribute("TMP003&1", null);
//        File fileInput = new File("src\\test\\resources\\fileData4.xml");
//        this.excuteTest("003", fileInput, () -> {
//            try {
//                Assert.assertEquals(documentService.getImagesByFolderItem(session, "003", "1", 1, 1), true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }

//    @Test
//    public void closeImage() throws Exception {
//        Assert.assertEquals(documentService.closeImage(session, "003", "1"), true);
//    }
//
//    @Test
//    public void closeImageViewer() throws Exception {
//        Assert.assertEquals(documentService.closeImageViewer(session, "003", "1"), true);
//    }
//
//    @Test
//    public void saveAndCloseImageViewer() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), null);
//        Assert.assertEquals(documentService.saveAndCloseImageViewer(session, "003", "1", new ArrayList<>()),
//                false);
//    }
//
//    @Test
//    public void saveAndCloseImages1() throws Exception {
//        session.setAttribute("TMP003&1", new ArrayList<>());
//        try {
//            documentService.saveAndCloseImageViewer(session, "003", "1", new ArrayList<>());
//        } catch (Exception e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void saveAndCloseImages2() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Assert.assertEquals(documentService.saveAndCloseImageViewer(session, "003", "1", documents), true);
//    }
//
//    @Test
//    public void saveAndCloseImages3() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(null);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Assert.assertEquals(documentService.saveAndCloseImageViewer(session, "003", "1", documents), true);
//    }
//
//    @Test
//    public void saveAndCloseImages4() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        workItem.setFolderItems(null);
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Assert.assertEquals(documentService.saveAndCloseImageViewer(session, "003", "1", documents), true);
//    }
//
//    @Test
//    public void saveAndCloseImages5() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        List<Document> documents2 = new ArrayList<>();
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo(2);
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("2");
//        documents2.add(document2);
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Assert.assertEquals(documentService.saveAndCloseImageViewer(session, "003", "1", documents2), true);
//    }
//
//    @Test
//    public void saveImage() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document), true);
//    }
//
//    @Test
//    public void saveImage1() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage2() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo(2);
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage3() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("2");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage4() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        folderItem.setDocuments(null);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage5() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        workItem.setFolderItems(null);
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage6() throws Exception {
//        session.setAttribute("TMP003&1", new ArrayList<>());
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//        try {
//            documentService.saveImage(session, "003", document2);
//        } catch (Exception e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    @Test
//    public void saveImage7() throws Exception {
//        session.setAttribute("TMP003&1", null);
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentNo(2);
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage8() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo("1");
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("2");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo("1");
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage9() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("1");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo(2);
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo("1");
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }
//
//    @Test
//    public void saveImage10() throws Exception {
//        WorkItem workItem = new WorkItem();
//        workItem.setWorkitemId("1");
//        TemplateData templateData = new TemplateData();
//        templateData.setDataMap(new HashMap<>());
//        workItem.setTemplateId("1");
//        workItem.setTaskId("1");
//        
//        workItem.setLastHistoryNo(0);
//        workItem.setWorkItemData(templateData);
//        List<FolderItem> folderItems = new ArrayList<>();
//        FolderItem folderItem = new FolderItem();
//        folderItem.setAction(1);
//        folderItem.setFolderItemNo("1");
//        folderItem.setWorkitemId("2");
//        folderItem.setTemplateId("1");
//        folderItem.setFolderItemData(templateData);
//        List<Document> documents = new ArrayList<>();
//        Document document = new Document();
//        document.setAction(1);
//        document.setDocumentBinary(new byte[10]);
//        document.setDocumentData(templateData);
//        document.setDocumentNo("1");
//        document.setDocumentPath("1");
//        document.setDocumentType("1");
//        document.setFolderItemNo(2);
//        document.setPageCount(1);
//        document.setTemplateId("1");
//        document.setViewInformation("1");
//        document.setWorkitemId("1");
//
//        documents.add(document);
//        folderItem.setDocuments(documents);
//        folderItems.add(folderItem);
//        workItem.setFolderItems(folderItems);
//
//        Document document2 = new Document();
//        document2.setAction(1);
//        document2.setDocumentBinary(new byte[10]);
//        document2.setDocumentData(templateData);
//        document2.setDocumentNo("1");
//        document2.setDocumentPath("1");
//        document2.setDocumentType("1");
//        document2.setFolderItemNo("1");
//        document2.setPageCount(1);
//        document2.setTemplateId("1");
//        document2.setViewInformation("1");
//        document2.setWorkitemId("1");
//
//        session.setAttribute("TMP003&" + workItem.getWorkitemId(), workItem);
//
//        Assert.assertEquals(documentService.saveImage(session, "003", document2), false);
//    }

    @Test
    public void getThumbnail() throws Exception {
        File fileInput = new File("src\\test\\resources\\fileData2.xml");
        this.excuteTest("003", fileInput, () -> {
            try {
                Assert.assertEquals(documentService.getAnnotationsByDocument(session, "003", "1", "1", "1"), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void getThumbnail1() throws Exception {
        File fileInput = new File("src\\test\\resources\\fileData.xml");
        this.excuteTest("003", fileInput, () -> {
            try {
                Assert.assertEquals(documentService.getAnnotationsByDocument(session, "003", "1", "1", "1"), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }
}
