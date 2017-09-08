package co.jp.nej.earth.model.sql;

import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.TableNames;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.ColumnMetadata;

public class QCtlLogin extends QBase<CtlLogin> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final StringPath sessionId = createString(ColumnNames.SESSION_ID.toString());
    public final StringPath userId = createString(ColumnNames.USER_ID.toString());
    public final StringPath loginTime = createString(ColumnNames.LOGIN_TIME.toString());
    public final StringPath logoutTime = createString(ColumnNames.LOGOUT_TIME.toString());
    public final StringPath lastUpdatedTime = createString(ColumnNames.LAST_UPDATE_TIME.toString());

    public static QCtlLogin newInstance() {
        return new QCtlLogin(QCtlLogin.class.getSimpleName(), null, TableNames.CTL_LOGIN.name());
    }

    public QCtlLogin(String path, String schema, String table) {
        super(CtlLogin.class, PathMetadataFactory.forVariable(path), schema, table);
        addMetadata();

        // create primary key of Q_Object
        createPrimaryKey(sessionId);
    }

    protected void addMetadata() {
        addMetadata(sessionId, ColumnMetadata.named(ColumnNames.SESSION_ID.toString()));
        addMetadata(userId, ColumnMetadata.named(ColumnNames.USER_ID.toString()));
        addMetadata(loginTime, ColumnMetadata.named(ColumnNames.LOGIN_TIME.toString()));
        addMetadata(logoutTime, ColumnMetadata.named(ColumnNames.LOGOUT_TIME.toString()));
        addMetadata(lastUpdatedTime, ColumnMetadata.named(ColumnNames.LAST_UPDATE_TIME.toString()));
    }
}
