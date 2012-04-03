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
	
    public void parseAllResultsFromCSV2011(String fileName);
    public void parseAllResultsFromCSV2010(String fileName);
    public void parseAllResultsFromCSV2009(String fileName);
    public void parseAllResultsFromCSV2008(String fileName);
    public void parseAllResultsFromCSV2007(String fileName);
    public void parseAllResultsFromCSV2006(String fileName);
    public void parseAllResultsFromCSV2005(String fileName);
    public void parseAllResultsFromCSV2004(String fileName);
    public void parseAllResultsFromCSV2003(String fileName);
    
}
