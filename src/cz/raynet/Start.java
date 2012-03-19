/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet;

import cz.raynet.core.AppConnectionConfig;
import cz.raynet.dto.Category;
import cz.raynet.dto.Racer;
import cz.raynet.dto.Team;
import cz.raynet.spac.FileParser;
import cz.raynet.spac.IFileParser;
import cz.raynet.spac.ISpacDataManager;
import cz.raynet.spac.SpacDataManager;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class Start {

    private static void initDBConnection() {
        AppConnectionConfig.initConnectionConfig();
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        initDBConnection();
        
        ISpacDataManager spacDM = SpacDataManager.getInstance();
        
        IFileParser parser = FileParser.getInstance();
        //parser.parseRacersFromFile("C:\\racers_export_teams.csv");
        //parser.parseResultsFromFile("C:\\01_Staric_all.csv", 1);
        
        //parser.clearDatabaseTableResultCSV(2011);
        parser.parseAllResultsFromCSV2011("C:\\poradi.csv");       
        
        
        for (Racer r : spacDM.getRacers(2010).values()) {
            //System.out.println(r.toString());
        }
        
        for (Category c : spacDM.getCategories().values()) {
            //System.out.println(c.toString());
        }
        
        for (Team t : spacDM.getTeams(2010)) {
            //System.out.println(t.toString());
        }

    }

}
