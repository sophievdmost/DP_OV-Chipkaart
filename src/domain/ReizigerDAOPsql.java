package domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
   private Connection myConn;

    public ReizigerDAOPsql(Connection myConn) {
        this.myConn = myConn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
        PreparedStatement stat = myConn.prepareStatement("INSERT INTO reiziger ( reiziger_id, voorletters, tussenvoegsel,achternaam, geboortedatum)" +
        "VALUES (?,?,?,?,?)");
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
                    Reiziger r2 = new Reiziger(id, voorl, tussen, achtern, geb);
                    System.out.println(r2);
                    return r2;
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
