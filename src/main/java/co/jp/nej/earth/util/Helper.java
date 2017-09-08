package co.jp.nej.earth.util;

import co.jp.nej.earth.model.enums.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Helper method for difficult display logic in view layer (freemarker)
 * An instance of this class would be injected into all views
 */
@Component
public class Helper {
    private static final int length = 8;
    private static final String QUOTE = "'";
    private static final String QCOMMA = "',";
    private static String cacheMessageJS;

    @Autowired
    private EMessageResource messageResource;

    public Type getDisplayType(Integer currentType, List<Type> types) {
        for (Type type : types) {
            if (type.getValue() == currentType) {
                return type;
            }
        }
        return null;
    }

    public boolean present(String date) {
        return !StringUtils.isEmpty(date);
    }

    public Map<String, String> getBuildInfo() {
        Map<String, String> buildInfo = new HashMap<>();
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("build.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildInfo.put("version", props.getProperty("build.version", ""));
        String revision = props.getProperty("build.revision", "");
        String displayRevision = revision.length() > length ? revision.substring(0, length) : revision;
        buildInfo.put("revision", displayRevision);
        buildInfo.put("date", props.getProperty("build.date", ""));
        String url = props.getProperty("build.url", "");
        buildInfo.put("url", url + revision);

        return buildInfo;
    }

    public String getMessageSourceJS() {
        if(cacheMessageJS == null) {
            StringBuilder messageJS = new StringBuilder("earth.Messages = {");

            //messageJS.append("'temp':'").append(messageResource.get("temp")).append(QCOMMA);
            messageJS.append("'E0001':'").append(messageResource.get("E0001")).append(QCOMMA).append("\n");
            messageJS.append("'E0003':'").append(messageResource.get("E0003")).append(QCOMMA).append("\n");
            messageJS.append("'E0005':'").append(messageResource.get("E0005")).append(QCOMMA).append("\n");
            messageJS.append("'E0006':'").append(messageResource.get("E0006")).append(QCOMMA).append("\n");
            messageJS.append("'E0010':'").append(messageResource.get("E0010")).append(QCOMMA).append("\n");
            messageJS.append("'E0008':'").append(messageResource.get("E0008")).append(QCOMMA).append("\n");
            messageJS.append("'E0011':'").append(messageResource.get("E0011")).append(QCOMMA).append("\n");
            messageJS.append("'E0012':'").append(messageResource.get("E0012")).append(QCOMMA).append("\n");
            messageJS.append("'E1014':'").append(messageResource.get("E1014")).append(QCOMMA).append("\n");
            messageJS.append("'E0026':'").append(messageResource.get("E0026")).append(QCOMMA).append("\n");
            messageJS.append("'directory':'").append(messageResource.get("directory")).append(QCOMMA).append("\n");
            messageJS.append("'process':'").append(messageResource.get("process")).append(QCOMMA).append("\n");
            messageJS.append("'profile':'").append(messageResource.get("profile")).append(QCOMMA).append("\n");
            messageJS.append("'schedule':'").append(messageResource.get("schedule")).append(QCOMMA).append("\n");
            messageJS.append("'site':'").append(messageResource.get("site")).append(QCOMMA).append("\n");
            messageJS.append("'template':'").append(messageResource.get("template")).append(QCOMMA).append("\n");
            messageJS.append("'field':'").append(messageResource.get("field")).append(QCOMMA).append("\n");
            messageJS.append("'field.name':'").append(messageResource.get("field.name")).append(QCOMMA).append("\n");
            messageJS.append("'field.description':'")
                .append(messageResource.get("field.description")).append(QCOMMA).append("\n");
            messageJS.append("'field.size':'").append(messageResource.get("field.size")).append(QCOMMA).append("\n");
            messageJS.append("'user':'").append(messageResource.get("user")).append(QCOMMA).append("\n");
            messageJS.append("'workspace':'").append(messageResource.get("workspace")).append(QCOMMA).append("\n");
            messageJS.append("'button.confirmDelete':'").append(messageResource.get("button.confirmDelete"))
                .append(QUOTE);
            messageJS.append("};");
            cacheMessageJS = messageJS.toString();
        }
        return cacheMessageJS;
    }
}
