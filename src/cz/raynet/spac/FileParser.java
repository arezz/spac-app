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
            			category = recognizeCategory(categories, firstCell, 2011	);
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
    
	public void parseAllResultsFromCSV2010(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("13. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2011);
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2010(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2010(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void parseAllResultsFromCSV2009(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("12. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2009(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2009(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void parseAllResultsFromCSV2008(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("11. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2008(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2008(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void parseAllResultsFromCSV2007(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("10. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2007(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2007(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	public void parseAllResultsFromCSV2006(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("9. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2006(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2006(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	public void parseAllResultsFromCSV2005(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("8. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2005(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2005(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	public void parseAllResultsFromCSV2004(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("7. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2004(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2004(racerResult);
						System.out.print(".");
					}
				}
			}
			in.close();
		} catch (IOException e) {
			//System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	public void parseAllResultsFromCSV2003(String fileName) {
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
				if (cells != null && cells.length > 1) {
					String secondCell = cells[1]; 
					if (secondCell == null || secondCell.length() == 0 
							|| secondCell.toUpperCase().startsWith("PŘÍJMENÍ")
							|| secondCell.toUpperCase().startsWith("KONEČNÉ")
							|| secondCell.toUpperCase().startsWith("6. ROČNÍK")) {
						// empty line, first 2 lines, skip it!
						continue;
					}
					// if category, recognize it
					if (secondCell.toUpperCase().startsWith("KATEGORIE")) {
						category = recognizeCategory(categories, secondCell, 2009); // zeny
						if (category == null) {
							category = recognizeCategory(categories, secondCell, 2011);
						}
						continue;
					}
					// if "DRUZSTVA" - the end of parsing;
					if (secondCell.toUpperCase().startsWith("DRUŽSTVA")) {
						break;
					}
					
					// else racer! parse him, store him! (if valid category)
					RacerCSVLineDto racerResult = parseRacerCsvLine2003(cells, category);
					if (racerResult != null) {
						fDataManager.storeRacerCsvLine2003(racerResult);
						System.out.print(".");
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
	 * rozpozna kategorii
	 * @param categories
	 * @param firstCell
	 * @param season platna sezona
	 * @return
	 */
	private Category recognizeCategory(Map<String,Category> categories, String cell, int season) {
		for (Category cat : categories.values()) {
			if (cat.getSeason() != season) {
				continue;
			}
			if (cell.equalsIgnoreCase("KATEGORIE " + cat.getPrefix())) {
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
			racer.setSurname(cells[0]);
			racer.setFirstname(cells[1]);
			racer.setTeam(cells[2]);
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
	
	private RacerCSVLineDto parseRacerCsvLine2010(String[] cells, Category category) {
		if (category == null || cells.length < 25) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; points = cells[5]; racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			points = cells[16]; racer.setRace12((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[16].replace(",", ".")));
			points = cells[17]; racer.setRace13((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[17].replace(",", ".")));
			points = cells[18]; racer.setRace14((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[18].replace(",", ".")));
			points = cells[19]; racer.setRace15((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[19].replace(",", ".")));
			points = cells[20]; racer.setRace16((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[20].replace(",", ".")));
			points = cells[21]; racer.setRace17((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[21].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[22].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[23]));
			String licence = cells[24];
			racer.setSpacLicence((licence != null && "1".equalsIgnoreCase(licence)) ? 1 : 0);
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private RacerCSVLineDto parseRacerCsvLine2009(String[] cells, Category category) {
		if (category == null || cells.length < 25) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; points = cells[5]; racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			points = cells[16]; racer.setRace12((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[16].replace(",", ".")));
			points = cells[17]; racer.setRace13((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[17].replace(",", ".")));
			points = cells[18]; racer.setRace14((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[18].replace(",", ".")));
			points = cells[19]; racer.setRace15((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[19].replace(",", ".")));
			points = cells[20]; racer.setRace16((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[20].replace(",", ".")));
			points = cells[21]; racer.setRace17((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[21].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[22].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[23]));
			String licence = cells[24];
			racer.setSpacLicence((licence != null && "1".equalsIgnoreCase(licence)) ? 1 : 0);
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private RacerCSVLineDto parseRacerCsvLine2008(String[] cells, Category category) {
		if (category == null || cells.length < 24) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			points = cells[16]; racer.setRace12((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[16].replace(",", ".")));
			points = cells[17]; racer.setRace13((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[17].replace(",", ".")));
			points = cells[18]; racer.setRace14((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[18].replace(",", ".")));
			points = cells[19]; racer.setRace15((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[19].replace(",", ".")));
			points = cells[20]; racer.setRace16((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[20].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[21].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[22]));
			String licence = cells[23];
			racer.setSpacLicence((licence != null && "1".equalsIgnoreCase(licence)) ? 1 : 0);
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
    
	private RacerCSVLineDto parseRacerCsvLine2007(String[] cells, Category category) {
		if (category == null || cells.length < 21) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			points = cells[16]; racer.setRace12((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[16].replace(",", ".")));
			points = cells[17]; racer.setRace13((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[17].replace(",", ".")));
			points = cells[18]; racer.setRace14((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[18].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[19].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[20]));
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	private RacerCSVLineDto parseRacerCsvLine2006(String[] cells, Category category) {
		if (category == null || cells.length < 11) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[10]));
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	private RacerCSVLineDto parseRacerCsvLine2005(String[] cells, Category category) {
		if (category == null || cells.length < 14) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[12].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[13]));
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	private RacerCSVLineDto parseRacerCsvLine2004(String[] cells, Category category) {
		if (category == null || cells.length < 19) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			points = cells[16]; racer.setRace12((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[16].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[17].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[18]));
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	private RacerCSVLineDto parseRacerCsvLine2003(String[] cells, Category category) {
		if (category == null || cells.length < 18) {
			return null;
		}
		RacerCSVLineDto racer = new RacerCSVLineDto();
		racer.setIdCategory(category.getId());
		try {
			racer.setFinalStanding(Integer.parseInt(cells[0].replace(".", "")));
			racer.setSurname(cells[1]);
			racer.setFirstname(cells[2]);
			racer.setTeam(cells[3]);
			racer.setTotalBestRaces(Float.parseFloat(cells[4].replace(",", ".")));
			String points = cells[5]; 
			racer.setRace1((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[5].replace(",", ".")));		
			points = cells[6]; racer.setRace2((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[6].replace(",", ".")));	
			points = cells[7]; racer.setRace3((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[7].replace(",", ".")));
			points = cells[8]; racer.setRace4((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[8].replace(",", ".")));
			points = cells[9]; racer.setRace5((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[9].replace(",", ".")));
			points = cells[10]; racer.setRace6((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[10].replace(",", ".")));
			points = cells[11]; racer.setRace7((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[11].replace(",", ".")));
			points = cells[12]; racer.setRace8((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[12].replace(",", ".")));
			points = cells[13]; racer.setRace9((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[13].replace(",", ".")));
			points = cells[14]; racer.setRace10((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[14].replace(",", ".")));
			points = cells[15]; racer.setRace11((points == null || "DQ".equalsIgnoreCase(points) || "DNF".equalsIgnoreCase(points)) ? 0 : Float.parseFloat(cells[15].replace(",", ".")));
			racer.setTotal(Float.parseFloat(cells[16].replace(",", ".")));
			racer.setTotalRacers(Integer.parseInt(cells[17]));
			
			return racer;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    // --- parsing from CSV file (the same file that is exported to WEB ===========================================================
}
