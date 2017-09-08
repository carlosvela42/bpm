package co.jp.nej.earth.model.entity;

import java.io.Serializable;

import co.jp.nej.earth.model.BaseModel;

public class MstCode extends BaseModel<MstCode> implements Serializable{
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private String codeId;
    private String codeValue;
    private String section;
    private String sectionValue;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(String sectionValue) {
        this.sectionValue = sectionValue;
    }
}
