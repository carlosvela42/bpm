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

public class LayerWebServiceTest extends BaseTest {

    @Autowired
    private HttpSession session;

    @Autowired
    private LayerService layerService;

    @Test
    public void getLayer() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), true);*/
    }

    @Test
    public void getLayer1() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "2", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer2() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "2", "1", 1), false);*/
    }

    @Test
    public void getLayer3() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", 1, 2, 1), false);*/
    }

    @Test
    public void getLayer4() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 2), false);*/
    }

    @Test
    public void getLayer5() throws Exception {

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
        document.setLayers(null);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
       /* Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer6() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer7() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer8() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer9() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer10() throws Exception {

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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer11() throws Exception {

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
        layer.setFolderItemNo("2");
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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer12() throws Exception {

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
        layer.setWorkitemId("2");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer13() throws Exception {

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
        layer.setDocumentNo("2");
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
        /*Assert.assertEquals(layerService.getLayer(session, "003", "1", "1", "1", 1), false);*/
    }

    @Test
    public void getLayer14() throws Exception {

        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            /*layerService.getLayer(session, "003", "1", "1", "1", 1);*/
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateLayerSession() throws Exception {
        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("1");
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), new ArrayList<>());
        try {
            layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer());
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateLayer1() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "2", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer2() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "2", "1", new Layer()), false);
    }

    @Test
    public void updateLayer3() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "2", new Layer()), false);
    }

    @Test
    public void updateLayer4() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer5() throws Exception {

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
        document.setLayers(null);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer6() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer7() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer8() throws Exception {

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
        layer.setDocumentNo("2");
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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer9() throws Exception {

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
        layer.setFolderItemNo("2");
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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer10() throws Exception {

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
        layer.setWorkitemId("2");
        layer.setTemplateId("1");
        layers.add(layer);
        document.setLayers(layers);
        documents.add(document);
        folderItem.setDocuments(documents);
        folderItems.add(folderItem);
        workItem.setFolderItems(folderItems);
        session.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer11() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer12() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer13() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", new Layer()), false);
    }

    @Test
    public void updateLayer14() throws Exception {

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
        Assert.assertEquals(layerService.updateLayerSession(session, "003", "1", "1", "1", layer), true);
    }
}
