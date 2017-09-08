package co.jp.nej.earth.id;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant.EarthId;

public interface EAutoIncrease {

    String getAutoId(EarthId type, String sessionId) throws EarthException;
}
