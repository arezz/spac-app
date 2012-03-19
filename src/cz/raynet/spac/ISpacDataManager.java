/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

import java.util.*;

import cz.raynet.core.IDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto.Racer;
import cz.raynet.dto.RacerCSVLineDto;
import cz.raynet.dto.Team;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public interface ISpacDataManager extends IDataManager {

    public Map<String,Category> getCategories();
    public List<Team> getTeams(int season);
    public Map<Integer,Racer> getRacers(int season);
    
    public boolean clearDatabaseTableResultCSV(int year);
    
    public boolean insertRacer(Racer racer);
    public void storeRacerCsvLine2011(RacerCSVLineDto racer);
    
}
