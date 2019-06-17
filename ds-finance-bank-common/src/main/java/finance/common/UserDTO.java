/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.common;

import java.io.Serializable;

/**
 *
 * @author Baris
 */
public class UserDTO implements Serializable {
    private int userId;
    private String rolle;
    private String vorname;
    private String nachname;
    private String adresse;

    public String getRolle() {
    return rolle;
    }
    
    public void setRolle(String rolle){
    this.rolle=rolle;
    }
    
    public String getVorname() {
    return vorname;
    }
    
    public void setVorname(String vorname){
    this.vorname=vorname;
    }
    
    public String getNachname() {
    return nachname;
    }
    
    public void setNachname(String nachname){
    this.nachname=nachname;
    }
    
    public String getAdresse() {
    return adresse;
    }
    
    public void setAdresse(String adresse){
    this.adresse=adresse;
    }
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
