package org.example.EJ6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

public class F12006_MAIN {
    public static void main(String[] args) {
        OperacionesCRUDPilotos operaciones=new OperacionesCRUDPilotos();
        String usuario = "postgres";
        String contraseña = "postgres";
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/F12006", "postgres", "postgres")) {
            Piloto p1 = new Piloto(40, "AJD", "Asahbi", "JaimeDaniel", "1200-07-29", "Spanish", 1, "http://en.wikipedia.org/wiki/Asahbi");
            operaciones.CrearPiloto(p1,con);
            Piloto p2 = operaciones.LeerPiloto(40,con);
            if (p1.equals(p2)) {
                System.out.println("IGUALES");
                System.out.println(p2.toString());
            }
            System.out.println("/////////////////////////////////////////");
            Iterator iter = operaciones.LeerPilotos(con).iterator();
            while (iter.hasNext()){
                System.out.println(iter.next());
            }
            System.out.println("/////////////////////////////////////////");
            Piloto p3 = new Piloto(40, "CR7", "Cristiano", "Runardo", "1200-07-22", "Spanish", 1, "http://en.wikipedia.org/wiki/Asahbi");
            operaciones.ActualizarPiloto(con,p3);
            Piloto p4 = operaciones.LeerPiloto(40,con);
            if (p3.equals(p4)) {
                System.out.println("IGUALES");
                System.out.println(p4.toString());
            }
            System.out.println(" ");
            operaciones.BorrarPiloto(con,p3);
            System.out.println("");

            System.out.println("/////////////////////////////////////////");
            iter = operaciones.LeerPilotos(con).iterator();
            while (iter.hasNext()) {
                System.out.println(iter.next());
            }
            System.out.println("/////////////////////////////////////////");

            operaciones.MostrarClasificacionPiloto(con);

            System.out.println("/////////////////////////////////////////");

            operaciones.MostrarClasificacionConstructores(con);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
