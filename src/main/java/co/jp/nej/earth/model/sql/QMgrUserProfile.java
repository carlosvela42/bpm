package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrUserProfile;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrUserProfile extends QBase<MgrUserProfile> {

    private static final long serialVersionUID = 1L;
    public final StringPath profileId = createString(ColumnNames.PROFILE_ID.toString());
    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath lastUpdateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrUserProfile newInstance() {
        return new QMgrUserProfile(QMgrUserProfile.class.getSimpleName(), null, TableNames.MGR_USER_PROFILE.name());
    }

    public QMgrUserProfile(String path, String schema, String table) {
        super(MgrUserProfile.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(profileId, ColumnMetadata.named(ColumnNames.PROFILE_ID.toString()));
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(lastUpdateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
