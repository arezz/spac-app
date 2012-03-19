/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.raynet.dto.Category;
import cz.raynet.dto.RacerCSVLineDto;

/**
 * @author arezz
 * created 12.5.2010
 *
 */
public class FileParser implements IFileParser {

    private ISpacDataManager fDataManager = SpacDataManager.getInstance();
    
    private static final IFileParser INSTANCE = new FileParser();

    public static IFileParser getInstance() {
        return FileParser.INSTANCE;
    }
    
    // +++ parsing from CSV file (the same file that is exported to WEB ===========================================================
    public boolean clearDatabaseTableResultCSV(int year) {
    	return fDataManager.clearDatabaseTableResultCSV(year);
    }
    
    
	public void parseAllResultsFromCSV2011(String fileName) {
		Map<String,Category> categories = fDataManager.getCategories();
        try {
            FileInputStream fr = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fr, "windows-1250");
            BufferedReader in = new BufferedReader(isr);
            String line;
            Category category = null;
            while ((line = in.readLine()) != null) {
            	// rozlisit radek - kategorie, nazvy zavodu, zavodnici
            	String[] cells = line.split(";");
            	if (cells != null && cells.length > 0) {
            		String firstCell = cells[0]; 
            		if (firstCell == null || firstCell.length() == 0 || firstCell.toUpperCase().startsWith("PŘÍJMENÍ")) {
            			// empty line, skip it!
            			continue;
            		}
            		// if category, recognize it
            		if (firstCell.toUpperCase().startsWith("KATEGORIE")) {
            			category = recognizeCategory(categories, firstCell);
            			continue;
            		}
            		// if "DRUZSTVA" - the end of parsing;
            		if (firstCell.toUpperCase().startsWith("DRUŽSTVA")) {
            			break;
            		}
            		
            		// else racer! parse him, store him! (if valid category)
            		RacerCSVLineDto racer = parseRacerCsvLine2011(cells, category);
            		if (racer != null) {
            			// store racer into DB
            			fDataManager.storeRacerCsvLine2011(racer);
            		}
            	}
            }
            in.close();
        } catch (IOException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
	}
    
	/**
	 * rozpozna kategorii, kategorii Z priradi jako ZA
	 * @param categories
	 * @param firstCell
	 * @return
	 */
	private Category recognizeCategory(Map<String,Category> categories, String firstCell) {
		for (Category cat : categories.values()) {
			if (firstCell.equalsIgnoreCase("KATEGORIE " + cat.getPrefix())) {
				return cat;
			}
			if (firstCell.equalsIgnoreCase("KATEGORIE Ž") && cat.getPrefix().equalsIgnoreCase("ŽA")) {
	            return cat;
	        }			
		}
		return null;
	}
	
	private RacerCSVLineDto parseRacerCsvLine2011(String[] cells, Category category) {
		if (category == null || cells.length < 21) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		List<Integer> results = new LinkedList<Integer>();
		racer.setIdCategory(category.getId());
		try {
			racer.setRacerSurname(cells[0]);
			racer.setRacerFirstname(cells[1]);
			racer.setRacerTeam(cells[2]);
			int points = Math.round(Float.parseFloat(cells[3].replace(",", ".")));
			racer.setRace1(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[4].replace(",", ".")));
			racer.setRace2(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[5].replace(",", ".")));
			racer.setRace3(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[6].replace(",", ".")));
			racer.setRace4(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[7].replace(",", ".")));
			racer.setRace5(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[8].replace(",", ".")));
			racer.setRace6(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[9].replace(",", ".")));
			racer.setRace7(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[10].replace(",", ".")));
			racer.setRace8(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[11].replace(",", ".")));
			racer.setRace9(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[12].replace(",", ".")));
			racer.setRace10(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[13].replace(",", ".")));
			racer.setRace11(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[14].replace(",", ".")));
			racer.setRace12(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[15].replace(",", ".")));
			racer.setRace13(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[16].replace(",", ".")));
			racer.setRace14(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[17].replace(",", ".")));
			racer.setRace15(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[18].replace(",", ".")));
			racer.setRace16(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[19].replace(",", ".")));
			racer.setRace17(points);		results.add(points);
			points = Math.round(Float.parseFloat(cells[20].replace(",", ".")));
			racer.setRace18(points);		results.add(points);
			
			Collections.sort(results);
			
			int total = 0;
			int totalBest = 0;
			if (results.size() == 18) {
				for (int i = 0; i < 18; i++) {
					total += results.get(i);
					if (i >= 9) {
						totalBest += results.get(i);
					}
				}
			}
			
			racer.setTotal(total);
			racer.setTotalBestRaces(totalBest);
			
			return racer;
			
		} catch (Exception e) {
			// nop
		}
		
		return null;
	}
    
    // --- parsing from CSV file (the same file that is exported to WEB ===========================================================
}
