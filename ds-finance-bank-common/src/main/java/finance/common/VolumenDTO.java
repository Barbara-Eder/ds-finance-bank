/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Baris
 */
public class VolumenDTO implements Serializable{
    private int id;
    private BigDecimal gesamt;
    
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
}
