/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cz.raynet.core.AbstractDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto.RacerCSVLineDto;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class SpacDataManager extends AbstractDataManager implements ISpacDataManager {

    private static final ISpacDataManager INSTANCE = new SpacDataManager();

    public static ISpacDataManager getInstance() {
        return SpacDataManager.INSTANCE;
    }
    
    public Map<String,Category> getCategories() {
        Connection connection = null;
        Map<String,Category> map = new HashMap<String,Category>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.SELECT_SPAC_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cat = new Category();
                String prefix = rs.getString("prefix");
                cat.setId(rs.getInt("id"));
                cat.setPrefix(prefix);
                cat.setName(rs.getString("name"));
                cat.setCoefficient(rs.getDouble("coefficient"));
                cat.setSeason(rs.getInt("season"));
                
                map.put(prefix, cat);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
        return map;
    }
    
    public boolean clearDatabaseTableResultCSV(int year) {
        Connection connection = null;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = null;
            if (year == 2011) {
                ps = connection.prepareStatement(ESQLCommandsSpac.DELETE_TABLE_SPAC_RESULTS_2011);
            } else if (year == 2010) {
                ps = connection.prepareStatement(ESQLCommandsSpac.DELETE_TABLE_SPAC_RESULTS_2010);
            } else {
                ps = connection.prepareStatement(ESQLCommandsSpac.DELETE_TABLE_SPAC_RESULTS_2011);
            }
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public void storeRacerCsvLine2011(RacerCSVLineDto racer) {
        Connection connection = null;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2011);
            ps.setInt(1, racer.getIdCategory());
            ps.setString(2, racer.getSurname());
            ps.setString(3, racer.getFirstname());
            ps.setString(4, racer.getTeam());
            ps.setInt(5, Math.round(racer.getRace1()));
            ps.setInt(6, Math.round(racer.getRace2()));
            ps.setInt(7, Math.round(racer.getRace3()));
            ps.setInt(8, Math.round(racer.getRace4()));
            ps.setInt(9, Math.round(racer.getRace5()));
            ps.setInt(10, Math.round(racer.getRace6()));
            ps.setInt(11, Math.round(racer.getRace7()));
            ps.setInt(12, Math.round(racer.getRace8()));
            ps.setInt(13, Math.round(racer.getRace9()));
            ps.setInt(14, Math.round(racer.getRace10()));
            ps.setInt(15, Math.round(racer.getRace11()));
            ps.setInt(16, Math.round(racer.getRace12()));
            ps.setInt(17, Math.round(racer.getRace13()));
            ps.setInt(18, Math.round(racer.getRace14()));
            ps.setInt(19, Math.round(racer.getRace15()));
            ps.setInt(20, Math.round(racer.getRace16()));
            ps.setInt(21, Math.round(racer.getRace17()));
            ps.setInt(22, Math.round(racer.getRace18()));
            
            ps.setInt(23, Math.round(racer.getTotal()));
            ps.setInt(24, Math.round(racer.getTotalBestRaces()));

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
