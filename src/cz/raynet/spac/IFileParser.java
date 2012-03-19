/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

/**
 * @author arezz
 * created 12.5.2010
 *
 */
public interface IFileParser {

    public void parseRacersFromFile(String fileName);
    
    public void parseResultsFromFile(String fileName, int idRace);
    
    public boolean clearDatabaseTableResultCSV(int year);
    public void parseAllResultsFromCSV2011(String fileName);
    
}
