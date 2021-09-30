package domain.DAO;

import domain.domein.Adres;
import domain.domein.OVChipkaart;
import domain.interfaces.Reiziger;
import domain.interfaces.AdresDAO;
import domain.interfaces.OVChipkaartDAO;
import domain.interfaces.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
   private Connection myConn;
   private AdresDAO adao;
   private OVChipkaartDAO ovdao;

    public ReizigerDAOPsql(Connection myConn) {
        this.myConn = myConn;
    }
    public ReizigerDAOPsql(Connection myConn, AdresDAO adao, OVChipkaartDAO ovdao){
        this.myConn = myConn;
        this.adao = adao;
        this.ovdao = ovdao;
    }

    public void setAdresDao(AdresDAO adao){
        this.adao = adao;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
        PreparedStatement stat = myConn.prepareStatement("INSERT INTO reiziger ( reiziger_id, voorletters, tussenvoegsel,achternaam, geboortedatum)" +
        "VALUES (?,?,?,?,?)");
        if(reiziger.getAdres() != null){
                adao.save(reiziger.getAdres());
            }
        if (reiziger.getOvChipkaart() != null){
            ovdao.save(reiziger.getOvChipkaart());
        }
        stat.setInt(1, reiziger.getId());
        stat.setString(2, reiziger.getVoorletters());
        stat.setString(3, reiziger.getTussenvoegsel());
        stat.setString(4, reiziger.getAchternaam());
        stat.setDate(5, reiziger.getGeboortedatum());
        stat.executeUpdate();
        return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            PreparedStatement stat = myConn.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ? ,achternaam = ?, geboortedatum =? WHERE reiziger_id = ?");
            if(reiziger.getAdres() != null){
                adao.update(reiziger.getAdres());
            }
            if (reiziger.getOvChipkaart() != null){
                ovdao.update(reiziger.getOvChipkaart());
            }
            stat.setString(1, reiziger.getVoorletters());
            stat.setString(2, reiziger.getTussenvoegsel());
            stat.setString(3, reiziger.getAchternaam());
            stat.setDate(4, reiziger.getGeboortedatum());
            stat.setInt(5, reiziger.getId());
            stat.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            if(reiziger.getAdres() != null){
                AdresDAOsql adaop = new AdresDAOsql(myConn);
                adaop.delete(reiziger.getAdres());
            }
            if (reiziger.getOvChipkaart() != null){
                ovdao.delete(reiziger.getOvChipkaart());
            }
            PreparedStatement st = myConn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");
            st.setInt(1, reiziger.getId());
            st.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        return false;
        }

    }

    @Override
    public Reiziger findByID(int id2) {
        try {
            Statement stat = myConn.createStatement();
            ResultSet res = stat.executeQuery("SELECT * FROM reiziger");
            while (res.next()) {
                int id = res.getInt("reiziger_id");
                String voorl = res.getString("voorletters");
                String tussen = res.getString("tussenvoegsel");
                String achtern = res.getString("achternaam");
                Date geb = res.getDate("geboortedatum");
                if (id == id2) {
                    Reiziger reiziger = new Reiziger(id, voorl, tussen, achtern, geb);
                    if (reiziger.getAdres() != null){
                        Adres adres = adao.findByReiziger(reiziger);
                        reiziger.setAdres(adres);
                    }
                    if (reiziger.getOvChipkaart() != null){
                        OVChipkaart ov = (OVChipkaart) ovdao.findbyReiziger(reiziger);
                        reiziger.setOvChipkaart(ov);
                    }

                    return reiziger;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try{
            List<Reiziger> reizigerList = new ArrayList<Reiziger>();
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM reiziger");
            while(set.next()){
                if(set.getDate(5).equals(Date.valueOf(datum))){
                    Reiziger reiz = new Reiziger(set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getDate(5)
                            );
                    if (reiz.getAdres() != null){
                        Adres adres = adao.findByReiziger(reiz);
                        reiz.setAdres(adres);
                    }
                    if (reiz.getOvChipkaart() != null){
                        OVChipkaart ov = (OVChipkaart) ovdao.findbyReiziger(reiz);
                        reiz.setOvChipkaart(ov);
                    }

                    System.out.println(reiz);
                    reizigerList.add(reiz);
                }
            }
            return reizigerList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try{
            List<Reiziger> reizigerList = new ArrayList<Reiziger>();
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM reiziger");
            while(set.next()){
                Reiziger reiz = new Reiziger(set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getDate(5)
                );
                if (reiz.getAdres() != null){
                    Adres adres = adao.findByReiziger(reiz);
                    reiz.setAdres(adres);
                }
                if (reiz.getOvChipkaart() != null){
                    OVChipkaart ov = (OVChipkaart) ovdao.findbyReiziger(reiz);
                    reiz.setOvChipkaart(ov);
                }
                reizigerList.add(reiz);
            }
            set.close();
            stat.close();
            if(reizigerList.size() < 1){
                    throw new IllegalArgumentException();
            }
            return reizigerList;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }}
