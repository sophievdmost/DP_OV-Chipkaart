package domain.interfaces;

import domain.domein.OVChipkaart;
import domain.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ov) throws SQLException;
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    OVChipkaart findByID(int kaart_nummer);
    List<OVChipkaart> findAll();
    List<OVChipkaart> findbyReiziger(Reiziger reiziger);
}
