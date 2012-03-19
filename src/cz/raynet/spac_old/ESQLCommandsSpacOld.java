/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac_old;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class ESQLCommandsSpacOld {

    public static final String SELECT_CATEGORIES_OLD = "SELECT * FROM category_old";
    
    public static final String SELECT_TEAMS_OLD = "SELECT * FROM team_old t WHERE t.season=?";
    
    public static final String SELECT_RACERS_OLD = "SELECT * FROM racer_old r WHERE r.season=?";
    
    
    // =======  PARSER  =========
    public static final String INSERT_RACER_OLD = "INSERT INTO racer_old (id_category, id_team, first_name, surname, year_born, spac_number, season, team_name) " +
    "VALUES (?,?,?,?,?,?,2010,?)";
    
    // =======  END PARSER  =========
    
}
