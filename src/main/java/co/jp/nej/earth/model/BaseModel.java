package co.jp.nej.earth.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.sql.RelationalPathBase;

/**
 * Base Model for Model object.
 *
 * @author landd
 */
public class BaseModel<T> implements Serializable {

    /**
     * Serial number
     */
    private static final long serialVersionUID = 1L;


    // Currently, this is the date string follow yyyyMMddHHmmssSSS(yyyy MM dd HH
    // mm ss SSS) format

    private String lastUpdateTime;

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @JsonIgnore
    private RelationalPathBase<T> qObj;

    public RelationalPathBase<T> getqObj() {
        return qObj;
    }

    public void setqObj(RelationalPathBase<T> qObj) {
        this.qObj = qObj;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
