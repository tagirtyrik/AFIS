
package db;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;
import model.aircraft.*;
import model.airport.*;
import model.flight.*;
import model.route.*;
import exception.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * объект для доступа к базе данных
 * *управляет соединением
 * *плодит объекты
 * *переводит объекты в SQL
 * *не содержит самого SQL
 * *данные не хранит
 * @author Ксю
 */
public class DataAccessObject {
    /**
     * инициализация соединения с БД и создание таблиц, если их нет
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void  initConnection()throws SQLException,ClassNotFoundException{
        try{
            getPlanes();
            getAirports();
            getRoutes();
            getFlights();
        }catch (Exception e){
            System.err.println(e.toString());
        }
    }
    public static ArrayList<Plane> getPlanes() throws SQLException, ClassNotFoundException {
        return Hdb.Plane.getAll();
    }
    public static Plane getPlane(int id) throws SQLException,PlaneNotFoundException{
        return Hdb.Plane.get(id);
    }
    public static ArrayList<Plane> getPlaneSearch(String id,String name,String number, String passengerSeatsCount,String fuelCons) throws SQLException, ClassNotFoundException {
     return Hdb.Plane.getWhere(id, name, number, passengerSeatsCount, fuelCons);
    }
    public static void setPlane(Plane plane)throws SQLException{
        Hdb.Plane.add(plane);

    }
    public static void addPlane(Plane plane)throws SQLException{
        Hdb.Plane.add(plane);
    }
    public static void deletePlane(int id)throws SQLException{
        Hdb.Plane.delete(id);
    }



    public static ArrayList<Airport> getAirports() throws SQLException{
        return Hdb.Airport.getAll();
    }
    public static ArrayList<Airport> getAirportsSearch(String id,String name,String location)throws SQLException{
     return Hdb.Airport.getWhere(id,location,name);
    }
    public static Airport getAirport(int id) throws SQLException,RouteNotFoundException{
        return Hdb.Airport.get(id);
    }
    public static void setAirport(Airport airport)throws SQLException{
        Hdb.Airport.update(airport);
    }
    public static void addAirport(Airport airport)throws SQLException{
        Hdb.Airport.add(airport);
    }
    public static void deleteAirport(int id)throws SQLException{
        Hdb.Airport.delete(id);
    }



    public static ArrayList<Route> getRoutes()throws SQLException{
        return Hdb.Route.getAll();
    }
    public static ArrayList<Route> getRoutesSearch(String id, String takeOffId, String landId,String dist)throws SQLException{
     return Hdb.Route.getWhere(id, dist,takeOffId, landId);
    }
    public static Route getRoute(int id) throws SQLException,RouteNotFoundException{
        return Hdb.Route.get(id);
    }
    public static void setRoute(Route route)throws SQLException{
        Hdb.Route.update(route);
    }
    public static void addRoute(Route route)throws SQLException{
        Hdb.Route.add(route);
    }
    public static void deleteRoute(int id)throws SQLException{
        Hdb.Route.delete(id);
    }


    public static ArrayList<Flight> getFlights()throws SQLException,java.text.ParseException{
        return Hdb.Flight.getAll();
    }
    public static ArrayList<Flight> getFlightsSearch(String id,String routeId, String planeId, String takeOffTime, String landTime)throws SQLException,java.text.ParseException{

        return Hdb.Flight.getWhere(id,planeId, routeId, takeOffTime, landTime);
    }
    public static Flight getFlight(int id) throws SQLException,FlightNotFoundException,java.text.ParseException{
        return Hdb.Flight.get(id);
    }
    public static void setFlight(Flight flight)throws SQLException{
        Hdb.Flight.update(flight);
    }
    public static void addFlight(Flight flight)throws SQLException{
        Hdb.Flight.add(flight);
    }
    public static void deleteFlight(int id)throws SQLException{
        Hdb.Flight.delete(id);
    }
}
