package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.MenuAuthorityDao;
import co.jp.nej.earth.dao.MenuDao;
import co.jp.nej.earth.dao.UserProfileDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrMenu;
import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.model.sql.QMgrMenu;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.UserAccessRightUtil;
import com.querydsl.core.types.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends BaseService implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private MenuAuthorityDao menuAuthorityDao;

    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    public List<MgrMenu> getMenuByProfileId(List<String> profileIds) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuDao.getMenuByProfileId(profileIds);
        }), MgrMenu.class);
    }

    @Override
    public List<MgrMenu> getAll() throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuDao.getAll();
        }), MgrMenu.class);
    }

    @Override
    public MgrMenu getDetail(String functionId) throws EarthException {
        return (MgrMenu) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            QMgrMenu qMgrMenu = QMgrMenu.newInstance();
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrMenu.functionId, functionId);
            return menuDao.showDetail(functionId);
        });
    }

    @Override
    public List<UserAccessRight> getUserAuthority(String functionId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuAuthorityDao.getUserAuthority(functionId);
        }), UserAccessRight.class);
    }

    @Override
    public List<ProfileAccessRight> getProfileAuthority(String functionId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return menuAuthorityDao.getProfileAuthority(functionId);
        }), ProfileAccessRight.class);
    }

    @Override
    public long saveAuthority(String functionId, List<UserAccessRight> userAccessRights,
                              List<ProfileAccessRight> profileAccessRights)
        throws EarthException {
        return (long) executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            menuAuthorityDao.deleteAllUserAuthority(functionId);

            if (userAccessRights.size() > 0) {
                menuAuthorityDao.insertUserAuthority(functionId, userAccessRights);
            }

            menuAuthorityDao.deleteAllProfileAuthority(functionId);

            if (profileAccessRights.size() > 0) {
                menuAuthorityDao.insertProfileAuthority(functionId, profileAccessRights);
            }
            menuAuthorityDao.deleteAllMixAuthority(Arrays.asList(functionId));

            List<String> profileIds = new ArrayList<>();
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                profileIds.add(profileAccessRight.getProfileId());
            }
            List<MgrUserProfile> mgrUserProfiles = userProfileDao.getListByProfileIds(profileIds);

            Map<String, AccessRight> accessRightPMap = new HashMap<>();
            Map<String, Integer> accessRightValuePMap = new HashMap<>();
            for (ProfileAccessRight profileAccessRight : profileAccessRights) {
                accessRightPMap.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRight());
                accessRightValuePMap.put(profileAccessRight.getProfileId(), profileAccessRight.getAccessRightValue());
            }

            List<UserAccessRight> userAccessRightByProfiles = UserAccessRightUtil.getUserAccessRightProfiles(
                mgrUserProfiles, accessRightValuePMap);
            List<UserAccessRight> menuAccessRights = UserAccessRightUtil.mixAuthority(userAccessRights,
                userAccessRightByProfiles);
            if (menuAccessRights.size() > 0) {
                return menuAuthorityDao.insertMixAuthority(functionId, menuAccessRights);
            } else {
                return 0L;
            }

        });
    }

}
