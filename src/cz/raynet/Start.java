/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet;

import cz.raynet.core.AppConnectionConfig;
import cz.raynet.spac.FileParser;
import cz.raynet.spac.IFileParser;

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
        
        //processStatsOld();
        
        processStatisticsOverall();

    }
    
    public static void processStatisticsOverall() {
    	
    	//ISpacDataManager spacDM = SpacDataManager.getInstance();        
       
    	IFileParser parser = FileParser.getInstance();
        
        //parser.clearDatabaseTableResultCSV(XXXX);
        //parser.parseAllResultsFromCSV2011("C:\\poradi2011.csv");  
        //parser.parseAllResultsFromCSV2010("C:\\poradi2010.csv");
    	//parser.parseAllResultsFromCSV2009("C:\\poradi2009.csv");
    	parser.parseAllResultsFromCSV2008("C:\\poradi2008.csv");
    }
    
    public static void processStatsOld() {
/*    	
    	ISpacDataManagerOld spacDMold = SpacDataManagerOld.getInstance();
        
        IFileParserOld parserOld = FileParserOld.getInstance();
        //parser.parseRacersFromFile("C:\\racers_export_teams.csv");
        //parser.parseResultsFromFile("C:\\01_Staric_all.csv", 1);
        
        for (Racer r : spacDMold.getRacersOld(2010).values()) {
            //System.out.println(r.toString());
        }
        
        for (Category c : spacDMold.getCategoriesOld().values()) {
            //System.out.println(c.toString());
        }
        
        for (Team t : spacDMold.getTeamsOld(2010)) {
            //System.out.println(t.toString());
        }
 */
    }
}
