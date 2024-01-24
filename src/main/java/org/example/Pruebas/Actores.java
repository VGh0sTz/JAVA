package org.example.Pruebas;

import java.sql.*;

public class Actores {
    public static void main(String[] args) {
        String sentenciaSql = "Select * FROM actores";
        String insertsql = "INSERT into actores VALUES(3,'Paco',40)";
        String deletesql = "Delete from actores where id=4";
        String updatesql = "UPDATE actores set id=4 where id=3 ";
        String usuario = "postgres";
        String contraseña = "postgres";
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mi base", usuario, contraseña);
             PreparedStatement sentencia = con.prepareStatement(sentenciaSql);
             PreparedStatement insert = con.prepareStatement(insertsql);
             PreparedStatement delete = con.prepareStatement(deletesql);
             PreparedStatement update = con.prepareStatement(updatesql)) {
            try {
                ResultSet resultados = sentencia.executeQuery();

                con.setAutoCommit(false);

                //COMPROBAMOS POR PRIMERA VEZ
                ver_tabla(resultados);
                //INTRODUCIMOS
                int resultado_insert = insert.executeUpdate();
                ver_tabla(resultado_insert);
                //COMPROBAMOS OTRA VEZ
                ResultSet resultados2 = sentencia.executeQuery();
                ver_tabla(resultados2);
                //UPDATE
                int resultado_update = update.executeUpdate();
                ver_tabla(resultado_update);
                //COMPROBAMOS
                ResultSet resultados3 = sentencia.executeQuery();
                ver_tabla(resultados3);
                //DELETE
                int resultado_delete = delete.executeUpdate();
                ver_tabla(resultado_delete);
                //COMPROBAMOS OTRA VEZ
                ResultSet resultados4 = sentencia.executeQuery();
                ver_tabla(resultados4);
                con.commit();
            }catch (SQLException e){
                // En caso de error, se revierte la transacción
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ver_tabla(ResultSet resultados){
        try {
            while (resultados.next()) {
                Integer id = resultados.getInt("id");
                String nombre = resultados.getString("nombre");
                Integer edad = resultados.getInt("edad");
                System.out.println("id: " + id + " nombre: " + nombre + " edad: " + edad);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void ver_tabla(int filas){
        if (filas > 0) {
            System.out.println("La operación fue exitosa.");
        } else {
            System.out.println("No se pudo realizar la operación");
        }
    }

}