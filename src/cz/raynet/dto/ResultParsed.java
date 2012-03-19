/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.dto;

/**
 * @author arezz
 * created 12.5.2010
 * Parsed line should ALLWAYS be in order below
 * 
 */
public class ResultParsed {
    
    private int fStanding;
    private int fStartNumber;
    private int fYearBorn;
    private String fSurname;
    private String fFirstName;
    private String fTeamName;
    private String fCategory;
    private int fTotalTime;
    private double fRaceLength;
    private int fPoints;
    private boolean fSpacLicence;
    private int fIdRacer;
    
    public ResultParsed() {
        
    }

    public int getStanding() {
        return fStanding;
    }

    public void setStanding(int standing) {
        fStanding = standing;
    }

    public int getStartNumber() {
        return fStartNumber;
    }

    public void setStartNumber(int startNumber) {
        fStartNumber = startNumber;
    }

    public int getYearBorn() {
        return fYearBorn;
    }

    public void setYearBorn(int yearBorn) {
        fYearBorn = yearBorn;
    }

    public String getSurname() {
        return fSurname;
    }

    public void setSurname(String surname) {
        fSurname = surname;
    }

    public String getFirstName() {
        return fFirstName;
    }

    public void setFirstName(String firstName) {
        fFirstName = firstName;
    }

    public String getTeamName() {
        return fTeamName;
    }

    public void setTeamName(String teamName) {
        fTeamName = teamName;
    }

    public String getCategory() {
        return fCategory;
    }

    public void setCategory(String category) {
        fCategory = category;
    }

    public int getTotalTime() {
        return fTotalTime;
    }

    public void setTotalTime(int totalTime) {
        fTotalTime = totalTime;
    }

    public double getRaceLength() {
        return fRaceLength;
    }

    public void setRaceLength(double raceLength) {
        fRaceLength = raceLength;
    }

    public int getPoints() {
        return fPoints;
    }

    public void setPoints(int points) {
        fPoints = points;
    }

    public boolean isSpacLicence() {
        return fSpacLicence;
    }

    public void setSpacLicence(boolean spacLicence) {
        fSpacLicence = spacLicence;
    }

    public int getIdRacer() {
        return fIdRacer;
    }

    public void setIdRacer(int idRacer) {
        fIdRacer = idRacer;
    }

}
