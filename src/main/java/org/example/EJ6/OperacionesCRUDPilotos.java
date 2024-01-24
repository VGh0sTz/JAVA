package org.example.EJ6;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class OperacionesCRUDPilotos {
    //DONE
    public void CrearPiloto(Piloto p1, Connection con) throws SQLException{
        LocalDate date = LocalDate.parse(p1.getDob());
        String insercion="Insert Into drivers Values (?,?,?,?,?,?,?,?)";
        try(PreparedStatement insercio=con.prepareStatement(insercion)){
            insercio.setInt(1,p1.getDriverid());
            insercio.setString(2,p1.getCode());
            insercio.setString(3,p1.getForename());
            insercio.setString(4,p1.getSurname());
            insercio.setDate(5, Date.valueOf(date));
            insercio.setString(6,p1.getNationality());
            insercio.setInt(7,p1.getConstructorid());
            insercio.setString(8,p1.getUrl());
            insercio.executeUpdate();
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new SQLException();
        }
    }

    //DONE
    public Piloto LeerPiloto(int id, Connection con) throws SQLException {
        String select="Select * from drivers where driverid="+id;
        try(PreparedStatement Select=con.prepareStatement(select)){
            ResultSet rs= Select.executeQuery();
            return convertir_rs_a_piloto(rs);
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new SQLException();
        }
    }

    //DONE
    public ArrayList<Piloto> LeerPilotos(Connection con) throws SQLException {
        String select="Select * from drivers";
        ArrayList<Piloto> arr;
        try(PreparedStatement Select=con.prepareStatement(select)){
            ResultSet rs= Select.executeQuery();
            arr=convertir_rs_a_varios_pilotos(rs);
            return arr;
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new SQLException();
        }
    }

    //DONE
    public void ActualizarPiloto(Connection con,Piloto p1) {
        LocalDate date = LocalDate.parse(p1.getDob());
        String update="Update drivers set code=?, forename=?, surname=?, dob=?, nationality=?, constructorid=?, url=? Where driverid="+p1.getDriverid();
        try(PreparedStatement updater=con.prepareStatement(update)){
            updater.setString(1,p1.getCode());
            updater.setString(2,p1.getForename());
            updater.setString(3,p1.getSurname());
            updater.setDate(4, Date.valueOf(date));
            updater.setString(5,p1.getNationality());
            updater.setInt(6,p1.getConstructorid());
            updater.setString(7,p1.getUrl());
            int filas_afectadas=updater.executeUpdate();
            update(filas_afectadas);
        } catch (SQLException e) {
            System.err.println("No se pudo realizar el update");
            throw new RuntimeException(e);
        }

    }

    //DONE
    public void BorrarPiloto(Connection con,Piloto p1){
        String deletesql = "Delete from drivers where driverid="+p1.getDriverid();
        try(PreparedStatement delete=con.prepareStatement(deletesql)){
            int filas_afectadas=delete.executeUpdate();
            update(filas_afectadas);
        } catch (SQLException e) {
            System.err.println("No se pudo realizar el update");
            throw new RuntimeException(e);
        }
    }

    //DONE
    public void MostrarClasificacionPiloto(Connection con) throws Exception{
        String clasificacion= "SELECT d.driverid, CONCAT(d.forename, ' ', d.surname) AS driver_name," +
                " c.name AS constructor_name, SUM(r.points) AS total_points FROM drivers d JOIN" +
                " results r ON d.driverid = r.driverid LEFT JOIN constructors c ON d.constructorid =" +
                " c.constructorid GROUP BY d.driverid, d.forename, d.surname, c.name ORDER BY total_points DESC";
        try (PreparedStatement clasificar = con.prepareStatement(clasificacion)){
            ResultSet rs= clasificar.executeQuery();
            System.out.println("");
            System.out.println("TABLA DE LA CLASIFICACION DE PILOTOS");
            System.out.printf("%-15s %-20s %-20s %-20s%n", "driverid", "driver_name", "constructor_name", "total_points");
            while(rs.next()){
                Integer driverid=rs.getInt("driverid");
                String driver_name=rs.getString("driver_name");
                String constructor_name=rs.getString("constructor_name");
                Integer total_points =rs.getInt("total_points");
                System.out.printf("%-15s %-20s %-20s %-20s%n",driverid ,driver_name ,constructor_name ,total_points );
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new SQLException();
        }
    }

    //FALTA
    public void MostrarClasificacionConstructores(Connection con) throws SQLException {
        String clasificacion= "SELECT c.constructorid, c.name AS constructor_name, SUM(r.points) AS total_points FROM constructors c " +
                "LEFT JOIN drivers d ON c.constructorid = d.constructorid " +
                "LEFT JOIN results r ON d.driverid = r.driverid " +
                "GROUP BY c.constructorid, c.name " +
                "ORDER BY total_points DESC;";
        try (PreparedStatement clasificar = con.prepareStatement(clasificacion)){
            ResultSet rs= clasificar.executeQuery();
            System.out.println("");
            System.out.println("TABLA DE LA CLASIFICACION DE CONSTRUCTORES");
            System.out.printf("%-15s %-20s %-20s%n","ConstructorID" ,"Constructor_Name" ,"Total Points" );
            while(rs.next()){
                String constructorid=rs.getString("constructorid");
                String constructor_name=rs.getString("constructor_name");
                Integer total_points =rs.getInt("total_points");
                System.out.printf("%-15s %-20s %-20s%n",constructorid ,constructor_name ,total_points );
            }
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new SQLException();
        }
    }

    //DONE
    public static Piloto convertir_rs_a_piloto(ResultSet rs){
        //Convierte un result set a un piloto
        try {
            while (rs.next()) {
                Integer driverid = rs.getInt("driverid");
                String code = rs.getString("code");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String dob = rs.getDate("dob").toString();
                String nationality = rs.getString("nationality");
                Integer constructorid = rs.getInt("constructorid");
                String url = rs.getString("url");
                Piloto p1=new Piloto(driverid,code,forename,surname,dob,nationality,constructorid,url);
                return p1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //DONE
    public static ArrayList<Piloto> convertir_rs_a_varios_pilotos(ResultSet rs){
        //Convierte varios pilotos de un rs
        try {
            ArrayList<Piloto> pilotos=new ArrayList<>();
            while (rs.next()) {
                Integer driverid = rs.getInt("driverid");
                String code = rs.getString("code");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String dob = rs.getString("dob");
                String nationality = rs.getString("nationality");
                Integer constructorid = rs.getInt("constructorid");
                String url = rs.getString("url");
                Piloto p1=new Piloto(driverid,code,forename,surname,dob,nationality,constructorid,url);
                pilotos.add(p1);
            }
            return pilotos;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void update(int filas){
        if (filas > 0) {
            System.out.println("La operación fue exitosa.");
        } else {
            System.out.println("No se pudo realizar la operación");
        }
    }


}
