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
public class TransactionsDTO implements Serializable{
    private int transactionID;
    private String companyName;
    private String symbol;
    private BigDecimal tradedPrice;
    private int menge;
    private BigDecimal gesamtPreis;
    private BigDecimal monatsDurchschnitt;
    //private User user;
    
    //User id Foreign Key
   /* public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    */
    
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
}
