package co.jp.nej.earth.util;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.DatProcess;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.FolderItem;
import co.jp.nej.earth.model.Layer;
import co.jp.nej.earth.model.TemplateAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.WorkItem;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.enums.AccessRight;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class TemplateUtil {
    public static void saveToSession(HttpSession session, TemplateAccessRight templateAccess) {
        session.setAttribute(Session.TEMPLATE_ACCESS_RIGHT_MAP, templateAccess);
    }

    public static List<String> getAccessibleTemplates(HttpSession session, String workspaceId) {
        List<String> listTemplate = new ArrayList<String>();

        TemplateAccessRight templateAccessRight = getAuthorityFromSession(session);
        if (templateAccessRight != null) {
            for (TemplateKey templateKey : templateAccessRight.getTemplatesAccessRights().keySet()) {
                if (templateKey.getWorkspaceId().equals(workspaceId)) {
                    listTemplate.add(templateKey.getTemplateId());
                }
            }
        }
        return listTemplate;
    }

    public static AccessRight getAuthority(HttpSession session, TemplateKey tKey) {
        TemplateAccessRight templateAccessRight = getAuthorityFromSession(session);
        if (templateAccessRight != null) {
            return templateAccessRight.get(tKey);
        }
        return AccessRight.FULL;
    }

    public static TemplateAccessRight getAuthorityFromSession(HttpSession session) {
        return (TemplateAccessRight) session.getAttribute(Session.TEMPLATE_ACCESS_RIGHT_MAP);
    }

    public static String getTemplateTableName(String templateType, String templateId) {
        String templateTypeString = templateTypeCodes.inverse().get(templateType);
        return "TMP_" + templateTypeString.substring(0, 1) + templateId;
    }

    private static BiMap<String, String> templateTypeCodes = null;
    public static String codeIdFromTemplateType(String tempalateType) {
        if(templateTypeCodes == null) {
            templateTypeCodes = HashBiMap.create();
            templateTypeCodes.put("PROCESS", "1");
            templateTypeCodes.put("WORKITEM", "2");
            templateTypeCodes.put("FOLDERITEM", "3");
            templateTypeCodes.put("DOCUMENT", "4");
            templateTypeCodes.put("LAYER", "5");
        }
        return templateTypeCodes.get(tempalateType);
    }

    public static void checkPermission(Object object, AccessRight minRequiredAccessRight, String errorMessage) throws
        EarthException {
        AccessRight accessRight = null;
        if (object instanceof WorkItem) {
            WorkItem workItem = (WorkItem) object;
            accessRight = workItem.getAccessRight();
        } else if (object instanceof DatProcess) {
            DatProcess process = (DatProcess) object;
            accessRight = process.getAccessRight();
        } else if (object instanceof FolderItem) {
            FolderItem ft = (FolderItem) object;
            accessRight = ft.getAccessRight();
        } else if (object instanceof Document) {
            Document doc = (Document) object;
            accessRight = doc.getAccessRight();
        } else if (object instanceof Layer) {
            Layer layer = (Layer) object;
            accessRight = layer.getAccessRight();
        }

        if (accessRight == null) {
            accessRight = AccessRight.NONE;
        }

        if (accessRight.le(minRequiredAccessRight)) {
            throw new EarthException(errorMessage);
        }
    }
}