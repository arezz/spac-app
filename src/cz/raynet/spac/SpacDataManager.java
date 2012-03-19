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
import java.util.*;

import cz.raynet.core.AbstractDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto.Racer;
import cz.raynet.dto.RacerCSVLineDto;
import cz.raynet.dto.Team;

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
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.SELECT_CATEGORIES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category cat = new Category();
                String prefix = rs.getString("prefix");
                cat.setId(rs.getInt("id"));
                cat.setPrefix(prefix);
                cat.setName(rs.getString("name"));
                cat.setCoefficient(rs.getDouble("coefficient"));
                
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
    
    public List<Team> getTeams(int season) {
        Connection connection = null;
        List<Team> list = new ArrayList<Team>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.SELECT_TEAMS);
            ps.setInt(1, season);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team();
                team.setId(rs.getInt("id"));
                team.setName(rs.getString("name"));
                team.setRacersCount(rs.getInt("count_racers"));
                team.setSeason(rs.getInt("season"));
                
                list.add(team);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            //System.err.println(e.toString());
            e.printStackTrace();
        }
        return list;
    }
    
    public Map<Integer,Racer> getRacers(int season) {
        Connection connection = null;
        Map<Integer, Racer> map = new HashMap<Integer, Racer>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.SELECT_RACERS);
            ps.setInt(1, season);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Racer racer = new Racer();
                Integer number = rs.getInt("spac_number");
                racer.setId(rs.getInt("id"));
                racer.setIdCategory(rs.getInt("id_category"));
                racer.setIdTeam(rs.getInt("id_team"));
                racer.setSpacNumber(number);
                racer.setFirstName(rs.getString("first_name"));
                racer.setSurname(rs.getString("surname"));
                racer.setSeason(season);
                racer.setYearBorn(rs.getInt("year_born"));
                racer.setTeamName(rs.getString("team_name"));
                
                map.put(number, racer);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
        return map;
    }
    
    
    public boolean insertRacer(Racer racer) {
        Connection connection = null;
        boolean result = false;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER);
            ps.setInt(1, racer.getIdCategory());
            ps.setInt(2, racer.getIdTeam());
            ps.setString(3, racer.getFirstName());
            ps.setString(4, racer.getSurname());
            ps.setInt(5, racer.getYearBorn());
            ps.setInt(6, racer.getSpacNumber());
            ps.setString(7, racer.getTeamName());
            
            if (ps.executeUpdate() == 1) {
                result = true;
            }
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean clearDatabaseTableResultCSV(int year) {
        Connection connection = null;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = null;
            if (year == 2011) {
                ps = connection.prepareStatement(ESQLCommandsSpac.DELETE_TABLE_SPAC_RESULTS_2011);
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
            ps.setString(2, racer.getRacerSurname());
            ps.setString(3, racer.getRacerFirstname());
            ps.setString(4, racer.getRacerTeam());
            ps.setInt(5, racer.getRace1());
            ps.setInt(6, racer.getRace2());
            ps.setInt(7, racer.getRace3());
            ps.setInt(8, racer.getRace4());
            ps.setInt(9, racer.getRace5());
            ps.setInt(10, racer.getRace6());
            ps.setInt(11, racer.getRace7());
            ps.setInt(12, racer.getRace8());
            ps.setInt(13, racer.getRace9());
            ps.setInt(14, racer.getRace10());
            ps.setInt(15, racer.getRace11());
            ps.setInt(16, racer.getRace12());
            ps.setInt(17, racer.getRace13());
            ps.setInt(18, racer.getRace14());
            ps.setInt(19, racer.getRace15());
            ps.setInt(20, racer.getRace16());
            ps.setInt(21, racer.getRace17());
            ps.setInt(22, racer.getRace18());
            
            ps.setInt(23, racer.getTotal());
            ps.setInt(24, racer.getTotalBestRaces());
            
            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
