package co.jp.nej.earth.model.entity;

public class MstSystem {
    private Integer id;
    private String section;
    private String variableName;
    private String configValue;
    private String lastUpdateTime;

    public MstSystem() {
        super();
    }

    public MstSystem(Integer id, String section, String variableName, String configValue, String lastUpdateTime) {
        super();
        this.id = id;
        this.section = section;
        this.variableName = variableName;
        this.configValue = configValue;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

}
