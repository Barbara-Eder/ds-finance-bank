/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.common;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Baris
 */
public class FindStockQuotesDTO implements Serializable {
    private String CompanyName;
    private Long floatShares;
    private BigDecimal lastTradePrice;
    //@XmlSchemaType(name = "dateTime")
    private XMLGregorianCalendar lastTradeTime;
    private Long marketCapitalization;
    private String stockExchange;
    private String symbol;

    public Long getFloatShares(){
    return floatShares;
    }
    
    public void setFloatShares(Long floatShares){
    this.floatShares=floatShares;
    }
    
    public BigDecimal getLastTradePrice(){
    return lastTradePrice;
    }
    
    public void setLastTradePrice(BigDecimal lastTradePrice){
    this.lastTradePrice=lastTradePrice;
    }
    
    public XMLGregorianCalendar getLastTradeTime(){
    return lastTradeTime;
    }
    
    public void setLastTradeTime(XMLGregorianCalendar lastTradeTime){
    this.lastTradeTime=lastTradeTime;
    }
    
    public Long getMarketCapitalization(){
    return marketCapitalization;
    }
    
    public void setMarketCapitalization(Long marketCapitalization){
    this.marketCapitalization=marketCapitalization;
    }
    
    public String getStockExchange(){
    return stockExchange;
    }
    
    public void setStockExchange(String stockExchange){
    this.stockExchange=stockExchange;
    }
    
    public String getSymbol(){
    return symbol;
    }
    
    public void setSymbol(String symbol){
    this.symbol=symbol;
    }
    /**
     * Ruft den Wert der partOfCompanyName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * Legt den Wert der partOfCompanyName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyName(String value) {
        this.CompanyName = value;
    }
    
}
