package domain;

import domain.DAO.AdresDAOsql;
import domain.DAO.OVChipkaartDAOsql;
import domain.DAO.ReizigerDAOPsql;
import domain.domein.Adres;
import domain.domein.OVChipkaart;
import domain.domein.Product;
import domain.domein.Reiziger;
import domain.interfaces.AdresDAO;
import domain.interfaces.OVChipkaartDAO;
import domain.DAO.ProductDAOsql;
import domain.interfaces.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchimps" ,"postgres", "0611");
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(myConn);
            AdresDAOsql adao = new AdresDAOsql(myConn, rdao);
            OVChipkaartDAOsql ovdao = new OVChipkaartDAOsql(myConn, rdao);

            rdao = new ReizigerDAOPsql(myConn, adao, ovdao);

            ProductDAOsql pdao = new ProductDAOsql(myConn, ovdao);

            testReizigerDAO(rdao);
            testAdresDAO(adao,rdao);
            testOVChipkaart(ovdao,rdao);
            testProductDAO(pdao, ovdao);
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
        List<domain.domein.Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (domain.domein.Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        domain.domein.Reiziger sietske = new domain.domein.Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        //update
            reizigers =  rdao.findAll();
            System.out.println("[Test] update van reiziger " );
            domain.domein.Reiziger test = new domain.domein.Reiziger(77, "p", "", "Boers",java.sql.Date.valueOf(gbdatum));
        rdao.update(test);
            System.out.println(sietske.getVoorletters() + " naamtest");
        reizigers =rdao.findAll();
        System.out.println("naam is nu: " + test.getVoorletters()+ "\n");


        //findByid test
            System.out.println("[Test] vinden bij ID");
            System.out.println("Het id is:" + sietske.getId());
            rdao.findByID(77);

        //findByGB test
            System.out.println( "\n" + "[test] vinden bij geboortedatum" );
            System.out.println("We gebruiken deze geboorte datum:" + sietske.getGeboortedatum());
            rdao.findByGbdatum("1981-03-14");


        //delete test
            System.out.println(  "\n"+ "[Test] delete de nieuwe reiziger om alles te resetten:  " + test.getId());
            System.out.println("Er zijn nu: " + reizigers.size()+ " reizigers" + "\n");
            rdao.delete(test);
            reizigers = rdao.findAll();
            System.out.println("Er zijn nu: " + reizigers.size()+ " reizigers" + "\n");
        }


        catch(Exception e){
            e.printStackTrace();
        }


    }

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao){
        try {
            System.out.println("\n---------- Test AdresDAO -------------");
            String gbdatum = "1981-03-14";
           domain.domein.Reiziger sietske = new domain.domein.Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
           rdao.save(sietske);
           Adres siet = new Adres(69, "3831VP", "1", "van heemstrastraat", "Driebergen", sietske);

            // Haal alle adressen op uit de database
            System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
            List<Adres> adressen = adao.findAll();
            for (Adres a : adressen) {
                System.out.println(a);
            }
            System.out.println();

            // Maak een nieuwe adres aan en persisteer deze in de database
            System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdressenDAO.save() ");
            adao.save(siet);
            adressen = adao.findAll();
            System.out.println(adressen.size() + " adressen\n");

            //update
            adressen =  adao.findAll();
            System.out.println("[Test] update van een adres " );
            System.out.println("Adres was: " + siet);
            Adres test = new Adres(69, "6969VP", "2", "van mopestraat", "Driebergen", sietske);
            adao.update(test);
            System.out.println("adres is nu: ");
            adao.findByReiziger(sietske);

            //find by reiziger
            System.out.println("\n" + "[Test] find by reiziger");
            System.out.println("We gebruiken deze reizger: " + sietske +"\n dus het adres is: ");
            adao.findByReiziger(sietske);

            //delete
            System.out.println("\n" + "[Test] delete adres");
            adressen = adao.findAll();
            System.out.println(adressen.size() + " adressen\n");
            adao.delete(test);
            adressen = adao.findAll();
            System.out.println(adressen.size() + " adressen\n");
            rdao.delete(sietske);

        }catch(Exception e){
        e.printStackTrace();}
    }

    public static void testOVChipkaart(OVChipkaartDAOsql ovdao, ReizigerDAOPsql rdao){
        try{
            System.out.println("\n---------- Test OVChipkaartDAO -------------");
            String gbdatum = "1981-03-14";
            String geldigdatum = "2025-11-07";
            domain.domein.Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
            rdao.save(sietske);
            OVChipkaart siets = new OVChipkaart(69, Date.valueOf(geldigdatum), 1, 25, sietske);
            List<OVChipkaart> ovchips = new ArrayList<>();
           ovchips.add(siets);

            //pak alle kaarten
            System.out.println("[Test] OVCHipkaartdao.findAll() geeft de volgende adressen:");
            List<OVChipkaart> ovchipkaarten = ovdao.findAll();
            for (OVChipkaart a : ovchipkaarten) {
                System.out.println(a);
            }

            // Maak een nieuwe kaart aan en persisteer deze in de database
            System.out.print("[Test] Eerst " + ovchipkaarten.size() + " adressen, na OVChipkaartenDAO.save() ");
            ovdao.save( ovchips);
            ovchipkaarten = ovdao.findAll();
            System.out.println(ovchipkaarten.size() + " kaarten\n");

            //update
            ovchipkaarten = ovdao.findAll();
            System.out.println("[Test] update van een kaart " );
            System.out.println("kaart was: " + siets);
            String testDatum = "2066-11-14";
            OVChipkaart test = new OVChipkaart(69, java.sql.Date.valueOf(testDatum), 2, 6,  sietske);
            List<OVChipkaart> testlist = new ArrayList<>();
            testlist.add(test);
            ovdao.update(testlist);
            System.out.println("adres is nu: ");
            ovdao.findbyReiziger(sietske);

            //find by reiziger
            System.out.println("\n" + "[Test] find by reiziger");
            System.out.println("We gebruiken deze reizger: " + sietske +"\n dus de kaart is: ");
            ovdao.findbyReiziger(sietske);

            //findByid test
            System.out.println("[Test] vinden bij ID");
            System.out.println("Het id is:" + siets.getKaart_nummer());
            ovdao.findByID(69);

            //delete
            System.out.println("\n" + "[Test] delete kaart");
            ovchipkaarten = ovdao.findAll();
            System.out.println(ovchipkaarten.size() + " kaarten\n");
            ovdao.delete( testlist);
            ovchipkaarten = ovdao.findAll();
            System.out.println(ovchipkaarten.size() + " kaarten\n");
            rdao.delete(sietske);


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void testProductDAO(ProductDAOsql pdao, OVChipkaartDAO ovdao){
        try{
            System.out.println("\n---------- Test ProductDAO -------------");

            Product biets = new Product(10, "gratis", "Gratis reizen voor iedereen die een studieschuld heeft", 69);

            //pak alle producten
            System.out.println("[Test] OVCHipkaartdao.findAll() geeft de volgende adressen:");
            List<Product> producten = pdao.findAll();
            for (Product p : producten) {
                System.out.println(p);
            }

            // Maak een nieuwe product aan en persisteer deze in de database
            System.out.print("[Test] Eerst " + producten.size() + " producten, na productDAO.save() ");
            pdao.save(biets);
            producten = pdao.findAll();
            System.out.println(producten.size() + " kaarten\n");

            //update
            producten = pdao.findAll();
            System.out.println("[Test] update van een product " );
            System.out.println("product was: " + biets);
            Product test = new Product(10, "nietes", "het is niet gratis", 1000);
            List<Product> testlist = new ArrayList<>();
            testlist.add(test);
            pdao.update(test);
            System.out.println("product is nu: ");
            System.out.println(testlist);

            //find by ovchipkaart
            OVChipkaart ov = ovdao.findAll().get(1);
            System.out.println("\n" + "[Test] find by ovchipkaart");
            System.out.println("We gebruiken deze kaart: " + ov +"\n dus het product is: ");
            pdao.findByOvchipkaart(ov);


            //delete
            System.out.println("\n" + "[Test] delete product");
            producten = pdao.findAll();
            System.out.println(producten.size() + " kaarten\n");
            pdao.delete(test);
            testlist.remove(test);
            producten = pdao.findAll();
            System.out.println(producten.size() + " kaarten\n");


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
