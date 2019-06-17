/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author Baris
 */
public class VariableDAO {
    @PersistenceContext
    private EntityManager entityManager;

    //Volumen setzen
    public int createVolumen(BigDecimal gesamtPreis) {
        Volumen volumen = new Volumen(gesamtPreis);
        entityManager.persist(volumen);
        return volumen.getId();
    }

    //Volumen dazugeben
    public void addVolumen(int bankId, BigDecimal gesamtPreis) {
        entityManager.createQuery("UPDATE Volumen t SET t.gesamt =  t.gesamt + :gesamtPreis WHERE t.id= :bankId")
                .setParameter("bankId", bankId)
                .setParameter("gesamtPreis", gesamtPreis)
                .executeUpdate();
    }

    //Depot des Kunden updaten
    public void updaten_Depot(int menge, User user, BigDecimal gesamtPreis_Trade, int transactionid) {
        entityManager.createQuery("UPDATE Transactions t SET t.gesamtPreis = t.gesamtPreis - :gesamtPreis_Trade, t.menge=t.menge - :menge WHERE t.user=:user AND t.transactionID=:transactionid").setParameter("gesamtPreis_Trade", gesamtPreis_Trade).setParameter("menge", menge).setParameter("user", user).setParameter("transactionid", transactionid).executeUpdate();
    }

    //Lösch Depot Eintrag wenn Menge 0
    public void deleteDepot(int transactionid) {
        entityManager.createQuery("DELETE FROM Transactions t WHERE t.transactionID=:transactionid").setParameter("transactionid", transactionid).executeUpdate();
    }

    //Volumen subtrahieren
    public void minusVolumen(int bankId, BigDecimal gesamtPreis) {
        entityManager.createQuery("UPDATE Volumen t SET t.gesamt =  t.gesamt - :gesamtPreis WHERE t.id= :bankId")
                .setParameter("gesamtPreis", gesamtPreis)
                .setParameter("bankId", bankId)
                .executeUpdate();
    }

    //Volumen entnehmen
    public List<Volumen> findVolumen(int id) {
        //return entityManager.
        return entityManager.createQuery("Select t FROM Volumen t WHERE t.id = :id", Volumen.class)
                .setParameter("id", id)
                .getResultList();

    }


    public User findUser(int userid) {
        return entityManager.find(User.class, userid);
    }

    //Kunde anlegen
    public int persistUser(User variable) {
        entityManager.persist(variable);
        return variable.getUserId();
    }

    public void deleteUser(User variable) {
        entityManager.remove(variable);
    }

    //Transaktion anlegen
    public void persistTransaktion(Transactions variable) {
        entityManager.persist(variable);
    }

    //Query
    //Kunde suchen mit Nachname
    public List<User> findUserWithName(String nachname) {
        return entityManager.createQuery("Select u FROM User u WHERE u.nachname = :nachname and u.rolle='Kunde'", User.class).setParameter("nachname", nachname).getResultList();
        //return entityManager.createQuery("Select u FROM User u WHERE u.nachname = :nachname").setParameter("nachname", nachname).getResultList();
    }

    //Depot des Kunden anzeigen
    //Select t FROM Transaction t WHERE t.user = :user
    public List<Transactions> findDepotWithID(User user) {
        return entityManager.createQuery("Select t FROM Transactions t WHERE t.user = :user", Transactions.class).setParameter("user", user).getResultList();
    }

    public List<Transactions> getGesamt(User user) {
        return entityManager.createQuery("Select t FROM Transactions t WHERE t.user = :user", Transactions.class).setParameter("user", user).getResultList();
    }

    //Kunde suchen mit Nachname Objekt
    public User findUserWithIDObject(int fk) {
        return entityManager.createQuery("Select u FROM User u WHERE u.userid = :userid", User.class).setParameter("userid", fk).getSingleResult();
        //return entityManager.createQuery("Select u FROM User u WHERE u.nachname = :nachname").setParameter("nachname", nachname).getResultList();
    }

    //Kunde suchen mit ID
    public List<User> findUserWithID(int userid) {
        return entityManager.createQuery("Select u FROM User u WHERE u.userid = :userid", User.class).setParameter("userid", userid).getResultList();
        //return entityManager.createQuery("Select u FROM User u WHERE u.nachname = :nachname").setParameter("nachname", nachname).getResultList();
    }
}
