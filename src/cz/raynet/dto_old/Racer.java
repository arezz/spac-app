/*
 * Copyright 2010 RAYNET s.r.o., All rights reserved.
 * RAYNET s.r.o. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * www.raynet.cz
 */
package cz.raynet.dto_old;

/**
 * @author arezz
 * created 5.5.2010
 *
 */
public class Racer {

    private int fId;
    private int fIdCategory;
    private int fIdTeam;
    private String fFirstName;
    private String fSurname;
    private int fYearBorn;
    private int fSpacNumber;
    private int fSeason;
    private String fTeamName;
    
    public Racer() {
        
    }

    public int getId() {
        return fId;
    }

    public void setId(int id) {
        fId = id;
    }

    public int getIdCategory() {
        return fIdCategory;
    }

    public void setIdCategory(int idCategory) {
        fIdCategory = idCategory;
    }

    public int getIdTeam() {
        return fIdTeam;
    }

    public void setIdTeam(int idTeam) {
        fIdTeam = idTeam;
    }

    public String getFirstName() {
        return fFirstName;
    }

    public void setFirstName(String firstName) {
        fFirstName = firstName;
    }

    public String getSurname() {
        return fSurname;
    }

    public void setSurname(String surname) {
        fSurname = surname;
    }

    public int getYearBorn() {
        return fYearBorn;
    }

    public void setYearBorn(int yearBorn) {
        fYearBorn = yearBorn;
    }

    public int getSpacNumber() {
        return fSpacNumber;
    }

    public void setSpacNumber(int spacNumber) {
        fSpacNumber = spacNumber;
    }

    public int getSeason() {
        return fSeason;
    }

    public void setSeason(int season) {
        fSeason = season;
    }

    public String getTeamName() {
        return fTeamName;
    }

    public void setTeamName(String teamName) {
        fTeamName = teamName;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fIdCategory + ", " + fIdTeam + ", " + fFirstName + ", " + fSurname + ", " + fYearBorn + ", " + fSpacNumber + ", " + fSeason + ", " + fTeamName; 
    }
}
