package domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection myConn;

    public AdresDAOsql(Connection myConn) {
        this.myConn = myConn;
    }

    @Override
    public boolean save(Adres adres) {
        try{
            PreparedStatement stat = myConn.prepareStatement("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)" +
                    "VALUES (?,?,?,?,?,?)");
            stat.setInt(1, adres.getId());
            stat.setString(2, adres.getPostcode());
            stat.setString(3,adres.getHuisnummer());
            stat.setString(4, adres.getStraat());
            stat.setString(5, adres.getWoonplaats());
            stat.setInt(6, adres.getReisigerid());
            stat.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Adres adres) {
        try{
            PreparedStatement stat = myConn.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ? ,straat = ?, woonplaats =?, reiziger_id = ? WHERE adres_id = ?");

            stat.setString(1, adres.getPostcode());
            stat.setString(2, adres.getHuisnummer());
            stat.setString(3, adres.getStraat());
            stat.setString(4, adres.getWoonplaats());
            stat.setInt(5, adres.getReisigerid());
            stat.setInt(6, adres.getId());
            stat.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Adres adres) {
        try{
            PreparedStatement st = myConn.prepareStatement("DELETE FROM adres WHERE adres_id = ?");
            st.setInt(1, adres.getId());
            st.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM adres");
            while (set.next()) {
                if (set.getInt(6) == reiziger.getId()) {
                    Adres ad = new Adres(set.getInt(1 ), set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5),
                    set.getInt(6)
                    );
                    System.out.println(ad);
                    return ad;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try{
            List<Adres> Adressen = new ArrayList<Adres>();
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM adres");
            while(set.next()){
                Adres ad = new Adres(set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getInt(6)
                );

                Adressen.add(ad);
            }
            set.close();
            stat.close();
            if(Adressen.size() < 1){
                throw new IllegalArgumentException();
            }
            return Adressen;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
