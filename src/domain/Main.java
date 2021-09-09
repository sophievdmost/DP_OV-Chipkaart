package domain;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        try{
            Connection myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchimps" ,"postgres", "0611");
            Statement st = myConn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger;");
            System.out.println("Alle reizgers:");
            while (rs.next()) {
                int id = rs.getInt("reiziger_id");
                String voorletter = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("Geboortedatum");

                if (tussenvoegsel == null){
                    tussenvoegsel = "";
                }

                String str = voorletter+ ". " + tussenvoegsel+ " " + achternaam+ " (" + geboortedatum + ")";
                System.out.println(str);
            }
            rs.close();
            st.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}
