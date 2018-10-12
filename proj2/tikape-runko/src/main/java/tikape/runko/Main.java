package tikape.runko;

import java.util.*;
import java.sql.*;
import spark.ModelAndView;
import spark.*;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:kysymykset.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);
        
        
        //kysymykset sivu
        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());
        
        //maini
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        
        //yksittäisen kysymyksen sivu
        get("/kysymys/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params("id"));
            map.put("kysymys", kysymysDao.findOne(id));
            
            map.put("xs", vastausDao.findAllForId(id));

            return new ModelAndView(map, "kysymys");
            
        }, new ThymeleafTemplateEngine());
        
        
        //vastaukset id:llä
//        get("/vastauksetId", (req, res) -> {
//            HashMap map = new HashMap<>();
//            map.put("vastauksetId", vastausDao.findAllForId(Integer.parseInt(req.params("kurssin_id"))));
//            
//            return new ModelAndView(map, "kysymys");
//        }, new ThymeleafTemplateEngine());
        
        Spark.post("/lisaaKysymys", (req, res) -> {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES (?,?,?)");
            stmt.setString(1, req.queryParams("kurssi"));
            stmt.setString(2, req.queryParams("aihe"));
            stmt.setString(3, req.queryParams("kysymysteksti"));
            
            stmt.executeUpdate();
            conn.close();
            
            res.redirect("/");
                    
            return "";
            
        });
        
        
        Spark.post("/lisaaVastaus", (req,res) -> {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (vastaus, oikein, kysymys_id) VALUES (?,?,?)");
            stmt.setString(1, req.queryParams("vastaus"));
            String oikein = req.queryParams("oikein");
            if (oikein.equals("K")) {
                stmt.setBoolean(2, true);
            } else {
                stmt.setBoolean(2, false);
            }
            
            stmt.setInt(3, Integer.parseInt(req.queryParams("kysymysid")));
            
            stmt.executeUpdate();
            conn.close();
            
            res.redirect("/kysymykset");
                    
            return "";
        });
        
        Spark.post("/poistaVastaus", (req,res) -> {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
            stmt.setString(1, req.queryParams("id"));
            
            stmt.executeUpdate();
            
            conn.close();
            res.redirect("/kysymykset");
            
            return "";
        });
        
        Spark.post("/poistaKysymys", (req,res) -> {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:kysymykset.db");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setString(1, req.queryParams("id"));
            
            stmt.executeUpdate();
            
            conn.close();
            res.redirect("/kysymykset");
            
            return "";
        });
        
    }
}

