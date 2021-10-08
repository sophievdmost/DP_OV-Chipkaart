package domain.DAO;

import domain.domein.OVChipkaart;
import domain.domein.Product;
import domain.domein.Reiziger;
import domain.interfaces.OVChipkaartDAO;
import domain.interfaces.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OVChipkaartDAOsql implements OVChipkaartDAO {
    private Connection myConn;
    private ReizigerDAO rdao;

    public OVChipkaartDAOsql(Connection myConn) {
        this.myConn = myConn;
    }

    public OVChipkaartDAOsql(Connection myConn, ReizigerDAO rdao){
        this.myConn = myConn;
        this.rdao = rdao;
    }

    @Override
    public boolean save(OVChipkaart ov) throws SQLException {
        try{
            PreparedStatement stat = myConn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot,klasse, saldo, reiziger_id)" +
                    "VALUES (?,?,?,?,?)");{
            stat.setInt(1, ov.getKaart_nummer());
            stat.setDate(2, ov.getGeldig_tot());
            stat.setInt(3, ov.getKlasse());
            stat.setInt(4, ov.getSaldo());
            stat.setInt(5, ov.getReisigerid());
            stat.executeUpdate();}

            PreparedStatement stat2 = myConn.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer)" +
                    "VALUES (?,?)");{
            if (ov.getProducten() != null)        {
            for (Product product : ov.getProducten()){
                stat2.setInt(1, ov.getKaart_nummer());
                stat2.setInt(2, product.getProduct_nummer());
                stat2.executeUpdate();
            }}}
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public boolean update(OVChipkaart ovchip) {
        try{
            PreparedStatement stat = myConn.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot= ? ,klasse = ?, saldo =? WHERE reiziger_id = ?");

            stat.setInt(1, ovchip.getKaart_nummer());
            stat.setDate(2, ovchip.getGeldig_tot());
            stat.setInt(3, ovchip.getKlasse());
            stat.setInt(4, ovchip.getSaldo());
            stat.setInt(5, ovchip.getReisigerid());
            stat.executeUpdate();
            if (ovchip.getProducten() != null) {
                ArrayList<Integer> OBproductNummers = new ArrayList<Integer>();
                for (Product product : ovchip.getProducten()) {
                    OBproductNummers.add(product.getProduct_nummer());
                }

                ArrayList<Integer> DBproductNumemrs = new ArrayList<>();
                PreparedStatement stat2 = myConn.prepareStatement("SElECT product_nummer from ov_chipkaart_product WHERE kaart_nummer = ?");
                stat2.setInt(1, ovchip.getKaart_nummer());
                ResultSet rs = stat2.executeQuery();
                while (rs.next()) {
                    int productnummer = rs.getInt("product_nummer");
                    DBproductNumemrs.add(productnummer);
                }

                for (int productnummer : OBproductNummers) {
                    if (!DBproductNumemrs.contains(productnummer)) {
                        PreparedStatement stat3 = myConn.prepareStatement("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?,?)");
                        stat3.setInt(1, ovchip.getKaart_nummer());
                        stat3.setInt(2, productnummer);
                        stat3.executeUpdate();
                    }
                }
                for (int productnummer : DBproductNumemrs)
                    if (OBproductNummers.contains(productnummer)) {
                        PreparedStatement stat4 = myConn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ? AND kaart_nummer = ?");
                        stat4.setInt(1, productnummer);
                        stat4.setInt(2, ovchip.getKaart_nummer());
                        stat4.executeUpdate();
                    }
            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(OVChipkaart ovchip) {
        try{
            PreparedStatement st = myConn.prepareStatement("DELETE FROM ov_chipkaart WHERE reiziger_id = ?");
            st.setInt(1, ovchip.getReisigerid());
            st.executeUpdate();
            if (ovchip.getProducten() != null){
            PreparedStatement stat2 = myConn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ? AND product_nummer = ?"); {
                for (Product product : ovchip.getProducten()){
                    stat2.setInt(1, ovchip.getKaart_nummer());
                    stat2.setInt(2, product.getProduct_nummer());
                    stat2.executeUpdate();
                }}}
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public OVChipkaart findByID(int id2) {
        try {
            Statement stat = myConn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM ov_chipkaart");
            while (res.next()) {
                Reiziger reiziger_id = rdao.findByID(res.getInt("reiziger_id"));
                int id = res.getInt("kaart_nummer");
                Date geldigtot = res.getDate("geldig_tot");
                int klasse = res.getInt("klasse");
                int saldo = res.getInt("saldo");
                if (id == id2) {
                    OVChipkaart ovChipkaart = new OVChipkaart(id,geldigtot,klasse,saldo,reiziger_id);
                    System.out.println(ovChipkaart);
                    return ovChipkaart;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        try{
            List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM ov_chipkaart");
            while(set.next()){
                OVChipkaart ov = new OVChipkaart(set.getInt(1),
                        set.getDate(2),
                        set.getInt(3),
                        set.getInt(4),
                        rdao.findByID(set.getInt(5)));

                ovchipkaarten.add(ov);
            }
            set.close();
            stat.close();
            if(ovchipkaarten.size() < 1){
                throw new IllegalArgumentException();
            }
            return ovchipkaarten;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public List<OVChipkaart> findbyReiziger(Reiziger reiziger) {
        try {
            Statement stat = myConn.createStatement();
            List<OVChipkaart> kaarten = new ArrayList<OVChipkaart>();
            ResultSet set = stat.executeQuery("SELECT * FROM ov_chipkaart");
            while (set.next()) {
                if (set.getInt("reiziger_id") == reiziger.getId()) {
                    OVChipkaart ov= new OVChipkaart(set.getInt("kaart_nummer"),
                            set.getDate("geldig_tot"),
                            set.getInt("klasse"),
                            set.getInt("saldo"),
                            reiziger);

                    System.out.println(ov);
                    kaarten.add(ov);
                    return kaarten;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }}

