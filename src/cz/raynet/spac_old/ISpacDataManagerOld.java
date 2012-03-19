/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac_old;

import java.util.List;
import java.util.Map;

import cz.raynet.core.IDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto_old.Racer;
import cz.raynet.dto_old.Team;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public interface ISpacDataManagerOld extends IDataManager {

    public Map<String,Category> getCategoriesOld();
    public List<Team> getTeamsOld(int season);
    public Map<Integer,Racer> getRacersOld(int season);
    
    public boolean insertRacerOld(Racer racer);
    
}
