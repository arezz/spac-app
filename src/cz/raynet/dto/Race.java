/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.dto;

import java.sql.Date;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class Race {

    private int fId;
    private String fName;
    private Date fDate;
    private String fPlace;
    private int fChampionchip;
    private int fSeason;
    private String fNotice;
    
    public Race() {
        
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

    public Date getDate() {
        return fDate;
    }

    public void setDate(Date date) {
        fDate = date;
    }

    public String getPlace() {
        return fPlace;
    }

    public void setPlace(String place) {
        fPlace = place;
    }

    public int getChampionchip() {
        return fChampionchip;
    }

    public void setChampionchip(int championchip) {
        fChampionchip = championchip;
    }

    public int getSeason() {
        return fSeason;
    }

    public void setSeason(int season) {
        fSeason = season;
    }

    public String getNotice() {
        return fNotice;
    }

    public void setNotice(String notice) {
        fNotice = notice;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fName + ", " + fDate.toString() + ", " + fPlace + ", " + fChampionchip + ", " + fSeason + ", " + fNotice; 
    }
}
