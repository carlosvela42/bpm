//package co.jp.nej.earth.processservice;
//
//import co.jp.nej.earth.config.JdbcConfig;
//import co.jp.nej.earth.config.MessageConfig;
//import co.jp.nej.earth.exception.EarthException;
//import co.jp.nej.earth.model.DatProcess;
//import co.jp.nej.earth.model.Document;
//import co.jp.nej.earth.model.FolderItem;
//import co.jp.nej.earth.model.TemplateData;
//import co.jp.nej.earth.model.WorkItem;
//import co.jp.nej.earth.model.constant.Constant;
//import co.jp.nej.earth.model.constant.Constant.AgentBatch;
//import co.jp.nej.earth.model.entity.CtlEvent;
//import co.jp.nej.earth.service.EventControlService;
//import co.jp.nej.earth.util.ApplicationContextUtil;
//import co.jp.nej.earth.util.DateUtil;
////import co.jp.nej.earth.util.DateUtil;
//import co.jp.nej.earth.util.EMessageResource;
//import co.jp.nej.earth.util.FileUtil;
//import co.jp.nej.earth.util.ZipUtil;
//
//import org.apache.commons.io.FilenameUtils;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
////import org.quartz.SchedulerException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.ComponentScan.Filter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sun.media.jai.codec.FileSeekableStream;
//import com.sun.media.jai.codec.ImageCodec;
//import com.sun.media.jai.codec.ImageDecoder;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
////import javax.xml.bind.Marshaller;
////import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.bind.annotation.XmlAttribute;
//import javax.xml.bind.annotation.XmlElement;
//import javax.xml.bind.annotation.XmlElementWrapper;
////import javax.xml.bind.annotation.XmlMixed;
//import javax.xml.bind.annotation.XmlRootElement;
//
////import javax.xml.bind.annotation.XmlElementRefs;
////import javax.xml.bind.annotation.XmlElementRef;
//import javax.xml.bind.annotation.XmlType;
//import javax.xml.bind.annotation.XmlValue;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.StringWriter;
//import java.nio.file.DirectoryStream;
//import java.nio.file.FileSystems;
//import java.nio.file.FileVisitOption;
//import java.nio.file.FileVisitResult;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.PathMatcher;
//import java.nio.file.Paths;
//import java.nio.file.SimpleFileVisitor;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
///** @author cuongvq */
//
//@Configuration
//@Import({ JdbcConfig.class, MessageConfig.class })
//@ComponentScan(basePackages = { "co.jp.nej.earth" }, excludeFilters = {
//        @Filter(type = FilterType.ANNOTATION, value = Configuration.class),
//        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "co.jp.nej.earth.web.controller.*") })
//
//public class ThemeScanProcessService implements Job {
//
//    /* Logging */
//    private static final Logger LOG = LoggerFactory.getLogger(ThemeScanProcessService.class);
//
//    /* Directory scan */
//    private static final String SEPARATOR = File.separator;
//
//    private static final int DEPTH_SCAN = 1;
//
//    private static final String SEARCH_ZIP = "glob:**.zip";
//    private static final String SEARCH_XML = "glob:**.xml";
//
//    private static final String ZIP = ".zip";
//    private static final String UNDERSCORE = "_";
//
//    private static final String SYSTYPE_IMG = "1";
//    private static final String SYSTYPE_TIF = "2";
//    private static final String SYSTYPE_PDF = "3";
//
//    private static final String XMLTYPE_IMG = "2";
//    private static final String XMLTYPE_TIF = "1";
//    private static final String XMLTYPE_PDF = "3";
//
//    /* XML Parser */
//    // private static final String ROOT_ELEMENT = "batch";
//    // private static final String NAMESPACE = "http://tempuri.org/Batch.xsd";
//
//    /* Control Event */
//    private static final String DEFAULT_EVENT_STATUS = "EDIT";
//    private static final String DEFAULT_EVENT_WIID = "-1";
//
//    @Autowired
//    private EventControlService service;
//
//    @Autowired
//    private EMessageResource messageSource;
//
//    private String workspaceId = "";
//    private String userId = "";
//    private String importPath = "";
//    private String tempPath = "";
//
//    private StringBuilder importPathBuilder;
//    private StringBuilder tempPathBuilder;
//
//    public ThemeScanProcessService() {
//    }
//
//    /** execute */
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        LOG.info("START at " + DateUtil.getCurrentDateString());
//        try {
//
//            workspaceId = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_WORKSPACE_ID);
//            userId = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_USER_ID);
//            importPath = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_IMPORT_PATH);
//            tempPath = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_TEMP_PATH);
//            importPathBuilder = new StringBuilder(importPath);
//            tempPathBuilder = new StringBuilder(tempPath);
//            // test();
//            process();
//
//        } catch (EarthException | IOException e) {
//            LOG.error(e.getMessage(), e);
//        }
//        // LOG.info("END at " + DateUtil.getCurrentDateString());
//    }
//
//    /** The process walk through the file tree of the import folder
//     *
//     * @throws EarthException
//     * @throws IOException */
//    private void process() throws EarthException, IOException {
//
//        ApplicationContext context = ApplicationContextUtil.getApplicationContext();
//        service = context.getBean(EventControlService.class);
//        messageSource = context.getBean(EMessageResource.class);
//
//        Path pathToImport = Paths.get(importPathBuilder.toString());
//
//        Files.walkFileTree(pathToImport, Collections.<FileVisitOption>emptySet(), DEPTH_SCAN,
//                new SimpleFileVisitor<Path>() {
//                    @Override
//                    public FileVisitResult postVisitDirectory(Path batchPath, IOException e) {
//                        if (e == null) {
//                            try {
//                                importThemeScan(batchPath);
//                            } catch (Exception innerException) {
//                                e.printStackTrace();
//                                LOG.error(innerException.getMessage(), innerException);
//                            }
//                        } else {
//                            e.printStackTrace();
//                            LOG.error("Error walk through files.", e);
//                        }
//                        return FileVisitResult.CONTINUE;
//                    }
//                });
//    }
//
//    /** import theme scan
//     *
//     * @param path
//     * @return
//     * @throws EarthException */
//    private boolean importThemeScan(Path path) throws EarthException, IOException {
//
//        boolean resultImport = false;
//
//        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(SEARCH_ZIP);
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
//            for (Path entry : stream) {
//                if (Files.isRegularFile(entry)) {
//
//                    if (matcher.matches(entry)) {
//
//                        String source = entry.toAbsolutePath().toString();
//                        String newSource = entry.toAbsolutePath().toString() + UNDERSCORE;
//                        String name = FilenameUtils.getBaseName(entry.getFileName().toString());
//                        String newName = name + ZIP + UNDERSCORE;
//
//                        try {
//                            if (FileUtil.rename(source, newName)) {
//                                String destination = new StringBuilder(tempPathBuilder).append(SEPARATOR).append(name)
//                                        .toString();
//                                LOG.info("Processing file: " + newSource);
//                                LOG.info("Extract to: " + destination);
//
//                                ZipUtil.unZip(newSource, destination);
//                                DataResult data = processData(destination);
//                                if (data.isOk) {
//                                    resultImport = service.insertEvents(workspaceId, data.events);
//                                    if (resultImport) {
//                                        // remover the source file and the all
//                                        // xml files
//                                        data.files.add(newSource);
//                                        FileUtil.deleteFiles(data.files);
//                                    }
//                                } else {
//                                    FileUtil.rename(newSource, name + ZIP);
//                                }
//                            }
//                        } catch (Exception e) {
//                            FileUtil.rename(newSource, name + ZIP);
//                            LOG.error(e.getMessage(), e);
//                        }
//                    }
//                }
//            }
//        }
//
//        return resultImport;
//    }
//
//    /** import theme scan
//     *
//     * @param dataPath
//     * @return
//     * @throws EarthException */
//
//    private DataResult processData(String dataPath) throws EarthException, IOException {
//
//        DataResult dataResult = new DataResult();
//
//        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(SEARCH_XML);
//
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dataPath))) {
//            List<String> fileList = new LinkedList<>();
//            for (Path entry : stream) {
//                if (Files.isRegularFile(entry)) {
//
//                    if (matcher.matches(entry)) {
//                        try {
//                            LOG.info("Processing file: " + entry.toAbsolutePath());
//                            FoldersXML folders = parseXml(entry.toFile());
//
//                            folders.setPathBach(new StringBuilder(dataPath).append(SEPARATOR).toString());
//                            // List<String> fileList = makeFileList(dataPath,
//                            // folders);
//                            fileList.add(entry.toAbsolutePath().toString());
//                            List<CtlEvent> events = new LinkedList<>();
//                            if (folders.folderList != null) {
//                                for (FolderXML f : folders.folderList) {
//                                    CtlEvent event = new CtlEvent();
//                                    event.setUserId(userId);
//                                    event.setWorkitemId(DEFAULT_EVENT_WIID);
//                                    event.setStatus(AgentBatch.STATUS_EDIT);
//                                    event.setTaskId(folders.getTaskId());
//                                    event.setWorkitemData(f.makeWorkItemJson(workspaceId, folders.getTaskId(),
//                                            folders.getPathBach()));
//                                    events.add(event);
//                                }
//                            }
//                            dataResult.isOk = true;
//                            dataResult.events = events;
//                            dataResult.files = fileList;
//                        } catch (JAXBException e) {
//                            LOG.error(e.getMessage());
//                            throw new EarthException(e);
//                        }
//                    }
//                }
//            }
//        }
//
//        return dataResult;
//    }
//
//    /** parse Xml file to folderXML class
//     *
//     * @param fileChild
//     * @return
//     * @throws JAXBException */
//    private FoldersXML parseXml(File fileChild) throws JAXBException {
//        JAXBContext jaxbContext = JAXBContext.newInstance(FoldersXML.class);
//        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//        FoldersXML folders = (FoldersXML) jaxbUnmarshaller.unmarshal(fileChild);
//        return folders;
//    }
//
//    public static void main(String[] args) throws EarthException, ClassNotFoundException {
//        ApplicationContext context = new AnnotationConfigApplicationContext(ThemeScanProcessService.class);
//
//        ApplicationContextUtil appUtil = new ApplicationContextUtil();
//        appUtil.setApplicationContext(context);
//        // System.out.println("Working Directory = " +
//        // System.getProperty("user.dir"));
//        // System.out.println("Working Directory = " +
//        // Paths.get(".").toAbsolutePath().normalize().toString());
//        // inputPath = new
//        // StringBuilder(Paths.get(".").toAbsolutePath().normalize().toString());
//        ThemeScanProcessService process = new ThemeScanProcessService();
//        String wiId = null;
//        if (args.length > 0) {
//            wiId = args[0];
//        } else {
//            wiId = Constant.EARTH_WORKSPACE_ID;
//        }
//        appUtil.setWorkspaceId(wiId);
//        // process.test();
//
//        try {
//
//            process.workspaceId = ApplicationContextUtil.getWorkspaceId();
//            process.userId = "admin";
//            process.process();
//
//        } catch (EarthException | IOException e) {
//            LOG.error(e.getMessage());
//        }
//    }
//
//    private class DataResult {
//        private boolean isOk;
//        private List<CtlEvent> events;
//        private List<String> files;
//
//        public boolean isOk() {
//            return isOk;
//        }
//
//        public void setOk(boolean isOk) {
//            this.isOk = isOk;
//        }
//
//        public List<CtlEvent> getEvents() {
//            return events;
//        }
//
//        public void setEvents(List<CtlEvent> events) {
//            this.events = events;
//        }
//
//        public List<String> getFiles() {
//            return files;
//        }
//
//        public void setFiles(List<String> files) {
//            this.files = files;
//        }
//    }
//
//    /* private classes for parser XML */
//    // @XmlRootElement(name = "Batch", namespace="http://tempuri.org/Batch.xsd")
//    @XmlRootElement(name = "Batch")
//    private static class FoldersXML {
//
//        private String taskId = "";
//        private String pathBach = "";
//
//        private ArrayList<FolderXML> folderList;
//
//        public void setPathBach(String pathBach) {
//            this.pathBach = pathBach;
//        }
//
//        public String getPathBach() {
//            return pathBach;
//        }
//
//        @XmlElement(name = "Definition")
//        public void setTaskId(String taskId) {
//            this.taskId = taskId;
//        }
//
//        public String getTaskId() {
//            return taskId;
//        }
//
//        @XmlElementWrapper(name = "Folders")
//        @XmlElement(name = "Folder")
//        public void setFolderList(ArrayList<FolderXML> folderList) {
//            this.folderList = folderList;
//        }
//
//        public ArrayList<FolderXML> getFolderList() {
//            return folderList;
//        }
//
//    }
//
//    @XmlType(propOrder = { "templateId", "attributeList", "documentList" })
//    @XmlRootElement(name = "Folder")
//    public static class FolderXML {
//
//        private String taskId = "";
//
//        private String templateId = "";
//
//        private ArrayList<AttributeXML> attributeList;
//
//        private ArrayList<ImageDocumentXML> documentList;
//
//        @XmlElement(name = "TemplateId")
//        public void setTemplateId(String templateId) {
//            this.templateId = templateId;
//        }
//
//        public String getTemplateId() {
//            return templateId;
//        }
//
//        @XmlElementWrapper(name = "Attributes")
//        @XmlElement(name = "Attribute")
//        public void setAttributeList(ArrayList<AttributeXML> attributeList) {
//            this.attributeList = attributeList;
//        }
//
//        public ArrayList<AttributeXML> getAttributeList() {
//            return attributeList;
//        }
//
//        @XmlElement(name = "ImageDocument")
//        public void setDocumentList(ArrayList<ImageDocumentXML> documentList) {
//            this.documentList = documentList;
//        }
//
//        public ArrayList<ImageDocumentXML> getDocumentList() {
//            return documentList;
//        }
//
//        public String makeWorkItemJson(String workspaceId, String taskId, String fullPath) {
//
//            WorkItem workItem = new WorkItem();
//
//            workItem.setWorkspaceId(workspaceId);
//            // workItem.setWorkitemId(DEFAULT_ADD_ID);
//            workItem.setTaskId(taskId);
//            workItem.setTemplateId(this.getTemplateId());
//
//            TemplateData workItemData = new TemplateData();
//            workItemData.setDataMap(this.getMapOfAttributes());
//            workItem.setWorkItemData(workItemData);
//
//            DatProcess dataProcess = new DatProcess();
//            // dataProcess.setProcessId(this.getProcessId());
//            workItem.setDataProcess(dataProcess);
//
//            List<FolderItem> folderItemList = new LinkedList<>();
//            if (this.documentList != null) {
//                for (ImageDocumentXML documentXML : this.documentList) {
//                    FolderItem folderItem = new FolderItem();
//
//                    folderItem.setTemplateId(documentXML.getTemplateId());
//                    // folderItem.setFolderItemNo(DEFAULT_ADD_ID);
//
//                    TemplateData folderItemData = new TemplateData();
//                    folderItemData.setDataMap(documentXML.getMapOfAttributes());
//                    folderItem.setFolderItemData(folderItemData);
//
//                    List<Document> documents = new LinkedList<>();
//                    if (documentXML.pageList != null) {
//                        for (ImagePageXML page : documentXML.pageList) {
//                            String filePath = new StringBuilder(fullPath).append(page.getFilePath()).toString();
//                            int totalPages = page.getTotalPage(filePath);
//
//                            for (int i = 0; i < totalPages; i++) {
//                                Document document = new Document();
//                                // TemplateData documentItemData = new
//                                // TemplateData();
//                                // documentItemData.setDataMap(page.getMapOfAttributes());
//                                // document.setDocumentData(documentItemData);
//                                document.setDocumentType(page.getSystemDocumentType());
//                                document.setDocumentPath(filePath);
//                                document.setPageCount(i + 1);
//                                documents.add(document);
//                            }
//                        }
//                    }
//
//                    folderItem.setDocuments(documents);
//                    folderItemList.add(folderItem);
//                }
//            }
//            workItem.setFolderItems(folderItemList);
//
//            String workItemDataJson = "";
//            try {
//                workItemDataJson = new ObjectMapper().writeValueAsString(workItem);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            LOG.debug("Json data: " + workItemDataJson);
//
//            return workItemDataJson;
//        }
//
//        private Map<String, Object> getMapOfAttributes() {
//            Map<String, Object> map = new HashMap<String, Object>();
//            if (this.attributeList != null) {
//                for (AttributeXML attribute : this.attributeList) {
//                    map.put(attribute.getName(), attribute.getValue());
//                }
//            }
//            return map;
//        }
//    }
//
//    @XmlType(propOrder = { "templateId", "attributeList", "pageList" })
//    // @XmlRootElement(name = "ImageDocument")
//    public static class ImageDocumentXML {
//
//        private String templateId = "";
//
//        private ArrayList<AttributeXML> attributeList;
//
//        private ArrayList<ImagePageXML> pageList;
//
//        @XmlElement(name = "TemplateId")
//        public void setTemplateId(String templateId) {
//            this.templateId = templateId;
//        }
//
//        public String getTemplateId() {
//            return templateId;
//        }
//
//        @XmlElementWrapper(name = "Attributes")
//        @XmlElement(name = "Attribute")
//        public void setAttributeList(ArrayList<AttributeXML> attributeList) {
//            this.attributeList = attributeList;
//        }
//
//        public ArrayList<AttributeXML> getAttributeList() {
//            return attributeList;
//        }
//
//        public void setPageList(ArrayList<ImagePageXML> pageList) {
//            this.pageList = pageList;
//        }
//
//        @XmlElement(name = "ImagePage")
//        // @XmlMixed
//        public ArrayList<ImagePageXML> getPageList() {
//            return pageList;
//        }
//
//        private Map<String, Object> getMapOfAttributes() {
//            Map<String, Object> map = new HashMap<String, Object>();
//            if (this.attributeList != null) {
//                for (AttributeXML attribute : this.attributeList) {
//                    map.put(attribute.getName(), attribute.getValue());
//                }
//            }
//            return map;
//        }
//    }
//
//    // @XmlRootElement(name = "ImagePage")
//    private static class ImagePageXML {
//
//        private String documentType = "";
//
//        private String filePath = "";
//        private ArrayList<AttributeXML> attributeList;
//
//        @XmlAttribute(name = "imagetype")
//        public String getDocumentType() {
//            return documentType;
//        }
//
//        public void setDocumentType(String documentType) {
//            this.documentType = documentType;
//        }
//
//        public String getSystemDocumentType() {
//            String systemDataType = SYSTYPE_IMG;
//            if (XMLTYPE_TIF.equals(this.documentType)) {
//                systemDataType = SYSTYPE_TIF;
//            } else if (XMLTYPE_PDF.equals(this.documentType)) {
//                systemDataType = SYSTYPE_PDF;
//            }
//            return systemDataType;
//        }
//
//        @XmlValue
//        // @XmlAttribute(name = "filename")
//        public String getFilePath() {
//            return filePath;
//        }
//
//        public void setFilePath(String filePath) {
//            this.filePath = filePath;
//        }
//
//        public int getTotalPage(String filePath) {
//
//            int count = 1;
//            try {
//                if (XMLTYPE_TIF.equals(this.documentType)) {
//                    FileSeekableStream ss;
//
//                    ss = new FileSeekableStream(filePath);
//                    ImageDecoder dec = ImageCodec.createImageDecoder("tiff", ss, null);
//                    count = dec.getNumPages();
//                    ss.close();
//                } else if (XMLTYPE_PDF.equals(this.documentType)) {
//                    PDDocument doc;
//                    doc = PDDocument.load(new File(filePath));
//                    count = doc.getNumberOfPages();
//                    doc.close();
//
//                }
//            } catch (IOException e) {
//                LOG.error(e.getMessage(), e);
//            }
//
//            return count;
//        }
//        /*
//        @XmlElementWrapper(name = "Attributes")
//        @XmlElement(name = "Attribute")
//        public void setAttributeList(ArrayList<AttributeXML> attributeList) {
//            this.attributeList = attributeList;
//        }
//
//        public ArrayList<AttributeXML> getAttributeList() {
//            return attributeList;
//        }
//
//        private Map<String, Object> getMapOfAttributes() {
//            Map<String, Object> map = new HashMap<String, Object>();
//            if (this.attributeList != null) {
//                for (AttributeXML attribute : this.attributeList) {
//                    map.put(attribute.getName(), attribute.getValue());
//                }
//            }
//            return map;
//        }*/
//    }
//
//    // @XmlRootElement(name = "Attribute")
//    private static class AttributeXML {
//
//        private String name = "";
//        private String value = "";
//
//        @XmlAttribute(name = "name")
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        @XmlValue
//        public String getValue() {
//            return value;
//        }
//
//        public void setValue(String value) {
//            this.value = value;
//        }
//
//    }
//
//    private void test() {
//        final int limit = 5;
//        JAXBContext jaxbContext;
//        try {
//            jaxbContext = JAXBContext.newInstance(FoldersXML.class);
//
//            Marshaller marshallerObj = jaxbContext.createMarshaller();
//            // marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
//            // true);
//
//            FoldersXML f = new FoldersXML();
//            f.folderList = new ArrayList<>();
//
//            FolderXML folder1 = new FolderXML();
//            folder1.taskId = "InitialScan";
//            folder1.attributeList = new ArrayList<AttributeXML>();
//
//            folder1.templateId = "Temp001";
//
//            for (int i = 1; i < limit; i++) {
//                AttributeXML attr1 = new AttributeXML();
//                attr1.name = "attr" + i;
//                attr1.value = "value of attr" + i;
//                folder1.attributeList.add(attr1);
//            }
//
//            ImageDocumentXML doc1 = new ImageDocumentXML();
//            doc1.attributeList = new ArrayList<AttributeXML>();
//            doc1.templateId = "TempDoc001";
//
//            for (int i = 1; i < limit; i++) {
//                AttributeXML attr = new AttributeXML();
//                attr.name = "doc_attr" + i;
//                attr.value = "doc_value of attr" + i;
//                doc1.attributeList.add(attr);
//            }
//
//            ImagePageXML page1 = new ImagePageXML();
//            page1.filePath = "6111.IMG";
//
//            doc1.pageList = new ArrayList<>();
//            doc1.pageList.add(page1);
//
//            ImagePageXML page2 = new ImagePageXML();
//            page2.filePath = "6112.IMG";
//            doc1.pageList.add(page2);
//
//            ImageDocumentXML doc2 = new ImageDocumentXML();
//            doc2.attributeList = new ArrayList<AttributeXML>();
//            doc2.pageList = new ArrayList<>();
//            doc2.pageList.add(page1);
//
//            folder1.documentList = new ArrayList<>();
//            folder1.documentList.add(doc1);
//            folder1.documentList.add(doc2);
//
//            f.folderList.add(folder1);
//
//            StringWriter sw = new StringWriter();
//            marshallerObj.marshal(f, sw);
//            System.out.println(sw.toString());
//        } catch (JAXBException e2) {
//            e2.printStackTrace();
//        }
//    }
//
//}
