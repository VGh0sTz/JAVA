package org.example.Pruebas;

import java.sql.*;

public class F12006 {
    public static void main(String[] args) {
        String sentenciaSql = "Select * FROM drivers where nationality='Spanish'";
        String insercion1="Insert into drivers values(71, 'ALO', 'JosePI', 'Nocho', '1200-07-29', 'Spanish', 4, 'http://en.wikipedia.org/wiki/Fernando_Alonso')";
        String insercion2="Insert into drivers values(72, 'ANA', 'ANA', 'NOSE', '1200-07-29', 'Spanish', 4, 'http://en.wikipedia.org/wiki/Ana')";
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/F12006", "postgres", "postgres");
             PreparedStatement sentencia = con.prepareStatement(sentenciaSql);
             CallableStatement procedimiento = con.prepareCall("{CALL get_results_by_driver(?)}");
             PreparedStatement insercion = con.prepareStatement(insercion1);
             PreparedStatement insercio = con.prepareStatement(insercion2)) {
            try {
                //EJERCICIO: VER PILOTOS ESPAÑOLES
                ResultSet resultados = sentencia.executeQuery();
                //COMPROBAMOS
                ver_conductores(resultados);

                //EJERCICIO: ACCEDER AL PROCEDIMIENTO ALMACENADO "get_resilts_by_driver" (devuelve una lista)
                procedimiento.setString(1, "ALO");
                procedimiento.execute();
                ResultSet rs=procedimiento.getResultSet();
                //COMPROBAMOS
                get_results_by_driver(rs);

                //EJERCICIO TRANSACCION
                con.setAutoCommit(false);
                insercion.executeQuery();
                insercio.executeQuery();
                //COMPROBAMOS
                ResultSet rs1=sentencia.executeQuery();
                ver_conductores(rs1);
                con.commit();


                //EJERCICIO


            }catch (SQLException e){
                // En caso de error, se revierte la transacción
                if (con != null) {
                    try {
                        con.rollback();
                        System.out.println("ROLLBACK HECHO");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ver_conductores(ResultSet resultados){
        try {
            while (resultados.next()) {
                Integer driverid = resultados.getInt("driverid");
                String code = resultados.getString("code");
                String forename = resultados.getString("forename");
                String surname = resultados.getString("surname");
                String dob = resultados.getString("dob");
                String nationality = resultados.getString("nationality");
                Integer constructorid = resultados.getInt("constructorid");
                String url = resultados.getString("url");
                System.out.println("driver_id: " + driverid + " code: " + code + " forename: " + forename+ " surname: " + surname+ " dob: " + dob+ " nationality: " + nationality+ " constructorid: " + constructorid +" url: "+url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void get_results_by_driver(ResultSet resultados){
        try {
            while (resultados.next()) {
                Integer round = resultados.getInt("round");
                String circuit = resultados.getString("circuit");
                Integer result = resultados.getInt("result");
                Integer points = resultados.getInt("points");
                Date date = new Date(resultados.getDate("date").getTime());
                System.out.println("Round: " + round + " circuit: " + circuit + " result: " + result+ " points: " + points+ " date: " + date);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}