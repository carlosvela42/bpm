package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;

/**
 * Process service service class
 *
 * @author daopq
 * @version  1.0
 */
public interface ProcessIServiceService {
    Integer getWorkSpaceIdByProcessServiceId(Integer processIServiceId) throws EarthException;
}
