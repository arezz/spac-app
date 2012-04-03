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
                int season = rs.getInt("season");
                cat.setId(rs.getInt("id"));
                cat.setPrefix(prefix);
                cat.setName(rs.getString("name"));
                cat.setCoefficient(rs.getDouble("coefficient"));
                cat.setSeason(season);
                
                map.put(prefix+season, cat);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
        return map;
    }
    
    public int retrieveIdRacer(String surname, String firstname) {
    	Connection connection = null;
    	int idRacer = -1;
        try {
            connection = getDAO().getConnection();
            // retrieve racer from db / insert racer and get id
            PreparedStatement ps = connection.prepareStatement(ESQLCommandsSpac.SELECT_RACER_ID);
            ps.setString(1, surname);
            ps.setString(2, firstname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	idRacer = rs.getInt("id");                
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            //System.err.println(e.toString());
              e.printStackTrace();
        }
        return idRacer;
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
            ps.setInt(5, (int)Math.round(racer.getRace1()));
            ps.setInt(6, (int)Math.round(racer.getRace2()));
            ps.setInt(7, (int)Math.round(racer.getRace3()));
            ps.setInt(8, (int)Math.round(racer.getRace4()));
            ps.setInt(9, (int)Math.round(racer.getRace5()));
            ps.setInt(10, (int)Math.round(racer.getRace6()));
            ps.setInt(11, (int)Math.round(racer.getRace7()));
            ps.setInt(12, (int)Math.round(racer.getRace8()));
            ps.setInt(13, (int)Math.round(racer.getRace9()));
            ps.setInt(14, (int)Math.round(racer.getRace10()));
            ps.setInt(15, (int)Math.round(racer.getRace11()));
            ps.setInt(16, (int)Math.round(racer.getRace12()));
            ps.setInt(17, (int)Math.round(racer.getRace13()));
            ps.setInt(18, (int)Math.round(racer.getRace14()));
            ps.setInt(19, (int)Math.round(racer.getRace15()));
            ps.setInt(20, (int)Math.round(racer.getRace16()));
            ps.setInt(21, (int)Math.round(racer.getRace17()));
            ps.setInt(22, (int)Math.round(racer.getRace18()));
            
            ps.setInt(23, (int)Math.round(racer.getTotal()));
            ps.setInt(24, (int)Math.round(racer.getTotalBestRaces()));

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public int retrieveStoreRacer(RacerCSVLineDto racer, Connection connection) {
    	int idRacer = -1;
        try {
        	PreparedStatement ps = null;
            // retrieve racer from db / insert racer and get id
            idRacer = retrieveIdRacer(racer.getSurname(), racer.getFirstname());            
            if (idRacer == -1) {
            	if (connection == null || connection.isClosed()) {
            		connection = getDAO().getConnection();
            	}
            	ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER);
                ps.setString(1, racer.getSurname());
                ps.setString(2, racer.getFirstname());
                ps.executeUpdate();
                
                // get id of the racer again
                if (connection == null || connection.isClosed()) {
                	connection = getDAO().getConnection();
                }
                idRacer = retrieveIdRacer(racer.getSurname(), racer.getFirstname()); 
            }
        } catch (SQLException e) {
            //System.err.println(e.toString());
              e.printStackTrace();
        }
        return idRacer;
    }
	       			
    public void storeRacerCsvLine2010(RacerCSVLineDto racer) {
        Connection connection = null;
        try {
            connection = getDAO().getConnection();
            
            int idRacer = retrieveStoreRacer(racer, connection);
            
            PreparedStatement ps = null;
            
        	// store racer into spac_result
            if (connection == null || connection.isClosed()) {
            	connection = getDAO().getConnection();
            }            
            ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2010);
            ps.setInt(1, racer.getIdCategory());
            ps.setInt(2, idRacer);
            ps.setString(3, racer.getSurname());
            ps.setString(4, racer.getFirstname());
            ps.setString(5, racer.getTeam());
            ps.setDouble(6, racer.getRace1());
            ps.setDouble(7, racer.getRace2());
            ps.setDouble(8, racer.getRace3());
            ps.setDouble(9, racer.getRace4());
            ps.setDouble(10, racer.getRace5());
            ps.setDouble(11, racer.getRace6());
            ps.setDouble(12, racer.getRace7());
            ps.setDouble(13, racer.getRace8());
            ps.setDouble(14, racer.getRace9());
            ps.setDouble(15, racer.getRace10());
            ps.setDouble(16, racer.getRace11());
            ps.setDouble(17, racer.getRace12());
            ps.setDouble(18, racer.getRace13());
            ps.setDouble(19, racer.getRace14());
            ps.setDouble(20, racer.getRace15());
            ps.setDouble(21, racer.getRace16());
            ps.setDouble(22, racer.getRace17());
            
            ps.setDouble(23, racer.getTotal());
            ps.setDouble(24, racer.getTotalBestRaces());
            
            ps.setInt(25, racer.getFinalStanding());
            ps.setInt(26, racer.getTotalRacers());
            ps.setInt(27, racer.getSpacLicence());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public void storeRacerCsvLine2009(RacerCSVLineDto racer) {
        Connection connection = null;
        try {
            connection = getDAO().getConnection();
            PreparedStatement ps = null;
            // retrieve racer from db / insert racer and get id
            connection = getDAO().getConnection();
            
            int idRacer = retrieveStoreRacer(racer, connection);
            
        	// store racer into spac_result
            if (connection == null || connection.isClosed()) {
            	connection = getDAO().getConnection();
            }            
            ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2009);
            ps.setInt(1, racer.getIdCategory());
            ps.setInt(2, idRacer);
            ps.setString(3, racer.getSurname());
            ps.setString(4, racer.getFirstname());
            ps.setString(5, racer.getTeam());
            ps.setInt(6, (int)Math.round(racer.getRace1()));
            ps.setInt(7, (int)Math.round(racer.getRace2()));
            ps.setInt(8, (int)Math.round(racer.getRace3()));
            ps.setInt(9, (int)Math.round(racer.getRace4()));
            ps.setInt(10, (int)Math.round(racer.getRace5()));
            ps.setInt(11, (int)Math.round(racer.getRace6()));
            ps.setInt(12, (int)Math.round(racer.getRace7()));
            ps.setInt(13, (int)Math.round(racer.getRace8()));
            ps.setInt(14, (int)Math.round(racer.getRace9()));
            ps.setInt(15, (int)Math.round(racer.getRace10()));
            ps.setInt(16, (int)Math.round(racer.getRace11()));
            ps.setInt(17, (int)Math.round(racer.getRace12()));
            ps.setInt(18, (int)Math.round(racer.getRace13()));
            ps.setInt(19, (int)Math.round(racer.getRace14()));
            ps.setInt(20, (int)Math.round(racer.getRace15()));
            ps.setInt(21, (int)Math.round(racer.getRace16()));
            ps.setInt(22, (int)Math.round(racer.getRace17()));
            
            ps.setInt(23, (int)Math.round(racer.getTotal()));
            ps.setInt(24, (int)Math.round(racer.getTotalBestRaces()));
            
            ps.setInt(25, racer.getFinalStanding());
            ps.setInt(26, racer.getTotalRacers());
            ps.setInt(27, racer.getSpacLicence());

            ps.executeUpdate();
            
            ps.close();
        } catch (SQLException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
    
    public void storeRacerCsvLine2008(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
            
            int idRacer = retrieveStoreRacer(racer, connection);
            
            PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2008);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		ps.setInt(11, (int)Math.round(racer.getRace6()));
    		ps.setInt(12, (int)Math.round(racer.getRace7()));
    		ps.setInt(13, (int)Math.round(racer.getRace8()));
    		ps.setInt(14, (int)Math.round(racer.getRace9()));
    		ps.setInt(15, (int)Math.round(racer.getRace10()));
    		ps.setInt(16, (int)Math.round(racer.getRace11()));
    		ps.setInt(17, (int)Math.round(racer.getRace12()));
    		ps.setInt(18, (int)Math.round(racer.getRace13()));
    		ps.setInt(19, (int)Math.round(racer.getRace14()));
    		ps.setInt(20, (int)Math.round(racer.getRace15()));
    		ps.setInt(21, (int)Math.round(racer.getRace16()));
    		
    		ps.setInt(22, (int)Math.round(racer.getTotal()));
    		ps.setInt(23, (int)Math.round(racer.getTotalBestRaces()));
    		
    		ps.setInt(24, racer.getFinalStanding());
    		ps.setInt(25, racer.getTotalRacers());
    		ps.setInt(26, racer.getSpacLicence());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    
    public void storeRacerCsvLine2007(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
    		
    		int idRacer = retrieveStoreRacer(racer, connection);
    		
    		PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2007);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		ps.setInt(11, (int)Math.round(racer.getRace6()));
    		ps.setInt(12, (int)Math.round(racer.getRace7()));
    		ps.setInt(13, (int)Math.round(racer.getRace8()));
    		ps.setInt(14, (int)Math.round(racer.getRace9()));
    		ps.setInt(15, (int)Math.round(racer.getRace10()));
    		ps.setInt(16, (int)Math.round(racer.getRace11()));
    		ps.setInt(17, (int)Math.round(racer.getRace12()));
    		ps.setInt(18, (int)Math.round(racer.getRace13()));
    		ps.setInt(19, (int)Math.round(racer.getRace14()));
    		
    		ps.setInt(20, (int)Math.round(racer.getTotal()));
    		ps.setInt(21, (int)Math.round(racer.getTotalBestRaces()));
    		
    		ps.setInt(22, racer.getFinalStanding());
    		ps.setInt(23, racer.getTotalRacers());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    
    public void storeRacerCsvLine2006(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
    		
    		int idRacer = retrieveStoreRacer(racer, connection);
    		
    		PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2006);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		
    		ps.setInt(11, (int)Math.round(racer.getTotal()));
    		
    		ps.setInt(12, racer.getFinalStanding());
    		ps.setInt(13, racer.getTotalRacers());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    public void storeRacerCsvLine2005(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
    		
    		int idRacer = retrieveStoreRacer(racer, connection);
    		
    		PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2005);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		ps.setInt(11, (int)Math.round(racer.getRace6()));
    		ps.setInt(12, (int)Math.round(racer.getRace7()));
    		
    		ps.setInt(13, (int)Math.round(racer.getTotal()));
    		ps.setInt(14, (int)Math.round(racer.getTotalBestRaces()));
    		
    		ps.setInt(15, racer.getFinalStanding());
    		ps.setInt(16, racer.getTotalRacers());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    
    public void storeRacerCsvLine2004(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
    		
    		int idRacer = retrieveStoreRacer(racer, connection);
    		
    		PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2004);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		ps.setInt(11, (int)Math.round(racer.getRace6()));
    		ps.setInt(12, (int)Math.round(racer.getRace7()));
    		ps.setInt(13, (int)Math.round(racer.getRace8()));
    		ps.setInt(14, (int)Math.round(racer.getRace9()));
    		ps.setInt(15, (int)Math.round(racer.getRace10()));
    		ps.setInt(16, (int)Math.round(racer.getRace11()));
    		ps.setInt(17, (int)Math.round(racer.getRace12()));
    		
    		ps.setInt(18, (int)Math.round(racer.getTotal()));
    		ps.setInt(19, (int)Math.round(racer.getTotalBestRaces()));
    		
    		ps.setInt(20, racer.getFinalStanding());
    		ps.setInt(21, racer.getTotalRacers());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    
    public void storeRacerCsvLine2003(RacerCSVLineDto racer) {
    	Connection connection = null;
    	try {
    		connection = getDAO().getConnection();
    		
    		int idRacer = retrieveStoreRacer(racer, connection);
    		
    		PreparedStatement ps = null;
    		
    		// store racer into spac_result
    		if (connection == null || connection.isClosed()) {
    			connection = getDAO().getConnection();
    		}            
    		ps = connection.prepareStatement(ESQLCommandsSpac.INSERT_RACER_SPAC_RESULT_2003);
    		ps.setInt(1, racer.getIdCategory());
    		ps.setInt(2, idRacer);
    		ps.setString(3, racer.getSurname());
    		ps.setString(4, racer.getFirstname());
    		ps.setString(5, racer.getTeam());
    		ps.setInt(6, (int)Math.round(racer.getRace1()));
    		ps.setInt(7, (int)Math.round(racer.getRace2()));
    		ps.setInt(8, (int)Math.round(racer.getRace3()));
    		ps.setInt(9, (int)Math.round(racer.getRace4()));
    		ps.setInt(10, (int)Math.round(racer.getRace5()));
    		ps.setInt(11, (int)Math.round(racer.getRace6()));
    		ps.setInt(12, (int)Math.round(racer.getRace7()));
    		ps.setInt(13, (int)Math.round(racer.getRace8()));
    		ps.setInt(14, (int)Math.round(racer.getRace9()));
    		ps.setInt(15, (int)Math.round(racer.getRace10()));
    		ps.setInt(16, (int)Math.round(racer.getRace11()));
    		
    		ps.setInt(17, (int)Math.round(racer.getTotal()));
    		ps.setInt(18, (int)Math.round(racer.getTotalBestRaces()));
    		
    		ps.setInt(19, racer.getFinalStanding());
    		ps.setInt(20, racer.getTotalRacers());
    		
    		ps.executeUpdate();
    		
    		ps.close();
    	} catch (SQLException e) {
    		//System.err.println(e.toString());
    		e.printStackTrace();
    	}
    }
    
}
