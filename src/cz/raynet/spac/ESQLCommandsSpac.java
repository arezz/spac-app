/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class ESQLCommandsSpac {

    public static final String SELECT_SPAC_CATEGORIES = "SELECT * FROM spac_category";
    
    
    // =======  PARSER  =========    
    public static final String DELETE_TABLE_SPAC_RESULTS_2011 = "DELETE FROM spac_results_2011 WHERE 1=1";
    
    public static final String INSERT_RACER_SPAC_RESULT_2011 = "INSERT INTO spac_results_2011 (id_category, racer_surname, racer_firstname, racer_team," +
    		"race_1, race_2, race_3, race_4, race_5, race_6, race_7, race_8, race_9, race_10, race_11, race_12, race_13, race_14, race_15, race_16, race_17, race_18, " +
    		"total, total_best_races) \n " +
		    "VALUES (?,?,?,?," +
		    		"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
		    		"?,?)";
    
    // =======  END PARSER  =========
    
}
