import javax.swing.*;
import java.sql.*;

public class Databas {
    public static final String DEFAULT_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    public static String hostname = "127.0.0.1";
    public static String dbName = "SoloAdventureJava";
    public static int port = 3306;
    public static final String DEFAULT_URL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
    private static final String DEFAULT_USERNAME = "alex";
    private static final String DEFAULT_PASSWORD = "Secret123";

    public static void main(String[] args) {
        Connection connection = null;
        int id  = 1;


        try {
            Class.forName(DEFAULT_DRIVER_CLASS);
            connection = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);

            PreparedStatement ps = null;
            ResultSet rs = null;

            while(true) {
                ps = connection.prepareStatement("SELECT text FROM story WHERE id = " + id);
                rs = ps.executeQuery();
                String story = "";
                while (rs.next()) {
                    story = rs.getString("text");
                    System.out.println(story);
                }

                ps = connection.prepareStatement("SELECT text FROM storylinks WHERE storyid = " + id);
                rs = ps.executeQuery();
                String[] storylinks = new String[3];

                int i = 0;
                while (rs.next() && i < storylinks.length) {
                    storylinks[i] = rs.getString("text");
                    i++;
                }

                String text = (String) JOptionPane.showInputDialog(null, story, "La Traviata", JOptionPane.QUESTION_MESSAGE,
                        null, storylinks, storylinks[2]);
                System.out.println(text);

                ps = connection.prepareStatement("SELECT target FROM storylinks WHERE text = '" + text + "'");
                rs = ps.executeQuery();

                while (rs.next()) id = rs.getInt("target");

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
