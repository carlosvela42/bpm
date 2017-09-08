package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.DataDbDao;
import co.jp.nej.earth.dao.DataFileDao;
import co.jp.nej.earth.dao.DirectoryDao;
import co.jp.nej.earth.dao.ProcessDao;
import co.jp.nej.earth.dao.StrageFileDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.DocumentSavingInfo;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrProcess;
import co.jp.nej.earth.model.StrageFile;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.WorkItemDictionary;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.entity.StrDataDb;
import co.jp.nej.earth.model.entity.StrDataFile;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.enums.Action;
import co.jp.nej.earth.model.enums.DocumentDataSavePath;
import co.jp.nej.earth.model.enums.DocumentSavingType;
import co.jp.nej.earth.model.sql.QMgrProcess;
import co.jp.nej.earth.model.sql.QStrDataDb;
import co.jp.nej.earth.model.sql.QStrDataFile;
import co.jp.nej.earth.model.sql.QStrageFile;
import co.jp.nej.earth.model.ws.DisplayImageResponse;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EModelUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.FileUtil;
import co.jp.nej.earth.util.SessionUtil;
import co.jp.nej.earth.util.TemplateUtil;
import com.google.common.io.Files;
import com.querydsl.core.types.Path;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentServiceImpl extends BaseService implements DocumentService {
    @Autowired
    private DataFileDao dataFileDao;
    @Autowired
    private DataDbDao dataDbDao;
    @Autowired
    private DirectoryDao directoryDao;
    @Autowired
    private StrageFileDao strageFileDao;
    @Autowired
    private ProcessDao processDao;
    /**
     * log
     */
    private static final Logger LOG = LoggerFactory.getLogger(EventControlServiceImpl.class);

    private static final long CONVERT_MB_TO_BYTE_VALUE = 1024 * 1024;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveDocumentDataFile(String workspaceId, Document document,
            Integer processId, Map<String, String> documentPathMap) throws EarthException {
        try {
            // Get process information
            Map<Path<?>, Object> condition = new HashMap<>();
            QMgrProcess qMgrProcess = QMgrProcess.newInstance();
            condition.put(qMgrProcess.processId, processId);
            MgrProcess process = processDao.findOne(workspaceId, condition);

            if ((process == null) || (document == null)) {
                throw new EarthException("Invalid parameter process and document");
            }

            // Do not insert/update file
            if (EStringUtil.isEmpty(document.getDocumentPath())) {
                return true;
            }

            File documentFile = new File(document.getDocumentPath());
            if (!(new File(document.getDocumentPath())).exists()) {
                throw new EarthException("File is not exists");
            }

            String documentSavePath = process.getDocumentDataSavePath();
            if (EStringUtil.isEmpty(documentSavePath)) {
                throw new EarthException("Can not get save path");
            }

            // Save file
            boolean isSaveFileSuccess = false;
            if (documentSavePath.equalsIgnoreCase(String.valueOf(DocumentDataSavePath.DATABASE.getId()))) {

                // Save file to database
                isSaveFileSuccess = saveDocumentToDB(workspaceId, document);
            } else if (documentSavePath.equalsIgnoreCase(String.valueOf(DocumentDataSavePath.FILE.getId()))) {

                // Save file to directory
                DocumentSavingInfo documentSavingInfo = getDocumentSavingInfo(workspaceId, processId);
                List<Directory> directories = documentSavingInfo.getDataDef();

                if (DocumentSavingType.FILE_UNTIL_FULL.equals(documentSavingInfo.getSavingType())) {

                    // If directory's memory is full, move to other directory
                    // Loop directories
                    for (Directory directory : directories) {
                        if (directory.getFolderPath() == null) {
                            continue;
                        }

                        File directoryFile = new File(directory.getFolderPath());

                        // Check size of file input and all file in directory
                        // are small than reserved disk volume size or not.
                        // Get directory size (MB -> Byte)
                        long directorySize = Long.parseLong(directory.getDiskVolSize());
                        directorySize = directorySize * CONVERT_MB_TO_BYTE_VALUE;
                        if (directorySize > (
                                FileUtil.getFileSize(documentFile) + FileUtils.sizeOfDirectory(directoryFile))) {
                            isSaveFileSuccess = copyAndSaveFile(directory, workspaceId, document, documentPathMap);
                        }
                    }
                } else if (DocumentSavingType.FILE_ROUND_ROBIN.equals(documentSavingInfo.getSavingType())) {

                    // Move file to folder that it's size is the biggest
                    Directory directory = FileUtil.getDirectoryUsedStorageMin(directories);
                    if (directory.getFolderPath() != null) {
                        isSaveFileSuccess = copyAndSaveFile(directory, workspaceId, document, documentPathMap);
                    }
                }
            }

            if (!isSaveFileSuccess) {
                LOG.error(new Message(ErrorCode.E1012,
                    messageSource.get(ErrorCode.E1012, new String[]{})).getContent());
            }
            return isSaveFileSuccess;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    /**
     * Copy and save file to database
     *
     * @param directory
     * @param workspaceId
     * @param document
     * @return
     * @throws EarthException
     */
    private boolean copyAndSaveFile(Directory directory, String workspaceId, Document document,
            Map<String, String> documentPathMap) throws EarthException {
        String oldDocumentPath = document.getDocumentPath();
        File fileCopy = new File(oldDocumentPath);
        String newDocumentPath;

        // File was not copied
        if (documentPathMap.containsKey(oldDocumentPath)) {
            newDocumentPath = documentPathMap.get(oldDocumentPath);
        } else {
            String folderPath = new StringBuilder(directory.getFolderPath())
                .append(File.separator).append(document.getWorkitemId())
                .append(File.separator).append(document.getFolderItemNo())
                .toString();
            newDocumentPath = new StringBuilder(folderPath)
                .append(File.separator).append(fileCopy.getName()).toString();

            // If destination is not similar source
            if (!newDocumentPath.equals(fileCopy.getAbsolutePath())) {
                File documentFolder = new File(folderPath);
                if ((documentFolder.exists()) || (documentFolder.mkdirs())) {
                    copyFileToDirectory(fileCopy, new File(newDocumentPath));
                } else {
                    throw new EarthException("Can not make folder and copy document file");
                }
            }
            documentPathMap.put(oldDocumentPath, newDocumentPath);
        }

        // Save file path to database
        return saveDocumentToDataFile(workspaceId, document, newDocumentPath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getDocument(String workspaceId, Document document, DocumentSavingInfo documentSavingInfo)
            throws EarthException {
        return (byte[]) this.executeTransaction(workspaceId, () -> {
            try {
                if (DocumentSavingType.DATABASE.equals(documentSavingInfo.getSavingType())) {
                    QStrDataDb qStrDataDb = QStrDataDb.newInstance();
                    Map<Path<?>, Object> condition = new HashMap<>();
                    condition.put(qStrDataDb.documentNo, document.getDocumentNo());
                    condition.put(qStrDataDb.folderItemNo, document.getFolderItemNo());
                    condition.put(qStrDataDb.workitemId, document.getWorkitemId());
                    StrDataDb dataDb = dataDbDao.findOne(workspaceId, condition);
                    return dataDb.getDocumentData().getBytes();
                } else {
                    QStrDataFile qStrDataFile = QStrDataFile.newInstance();
                    Map<Path<?>, Object> condition = new HashMap<>();
                    condition.put(qStrDataFile.documentNo, document.getDocumentNo());
                    condition.put(qStrDataFile.folderItemNo, document.getFolderItemNo());
                    condition.put(qStrDataFile.workitemId, document.getWorkitemId());
                    StrDataFile dataFile = dataFileDao.findOne(workspaceId, condition);
                    if (!EStringUtil.isEmpty(dataFile.getDocumentDataPath())) {
                        return FileUtil.convertFileToBinary(new File(dataFile.getDocumentDataPath()));
                    }
                    return null;
                }
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
    }

    /**
     * copy file to directory
     *
     * @param file
     * @throws EarthException
     */
    private void copyFileToDirectory(File file, File fileOutPut) throws EarthException {
        try {
            Files.copy(file, fileOutPut);
        } catch (IOException e) {
            throw new EarthException(e);
        }
    }

    /**
     * save Document to table DataFile in database
     *
     * @param document
     * @return
     * @throws EarthException
     */
    private boolean saveDocumentToDataFile(String workspaceId, Document document, String newDocumentPath)
            throws EarthException {
        QStrDataFile qStrDataFile = QStrDataFile.newInstance();
        if (!dataFileDao.isExistDocumentFile(workspaceId, document)) {

            // instance object
            StrDataFile dataFile = new StrDataFile();
            dataFile.setWorkitemId(document.getWorkitemId());
            dataFile.setDocumentNo(document.getDocumentNo());
            dataFile.setFolderItemNo(document.getFolderItemNo());
            dataFile.setDocumentDataPath(newDocumentPath);

            // insert DataFile
            return dataFileDao.add(workspaceId, dataFile) > 0;
        } else {

            // instance condition map
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qStrDataFile.documentNo, document.getDocumentNo());
            condition.put(qStrDataFile.folderItemNo, document.getFolderItemNo());
            condition.put(qStrDataFile.workitemId, document.getWorkitemId());

            // instance update map
            Map<Path<?>, Object> updateMap = new HashMap<>();
            updateMap.put(qStrDataFile.documentDataPath, newDocumentPath);
            updateMap.put(qStrDataFile.lastUpdateTime, DateUtil.getCurrentDateString());
            return dataFileDao.update(workspaceId, condition, updateMap) > 0;
        }
    }

    /**
     * save document to database
     *
     * @param document
     * @throws EarthException
     * @throws IOException
     */
    private boolean saveDocumentToDB(String workspaceId, Document document)
        throws EarthException, IOException {
        byte[] dataFile = FileUtil.convertFileToBinary(new File(document.getDocumentPath()));
        String base64File = Base64.encodeBase64String(dataFile);
        if (!dataDbDao.isExistDocumentDb(workspaceId, document)) {
            StrDataDb dataDb = new StrDataDb();
            dataDb.setWorkitemId(document.getWorkitemId());
            dataDb.setDocumentNo(document.getDocumentNo());
            dataDb.setFolderItemNo(document.getFolderItemNo());
            dataDb.setDocumentData(base64File);

            // insert dataDb
            return dataDbDao.add(workspaceId, dataDb) > 0;
        } else {
            QStrDataDb qStrDataDb = QStrDataDb.newInstance();
            // instance condition map
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qStrDataDb.documentNo, document.getDocumentNo());
            condition.put(qStrDataDb.folderItemNo, document.getFolderItemNo());
            condition.put(qStrDataDb.workitemId, document.getWorkitemId());

            // instance update map
            Map<Path<?>, Object> updateMap = new HashMap<>();
            updateMap = new HashMap<>();
            updateMap.put(qStrDataDb.documentData, base64File);
            updateMap.put(qStrDataDb.lastUpdateTime, DateUtil.getCurrentDateString());

            // update DataDb
            return dataDbDao.update(workspaceId, condition, updateMap) > 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentSavingInfo getDocumentSavingInfo(String workspaceId, Integer processId)
        throws EarthException {
        try {
            DocumentSavingInfo documentSavingInfo = new DocumentSavingInfo();
            QMgrProcess qMgrProcess = QMgrProcess.newInstance();

            QStrageFile qStrageFile = QStrageFile.newInstance();
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrProcess.processId, processId);
            condition = new HashMap<>();
            condition.put(qStrageFile.processId, processId);
            StrageFile strageFile = strageFileDao.findOne(workspaceId, condition);
            if (strageFile == null) {
                return documentSavingInfo;
            }
            if (DocumentSavingType.FILE_UNTIL_FULL.getValue()
                .equalsIgnoreCase(strageFile.getSiteManagementType())) {
                documentSavingInfo.setSavingType(DocumentSavingType.FILE_UNTIL_FULL);
            } else {
                documentSavingInfo.setSavingType(DocumentSavingType.FILE_ROUND_ROBIN);
            }

            documentSavingInfo.setDataDef(directoryDao.getDirectoriesBySite(strageFile.getSiteId()));
            return documentSavingInfo;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    /**
     * Get Document object from session
     *
     * @param session
     * @param workspaceId
     * @param workItemId
     * @param folderItemNo
     * @param documentNo
     * @return
     * @throws EarthException
     */
    @Override
    public Document getDocumentSession(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                                       String documentNo) throws EarthException {
        // Get Document data from session.
        Document documentSession = (Document) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getDocumentIndex(workItemId, String.valueOf(folderItemNo), String.valueOf(documentNo)));
        TemplateUtil.checkPermission(documentSession, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));
        Document document = EModelUtil.clone(documentSession, Document.class);
        document.setLayers(new ArrayList<Layer>());
        return document;
    }

    @Override
    public boolean updateDocumentSession(HttpSession session, String workspaceId, String workItemId,
                                         String folderItemNo, Document document) throws EarthException {
        Document documentSession = (Document) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId), EModelUtil.getDocumentIndex(
                workItemId, folderItemNo, document.getDocumentNo()));
        TemplateUtil.checkPermission(documentSession, AccessRight.RW, messageSource.get(Constant.ErrorCode.E0025));
        documentSession.setTemplateId(document.getTemplateId());
        documentSession.setDocumentData(document.getDocumentData());
        documentSession.setMgrTemplate(document.getMgrTemplate());
        documentSession.setAction(document.getAction());
        return true;
    }

    private FolderItem getFolderItemDataStructureSession(HttpSession session, String workspaceId, String workItemId,
                                                         String folderItemNo) throws EarthException {
        // Get FolderItem data from session
        FolderItem folderItemSession = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, folderItemNo));
        TemplateUtil.checkPermission(folderItemSession, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));
        return EModelUtil.clone(folderItemSession, FolderItem.class);
    }

    @Override
    public DisplayImageResponse getImagesByFolderItem(HttpSession session, String workspaceId, String workItemId
                            , String folderItemNo, String currentDocumentNo) throws EarthException {
        WorkItemDictionary workItemTempDictionary = (WorkItemDictionary) session
            .getAttribute(SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId));
        if (workItemTempDictionary == null) {
            FolderItem folderItem = getFolderItemDataStructureSession(session, workspaceId, workItemId, folderItemNo);
            TemplateUtil.checkPermission(folderItem, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));

            WorkItem originWorkItemSession = (WorkItem) getDataItemFromSession(session,
                SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
                EModelUtil.getWorkItemIndex(workItemId));

            WorkItem workItem = EModelUtil.clone(originWorkItemSession, WorkItem.class);
            workItem.setFolderItems(new ArrayList<>());
            workItem.addFolderItem(folderItem);

            // Create new temporary WorkItem Dictionary for Displaying ImageViewer Screen.
            WorkItemDictionary workItemDictionary = createWorkItemDictionaries(workItem);
            session.setAttribute(SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId), workItemDictionary);
        } else {
            WorkItem tmpWorkItemSession = (WorkItem) getDataItemFromSession(session,
                SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId),
                EModelUtil.getWorkItemIndex(workItemId));
            if (workItemTempDictionary.get(EModelUtil.getFolderItemIndex(workItemId, folderItemNo)) == null) {
                FolderItem originFolderItem = (FolderItem) getDataItemFromSession(session,
                    SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
                    EModelUtil.getFolderItemIndex(workItemId, folderItemNo));
                tmpWorkItemSession.addFolderItem(EModelUtil.clone(originFolderItem, FolderItem.class));
                workItemTempDictionary.put(EModelUtil.getFolderItemIndex(tmpWorkItemSession.getWorkitemId()
                    , originFolderItem.getFolderItemNo()), originFolderItem);
            }
        }


        FolderItem tempFolderItem = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, folderItemNo));

        List<Document> documents = tempFolderItem.getDocuments();
        Map<String, Document> documentMap = new LinkedHashMap<>();
        int currentDocumentIndex = 0;
        for (Document document : documents) {
            // Only load documents have access right >= RO.
            if (document.getAccessRight().goe(AccessRight.RO)) {
                documentMap.put(document.getDocumentNo(), document);
                if (currentDocumentNo.equals(document.getDocumentNo())) {
                    currentDocumentIndex = documents.indexOf(document);
                }
            }
        }

        return new DisplayImageResponse(currentDocumentIndex, documentMap);
    }

    @Override
    public byte[] getBinaryDataOfDocument(String workspaceId, Document document) throws EarthException {
        TemplateUtil.checkPermission(document, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));
        return (byte[]) this.executeTransaction(workspaceId, () -> {
            try {
                if (!EStringUtil.isEmpty(document.getDocumentPath())) {
                    File file = new File(document.getDocumentPath());
                    if (file.exists()) {
                        return FileUtil.convertFileToBinary(file);
                    }
                }

                QStrDataDb qStrDataDb = QStrDataDb.newInstance();
                Map<Path<?>, Object> keyMap = new HashMap<>();
                keyMap.put(qStrDataDb.workitemId, document.getWorkitemId());
                keyMap.put(qStrDataDb.folderItemNo, document.getFolderItemNo());
                keyMap.put(qStrDataDb.documentNo, document.getDocumentNo());
                StrDataDb dataDb = dataDbDao.findOne(workspaceId, keyMap);
                if (dataDb != null) {
                    if (!EStringUtil.isEmpty(dataDb.getDocumentData())) {
                        return Base64.decodeBase64(dataDb.getDocumentData());
                    }
                }
            } catch (Exception e) {
                throw new EarthException(e);
            }
            return null;
        });
    }

    @Override
    public boolean saveImageSession(HttpSession session, String workspaceId, Document document)
        throws EarthException {
        // Get work item data from session.
        Document documentSession = (Document) getDataItemFromSession(
            session
            , SessionUtil.getTempWorkItemDictionaryKey(workspaceId, document.getWorkitemId())
            , EModelUtil.getDocumentIndex(
                document.getWorkitemId()
                , String.valueOf(document.getFolderItemNo())
                , String.valueOf(document.getDocumentNo())));

        TemplateUtil.checkPermission(documentSession, AccessRight.RW, messageSource.get(Constant.ErrorCode.E0025));
        documentSession.setLayers(document.getLayers());
        documentSession.setAction(Action.UPDATE.getAction());

        return true;
    }

    @Override
    public boolean closeImage(HttpSession session, String workspaceId, String workItemId, String folderItemNo,
                              String documentNo) throws EarthException {
        // Get Origin Document Session.
        Document orginDocument = (Document) getDataItemFromSession(
            session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getDocumentIndex(workItemId, folderItemNo, documentNo));

        TemplateUtil.checkPermission(orginDocument, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));

        // Replace document into temporary session.
        setDataItemToSession(
            session
            ,SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getDocumentIndex(workItemId, folderItemNo, documentNo),
            orginDocument);
        return true;
    }

    @Override
    public boolean saveAndCloseImageViewer(HttpSession session, String workspaceId, String workItemId,
                                           String folderItemNo) throws EarthException {
        FolderItem tempFolderItemSession = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, folderItemNo));
        TemplateUtil.checkPermission(tempFolderItemSession, AccessRight.RO,
            messageSource.get(Constant.ErrorCode.E0025));
        // Get Origin WorkItem Dictionary.
        WorkItemDictionary originWorkItemDictionary = (WorkItemDictionary) session
            .getAttribute(SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId));
        // Save document image session into origin document image session.
        List<Document> tempDocuments = tempFolderItemSession.getDocuments();
        for (Document doc : tempDocuments) {
            Document originDocumentSession = (Document) getDataItemFromSession(session,
                SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
                EModelUtil.getDocumentIndex(workItemId, String.valueOf(folderItemNo),
                    String.valueOf(doc.getDocumentNo())));

            if (originDocumentSession.getAccessRight().goe(AccessRight.RW)) {
                List<Layer> tempLayers = doc.getLayers();
                int maxNo = originDocumentSession.getLayers().size();

                for (Layer tempLayer : tempLayers) {
                    Layer newLayer = EModelUtil.clone(tempLayer, Layer.class);
                    // In case create new Layer.
                    if (EStringUtil.isEmpty(newLayer.getLayerNo())) {
                        if (newLayer.getAction() != Action.DELETE.getAction()) {
                            newLayer.setLayerNo(String.valueOf(-1 * (++maxNo)));
                            originDocumentSession.getLayers().add(newLayer);
                            originWorkItemDictionary.put(
                                EModelUtil.getLayerIndex(
                                    tempLayer.getWorkitemId(), tempLayer.getFolderItemNo(),
                                    tempLayer.getDocumentNo(), tempLayer.getLayerNo()),
                                newLayer);
                        }
                    } else {
                        // In case Update Layer.
                        Layer originLayer = (Layer) originWorkItemDictionary.get(
                            EModelUtil.getLayerIndex(
                                tempLayer.getWorkitemId(), tempLayer.getFolderItemNo(),
                                tempLayer.getDocumentNo(), tempLayer.getLayerNo()));
                        if (newLayer.getLayerNo().contains("-")
                                && (newLayer.getAction() == Action.DELETE.getAction())) {
                            originDocumentSession.getLayers().remove(originLayer);
                        } else {
                            originLayer.setLayerName(newLayer.getLayerName());
                            originLayer.setAnnotations(newLayer.getAnnotations());
                            originLayer.setAction(newLayer.getAction());
                        }
                    }
                }
            }
        }

        session.removeAttribute(SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId));
        return true;
    }

    @Override
    public boolean closeImageViewer(HttpSession session, String workspaceId, String workItemId, String folderItemNo)
        throws EarthException {
        FolderItem tempFolderItemSession = (FolderItem) getDataItemFromSession(session,
            SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getFolderItemIndex(workItemId, folderItemNo));
        TemplateUtil.checkPermission(tempFolderItemSession, AccessRight.RO,
            messageSource.get(Constant.ErrorCode.E0025));
        session.removeAttribute(SessionUtil.getTempWorkItemDictionaryKey(workspaceId, workItemId));
        return true;
    }

    @Override
    public String getAnnotationsByDocument(HttpSession session, String workspaceId, String workItemId,
                                           String folderItemNo, String documentNo) throws EarthException {
        // Get Document data from session.
        Document documentSession = (Document) getDataItemFromSession(session,
            SessionUtil.getOriginWorkItemDictionaryKey(workspaceId, workItemId),
            EModelUtil.getDocumentIndex(workItemId, String.valueOf(folderItemNo), String.valueOf(documentNo)));
        TemplateUtil.checkPermission(documentSession, AccessRight.RO, messageSource.get(Constant.ErrorCode.E0025));
        List<Layer> layers = documentSession.getLayers();
        if (layers.size() == 0) {
            return EStringUtil.EMPTY;
        }

        StringBuilder thumbernail = new StringBuilder();
        for (Layer layer : layers) {
            if (!EStringUtil.isEmpty(layer.getAnnotations())) {
                thumbernail.append(layer.getAnnotations());
            }
        }

        return thumbernail.toString();
    }
}
