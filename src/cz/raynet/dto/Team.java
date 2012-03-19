/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.dto;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class Team {

    private int fId;
    private String fName;
    private int fRacersCount;
    private int fSeason;
    
    public Team() {
        
    }

    public int getId() {
        return fId;
    }

    public void setId(int id) {
        fId = id;
    }

    public String getName() {
        return fName;
    }

    public void setName(String name) {
        fName = name;
    }

    public int getRacersCount() {
        return fRacersCount;
    }

    public void setRacersCount(int racersCount) {
        fRacersCount = racersCount;
    }

    public int getSeason() {
        return fSeason;
    }

    public void setSeason(int season) {
        fSeason = season;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fName + ", " + fRacersCount + ", " + fSeason; 
    }
}
