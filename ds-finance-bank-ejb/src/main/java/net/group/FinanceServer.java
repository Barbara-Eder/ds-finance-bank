/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.group;

import finance.common.Finance;
import finance.common.FinanceException;
import finance.common.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.xml.ws.BindingProvider;
import net.froihofer.dsfinance.ws.trading.*;
import net.froihofer.dsfinance.ws.trading.TradingWSException_Exception;
import net.froihofer.dsfinance.ws.trading.TradingWebService;
import net.froihofer.dsfinance.ws.trading.TradingWebServiceService;

/**
 *
 * @author Baris
 */
@Stateless(name = "FinanceService")
@PermitAll
public class FinanceServer implements Finance {

    static final BigDecimal MAX_BANK_VOLUME = new BigDecimal("1000000000");

    private final TradingWebService tradingWebService;

    @Inject
    VariableDAO variableDAO;

    public FinanceServer() {
        tradingWebService = getTradingService();
    }

    @Override
    public int testen() {
        throw new UnsupportedOperationException("blablabla."); //To change body of generated methods, choose Tools | Templates.
    }

    private BigDecimal get30DayAverage(List<PublicStockQuote> history) {
        // With version 8 Java included much nicer Date and Time APIs but since
        // we only get GregorianCalendarXML from the SOAP endpoints, we are stuck
        // with the yucky legacy Date APIs.

        // We create a date object from 30 days ago for later comparisons
        Date today = new Date();
        Calendar thirtyDaysAgo = new GregorianCalendar();
        thirtyDaysAgo.setTime(today);
        thirtyDaysAgo.add(Calendar.DAY_OF_MONTH, -30);

        // We take the quote history and make a stream...
        return history.stream()
                // ...from which we filter out all quotes that are older than 30 days...
                .filter(quote -> quote.getLastTradeTime().toGregorianCalendar().compareTo(thirtyDaysAgo) >= 0)
                // ...then we extract just the trade price, since we don't care about other properties...
                .map(PublicStockQuote::getLastTradePrice)
                // ...and then we 'reduce' it to produce an average by adding each value and diving the new average by 2
                .reduce(BigDecimal.ZERO, (avg, price) -> avg.add(price).divide(BigDecimal.valueOf(2)));
                // ...this results in just one value, the average over all trades prices within the last 30 days
    }

    @Override
    public void buyStock(String companyName, int totalNumber, String symbol, int userId, int bankId) throws FinanceException {
        // Check for maximum number of shares per buy, throw error otherwise
        if(totalNumber > 100000) {
            throw new FinanceException("Es dürfen maximal 100 000 Stück gekauft werden");
        }

        // Check if user exists, throw error otherwise
        List<UserDTO> userResult = this.findUsersID(userId);
        if (userResult.isEmpty()) {
            throw new FinanceException("Kein Benutzer gefunden für Id: " + userId);
        }

        // Check if bank has enough money for this transaction
        BigDecimal preisPerAktie = this.buyStock(symbol, totalNumber);
        BigDecimal gesamtPreis = preisPerAktie.multiply(new BigDecimal(totalNumber));

        BigDecimal verbleibendesVolumen = MAX_BANK_VOLUME.subtract(variableDAO.findVolumen(bankId).get(0).getGesamt());

        if (verbleibendesVolumen.compareTo(gesamtPreis) < 1) {
            throw new FinanceException("Transaktion würde Bankvolumen überschreiten");
        }

        // We initialize the 30 day average to -1 to show it is missing
        BigDecimal quote30DaysAverage = BigDecimal.valueOf(-1);
        try {
            // And then we try to calculate the average price from the quote history
            List<PublicStockQuote> history = this.tradingWebService.getStockQuoteHistory(symbol);
            quote30DaysAverage = get30DayAverage(history);
        } catch (TradingWSException_Exception e) {
            e.printStackTrace();
        }

        // If all checks passed, we create a transaction and persist the new volume
        this.createTransaction(companyName, gesamtPreis, totalNumber, symbol, preisPerAktie, userId, quote30DaysAverage);
        this.plusVolumen(bankId, gesamtPreis);
    }

    //Kunde anlegen
    @Override
    public int createUser(String rolle, String vorname, String nachname, String adresse) throws FinanceException {
        try {
            return variableDAO.persistUser(new User(rolle, vorname, nachname, adresse));
        } catch (Exception e) {
            throw new FinanceException(e);
        }
    }

    //Transaktion anlegen
    @Override
    public void createTransaction(String companyName, BigDecimal gesamtPreis, int menge, String symbol, BigDecimal tradedPrice, int fk, BigDecimal monthAverage) throws FinanceException {
        try {
            User users = variableDAO.findUserWithIDObject(fk);

            variableDAO.persistTransaktion(new Transactions(companyName, gesamtPreis, menge, symbol, tradedPrice, users, monthAverage ));
        } catch (Exception e) {
            throw new FinanceException(e);
        }
    }

    //Depot des Kunden anzeigen
    @Override
    public List<TransactionsDTO> findDepotID(int userid) throws FinanceException {
        User users = variableDAO.findUserWithIDObject(userid);

        if (users == null) {
            throw new FinanceException("Kein Benutzer gefunden für Id: " + userid);
        }

        List<Transactions> transas = variableDAO.findDepotWithID(users);
        List<TransactionsDTO> result = new ArrayList<>();
        for (Transactions t : transas) {
            TransactionsDTO trans = new TransactionsDTO();
            trans.setCompanyName(t.getCompanyName());
            trans.setSymbol(t.getSymbol());
            trans.setTradedPrice(t.getTradedPrice());
            trans.setMenge(t.getMenge());
            trans.setGesamtPreis(t.getGesamtWert());
            trans.setTransactionID(t.getTransactionID());
            trans.setMonatsDurchschnitt(t.getMonatsDurchschnitt());
            result.add(trans);
        }
        return result;
    }

    @Override
    public BigDecimal getGesamtDepot(int userid) throws FinanceException{
        User users = variableDAO.findUserWithIDObject(userid);

        if (users == null) {
            throw new FinanceException("Kein Benutzer gefunden für Id: " + userid);
        }
        
        List<Transactions> transas = variableDAO.getGesamt(users);
        //List<TransactionsDTO> result = new ArrayList<>();
        BigDecimal gg=BigDecimal.ZERO;
        //BigDecimal temp=new BigDecimal("0");
        for (Transactions t : transas) {
            //TransactionsDTO trans = new TransactionsDTO();
            //trans.setGesamtPreis(t.getGesamtWert());
            if(null!=t.getGesamtWert())
            {
            gg=gg.add(t.getGesamtWert());
            }
            //gg=gg.add(t.getGesamtWert());
        }
        return gg;
        
    }
    
    @Override
    public String getUser(int userid) throws FinanceException {
        try {
            return variableDAO.findUser(userid).getVorname();
        } catch (Exception e) {
            throw new FinanceException(e);
        }

    }

    //Kunde suchen mit ID
    @Override
    public List<UserDTO> findUsersID(int userid) throws FinanceException {
        List<User> users = variableDAO.findUserWithID(userid);
        List<UserDTO> result = new ArrayList<>();
        for (User u : users) {
            UserDTO usr = new UserDTO();
            usr.setUserId(u.getUserId());
            usr.setAdresse(u.getAdresse());
            usr.setNachname(u.getNachname());
            usr.setRolle(u.getRolle());
            usr.setVorname(u.getVorname());
            result.add(usr);
        }
        return result;

    }

    @Override
    public int createVolumen(BigDecimal verbraucht) throws FinanceException {
        try {
            return this.variableDAO.createVolumen(verbraucht);
        } catch (Exception e) {
            throw new FinanceException(e);
        }
    }

    //Verbrauchtes Volumen bekommen
    @Override
    public List<VolumenDTO> getVerbraucht(int id) throws FinanceException {
        List<Volumen> volumen = variableDAO.findVolumen(id);
        List<VolumenDTO> result = new ArrayList<>();
        for (Volumen t : volumen) {
            VolumenDTO vol = new VolumenDTO();
            vol.setId(t.getId());
            vol.setGesamt(t.getGesamt());
            result.add(vol);
        }
        return result;
    }

    //Volumen dazu addieren
    @Override
    public void plusVolumen(int bankId, BigDecimal gesamtPreis) throws FinanceException {
        try {
            variableDAO.addVolumen(bankId, gesamtPreis);
        } catch (Exception e) {
            throw new FinanceException(e);
        }
    }
    
    //Volumen subtrahieren
    @Override
    public void minusVolumen(int bankId, BigDecimal gesamtPreis) throws FinanceException{
    try {
            variableDAO.minusVolumen(bankId, gesamtPreis);
        } catch (Exception e) {
            throw new FinanceException(e);
        }
    }
    
    //Update Depot
    @Override
    public void updateDepot(int menge,int userid,BigDecimal gesamtPreis_Trade, int transactionid,int menge_vorhanden) throws FinanceException{
    try {
            User users = variableDAO.findUserWithIDObject(userid);
        
            if(menge_vorhanden==menge)
            {
            variableDAO.deleteDepot(transactionid);
            }
            else
            {
            variableDAO.updaten_Depot(menge,users,gesamtPreis_Trade,transactionid);
            }
            } catch (Exception e) {
            throw new FinanceException(e);
        }
    }

    //Kunde suchen mit Nachname
    @Override
    public List<UserDTO> findUsers(String nachname) throws FinanceException {
        List<User> users = variableDAO.findUserWithName(nachname);
        List<UserDTO> result = new ArrayList<>();
        for (User u : users) {
            UserDTO usr = new UserDTO();
            usr.setUserId(u.getUserId());
            usr.setAdresse(u.getAdresse());
            usr.setNachname(u.getNachname());
            usr.setRolle(u.getRolle());
            usr.setVorname(u.getVorname());
            result.add(usr);
        }
        return result;
    }

    //Aktien suchen
    @Override
    public List<FindStockQuotesDTO> findStockByCompName(String name) throws FinanceException {
        try {

            List<PublicStockQuote> details = this.tradingWebService.findStockQuotesByCompanyName(name);
            List<FindStockQuotesDTO> result = new ArrayList<>();

            for (PublicStockQuote u : details) {
                FindStockQuotesDTO temp = new FindStockQuotesDTO();
                temp.setCompanyName(u.getCompanyName());
                temp.setFloatShares(u.getFloatShares());
                temp.setLastTradePrice(u.getLastTradePrice());
                //temp.setLastTradeTime(u.getLastTradeTime());
                temp.setMarketCapitalization(u.getMarketCapitalization());
                temp.setStockExchange(u.getStockExchange());
                temp.setSymbol(u.getSymbol());
                result.add(temp);
            }
            return result;
        } catch (TradingWSException_Exception e) {
            e.printStackTrace();
            throw new FinanceException(e.getMessage());
        }
    }

    //Aktie kaufen
    @Override
    public BigDecimal buyStock(String symbol, int shares) throws FinanceException {
        try {
            return this.tradingWebService.buy(symbol, shares);
        } catch (TradingWSException_Exception ex) {
            ex.printStackTrace();
            throw new FinanceException(ex.getMessage());
        }
    }
    
    //Aktie verkaufen
    @Override
    public BigDecimal sellstock(String symbol, int shares) throws FinanceException{
     try {
            return this.tradingWebService.sell(symbol, shares);
        } catch (TradingWSException_Exception ex) {
            ex.printStackTrace();
            throw new FinanceException(ex.getMessage());
        }
    }

    private TradingWebService getTradingService() {
        try {
            TradingWebServiceService helloSvc = new TradingWebServiceService(new URL("http://edu.dedisys.org/ds-finance/ws/TradingService?wsdl"));
            TradingWebService proxy = helloSvc.getTradingWebServicePort();
            BindingProvider bindingProvider = (BindingProvider) proxy;
            //bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8080/hello-world/services/TradingService"); 
            bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "bic4b19_04");
            bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "aeK0koo7a");
            return proxy;
        } catch (MalformedURLException e) {
            //log.error("Something did not work, see stack trace. ... MalformedURLException", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            //log.error("Something did not work, see stack trace.... Exception", e);
            throw new RuntimeException(e);
        }
    }

}
