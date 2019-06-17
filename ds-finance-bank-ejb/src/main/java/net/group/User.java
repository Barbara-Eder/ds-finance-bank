/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.group;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
//import javax.validation.constraints.Size;

/**
 *
 * @author floh
 */
@Entity
@Table(name="PERSONEN")
public class User implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@ManyToOne 
    //@JoinColumn(name="userid_fk")
    //private Long userid;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userid;
    
    //@Size(min=3)
    @Column(name="Rolle")
    private String rolle;
    
    @Column(name="Vorname")
    private String vorname;
    
    @Column(name="Nachname")
    private String nachname;
    
    @Column(name="Adresse")
    private String adresse;
    
    @OneToMany(mappedBy="user")
    private List<Transactions> transactions;
    
    public User()  {}
    
    public User(String rolle, String vorname, String nachname, String adresse){
    //this.userid=userid;
    this.rolle=rolle;
    this.vorname=vorname;
    this.nachname=nachname;
    this.adresse=adresse;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
    }
    
    //Rolle
    public String getRolle() {
        return rolle;
    }

    public void setRolle (String rolle) {
        this.rolle = rolle;
    }
    
    //Vornmae
    public String getVorname() {
        return vorname;
    }

    public void setVorname (String vorname) {
        this.vorname = vorname;
    }
    
    
    //Nachname
    public String getNachname() {
        return nachname;
    }

    public void setNachname (String nachname) {
        this.nachname = nachname;
    }
    
    //Adresse
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse (String adresse) {
        this.adresse = adresse;
    }
    
/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }*/
/*
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.group.User[ id=" + userid + " ]";
    }*/
    
}
