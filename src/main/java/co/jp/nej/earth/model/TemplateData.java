package co.jp.nej.earth.model;

import co.jp.nej.earth.model.xml.MapAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

public class TemplateData {
    private Integer historyNo;
    private String lastUpdateTime;

    /**
     * Map containing data: Key is field name, value is value of field.
     */
    private Map<String, Object> dataMap;

    public TemplateData() {
        super();
    }

    /**
     * @param historyNo
     * @param lastUpdateTime
     * @param dataMap
     */
    public TemplateData(Integer historyNo, String lastUpdateTime, Map<String, Object> dataMap) {
        super();
        this.historyNo = historyNo;
        this.lastUpdateTime = lastUpdateTime;
        this.dataMap = dataMap;
    }

    /**
     * @param dataMap
     */
    public TemplateData(Map<String, Object> dataMap) {
        super();
        this.dataMap = dataMap;
    }

    /**
     * @return the historyNo
     */
    public Integer getHistoryNo() {
        return historyNo;
    }

    /**
     * @param historyNo the historyNo to set
     */
    public void setHistoryNo(Integer historyNo) {
        this.historyNo = historyNo;
    }

    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return the dataMap
     */
    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    /**
     * @param dataMap the dataMap to set
     */
    @XmlElement
    @XmlJavaTypeAdapter(MapAdapter.class)
    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
