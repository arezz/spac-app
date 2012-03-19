/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.spac_old;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import cz.raynet.dto.Category;
import cz.raynet.dto_old.Racer;
import cz.raynet.dto_old.ResultParsed;
import cz.raynet.utils.Utils;

/**
 * @author arezz
 * created 12.5.2010
 *
 */
public class FileParserOld implements IFileParserOld {

    private ISpacDataManagerOld fDataManager = SpacDataManagerOld.getInstance();
    
    private static final IFileParserOld INSTANCE = new FileParserOld();

    public static IFileParserOld getInstance() {
        return FileParserOld.INSTANCE;
    }
    
    public void parseRacersFromFile(String fileName) {
        Map<String,Category> categories = fDataManager.getCategoriesOld();
        try {
            FileInputStream fr = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fr, "windows-1250");
            BufferedReader in = new BufferedReader(isr);
            String str;
            while ((str = in.readLine()) != null) {
                Racer r = parseRacerFromLine(str, categories);
                if (r != null) {
                    fDataManager.insertRacerOld(r);
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

}
