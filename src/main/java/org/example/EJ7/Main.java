package org.example.EJ7;

import org.example.EJ6.Piloto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String usuario="postgres";
        String contraseña="12345678";
        String punto_enlace="ejercicio7.c1egsq2oio7e.us-east-1.rds.amazonaws.com";
        String puerto="5432";
        String jdbcUrl = "jdbc:postgresql://"+punto_enlace+":"+puerto+"/f12006";
        try (Connection con= DriverManager.getConnection(jdbcUrl,usuario,contraseña)){
            //PARA PODER HACER LA TRANSACCION
            Operaciones o=new Operaciones();
            try {
                //SETEAMOS EL AUTOCOMIT A FALSE
                con.setAutoCommit(false);
                Constructor SeatF1=new Constructor(14,"seatf1","SeatF1","Spanish","https://es.wikipedia.org/wiki/SEAT_Sport");
                Piloto Carlos = new Piloto(42, "SNZ", "Carlos", "Sainz", "1994-12-01", "Spanish", 14, "https://es.wikipedia.org/wiki/Carlos_Sainz");
                o.CrearConstructor(SeatF1,con);
                o.CrearPiloto(Carlos,con);
                //COMPROBAMOS QUE SE INSERTO
                Piloto p2 = o.LeerPiloto(42,con);
                if (Carlos.equals(p2)) {
                    System.out.println("IGUALES");
                    System.out.println(p2.toString());
                }
                Piloto Manuel = new Piloto(44, "ALM", "MANUEL", "ALOMÁ", "1994-12-01", "Spanish", 14, "https://es.wikipedia.org/wiki/Carlos_Sainz");
                o.CrearPiloto(Manuel,con);

                //LLAMAMOS A UN PROCEDIMIENTO
                o.get_results_by_driver("ALO",con);
                //HACEMOS COMMIT
                con.commit();
            }catch (SQLException e){
                //ROLLBACK EN CASO DE FALLO
                con.rollback();
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
