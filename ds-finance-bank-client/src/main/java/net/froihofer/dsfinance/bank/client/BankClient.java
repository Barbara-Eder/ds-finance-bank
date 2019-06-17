package net.froihofer.dsfinance.bank.client;

import finance.common.Finance;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.JBoss7JndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {

    private static Logger log = LoggerFactory.getLogger(BankClient.class);

    public static Finance getFinanceService() {

        AuthCallbackHandler.setUsername("franz");
        AuthCallbackHandler.setPassword("Eins+zwei");
        Properties props = new Properties();
        props.put(Context.SECURITY_PRINCIPAL, AuthCallbackHandler.getUsername());
        props.put(Context.SECURITY_CREDENTIALS, AuthCallbackHandler.getPassword());

        try {
            JBoss7JndiLookupHelper jndiHelper = new JBoss7JndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
            return (Finance) jndiHelper.lookup("FinanceService", Finance.class);
            // Der JINDI Lookup liefert die Reference auf das implementierte Finance Service, welches die RMI Calls für uns (haben wir nicht programmiert) absestzt.
        } catch (NamingException e) {
            log.error("Failed to initialize InitialContext.", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Something did not work with something, see stack trace.", e);
            throw new RuntimeException(e);
        }
    }

   /* public static TradingWebService getTradingService() {
        try {
            TradingWebServiceService helloSvc = new TradingWebServiceService(new URL("http://edu.dedisys.org/ds-finance/ws/TradingService?wsdl"));
            TradingWebService proxy = helloSvc.getTradingWebServicePort();
            BindingProvider bindingProvider = (BindingProvider) proxy;
            //bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8080/hello-world/services/TradingService"); 
            bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "bic4b19_04");
            bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "aeK0koo7a");
            return proxy;
        } catch (MalformedURLException e) {
            log.error("Something did not work, see stack trace. ... MalformedURLException", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("Something did not work, see stack trace.... Exception", e);
            throw new RuntimeException(e);
        }
    }*/

    public static void main(String[] args) {

        Finance finance = getFinanceService();
        
        //TradingWebService trading = finance.getTradingService();
        
        Homescreen.start_GUI(finance); //, trading);

        /* 
        private static Logger log = LoggerFactory.getLogger(BankClient.class);
        AuthCallbackHandler.setUsername("franz");
    AuthCallbackHandler.setPassword("Eins+zwei");
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
         */
 /* //RMI
    try {
      JBoss7JndiLookupHelper jndiHelper = new JBoss7JndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
      Finance f = (Finance)jndiHelper.lookup("FinanceService", Finance.class);
      //f.testen();
      f.createUser("Mitarbeiter", "hgggggggief", "asas", "stssse");
      System.out.println("Got here");
      
      //System.out.println(f.getUser("Hans"));
    }catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }catch (FinanceException ce) {
      log.error("Calculator threw Exception: "+ce.getMessage());
    }catch (Exception e) {
      log.error("Something did not work with something, see stack trace.", e);
    }
         */
        

        /* //SOAP
        try {
            TradingWebServiceService helloSvc = new TradingWebServiceService(new URL("http://edu.dedisys.org/ds-finance/ws/TradingService?wsdl"));
            TradingWebService proxy = helloSvc.getTradingWebServicePort();
            BindingProvider bindingProvider = (BindingProvider) proxy;
            //bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,"http://localhost:8080/hello-world/services/TradingService"); 
            bindingProvider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "bic4b19_04");
            bindingProvider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "aeK0koo7a");
            System.out.println("The service says: " + proxy.findStockQuotesByCompanyName("Apple").get(0).getCompanyName());
        } catch (MalformedURLException e) {
            log.error("Something did not work, see stack trace. ... MalformedURLException", e);
        } catch (Exception e) {
            log.error("Something did not work, see stack trace.... Exception", e);
        }
         */
    }
}
