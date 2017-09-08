package co.jp.nej.earth.service;


import java.util.Map;

import co.jp.nej.earth.exception.EarthException;

/**
 * Service for All of master codes.
 *
 * @author KhanhNV
 * @version 1.0
 */
public interface MstCodeService {
    Map<String, String> getMstCodesBySection(String section) throws EarthException;
}
