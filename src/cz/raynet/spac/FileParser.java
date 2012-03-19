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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.raynet.dto.Category;
import cz.raynet.dto.Racer;
import cz.raynet.dto.RacerCSVLineDto;
import cz.raynet.dto.ResultParsed;
import cz.raynet.utils.Utils;

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
    
    public void parseRacersFromFile(String fileName) {
        Map<String,Category> categories = fDataManager.getCategories();
        try {
            FileInputStream fr = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fr, "windows-1250");
            BufferedReader in = new BufferedReader(isr);
            String str;
            while ((str = in.readLine()) != null) {
                Racer r = parseRacerFromLine(str, categories);
                if (r != null) {
                    fDataManager.insertRacer(r);
                }
            }
            in.close();
        } catch (IOException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
    }
    
    private Racer parseRacerFromLine(String line, Map<String,Category> categories) {
        Racer r = null;
        // cislo;rocnik;prijmeni;jmeno;tym;kategorie;id_tym
        String[] items = line.split(";");
        if (items.length == 7) {
            // POVINNE: number, surname, first name and category 
            if (("".equals(items[0])) || ("".equals(items[2])) || ("".equals(items[3])) || ("".equals(items[5]))) {
                return null;
            }
            r = new Racer();
            try {
                r.setIdCategory(categories.get(items[5]).getId());
                if (items[6].length() == 0) {
                    r.setIdTeam(0);
                } else {
                    r.setIdTeam(Integer.parseInt(items[6]));
                }
                r.setFirstName(items[3]);
                r.setSurname(items[2]);
                r.setSpacNumber(Integer.parseInt(items[0]));
                r.setTeamName(items[4]);
                if (items[1].length() == 0) {
                    r.setYearBorn(0);
                } else {
                    r.setYearBorn(Integer.parseInt(items[1]));
                }
                
            } catch (Exception e) {
              //System.err.println(e.toString());
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
        return r;
    }
    
    public void parseResultsFromFile(String fileName, int idRace) {
        Map<Integer, ResultParsed> map = new HashMap<Integer, ResultParsed>();
        try {
            FileInputStream fr = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fr, "windows-1250");
            BufferedReader in = new BufferedReader(isr);
            String str;
            while ((str = in.readLine()) != null) {
                ResultParsed r = parseResultFromLine(str, idRace);
                if (r != null) {
                    map.put(new Integer(r.getStartNumber()), r);
                }
            }
            in.close();
        } catch (IOException e) {
          //System.err.println(e.toString());
            e.printStackTrace();
        }
        
        if (map.size() == 0) {
            return;
        }
        
        // TODO get categories

        /* TODO for every racer from map:
         * - get id, spac_number, id_team from DB table racer where startnumber and surname
         *      - or where surname, first name and id_category 
         *   if spac_number <> 0 set spacLicence = true
         *      
         * - or insert racer and select his id again (spacLicence = false)
         *  
         * -> store ID racer and id_team from DB into ResultParsed
         */
        
        /* TODO for every ResultParsed - insert into TABLE result
         *      - also count points for teams - Map<id_team,List<integer>> - points by category and coefficient (all have spac license)
         */
        
        // TODO - sort Lists in map, count best 3 results and store into DB result_team
 
        

        
    }
    
    private ResultParsed parseResultFromLine(String line, int idRace) {
        ResultParsed r = null;
        // poradi;cislo;rocnik;prijmeni;jmeno;tym;kategorie;vysledny cas;delka zavodu;pocet bodu
        String[] items = line.split(";");
        if (items.length == 10) {
            // POVINNE: poradi, cislo, prijmeni, jmeno, kategorie, body
            if (("".equals(items[0])) || ("".equals(items[1])) || ("".equals(items[3])) || ("".equals(items[4])) || ("".equals(items[6])) || ("".equals(items[9]))) {
                System.out.println("CHYBA (chybi povinny udaj): " + line);
                return null;
            }
            r = new ResultParsed();
            try {
                r.setStanding(Integer.parseInt(items[0].replaceAll("\\.", "")));
                r.setStartNumber(Integer.parseInt(items[1]));
                if (items[2].length() == 0) {
                    r.setYearBorn(0);
                } else {
                    r.setYearBorn(Integer.parseInt(items[2]));
                }
                r.setSurname(items[3]);
                r.setFirstName(items[4]);
                r.setTeamName(items[5]);
                r.setCategory(items[6]);
                
                // items[7] - time in format: HH,MM,SS for all races except for STARIC (MM,SS,ms)
                if (idRace == 1) {
                    r.setTotalTime(Utils.getTimeFromCommaStringAlsoMillis(items[7]));
                } else {
                    r.setTotalTime(Utils.getTimeFromCommaString(items[7]));
                }
                
                try {
                    r.setRaceLength(Double.parseDouble(items[8].replace(",", ".")));
                } catch (Exception e) {
                    r.setRaceLength(0.0);
                }
                r.setPoints(Integer.parseInt(items[9]));
            } catch (Exception e) {
              //System.err.println(e.toString());
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("CHYBA (malo udaju): " + line);
            return null;
        }
        return r;
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
