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
public class ResultTeam {

    private int fId;
    private int fIdRace;
    private int fIdTeam;
    private double fPoints;
    
    public ResultTeam() {
        
    }

    public int getId() {
        return fId;
    }

    public void setId(int id) {
        fId = id;
    }

    public int getIdRace() {
        return fIdRace;
    }

    public void setIdRace(int idRace) {
        fIdRace = idRace;
    }

    public int getIdTeam() {
        return fIdTeam;
    }

    public void setIdTeam(int idTeam) {
        fIdTeam = idTeam;
    }

    public double getPoints() {
        return fPoints;
    }

    public void setPoints(double points) {
        fPoints = points;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fIdRace + ", " + fIdTeam + ", " + fPoints; 
    }
}
