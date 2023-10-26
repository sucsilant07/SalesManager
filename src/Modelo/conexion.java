package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    Connection con;

    public Connection getConnection() {
        try {
            String myDB = "jdbc:sqlite:resources/SalesManagerShop.sqlite3";
            if (myDB == null) {
                throw new RuntimeException("No se pudo cargar el archivo de base de datos.");
            }
            con = DriverManager.getConnection(myDB);
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
