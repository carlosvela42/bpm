package co.jp.nej.earth.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.StrageDb;
import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.form.DeleteProcessForm;
import co.jp.nej.earth.model.form.ProcessForm;

public class ProcessServiceTest extends BaseTest {

    @Autowired
    private ProcessService service;

    @Autowired
    private WorkItemService workItemService;
    
    @Autowired
    private HttpSession httpSession;

//    @Test
//    public void openProcess() throws Exception {
//        String workspaceId = "003";
//        File fileInput = new File("src\\test\\resources\\processMap.xml");
//        this.excuteTest(workspaceId, fileInput, () -> {
//            try {
//                Assert.assertEquals(service.openProcess(httpSession, workspaceId, "1", "1", OpenProcessMode.EDIT.getMode()),
//                        true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }
//
//    @Test
//    public void openProcess1() throws Exception {
//        String workspaceId = "003";
//        File fileInput = new File("src\\test\\resources\\processMap.xml");
//        this.excuteTest(workspaceId, fileInput, () -> {
//            try {
//                Assert.assertEquals(service.openProcess(httpSession, workspaceId, "1", "1", OpenProcessMode.READ_ONLY.getMode()),
//                        true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//
//    }

//    @Test
//    public void closeProcess() throws Exception {
//        String workspaceId = "003";
//        File fileInput = new File("src\\test\\resources\\processMap.xml");
//        this.excuteTest(workspaceId, fileInput, () -> {
//            try {
//                Assert.assertEquals(service.closeProcess(httpSession, "003", "1", "1"), true);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }
//
//    @Test
//    public void closeProcess1() throws Exception {
//        String workspaceId = "003";
//        File fileInput = new File("src\\test\\resources\\processMap.xml");
//        this.excuteTest(workspaceId, fileInput, () -> {
//            try {
//                Assert.assertEquals(service.closeProcess(httpSession, "003", "2", "1"), false);
//            } catch (Exception e) {
//                throw e;
//            }
//        });
//    }

    @Test
    public void getProcess() throws Exception {
        WorkItem workItem2 = new WorkItem();
        workItem2.setWorkitemId("1");
        TemplateData templateData2 = new TemplateData();
        templateData2.setDataMap(new HashMap<>());
        workItem2.setTemplateId("1");
        workItem2.setTaskId("1");
        
        workItem2.setLastHistoryNo(0);
        workItem2.setWorkItemData(templateData2);
        List<FolderItem> folderItems2 = new ArrayList<>();
        FolderItem folderItem2 = new FolderItem();
        folderItem2.setAction(1);
        folderItem2.setFolderItemNo("1");
        folderItem2.setWorkitemId("1");
        folderItem2.setTemplateId("1");
        folderItem2.setFolderItemData(templateData2);
        List<Document> documents2 = new ArrayList<>();
        Document document2 = new Document();
        document2.setAction(1);
        document2.setDocumentBinary(new byte[10]);
        document2.setDocumentData(templateData2);
        document2.setDocumentNo("1");
        document2.setDocumentPath("1");
        document2.setDocumentType("1");
        document2.setFolderItemNo("1");
        document2.setPageCount(1);
        document2.setTemplateId("1");
        document2.setViewInformation("1");
        document2.setWorkitemId("1");
        documents2.add(document2);
        folderItem2.setDocuments(documents2);
        folderItems2.add(folderItem2);
        workItem2.setFolderItems(folderItems2);
        httpSession.setAttribute("ORIGIN003&" + workItem2.getWorkitemId(), workItem2);
//        Assert.assertTrue(service.getProcess(httpSession, "003", 1) != null);

    }

    @Test
    public void updateProcess() throws Exception {
        HttpSession session = Mockito.mock(HttpSession.class);
        DatProcess datProcess = new DatProcess();
        datProcess.setProcessId(1);
        datProcess.setTemplateId("1");
        datProcess.setWorkitemId("1");
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processMap.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateProcessSession(session, "003", datProcess), false);
            } catch (Exception e) {
                throw e;
            }
        });

    }

    @Test
    public void getAllByWorkspace() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.getAllByWorkspace(workspaceId).size(), 3);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void closeAndSaveProcess() throws Exception {
        WorkItem workItem2 = new WorkItem();
        workItem2.setWorkitemId("1");
        httpSession.setAttribute("ORIGIN003&" + workItem2.getWorkitemId(), workItem2);
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
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processMap.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            Assert.assertEquals(workItemService.closeAndSaveWorkItem(httpSession, workItem.getWorkitemId(), workspaceId), true);
        });
    }

    @Test
    public void closeAndSaveProcess2() throws Exception {
        WorkItem workItem2 = new WorkItem();
        workItem2.setWorkitemId("1");
        TemplateData templateData2 = new TemplateData();
        templateData2.setDataMap(new HashMap<>());
        workItem2.setTemplateId("1");
        workItem2.setTaskId("1");
        
        workItem2.setLastHistoryNo(0);
        workItem2.setWorkItemData(templateData2);
        List<FolderItem> folderItems2 = new ArrayList<>();
        FolderItem folderItem2 = new FolderItem();
        folderItem2.setAction(1);
        folderItem2.setFolderItemNo("1");
        folderItem2.setWorkitemId("1");
        folderItem2.setTemplateId("1");
        folderItem2.setFolderItemData(templateData2);
        folderItems2.add(folderItem2);
        workItem2.setFolderItems(folderItems2);
        httpSession.setAttribute("ORIGIN003&" + workItem2.getWorkitemId(), workItem2);
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
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processMap.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            Assert.assertEquals(workItemService.closeAndSaveWorkItem(httpSession, workItem.getWorkitemId(), workspaceId), true);
        });
    }

    @Test
    public void closeAndSaveProcess3() throws Exception {
        WorkItem workItem2 = new WorkItem();
        workItem2.setWorkitemId("1");
        TemplateData templateData2 = new TemplateData();
        templateData2.setDataMap(new HashMap<>());
        workItem2.setTemplateId("1");
        workItem2.setTaskId("1");
        
        workItem2.setLastHistoryNo(0);
        workItem2.setWorkItemData(templateData2);
        List<FolderItem> folderItems2 = new ArrayList<>();
        FolderItem folderItem2 = new FolderItem();
        folderItem2.setAction(1);
        folderItem2.setFolderItemNo("1");
        folderItem2.setWorkitemId("1");
        folderItem2.setTemplateId("1");
        folderItem2.setFolderItemData(templateData2);
        List<Document> documents2 = new ArrayList<>();
        Document document2 = new Document();
        document2.setAction(1);
        document2.setDocumentBinary(new byte[10]);
        document2.setDocumentData(templateData2);
        document2.setDocumentNo("1");
        document2.setDocumentPath("1");
        document2.setDocumentType("1");
        document2.setFolderItemNo("1");
        document2.setPageCount(1);
        document2.setTemplateId("1");
        document2.setViewInformation("1");
        document2.setWorkitemId("1");
        documents2.add(document2);
        folderItem2.setDocuments(documents2);
        folderItems2.add(folderItem2);
        workItem2.setFolderItems(folderItems2);
        httpSession.setAttribute("ORIGIN003&" + workItem2.getWorkitemId(), workItem2);
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
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processMap.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            Assert.assertEquals(workItemService.closeAndSaveWorkItem(httpSession, workItem.getWorkitemId(), workspaceId), true);
        });
    }

    @Test
    public void closeAndSaveProcess1() throws Exception {
        WorkItem workItem = new WorkItem();
        workItem.setWorkitemId("11");
        httpSession.setAttribute("ORIGIN003&" + workItem.getWorkitemId(), workItem);
        workItem.setTemplateId("1");
        workItem.setTaskId("1");
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processMap.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(workItemService.closeAndSaveWorkItem(httpSession, workItem.getWorkitemId(), workspaceId), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void deleteList() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        List<Integer> processIds = new ArrayList<>();
        processIds.add(1);
        DeleteProcessForm form = new DeleteProcessForm();
        form.setProcessIds(processIds);
        form.setConfirmDelete(true);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.deleteList(form), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void deleteList1() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        List<Integer> processIds = new ArrayList<>();
        processIds.add(2);
        DeleteProcessForm form = new DeleteProcessForm();
        form.setProcessIds(processIds);
        form.setConfirmDelete(true);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.deleteList(form), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void deleteList2() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        List<Integer> processIds = new ArrayList<>();
        processIds.add(3);
        DeleteProcessForm form = new DeleteProcessForm();
        form.setProcessIds(processIds);
        form.setConfirmDelete(true);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.deleteList(form), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void deleteList3() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        List<Integer> processIds = new ArrayList<>();
        processIds.add(4);
        DeleteProcessForm form = new DeleteProcessForm();
        form.setProcessIds(processIds);
        form.setConfirmDelete(true);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.deleteList(form), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void insertOne() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setDocumentDataSavePath("file");
        process.setProcessDefinition("<abc></abc>");
        StrageFile strageFile = new StrageFile();
        strageFile.setSiteId(11);
        form.setStrageFile(strageFile);
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.insertOne(form, httpSession.getId()), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void insertOne1() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setDocumentDataSavePath("database");
        process.setProcessDefinition("<abc></abc>");
        StrageDb strageDb = new StrageDb();
        strageDb.setSchemaName("abc");
        form.setStrageDb(strageDb);
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.insertOne(form, httpSession.getId()), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void insertOne2() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setDocumentDataSavePath("file");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.insertOne(form, httpSession.getId()), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void insertOne3() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setDocumentDataSavePath("database");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.insertOne(form, httpSession.getId()), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(1);
        process.setDocumentDataSavePath("file");
        process.setProcessDefinition("<abc></abc>");
        StrageFile strageFile = new StrageFile();
        strageFile.setSiteId(11);
        form.setStrageFile(strageFile);
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne1() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(1);
        process.setDocumentDataSavePath("database");
        process.setProcessDefinition("<abc></abc>");
        StrageDb strageDb = new StrageDb();
        strageDb.setSchemaName("abc");
        form.setStrageDb(strageDb);
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne2() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(2);
        process.setDocumentDataSavePath("database");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne3() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(3);
        process.setDocumentDataSavePath("database");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne4() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(5);
        process.setDocumentDataSavePath("file");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void updateOne5() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        ProcessForm form = new ProcessForm();
        MgrProcess process = new MgrProcess();
        process.setProcessId(1);
        process.setDocumentDataSavePath("file");
        process.setProcessDefinition("<abc></abc>");
        form.setProcess(process);
        form.setWorkspaceId(workspaceId);
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(service.updateOne(form), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void getDetail() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        String processId = "3";
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertTrue(service.getDetail(workspaceId, processId).get("strageFile") != null);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void getDetail1() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\processList.xml");
        String processId = "2";
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertTrue(service.getDetail(workspaceId, processId).get("strageDb") != null);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    private String createString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += "a";
        }
        return result;
    }
//
//    @Test
//    public void validateProcess() {
//        ProcessForm form = new ProcessForm();
//        form.setProcess(new MgrProcess());
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess1() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setProcessName(createString(256));
//        process.setDocumentDataSavePath(createString(1));
//        process.setDescription(createString(1));
//        form.setProcess(process);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }

//    @Test
//    public void validateProcess2() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDescription(createString(256));
//        process.setDocumentDataSavePath(createString(1));
//        process.setProcessName(createString(1));
//        form.setProcess(process);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess3() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath(createString(261));
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        form.setProcess(process);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess4() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath(createString(1));
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("");
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess5() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath(createString(1));
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc");
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess6() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("file");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        StrageFile file = new StrageFile();
//        file.setSiteId(null);
//        form.setStrageFile(file);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess7() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(1));
//        db.setSchemaName(null);
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess8() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(256));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess9() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(256));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess10() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(256));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess11() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(256));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess12() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(256));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess13() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(256));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), false);
//    }
//
//    @Test
//    public void validateProcess14() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("database");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageDb db = new StrageDb();
//        db.setDbPassword(createString(1));
//        db.setDbServer(createString(1));
//        db.setDbUser(createString(1));
//        db.setOwner(createString(1));
//        db.setSchemaName(createString(1));
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        form.setStrageDb(db);
//        Assert.assertEquals(service.validateProcess(form), true);
//    }
//
//    @Test
//    public void validateProcess15() {
//        ProcessForm form = new ProcessForm();
//        MgrProcess process = new MgrProcess();
//        process.setDocumentDataSavePath("file");
//        process.setDescription(createString(1));
//        process.setProcessName(createString(1));
//        process.setProcessDefinition("<abc></abc>");
//        StrageFile file = new StrageFile();
//        file.setSiteId(1);
//        file.setSiteManagementType("fileRoundRobin");
//        form.setStrageFile(file);
//        form.setProcess(process);
//        form.setFileExtention("xml");
//        Assert.assertEquals(service.validateProcess(form), true);
//    }

}
