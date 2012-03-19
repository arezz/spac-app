/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac_old;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.raynet.core.AbstractDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto_old.Racer;
import cz.raynet.dto_old.Team;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class SpacDataManagerOld extends AbstractDataManager implements ISpacDataManagerOld {

    private static final ISpacDataManagerOld INSTANCE = new SpacDataManagerOld();

    public static ISpacDataManagerOld getInstance() {
        return SpacDataManagerOld.INSTANCE;
    }
    
    public Map<String,Category> getCategoriesOld() {
        Connection connection = null;
        Map<String,Category> map = new HashMap<String,Category>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpacOld.SELECT_CATEGORIES_OLD);
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
    
    public List<Team> getTeamsOld(int season) {
        Connection connection = null;
        List<Team> list = new ArrayList<Team>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpacOld.SELECT_TEAMS_OLD);
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
    
    public Map<Integer,Racer> getRacersOld(int season) {
        Connection connection = null;
        Map<Integer, Racer> map = new HashMap<Integer, Racer>();
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpacOld.SELECT_RACERS_OLD);
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
    
    
    public boolean insertRacerOld(Racer racer) {
        Connection connection = null;
        boolean result = false;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpacOld.INSERT_RACER_OLD);
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

}
