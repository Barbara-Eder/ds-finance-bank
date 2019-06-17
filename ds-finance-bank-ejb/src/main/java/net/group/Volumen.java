/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.group;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author Baris
 */
@Entity
@Table(name="Volumen")
public class Volumen implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="gesamt")
    private BigDecimal gesamt;
    

    public Volumen()  {}
    
    public Volumen(BigDecimal gesamt){
    this.gesamt=gesamt;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setGesamt(BigDecimal gesamt){
    this.gesamt=gesamt;
    }
    
    public BigDecimal getGesamt(){
    return gesamt;
    }
/*
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Volumen)) {
            return false;
        }
        Volumen other = (Volumen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.group.Volumen[ id=" + id + " ]";
    }
    */
}
