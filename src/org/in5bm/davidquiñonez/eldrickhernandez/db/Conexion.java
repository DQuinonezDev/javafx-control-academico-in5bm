package org.in5bm.davidquiñonez.eldrickhernandez.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 3 may. 2022
 * @time 11:08:59
 *
 * Código Técnico: IN5BM
 */
public class Conexion {

    private Connection conexion;
    private final String URL;
    private final String IP_SERVER;
    private final String PORT;
    private final String DB;
    private final String USER;
    private final String PASSWORD;

    private static Conexion instancia;

    public static Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    private Conexion() {
        IP_SERVER = "127.0.0.1";
        PORT = "3306";
        DB = "db_control_academico_in5bm";
        USER = "kinal";
        PASSWORD = "admin";

        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("La conexion fue todo un exito");

            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConected to: " + dma.getURL());
            System.out.println("Driver utilizad: " + dma.getDriverName());
            System.out.println("Version utilizada: " + dma.getDriverVersion() + "\n");

        } catch (ClassNotFoundException e) {
            System.err.println("No encuentra ninguna definicion para esta clase");
            e.printStackTrace();

            /*}catch (InstantiationException e){
            e.printStackTrace();
            System.err.println("No se puede crear una instancia de objeto");
        } catch (IllegalAccessException e){
            e.printStackTrace()
            System.err.println("No se tienen permisos para acceder al paquete");;*/
        } catch (CommunicationsException e) {
            System.err.println("No se puede establecer comunicacion con el servidor de MySQL");
            System.out.println("Verifique que el servicion MySQL este levantado, "
                    + "que la IP_SERVER y el puerto este correcto");

            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Se produjo un error de tipo SQLException");

            System.out.println("SQLSate: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            System.out.println("\n");

            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Se produjo un error al intetar establecer una conexion con la base de datos");
            e.printStackTrace();
        }

    }

    public Connection getConexion() {
        return conexion;
    }
}
