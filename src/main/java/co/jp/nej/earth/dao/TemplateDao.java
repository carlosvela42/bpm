package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.TemplateData;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.web.form.SearchForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;
import java.util.Map;

public interface TemplateDao extends BaseDao<MgrTemplate> {
    MgrTemplate getTemplate(String workspaceId, String templateId) throws EarthException;

    TemplateData getProcessTemplateData(String workspaceId, String processId, String workItemId, String templateId
        , int maxVersion) throws EarthException;

    List<TemplateData> getTemplateDataList(String workspaceId, MgrTemplate mgrTemplate,
                                    BooleanBuilder condition,Long offset, Long limit,List<OrderSpecifier<?>> orderBys)
        throws EarthException;

    Map<String, MgrTemplate> getTemplates(String workspaceId, List<String> templateIds) throws EarthException;

    TemplateData getWorkItemTemplateData(String workspaceId, String workItemId, String templateId, int maxVersion)
            throws EarthException;

    TemplateData getFolderItemTemplateData(String workspaceId, String workItemId, String folderItemNo
            , String templateId, int maxVersion) throws EarthException;

    TemplateData getDocumentTemplateData(String workspaceId, String workItemId, String folderItemNo, String docNo
            , String templateId, int maxVersion) throws EarthException;

    TemplateData getLayerTemplateData(String workspaceId, String workItemId, String folderItemNo, String docNo
            , String layerNo, String templateId, int maxVersion) throws EarthException;

    long insertProcessTemplateData(String workspaceId, DatProcess process, int historyNo) throws EarthException;

    long insertWorkItemTemplateData(WorkItem workItem) throws EarthException;

    long insertFolderItemTemplateData(String workspaceId, FolderItem folderItem, int historyNo) throws EarthException;

    long insertDocumentTemplateData(String workspaceId, Document document, int historyNo) throws EarthException;

    long insertLayerTemplateData(String workspaceId, Layer layer, int historyNo) throws EarthException;

    List<MgrTemplate> getAllByWorkspace(String workspaceId) throws EarthException;

    List<MgrTemplate> getAllByWorkspace(String workspaceId, String userId) throws EarthException;

    MgrTemplate getById(TemplateKey templateKey) throws EarthException;

    List<MgrTemplate> getTemplateByType(String workspaceId, String templateType) throws EarthException;

    List<MgrTemplate> getTemplateByType(String workspaceId, String templateType,String userId) throws EarthException;

    TemplateData getProcessTemplateData(String workspaceId, String processId, String templateId, int maxVersion)
            throws EarthException;

    long deleteTemplates(List<String> templateIds, String workspaceId) throws EarthException;

    long insertOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException;

    long resetNodeItemUsingTemplateId(String workspaceId, List<String> templateIds) throws EarthException;

    long createTemplateData(String workspaceId, MgrTemplate mgrTemplate) throws EarthException;

    long updateOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException;

    long isExistsTableData(String workspaceId, String templateTableName, String dbUser) throws EarthException;

    String getFieldJson(String workspaceId, SearchForm searchForm) throws EarthException;

    /**
     * Get List MgrTemplate by Ids
     *
     * @param ids         List template id
     * @param workspaceId Workspace ID
     * @param type Template type
     * @return List MgrTemplate if OK, otherwise throw EarthException
     */
    List<MgrTemplate> getByIdsAndType(String workspaceId, List<String> ids, TemplateType type) throws EarthException;

    Map<String, TemplateData> getTemplateDataMap(String workspaceId, String workItemId,
            Map<String, MgrTemplate> templates, int maxHistoryNo) throws EarthException;

    String getTemplateIdByItemId(String workspaceId, String id, TemplateType type) throws EarthException;

    String getTaskIdByItemId(String workspaceId, String id) throws EarthException;
}