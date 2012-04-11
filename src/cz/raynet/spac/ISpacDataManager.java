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
    public void storeRacerCsvLine2011(RacerCSVLineDto racer);
    public void storeRacerCsvLine2010(RacerCSVLineDto racer);
    public void storeRacerCsvLine2009(RacerCSVLineDto racer);
    public void storeRacerCsvLine2008(RacerCSVLineDto racer);
    public void storeRacerCsvLine2007(RacerCSVLineDto racer);
    public void storeRacerCsvLine2006(RacerCSVLineDto racer);
    public void storeRacerCsvLine2005(RacerCSVLineDto racer);
    public void storeRacerCsvLine2004(RacerCSVLineDto racer);
    public void storeRacerCsvLine2003(RacerCSVLineDto racer);
    public void storeRacerCsvLine2002(RacerCSVLineDto racer);
    public void storeRacerCsvLine2001(RacerCSVLineDto racer);
    public void storeRacerCsvLine2000(RacerCSVLineDto racer);
    public void storeRacerCsvLine1999(RacerCSVLineDto racer);
    public void storeRacerCsvLine1998(RacerCSVLineDto racer);
    
}
