package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.CtlTemplate;

import java.util.List;

public interface TestTemplateAuthorityDao extends BaseDao<CtlTemplate> {
    List<UserAccessRight> getMixAuthorityTemplate(TemplateKey templateKey) throws EarthException;
}