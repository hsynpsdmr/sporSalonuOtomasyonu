  package sporsalonu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.postgresql.translation.messages_bg;


public class databaseConnection {
   Connection Conn = null;
   String url = "jdbc:postgresql://localhost:5432/sporisletme2";
   String user = "postgres";
   String password = "postgres";
   public Connection databaseConn() {

       try {
           Class.forName("org.postgresql.Driver");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
       }

       try {
           Conn = DriverManager.getConnection(url, user, password);

   //        JOptionPane.showMessageDialog(null, "Database Bağlandı.");
           
       } catch (SQLException ex) {
           Logger.getLogger(databaseConnection.class.getName()).log(Level.SEVERE, null, ex);
           
       }
        return Conn;
   }

   public static void main(String[] args) {
   databaseConnection connDatabase = new databaseConnection();
   connDatabase.databaseConn();
   }

}