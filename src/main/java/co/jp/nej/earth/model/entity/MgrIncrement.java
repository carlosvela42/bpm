package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrIncrement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class MgrIncrement extends BaseModel<MgrIncrement> implements Serializable {

    /**
     * Serial version
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(MgrIncrement.class);

    //private Integer id;
    private String  incrementType;
    private int incrementData;
    private String incrementDateTime;
    private String sessionId;

    public MgrIncrement() {

        LOG.debug("Call to blank constructor");
        this.setqObj(QMgrIncrement.newInstance());
    }


    public String getIncrementType() {
        return incrementType;
    }

    public void setIncrementType(String incrementType) {
        this.incrementType = incrementType;
    }

    public int getIncrementData() {
        return incrementData;
    }

    public void setIncrementData(int incrementData) {
        this.incrementData = incrementData;
    }

    public String getIncrementDateTime() {
        return incrementDateTime;
    }

    public void setIncrementDatetime(String incrementDateTime) {
        this.incrementDateTime = incrementDateTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    /*public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }*/

}
