package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Directory;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.DocumentSavingInfo;
import co.jp.nej.earth.model.enums.DocumentSavingType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentServiceTest extends BaseTest {

    @Autowired
    private DocumentService documentService;

    private Document document;

    private DocumentSavingInfo documentSavingInfo;

    private static final String FOLDERITEMNO = "1121";

    @Before
    public void excutedBefore() throws IOException {
        File file = new File("src\\test\\resource\\test.png");

        if (file.createNewFile()) {
            System.out.println("File is created!");
        } else {
            System.out.println("File already exists.");
        }
        document = new Document();
        document.setFolderItemNo(FOLDERITEMNO);
        document.setDocumentNo("1");
        document.setDocumentPath("src\\test\\resource\\test.png");
        document.setWorkitemId("1002");

        documentSavingInfo = new DocumentSavingInfo();

        documentSavingInfo.setSavingType(DocumentSavingType.FILE_UNTIL_FULL);
        Directory directory = new Directory();
        directory.setDataDirectoryId(1);
        directory.setDiskVolSize("1000000000000000");
        directory.setReservedDiskVolSize("111111111111111");
        directory.setFolderPath("C:\\Users\\p-tvo-sonta\\Desktop\\directory");
        List<Directory> list = new ArrayList<>();
        list.add(directory);
        documentSavingInfo.setDataDef(list);
    }

    @Test
    public void getDocumentTest() {
        System.out.println("-----------------------------------------------");
        try {
            System.out.println(documentService.getDocument("003", document, documentSavingInfo));
        } catch (EarthException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void saveDocumentTest() {
        try {
            Assert.assertEquals(
                documentService.saveDocumentDataFile("003", document, 1, new HashMap<>()),
                true);
        } catch (EarthException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getDocumentSavingInfoTest() throws EarthException {
//        DocumentSavingInfo documentSavingInfo = documentService.getDocumentSavingInfo("003", 1);
//        Assert.assertEquals(documentSavingInfo.getSavingType(), DocumentSavingType.DATABASE);
    }
}
