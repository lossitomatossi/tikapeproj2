package tikape.runko.database;

import java.sql.*;
import java.util.*;
import tikape.runko.domain.Kysymys;

public class KysymysDao implements Dao<Kysymys, Integer> {
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        int id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String kysymysteksti = rs.getString("kysymysteksti");
        

        Kysymys k = new Kysymys(id, kurssi, aihe, kysymysteksti);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String kysymysteksti = rs.getString("kysymysteksti");


            Kysymys k = new Kysymys(id, kurssi, aihe, kysymysteksti);
            kysymykset.add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement delete = connection.prepareStatement("DELETE FROM Kysymys WHERE id= ?");
        
        delete.setInt(1, key);
        delete.executeUpdate();
    }

    

    
    
}
