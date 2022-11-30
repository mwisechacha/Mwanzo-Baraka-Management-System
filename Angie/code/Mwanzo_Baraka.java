
import javax.swing.JFrame;
import java.sql.*;

public class Mwanzo_Baraka {
    Statement sqlSt;
    String SQL;
    String useSQL = new String("baraka_db");// name of the db
    String output;
    ResultSet result;

    public static void main(String[] args) throws SQLException {

        Boolean loggedIn = false;
        if (loggedIn) {

        } else {
            JFrame frame = new Home();

            frame.setVisible(true);
        }
    }

}
