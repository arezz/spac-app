/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.utils;

/**
 * @author arezz
 * created 12.5.2010
 *
 */
public class Utils {

    /**
     * Counts milliseconds from time in this format: HH,MM,SS
     * @param input time in format HH,MM,SS
     * @return time in millis
     */
    public static int getTimeFromCommaString(String input) {
        int time = 0;
        
        // time can be also DNF, DNS, DSQ or DSF - then return 0;
        if (input == null || input.equalsIgnoreCase("DNF") ||
             input.equalsIgnoreCase("DNS") ||
             input.equalsIgnoreCase("DSQ") ||
             input.equalsIgnoreCase("DSF")) {
                return 0;
            }
        
        // time can be also -1, -kolo, -1 kolo, po limitu (if starts with MINUS) - return -1
        if (input.startsWith("-")
            || input.equalsIgnoreCase("po limitu")) {
            return -1;
        }
        
        String [] items = input.split(",");
        try {
            int hours = 0;      // = 3 600 000ms
            int minutes = 0;    // =    60 000ms
            int seconds = 0;    // =     1 000ms
            if (items.length == 2) {
                minutes = Integer.parseInt(items[0]);
                seconds = Integer.parseInt(items[1]);
            } else if (items.length == 3) {
                hours = Integer.parseInt(items[0]);
                minutes = Integer.parseInt(items[1]);
                seconds = Integer.parseInt(items[2]);
            }
            time = (hours * 3600000) + (minutes * 60000) + (seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return time;
    }
    
    /**
     * Counts milliseconds from time in this format: MM,SS,ms
     * @param input time in format MM,SS,ms
     * @return time in millis
     */
    public static int getTimeFromCommaStringAlsoMillis(String input) {
        int time = 0;
        
        // time can be also DNF, DNS, DSQ or DSF - then return 0;
        if (input == null || input.equalsIgnoreCase("DNF") ||
             input.equalsIgnoreCase("DNS") ||
             input.equalsIgnoreCase("DSQ") ||
             input.equalsIgnoreCase("DSF")) {
                return 0;
            }
        
        // time can be also -1, -kolo, -1 kolo, po limitu (if starts with MINUS) - return -1
        if (input.startsWith("-")
            || input.equalsIgnoreCase("po limitu")) {
            return -1;
        }
        
        String [] items = input.split(",");
        try {
            int minutes = 0;    // =    60 000ms
            int seconds = 0;    // =     1 000ms
            int ms = 0;         // =        10ms
            if (items.length == 3) {
                minutes = Integer.parseInt(items[0]);
                seconds = Integer.parseInt(items[1]);
                ms = Integer.parseInt(items[2]);
            }
            time = (minutes * 60000) + (seconds * 1000) + (ms * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return time;
    }
}
