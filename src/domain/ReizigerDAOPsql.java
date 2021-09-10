package domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
   private Connection myConn;

    public ReizigerDAOPsql(Connection myConn) {
        this.myConn = myConn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
        PreparedStatement stat = myConn.prepareStatement("INSERT INTO reiziger( reiziger_id, voorletters, tussenvoegsel,achternaam, geboortedatum)" +
        "VALUES (?,?,?,?,?");
        stat.setInt(1, reiziger.getId());
        stat.setString(2, reiziger.getVoorletters());
        stat.setString(3, reiziger.getTussenvoegsel());
        stat.setString(4, reiziger.getAchternaam());
        stat.setDate(5, reiziger.getGeboortedatum());
        stat.executeUpdate();
        return true;}
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            PreparedStatement stat = myConn.prepareStatement("UPDATE reiziger SET reiziger_ID = ?, voorletters = ?, tussenvoegsel = ? achternaam = ?, geboortedatum =? WHERE reiziger_id = ?");

            stat.setInt(1, (reiziger.getId()));
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
    public boolean delete(Reiziger reiziger) {
        try{
            PreparedStatement st = myConn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?");

            st.setInt(1,reiziger.getId());
            st.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
        return false;
        }

    }

    @Override
    public Reiziger findByID(int id) {
        try {
            PreparedStatement state = myConn.prepareStatement("GET FROM reiziger WHERE reiziger_id = ?");

            state.setInt(1, (reiziger.getId()));
            state.setString(2, reiziger.getVoorletters());
            state.setString(3, reiziger.getTussenvoegsel());
            state.setString(4, reiziger.getAchternaam());
            state.setDate(5, reiziger.getGeboortedatum());
            state.executeUpdate();

            return null;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        return null;
    }}
