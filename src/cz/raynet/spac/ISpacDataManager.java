/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

import java.util.Map;

import cz.raynet.core.IDataManager;
import cz.raynet.dto.Category;
import cz.raynet.dto.RacerCSVLineDto;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public interface ISpacDataManager extends IDataManager {
    
	public Map<String,Category> getCategories();
    public boolean clearDatabaseTableResultCSV(int year);    
    public void storeRacerCsvLine2011(RacerCSVLineDto racer);
    public void storeRacerCsvLine2010(RacerCSVLineDto racer);
    
}
