package baseDeDonnees;

import conversation.Historique;
import conversation.Message;
import utilisateur.Id;
import utilisateur.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Emile Sebastianutti
 */
public class LocalDB {
    protected Connection conn;
    private Utilisateur me;

    public LocalDB(Utilisateur me){
        this.me = me;
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
                + "     id INTEGER IDENTITY PRIMARY KEY,\n"
                + "     idPartenaire BIGINT NOT NULL,"
                + "     auteur boolean NOT NULL,\n" //true si je suis l'auteur, false si c'est mon partenaire
                + "     contenu LONGVARCHAR,\n"
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

    public void sauvegarderConversation(Historique hist){
        for (Message m : hist.getHistorique()){
            String query = "INSERT INTO messages (idPartenaire, auteur, contenu, horodatage) VALUES (?, ?, ?, ?)"; //INSERT INTO users (name, email) VALUES ('mkyong', 'aaa@gmail.com');
            try {
                PreparedStatement pstmt = this.conn.prepareStatement(query);
                pstmt.setString(1, hist.getIdPartenaire().toString());
                if (m.getAuteur().getId().equals(me.getId())){
                    pstmt.setString(2, "true");
                } else {
                    pstmt.setString(2, "false");
                }
                pstmt.setString(3, m.getContenu());
                pstmt.setString(4, m.getDate());
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("LocalDB: Error createTableKnownUsers");
                e.printStackTrace();
            }
        }
    }

    public Historique getConversation(Utilisateur partenaire){
        ArrayList<Message> msgs = new ArrayList<>();
        try {
            /*String query = "SELECT auteur FROM table where idPartenaire = " + partenaire.toString();
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeUpdate(query);
            stmt.close();*/
            String query = "SELECT * FROM messages WHERE idPartenaire = ?";
            PreparedStatement pstmt = this.conn.prepareStatement(query);
            pstmt.setString(1, partenaire.getId().toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                if (rs.getBoolean(3)){
                    msgs.add(new Message(this.me, partenaire, rs.getString(4), rs.getString(5)));
                } else {
                    msgs.add(new Message(partenaire, this.me, rs.getString(4), rs.getString(5)));
                }
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("LocalDB: Error createTableKnownUsers");
            e.printStackTrace();
        }
        Historique hist = new Historique(partenaire.getId(), msgs);
        return hist;
    }

    /*// Create and execute statement
    Statement stmt = conn.createStatement();
    ResultSet rs =  stmt.executeQuery("select FIRSTNAME, LASTNAME from CUSTOMER");

    // Loop through the data and print all artist names
            while(rs.next()) {
        System.out.println("Customer Name: " + rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));
    }*/
}