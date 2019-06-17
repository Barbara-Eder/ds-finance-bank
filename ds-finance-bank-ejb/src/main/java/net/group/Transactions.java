/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.group;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import finance.common.*;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.group.*;

/**
 *
 * @author floh
 */
@Entity
public class Transactions implements Serializable {
    //@Inject VariableDAO variableDAO;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionID;
    
    @Column(name="CompanyName")
    private String companyName;
    
    @Column(name="Kurz")
    private String symbol;
    
    @Column(name="TradedValue")
    private BigDecimal tradedPrice;
    
    @Column(name="Menge")
    private int menge;
    
    @Column(name="GesamtWert")
    private BigDecimal gesamtPreis;

    @Column(name="MonatsDurchschnitt")
    private BigDecimal monatsDurchschnitt;

    @ManyToOne
    @JoinColumn(name="UserID_FK")
    private User user;
    
    
    public Transactions()  {}
    
    public Transactions(String companyName, BigDecimal gesamtPreis, int menge, String symbol, BigDecimal tradedPrice, User user, BigDecimal monatsDurchschnitt){
    
    
    this.companyName=companyName;
    this.symbol=symbol;
    this.tradedPrice=tradedPrice;
    //this.tradedPrice=1235.6;
    this.menge=menge;
    this.gesamtPreis=gesamtPreis;
 
    //this.user=variableDAO.findUserWithID(fk).get(0);
    
    this.monatsDurchschnitt = monatsDurchschnitt;
    this.user=user;
    }
    
    //User id Foreign Key
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    //CompanyName
    public String getCompanyName(){
    return companyName;
    }
    
    public void setCompanyName(String companyName){
    this.companyName= companyName;
    }
    
    //lastTradePrice
    public BigDecimal getTradedPrice(){
    return tradedPrice;
    }
    
    public void setTradedPrice(BigDecimal tradedPrice){
    this.tradedPrice= tradedPrice;
    }
    
    //TransaktionsID
    public int getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(int transactionID){
    this.transactionID=transactionID;
    }
    
    //Aktien Kürzel
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    //Menge
    public int getMenge() {
        return menge;
    }
    
    public void setMenge(int menge) {
        this.menge = menge;
    }
    
    
    //Gesamtpreis
    public BigDecimal getGesamtWert(){
    return gesamtPreis;
    }
    
    public void setGesamtPreis(BigDecimal gesamtPreis){
    this.gesamtPreis= gesamtPreis;
    }

    public BigDecimal getMonatsDurchschnitt() {
        return monatsDurchschnitt;
    }

    public void setMonatsDurchschnitt(BigDecimal monatsDurchschnitt) {
        this.monatsDurchschnitt = monatsDurchschnitt;
    }
    

   /* @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.group.Transactions[ id=" + id + " ]";
    }
    */
}
