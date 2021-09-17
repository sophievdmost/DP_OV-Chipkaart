package domain;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ovChipkaart) throws SQLException;
    boolean update(OVChipkaart ovChipkaart);
    boolean delete(OVChipkaart ovChipkaart);
    ///OVChipkaart findByID(int id);
    List<OVChipkaart> findAll();
    List<OVChipkaart> findbyReiziger(Reiziger reiziger);
}
