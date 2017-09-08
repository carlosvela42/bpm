package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QCtlTemplate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestTemplateAuthorityDaoImpl extends BaseDaoImpl<CtlTemplate> implements TestTemplateAuthorityDao {

    public TestTemplateAuthorityDaoImpl() throws Exception {
        super();
    }

    public List<UserAccessRight> getMixAuthorityTemplate(TemplateKey templateKey) throws EarthException {
        List<UserAccessRight> userAccessRights = new ArrayList<UserAccessRight>();
        try {
            QCtlTemplate qCtlTemplate = QCtlTemplate.newInstance();
            QBean<CtlTemplate> selectList = Projections.bean(CtlTemplate.class, qCtlTemplate.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(templateKey.getWorkspaceId());
            List<CtlTemplate> ctlTemplates = earthQueryFactory.select(selectList).from(qCtlTemplate)
                    .where(qCtlTemplate.templateId.eq(templateKey.getTemplateId())).fetch();
            for (CtlTemplate ctlTemplate : ctlTemplates) {
                UserAccessRight userAccessRight = new UserAccessRight();
                userAccessRight.setUserId(ctlTemplate.getUserId());
                userAccessRight.setAccessRight(AccessRight.values()[ctlTemplate.getAccessAuthority()]);
                userAccessRights.add(userAccessRight);
            }
            return userAccessRights;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }
}
