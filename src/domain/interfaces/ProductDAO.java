package domain.interfaces;

import domain.domein.OVChipkaart;
import domain.domein.Product;

import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findByOvchipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
