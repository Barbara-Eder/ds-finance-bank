/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance.common;

//import net.froihofer.dsfinance.ws.trading.TradingWebService;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Baris
 */
@javax.ejb.Remote
public interface Finance {

    /*
     * Interface methods are public by default, so no need to repeat `public` everywhere.
     */

    //Kunde ANLEGEN
    int createUser(String rolle, String vorname, String nachname, String adresse) throws FinanceException;

    //KUNDE SUCHEN MITTELS USERID -- OLD
    String getUser(int userid) throws FinanceException;

    //Kunde suchen mit Nachname
    List<UserDTO> findUsers(String nachname) throws FinanceException;

    //Depot des Kunden anzeigen
    List<TransactionsDTO> findDepotID(int userid) throws FinanceException;

    //Kunde suchen mit Kunden ID
    List<UserDTO> findUsersID(int userid) throws FinanceException;

    int createVolumen(BigDecimal verbraucht) throws FinanceException;

    //Verbrauchte Volumen entnehmen
    List<VolumenDTO> getVerbraucht(int id) throws FinanceException;

    //Gesamtwert des Kunden im Depot
    BigDecimal getGesamtDepot(int userid) throws FinanceException;

    //Volumen dazugeben
    void plusVolumen(int bankId, BigDecimal gesamtPreis) throws FinanceException;

    //Volumen subtrahieren
    void minusVolumen(int bankId, BigDecimal gesamtPreis) throws FinanceException;

    //Depot updaten
    void updateDepot(int menge, int userid, BigDecimal gesamtPreis_Trade, int transactionid, int menge_vorhanden) throws FinanceException;

    //SOAP
    List<FindStockQuotesDTO> findStockByCompName(String name) throws FinanceException;

    //Aktie kaufen
    BigDecimal buyStock(String symbol, int shares) throws FinanceException;

    //Aktie verkaufen
    BigDecimal sellstock(String symbol, int shares) throws FinanceException;

    //gekaufte Aktie in die Transaktionen Tabelle persistieren
    void createTransaction(String companyName, BigDecimal gesamtPreis, int menge, String symbol, BigDecimal tradedPrice, int fk, BigDecimal monthAverage) throws FinanceException;

    public int testen();

    // This is the new method that allows a complete transaction to be processed by the server
    void buyStock(String companyName, int totalNumber, String symbol, int userId, int bankId) throws FinanceException;
}
