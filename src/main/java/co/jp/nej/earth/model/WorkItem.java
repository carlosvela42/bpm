/**
 *
 */
package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QWorkItem;
import co.jp.nej.earth.util.EStringUtil;

import org.hibernate.validator.constraints.NotEmpty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author p-dcv-khanhnv
 */
@XmlRootElement
public class WorkItem extends BaseModel<WorkItem> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String workspaceId;
    @NotEmpty(message = "E0001,workitemId")
    private String workitemId;
    private String taskId;
    private MgrTemplate mgrTemplate;
    private TemplateData workItemData;
    private String templateId;
    private List<FolderItem> folderItems = new ArrayList<>();
    private int lastHistoryNo;
    private AccessRight accessRight;

    private DatProcess dataProcess;

    /**
     * @param workitemId
     * @param taskId
     * @param templateId
     * @param lastHistoryNo
     */
    public WorkItem(String workitemId, String taskId, String templateId, int lastHistoryNo, String lastUpdateTime) {
        super();
        this.workitemId = workitemId;
        this.taskId = taskId;
        this.templateId = templateId;
        this.lastHistoryNo = lastHistoryNo;
        this.setLastUpdateTime(lastUpdateTime);
    }

    public WorkItem() {
        this.setqObj(QWorkItem.newInstance());
    }

    /**
     * @return the workspaceId
     */
    public String getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @param workspaceId the workspaceId to set
     */
    @XmlElement
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    /**
     * @return the workitemId
     */
    public String getWorkitemId() {
        return workitemId;
    }

    /**
     * @param workitemId the workitemId to set
     */
    @XmlElement
    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    @XmlElement
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public MgrTemplate getMgrTemplate() {
        return mgrTemplate;
    }

    public void setMgrTemplate(MgrTemplate mgrTemplate) {
        this.mgrTemplate = mgrTemplate;
    }

    /**
     * @return the workItemData
     */
    public TemplateData getWorkItemData() {
        return workItemData;
    }

    /**
     * @param workItemData the workItemData to set
     */
    public void setWorkItemData(TemplateData workItemData) {
        this.workItemData = workItemData;
    }

    /**
     * @return the templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    @XmlElement
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the folderItems
     */
    public List<FolderItem> getFolderItems() {
        return folderItems;
    }

    /**
     * @param folderItems the folderItems to set
     */
    @XmlElement
    public void setFolderItems(List<FolderItem> folderItems) {
        this.folderItems = folderItems;
    }

    public void addFolderItem(FolderItem folderItem) {
        if (folderItem != null && (!EStringUtil.isEmpty(folderItem.getFolderItemNo()))
                && !folderItems.contains(folderItem)) {
            folderItems.add(folderItem);
        }
    }

    /**
     * @return the lastHistoryNo
     */
    public int getLastHistoryNo() {
        return lastHistoryNo;
    }

    /**
     * @param lastHistoryNo the lastHistoryNo to set
     */
    public void setLastHistoryNo(int lastHistoryNo) {
        this.lastHistoryNo = lastHistoryNo;
    }

    /**
     * @return the dataProcess
     */
    public DatProcess getDataProcess() {
        return dataProcess;
    }

    /**
     * @param dataProcess the dataProcess to set
     */
    public void setDataProcess(DatProcess dataProcess) {
        this.dataProcess = dataProcess;
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
    }
}