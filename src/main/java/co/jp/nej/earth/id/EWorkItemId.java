package co.jp.nej.earth.id;

import co.jp.nej.earth.exception.EarthException;

public interface EWorkItemId {
    String getAutoId(String sessionId) throws EarthException;
}
