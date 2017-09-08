package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QMgrUser extends QBase<MgrUser> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath name = createString(ColumnNames.NAME.toString());
    public final StringPath password = createString(ColumnNames.PASSWORD.toString());
    public final StringPath lastUdpateTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QMgrUser newInstance() {
        return new QMgrUser(QMgrUser.class.getSimpleName(), null, TableNames.MGR_USER.name());
    }

    public QMgrUser(String path, String schema, String table) {
        super(MgrUser.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();
    }

    protected void addMetadata() {
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(name, ColumnMetadata.named(ColumnNames.NAME.toString()));
        addMetadata(password, ColumnMetadata.named(ColumnNames.PASSWD.toString()));
        addMetadata(lastUdpateTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
