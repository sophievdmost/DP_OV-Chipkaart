package domain;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchimps" ,"postgres", "0611");
            ReizigerDAOPsql dao = new ReizigerDAOPsql(myConn);

            testReizigerDAO(dao);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
        }

    }
    /// * P2. Reiziger DAO: persistentie van een klasse
    ///  *
    ///  * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
    ///  *
    ///  * @throws SQLException

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        try{
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");}
        catch(Exception e){
            e.printStackTrace();
        }

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}
