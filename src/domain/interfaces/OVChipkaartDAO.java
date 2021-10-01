package domain.interfaces;

import domain.domein.OVChipkaart;
import domain.domein.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(List<OVChipkaart> ovChipkaart) throws SQLException;
    boolean update(List<OVChipkaart> ovChipkaart);
    boolean delete(List<OVChipkaart> ovChipkaart);
    OVChipkaart findByID(int kaart_nummer);
    List<OVChipkaart> findAll();
    List<OVChipkaart> findbyReiziger(Reiziger reiziger);
}
