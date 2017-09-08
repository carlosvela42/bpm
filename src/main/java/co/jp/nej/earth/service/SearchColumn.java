package co.jp.nej.earth.service;

import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QBase;
import co.jp.nej.earth.model.sql.QMstCode;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.web.form.ColumnSearch;
import co.jp.nej.earth.web.form.SearchByColumnForm;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SearchColumn {

    private static final String TYPE_STRING_PATH = "CHAR";
    private static final Logger LOG = LoggerFactory.getLogger(SearchColumn.class);
    private static final int LENGTH_DATE = 14;
    private static final int MAX_LENGTH_DATE_TIME = 17;
    private static final int MAX_LENGTH_SECOND = 13;
    private static final int MAX_LENGTH_MINUTE = 12;
    private static final int LENGTH_MINUTE = 11;
    private static final int MAX_LENGTH_DAY = 10;
    private static final int LENGTH_DAY = 9;
    private static final int MAX_LENGTH_DATE_DAY = 8;
    private static final int LENGTH_DATE_DAY = 7;
    private static final int MAX_LENGTH_DATE_MONTH = 6;
    private static final int LENGTH_DATE_MONTH = 5;
    private static final int MAX_LENGTH_DATE_YEAR = 4;
    private static final int LENGTH_DATE_YEAR = 3;


    private BooleanBuilder search(QBase<?> qTemplateData, SearchByColumnForm searchByColumnForm) {
        BooleanBuilder condition = new BooleanBuilder();
        List<ColumnSearch> columnSearches = searchByColumnForm.getColumnSearchs();
        String valid = searchByColumnForm.getValid();
        String type = searchByColumnForm.getType();
        Integer date = searchByColumnForm.getIsDate();
        boolean isDate = false;
        if (date != null) {
            isDate = searchByColumnForm.getIsDate() == 1;
        }
        boolean isEncrypted = false;
        if (date != null) {
            isEncrypted = searchByColumnForm.getEncrypted() == 1;
        }
        if (columnSearches != null) {
            for (ColumnSearch search : columnSearches) {
                if ((search.getValue() != null && !EStringUtil.isEmpty(search.getValue()))
                    || (search.getOperator() != null
                    && (EStringUtil.equals(search.getOperator().toUpperCase(), Constant.Operator.ISNULL)
                    || EStringUtil.equals(search.getOperator().toUpperCase(), Constant.Operator.IS_NOT_NULL)
                    || EStringUtil.equals(search.getOperator().toUpperCase(), Constant.Operator.IS_EMPTY)
                    || EStringUtil.equals(search.getOperator().toUpperCase(), Constant.Operator.IS_NOT_EMPTY)))) {
                    Path<?> p = getPathFromName(qTemplateData, search.getName(), type);
                    if (EStringUtil.equals(search.getName(), ColumnNames.CODE_VALUE.toString())) {
                        QMstCode qMstCode = QMstCode.newInstance();
                        p = getPathFromName(qMstCode, search.getName(), type);
                    }
                    Predicate pre1 = null;
                    String value = search.getValue().trim();
                    if (isDate) {
                        value = DateUtil.convertDateToStringFormat(search.getValue().trim());
                        value = convertStringDate(value, search.getOperator());
                    }
                    if (isEncrypted) {
                        try {
                            value = CryptUtil.encryptData(search.getValue().trim());
                        } catch (Exception ex) {
                            LOG.error(ex.getMessage());
                        }
                    }
                    if (p instanceof StringPath) {
                        StringExpression p1 = (StringExpression) p;
                        switch (search.getOperator().toUpperCase()) {
                            case Constant.Operator.EQUAL:
                                pre1 = p1.trim().eq(value);
                                if (isDate && value.length() == LENGTH_DATE) {
                                    pre1 = p1.trim().like(Expressions.constant(value + Constant.Operator.PERCENT));
                                }
                                break;
                            case Constant.Operator.NOT_EQUAL:
                                pre1 = p1.trim().ne(value);
                                if (isDate && value.length() == LENGTH_DATE) {
                                    pre1 = p1.trim().notLike(Expressions.constant(value + Constant.Operator.PERCENT));
                                }
                                break;
                            case Constant.Operator.OVER:
                                pre1 = p1.trim().gt(value);
                                break;
                            case Constant.Operator.EQUAL_OVER:
                                pre1 = p1.trim().gt(value).or(p1.trim().eq(value));
                                break;
                            case Constant.Operator.UNDER:
                                pre1 = p1.trim().lt(value);
                                break;
                            case Constant.Operator.EQUAL_UNDER:
                                pre1 = p1.trim().lt(value).or(p1.trim().eq(value));
                                break;
                            case Constant.Operator.LIKE:
                                pre1 = p1.upper().like(Expressions.constant(
                                    Constant.Operator.PERCENT + value.toUpperCase()
                                        + Constant.Operator.PERCENT));
                                break;
                            case Constant.Operator.NOT_LIKE:
                                pre1 = p1.upper().notLike(Expressions.constant(
                                    Constant.Operator.PERCENT + value.toUpperCase()
                                        + Constant.Operator.PERCENT));
                                break;
                            case Constant.Operator.ISNULL:
                                pre1 = p1.trim().isNull();
                                break;
                            case Constant.Operator.IS_NOT_NULL:
                                pre1 = p1.trim().isNotNull();
                                break;
                            case Constant.Operator.IS_EMPTY:
                                pre1 = p1.trim().isEmpty();
                                break;
                            case Constant.Operator.IS_NOT_EMPTY:
                                pre1 = p1.trim().isNotEmpty();
                                break;
                            default:
                                break;
                        }
                    } else if (p instanceof NumberPath) {
                        NumberExpression<Integer> p1 = (NumberExpression<Integer>) p;
                        switch (search.getOperator().toUpperCase()) {
                            case Constant.Operator.EQUAL:
                                pre1 = p1.eq(Integer.parseInt(value));
                                break;
                            case Constant.Operator.NOT_EQUAL:
                                pre1 = p1.ne(Integer.parseInt(value));
                                break;
                            case Constant.Operator.OVER:
                                pre1 = p1.gt(Integer.parseInt(value));
                                break;
                            case Constant.Operator.EQUAL_OVER:
                                pre1 = p1.gt(Integer.parseInt(value))
                                    .or(p1.eq(Integer.parseInt(value)));
                                break;
                            case Constant.Operator.UNDER:
                                pre1 = p1.lt(Integer.parseInt(value));
                                break;
                            case Constant.Operator.EQUAL_UNDER:
                                pre1 = p1.lt(Integer.parseInt(value))
                                    .or(p1.eq(Integer.parseInt(value)));
                                break;
                            case Constant.Operator.ISNULL:
                                pre1 = p1.isNull();
                                break;
                            case Constant.Operator.IS_NOT_NULL:
                                pre1 = p1.isNotNull();
                                break;
                            case Constant.Operator.LIKE:
                                pre1 = p1.like(Expressions.constant(Constant.Operator.PERCENT + value.toUpperCase()
                                    + Constant.Operator.PERCENT));
                                break;
                            case Constant.Operator.NOT_LIKE:
                                pre1 = p1.like(Expressions.constant(Constant.Operator.PERCENT + value.toUpperCase()
                                    + Constant.Operator.PERCENT)).not();
                                break;
                            default:
                                break;
                        }
                    }
                    if (pre1 != null) {
                        switch (valid.toUpperCase()) {
                            case Constant.WorkItemList.WHERE_AND:
                                condition.and(pre1);
                                break;
                            case Constant.WorkItemList.WHERE_OR:
                                condition.or(pre1);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        return condition;
    }

    public BooleanBuilder searchColumns(QBase<?> qTemplateData, String valid,
                                        List<SearchByColumnForm> searchByColumnForms) {
        BooleanBuilder condition = new BooleanBuilder();
        if (searchByColumnForms != null) {
            for (SearchByColumnForm searchByColumnForm : searchByColumnForms) {
                if (searchByColumnForm != null) {
                    switch (valid.toUpperCase()) {
                        case Constant.WorkItemList.WHERE_AND:
                            condition.and(search(qTemplateData, searchByColumnForm));
                            break;
                        case Constant.WorkItemList.WHERE_OR:
                            condition.or(search(qTemplateData, searchByColumnForm));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return condition;
    }

    private Path<?> getPathFromName(QBase<?> qTemplateData, String name, String type) {
        if (EStringUtil.contains(type, TYPE_STRING_PATH)) {
            return (Path<?>) ConversionUtil.castObject(qTemplateData.getFieldString(name), Path.class);
        } else {
            return (Path<?>) ConversionUtil.castObject(qTemplateData.getFieldNumber(name), Path.class);
        }
    }


    private String convertStringDate(String stringDate, String operator) {
        Integer lengthDate = stringDate.length();
        String stringReturn = stringDate;
        if (EStringUtil.equals(operator.toUpperCase(), Constant.Operator.OVER)
            || EStringUtil.equals(operator.toUpperCase(), Constant.Operator.EQUAL_UNDER)) {
            if (lengthDate < MAX_LENGTH_DATE_TIME) {
                switch (lengthDate) {
                    case LENGTH_DATE:
                        stringReturn = stringDate + "999";
                        break;
                    case MAX_LENGTH_SECOND:
                        stringReturn = stringDate + "9999";
                        break;
                    case MAX_LENGTH_MINUTE:
                        stringReturn = stringDate + "59999";
                        break;
                    case LENGTH_MINUTE:
                        stringReturn = stringDate + "959999";
                        break;
                    case MAX_LENGTH_DAY:
                        stringReturn = stringDate + "5959999";
                        break;
                    case LENGTH_DAY:
                        stringReturn = stringDate + "35959999";
                        break;
                    case MAX_LENGTH_DATE_DAY:
                        stringReturn = stringDate + "235959999";
                        break;
                    case LENGTH_DATE_DAY:
                        stringReturn = stringDate + "1235959999";
                        break;
                    case MAX_LENGTH_DATE_MONTH:
                        stringReturn = stringDate + "31235959999";
                        break;
                    case LENGTH_DATE_MONTH:
                        stringReturn = stringDate + "231235959999";
                        break;
                    case MAX_LENGTH_DATE_YEAR:
                        stringReturn = stringDate + "1231235959999";
                        break;
                    case LENGTH_DATE_YEAR:
                        stringReturn = stringDate + "91231235959999";
                        break;
                    case 2:
                        stringReturn = stringDate + "991231235959999";
                        break;
                    case 1:
                        stringReturn = stringDate + "9991231235959999";
                        break;
                    default:
                        break;
                }
            }
        }
        if (EStringUtil.equals(operator.toUpperCase(), Constant.Operator.UNDER)
            || EStringUtil.equals(operator.toUpperCase(), Constant.Operator.EQUAL_OVER)) {
            if (lengthDate < MAX_LENGTH_DATE_TIME) {
                String add = "";
                Integer miss = MAX_LENGTH_DATE_TIME - lengthDate;

                for (Integer i = 0; i < miss; i++) {
                    add += "0";
                }
                stringReturn = stringDate + add;
            }
        }
        return stringReturn;
    }
}
