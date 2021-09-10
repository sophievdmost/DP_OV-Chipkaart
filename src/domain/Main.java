package domain;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchimps" ,"postgres", "0611");
            ReizigerDAOPsql dao = new ReizigerDAOPsql(myConn);
            AdresDAOsql dao2 = new AdresDAOsql(myConn);

            testAdresDAO(dao2);
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
        System.out.println(reizigers.size() + " reizigers\n");

        //update
            reizigers =  rdao.findAll();
            System.out.println("[Test] update van reiziger " );
            Reiziger test = new Reiziger(77, "p", "", "Boers",java.sql.Date.valueOf(gbdatum));
        rdao.update(test);
            System.out.println(sietske.getVoorletters() + " naamtest");
        reizigers =rdao.findAll();
        System.out.println("naam is nu " + test.getVoorletters());


        //findByid test
            System.out.println("[Test] vinden bij ID");
            System.out.println("Het id is:" + sietske.getId());
            rdao.findByID(77);
        //findByGB test

            System.out.println("[test] vinden bij geboortedatum");
            System.out.println("We gebruiken deze geboorte datum:" + sietske.getGeboortedatum());
            rdao.findByGbdatum("1981-03-14");


        //delete test

            System.out.println("[Test] delete de nieuwe " + test.getId());
            rdao.delete(test);
            reizigers = rdao.findAll();
            System.out.println("Er zijn nu" + reizigers.size()+ " reizigers");
        }


        catch(Exception e){
            e.printStackTrace();
        }


    }

    private static void testAdresDAO(AdresDAO adao){
        try {
            System.out.println("\n---------- Test AdresDAO -------------");

            // Haal alle reizigers op uit de database
            List<Adres> adressen = adao.findAll();
            System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
            for (Adres a : adressen) {
                System.out.println(a);
            }
            System.out.println();

            // Maak een nieuwe adres aan en persisteer deze in de database
            Adres siet = new Adres("s");
            ///System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            //rdao.save(sietske);
            //reizigers = rdao.findAll();
            // System.out.println(reizigers.size() + " reizigers\n");

        }catch(Exception e){
        e.printStackTrace();}
    }}
