package co.jp.nej.earth.model;

import co.jp.nej.earth.model.sql.QSite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author p-tvo-sonta
 */
public class Site extends BaseModel<Site> {
    /**
     * serial number
     */
    private static final Logger LOG = LoggerFactory.getLogger(Site.class);
    private static final long serialVersionUID = 1L;
    private Integer siteId;
    private Integer dataDirectoryId;

    public Site() {
        LOG.debug("Call to blank constructor");
        this.setqObj(QSite.newInstance());
    }

    public Site(Integer siteId, Integer dataDirectoryId, Boolean checked) {
        super();

        LOG.debug("Call to (siteId, dataDirectoryId");

        this.siteId = siteId;
        this.dataDirectoryId = dataDirectoryId;
    }

    /**
     * @return the siteId
     */
    public Integer getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the dataDirectoryId
     */
    public Integer getDataDirectoryId() {
        return dataDirectoryId;
    }

    /**
     * @param dataDirectoryId the dataDirectoryId to set
     */
    public void setDataDirectoryId(Integer dataDirectoryId) {
        this.dataDirectoryId = dataDirectoryId;
    }

}