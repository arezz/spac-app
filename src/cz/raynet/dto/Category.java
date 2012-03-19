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
public class Category {
    
    private int fId;
    private String fPrefix;
    private String fName;
    private double fCoefficient;
    
    public Category() {
        
    }

    public int getId() {
        return fId;
    }

    public void setId(int id) {
        fId = id;
    }

    public String getPrefix() {
        return fPrefix;
    }

    public void setPrefix(String prefix) {
        fPrefix = prefix;
    }

    public String getName() {
        return fName;
    }

    public void setName(String name) {
        fName = name;
    }

    public double getCoefficient() {
        return fCoefficient;
    }

    public void setCoefficient(double coefficient) {
        fCoefficient = coefficient;
    }
    
    @Override
    public String toString() {
        return fId + ", " + fPrefix + ", " + fName + ", " + fCoefficient; 
    }

}
