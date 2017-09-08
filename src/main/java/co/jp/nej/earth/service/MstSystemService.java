package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MstSystem;

import java.util.List;

public interface MstSystemService {
    List<MstSystem> getMstSystem() throws EarthException;

    List<MstSystem> getMstSystemBySection(String section) throws EarthException;
}
