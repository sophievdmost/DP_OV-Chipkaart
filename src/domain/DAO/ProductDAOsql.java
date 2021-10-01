package domain.DAO;

import domain.DAO.OVChipkaartDAOsql;
import domain.domein.OVChipkaart;
import domain.domein.Product;
import domain.interfaces.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOsql implements ProductDAO {
    private Connection myConn;
    private OVChipkaartDAOsql ovdao;

    public ProductDAOsql(Connection myConn, OVChipkaartDAOsql ovdao) {
        this.myConn = myConn;
        this.ovdao = ovdao;
    }

    @Override
    public boolean save(Product product) {
        try{
            PreparedStatement stat = myConn.prepareStatement("INSERT INTO product(product_nummer, naam, beschrijving, prijs)" +
                    "VALUES (?,?,?,?)");
            stat.setInt(1, product.getProduct_nummer());
            stat.setString(2, product.getNaam());
            stat.setString(3, product.getBeschrijving());
            stat.setInt(4,product.getPrijs());
            stat.executeUpdate();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(Product product) {
        try {
            PreparedStatement stat = myConn.prepareStatement("UPDATE product SET  naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ? ");
            stat.setString(1, product.getNaam());
            stat.setString(2, product.getBeschrijving());
            stat.setInt(3, product.getPrijs());
            stat.setInt(4, product.getProduct_nummer());
            stat.executeUpdate();
            return true;

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Product product) {
        try {
            PreparedStatement stat = myConn.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
            stat.setInt(1,product.getProduct_nummer());

            stat.executeUpdate();

            stat.close();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Product> findByOvchipkaart(OVChipkaart ovChipkaart) {
        try {
            List<Product> producten = new ArrayList<>();
            PreparedStatement stat = myConn.prepareStatement("select * from product inner join ov_chipkaart_product on product.product_nummer = ov_chipkaart_product.product_nummer where kaart_nummer = ?");
            stat.setInt(1, ovChipkaart.getKaart_nummer());
            ResultSet set = stat.executeQuery();
            while(set.next()){
                if (set.getInt("kaart_nummer") == ovChipkaart.getKaart_nummer()){
                    Product prod = new Product(set.getInt("product_nummer"),
                            set.getString("naam"),
                            set.getString("beschrijving"),
                            set.getInt("prijs"));

                    System.out.println(prod);
                    producten.add(prod);


                }

            }
            set.close();
            stat.close();
            return producten;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            List<Product> producten = new ArrayList<>();
            Statement stat = myConn.createStatement();
            ResultSet set = stat.executeQuery("SELECT * FROM product");
            while (set.next()){
                Product prod = new Product(set.getInt("product_nummer"),
                        set.getString("naam"),
                        set.getString("beschrijving"),
                        set.getInt("prijs"));

                producten.add(prod);
            }
            set.close();
            stat.close();
            if(producten.size() < 1){
                throw new IllegalArgumentException();
            }
            return producten;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
