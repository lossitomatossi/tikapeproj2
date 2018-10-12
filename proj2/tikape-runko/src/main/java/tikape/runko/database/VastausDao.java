package tikape.runko.database;

import java.sql.*;
import java.util.*;
import tikape.runko.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer> {
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        int id = rs.getInt("id");
        String vastaus = rs.getString("vastaus");
        boolean oikein = rs.getBoolean("oikein");
        int kysymys_id = rs.getInt("kysymys_id");
       
        

        Vastaus v = new Vastaus(id, vastaus, oikein, kysymys_id);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String vastaus = rs.getString("vastaus");
            boolean oikein = rs.getBoolean("oikein");
            int kysymys_id = rs.getInt("kysymys_id");


            Vastaus v = new Vastaus(id, vastaus, oikein, kysymys_id);
            vastaukset.add(v);
            
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement delete = connection.prepareStatement("DELETE FROM Kysymys WHERE id= ?");
        //tee resultsetit jne jne et tää toimis sillee kivasti, nyt ei hae sitä näköjää tarpeeks hyvin??
        
        
        delete.setInt(1, key);
        delete.executeUpdate();
    }
    
    public List<Vastaus> findAllForId(Integer nro) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setInt(1, nro);
        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String vastaus = rs.getString("vastaus");
            boolean oikein = rs.getBoolean("oikein");
            int kysymys_id = rs.getInt("kysymys_id");


            Vastaus v = new Vastaus(id, vastaus, oikein, kysymys_id);
            vastaukset.add(v);
            
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
        
    }
}