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
        stat.setInt(1, (reiziger.getId()));
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

        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        return false;
    }

    @Override
    public Reiziger findByID(int id) {
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
