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
public class Result {

    private int fId;
    private int fIdRace;
    private int fIdRacer;
    private int fPlace;
    private double fPoints;
    private int fFinished;
    private double fLength;
    private int fTotalTime; // max 10hours = 36 000 000ms
    
    public Result() {
        
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

    public int getIdRacer() {
        return fIdRacer;
    }

    public void setIdRacer(int idRacer) {
        fIdRacer = idRacer;
    }

    public int getPlace() {
        return fPlace;
    }

    public void setPlace(int place) {
        fPlace = place;
    }

    public double getPoints() {
        return fPoints;
    }

    public void setPoints(double points) {
        fPoints = points;
    }

    public int getFinished() {
        return fFinished;
    }

    public void setFinished(int finished) {
        fFinished = finished;
    }

    public double getLength() {
        return fLength;
    }

    public void setLength(double length) {
        fLength = length;
    }

    public int getTotalTime() {
        return fTotalTime;
    }

    public void setTotalTime(int totalTime) {
        fTotalTime = totalTime;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fIdRace + ", " + fIdRacer + ", " + fPlace + ", " + fPoints + ", " + fFinished + ", " + fLength + ", " + fTotalTime; 
    }
    
}
