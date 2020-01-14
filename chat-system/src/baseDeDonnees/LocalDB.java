package baseDeDonnees;

import conversation.Historique;
import conversation.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Emile Sebastianutti
 */
public class LocalDB {

    protected Connection conn;

    public LocalDB(){
        this.conn = connectionDB();
        this.createTableConv();
    }


    private static Connection connectionDB() {
        Connection conn = null;
        String db = "jdbc:hsqldb:hsql://localhost/xdb";   //Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa", "");         ../../database/hsqldb-2.5.0/hsqldb/
        String user = "SA";
        String password = "";
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
            return null;
        }
        try {
            // Create database connection
            conn = DriverManager.getConnection(db, user, password);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }

    private void createTableConv(){
        String query = "CREATE TABLE IF NOT EXISTS messages \n"
                + "(\n"
                + "     id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,\n"
                + "     idPartenaire BIGINT NOT NULL"
                + "     Auteur boolean NOT NULL,\n" //true si je suis l'auteur, false si c'est mon partenaire
                + "     contenu text,\n"
                + "     horodatage VARCHAR(20) NOT NULL\n"
                + ");";
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("LocalDB: Error createTableKnownUsers");
            e.printStackTrace();
        }
    }

    private void sauvegarderConversation(Historique hist){
        for (Message m : hist.getHistorique()){
            String query = "INSERT INTO messages VALUES ('33', 'true', 'bonjour', '11111111111111111111')";
            try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("LocalDB: Error createTableKnownUsers");
            e.printStackTrace();
        }
        }
    }

    /*// Create and execute statement
    Statement stmt = conn.createStatement();
    ResultSet rs =  stmt.executeQuery("select FIRSTNAME, LASTNAME from CUSTOMER");

    // Loop through the data and print all artist names
            while(rs.next()) {
        System.out.println("Customer Name: " + rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));
    }*/
}