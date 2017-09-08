package co.jp.nej.earth.model.sql;

import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.RelationalPathBase;

/**
 * Created by p-dcv-minhtv on 6/16/2017.
 */
public class QBase<T> extends RelationalPathBase<T> {

    public QBase(Class<? extends T> type, String variable, String schema, String table) {
        super(type, variable, schema, table);
    }

    public QBase(Class<? extends T> type, PathMetadata metadata, String schema, String table) {
        super(type, metadata, schema, table);
    }

    public StringPath getFieldString(String name) {
        return createString(name);
    }

    public NumberPath<Integer> getFieldNumber(String name) {
        return createNumber(name, Integer.class);
    }

}
