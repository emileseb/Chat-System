package baseDeDonnees;

import conversation.Historique;
import conversation.Message;
import utilisateur.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import org.hsqldb.Server;

public class LocalDB {
    protected Connection conn;
    private Utilisateur me;
    private Server serv;

    public LocalDB(Utilisateur me){
        this.me = me;
        this.conn = connectionDB();
        this.createTableConv();
    }
    
    /* Pour lancer la database,
    java -cp ../lib/hsqldb.jar org.hsqldb.server.Server --database.0 file:mydb --dbname.0 xdb
    clic droit sur le dossier chat-system, proprietes, librairies, add jar -> database/hsqldb-2.5.0/hsqldb/lib/hsqldb.jar
     */
    private Connection connectionDB() {
    	// creation de la bdd
    	serv = new Server();
    	//serv.setLogWriter(null);
    	//serv.setSilent(true);
    	serv.setDatabaseName(0, "messagesDB");
    	serv.setDatabasePath(0, "file:database/hsqldb-2.5.0/hsqldb/data/mydb");
    	serv.start();
    	
    	//connexion a la bdd
        Connection conn = null;
        String db = "jdbc:hsqldb:hsql://localhost/messagesDB";   //Connection c = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "sa", "");         ../../database/hsqldb-2.5.0/hsqldb/
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

    private void createTableUsers(){
        String query = "CREATE TABLE IF NOT EXISTS utilisateurs \n"
                + "(\n"
                + "     id INTEGER IDENTITY PRIMARY KEY,\n"
                + "     pseudo VARCHAR(30) NOT NULL,"
                + "     idUtilisateur BIGINT NOT NULL,\n" //true si je suis l'auteur, false si c'est mon partenaire
                + "     ipUtilisateur VARCHAR(15),\n"
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

    /*
    On ne va sauvegarder que les Utilisateurs avec lesquels nous avons clavardé afin de conserver leurs Historiques.
     */
    public void sauvegarderUsers(){
        for (Utilisateur user : me.getUtilisateursHistorique()){
            String query = "INSERT INTO utilisateurs (pseudo, idUtilisateur, ipUtilisateur) VALUES (?, ?, ?)";
            try {
                PreparedStatement pstmt = this.conn.prepareStatement(query);
                pstmt.setString(1, user.getPseudo());
                pstmt.setString(2, user.getId().toString());
                pstmt.setString(3, user.getAdresseIp());
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("LocalDB: Error createTableKnownUsers");
                e.printStackTrace();
            }
        }
    }

    public Historique getUsers(Utilisateur partenaire){
        ArrayList<Message> msgs = new ArrayList<>();
        try {
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

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        serv.stop();
    }
}