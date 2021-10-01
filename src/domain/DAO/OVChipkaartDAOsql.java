package domain.DAO;

import domain.domein.OVChipkaart;
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
    public boolean save(List<OVChipkaart> ovChipkaart) throws SQLException {
        try{
            PreparedStatement stat = myConn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot,klasse, saldo, reiziger_id)" +
                    "VALUES (?,?,?,?,?)");
            for (OVChipkaart ovchip : ovChipkaart) {
            stat.setInt(1, ovchip.getKaart_nummer());
            stat.setDate(2, ovchip.getGeldig_tot());
            stat.setInt(3, ovchip.getKlasse());
            stat.setInt(4, ovchip.getSaldo());
            stat.setInt(5, ovchip.getReisigerid());
            stat.executeUpdate();}
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @Override
    public boolean update(List<OVChipkaart> ovChipkaart) {
        try{
            PreparedStatement stat = myConn.prepareStatement("UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot= ? ,klasse = ?, saldo =? WHERE reiziger_id = ?");
            for (OVChipkaart ovchip : ovChipkaart) {
            stat.setInt(1, ovchip.getKaart_nummer());
            stat.setDate(2, ovchip.getGeldig_tot());
            stat.setInt(3, ovchip.getKlasse());
            stat.setInt(4, ovchip.getSaldo());
            stat.setInt(5, ovchip.getReisigerid());
            stat.executeUpdate();}
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(List<OVChipkaart> ovChipkaart) {
        try{
            PreparedStatement st = myConn.prepareStatement("DELETE FROM ov_chipkaart WHERE reiziger_id = ?");
            for (OVChipkaart ovchip : ovChipkaart) {
            st.setInt(1, ovchip.getReisigerid());
            st.executeUpdate();}
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

