
package db;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
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
    private static Connection connection;

     private static final String url = "jdbc:postgresql://127.0.0.1:5432/Afis";
    //имя пользователя
    private final static String name = "postgres";
    //пароль
    private static final String password = "1234";

    private static final String driverName="org.postgresql.Driver";


    private static final DateFormat fromDatabaseformatter =new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
    private static final DateFormat fromClientformatter =new SimpleDateFormat("dd.MM.yy-HH:mm");
    private static final SimpleDateFormat toDatabaseFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
    private static boolean useDataSourse=false;
    public static void setUseDataSourse(boolean useDataSourse) {
        DataAccessObject.useDataSourse = useDataSourse;
    }

    public static void  closeConnection()throws SQLException{
        if(!useDataSourse)connection.close();
    }
    /**
     * инициализация соединения с БД и создание таблиц, если их нет
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void  initConnection()throws SQLException,ClassNotFoundException{
    if(useDataSourse) {
        try {

            Class.forName(driverName);
            connection = DriverManager.getConnection(url, name, password);
                Statement statement = connection.createStatement();
                statement.execute("SELECT 1");
            System.out.println("Connection from Driver Manager");
        }catch (Exception e){
            System.err.println("Unable to use DataSourse");
            System.err.println("Application working in DriverManager mode");
            useDataSourse=false;
            initConnection();
            return;
        }
    }
        try{
            getPlanes();
            System.err.println("Select table "+Sql.Table.Names.plane);
        }catch (SQLException e){
            System.err.println(e.toString());
        }try{
           getAirports();
            System.err.println("Select table "+Sql.Table.Names.airport);
        }catch (SQLException e){
            System.err.println(e.toString());
        }try{
            getRoutes();
            System.err.println("Select table "+Sql.Table.Names.route);
            
        }catch (SQLException e){
            System.err.println(e.toString());
        }try{
            getFlights();
            System.err.println("Select table "+Sql.Table.Names.flight);
        }catch (SQLException e){
            System.err.println(e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Plane> getPlanes() throws SQLException, ClassNotFoundException {
        return selectPlanes(Sql.Plane.selectAll);
    }
    private static ArrayList<Plane> selectPlanes(String sql) throws SQLException, ClassNotFoundException {
       /* ArrayList<Plane> rezult=new ArrayList<Plane>();
        Statement statement = connection.createStatement();
            ResultSet sqlResult = statement.executeQuery(sql);
        while (sqlResult.next()) {
            int id=sqlResult.getInt(Sql.Plane.Field.id);
            String name=sqlResult.getString(Sql.Plane.Field.planeName);
            String number=sqlResult.getString(Sql.Plane.Field.planeNumber);
            int passengers=sqlResult.getInt(Sql.Plane.Field.passengerSeatsCount);
            double fuelCons=sqlResult.getDouble(Sql.Plane.Field.fuelConsumption);
            Plane plane=new Aircraft(id,number);
            plane.setFuelConsumption(fuelCons);
            plane.setName(name);
            plane.setPassengerSeatsCount(passengers);
            rezult.add(plane);
        }*/
        return Hdb.getPlane();
    }
    public static Plane getPlane(int id) throws SQLException,PlaneNotFoundException{
        Plane rezult=null;
        PreparedStatement statement = connection.prepareStatement(Sql.Plane.select);
        statement.setInt(1, id);
        ResultSet sqlResult =statement.executeQuery();
        int count=0;
        while (sqlResult.next()) {
            String name=sqlResult.getString(Sql.Plane.Field.planeName);
            String number=sqlResult.getString(Sql.Plane.Field.planeNumber);
            int passengers=sqlResult.getInt(Sql.Plane.Field.passengerSeatsCount);
            double fuelCons=sqlResult.getDouble(Sql.Plane.Field.fuelConsumption);
            rezult=new Aircraft(id,number);
            rezult.setFuelConsumption(fuelCons);
            rezult.setName(name);
            rezult.setPassengerSeatsCount(passengers);
            count++;
        }
        if (count==0) throw new PlaneNotFoundException(id);
        return rezult;
    }
    public static ArrayList<Plane> getPlane(String id,String name,String number,
        String passengerSeatsCount,String fuelCons) throws SQLException, ClassNotFoundException {
        StringBuilder where=new StringBuilder("(");
        insertIntArg(where,Sql.Plane.Field.id,id);
        insertStringArg(where,Sql.Plane.Field.planeName,name);
        insertStringArg(where, Sql.Plane.Field.planeNumber, number);
        insertIntArg(where,Sql.Plane.Field.passengerSeatsCount,passengerSeatsCount);
        insertDoubleArg(where,Sql.Plane.Field.fuelConsumption,fuelCons);
     if(where.toString().equals("("))where=new StringBuilder("true");
     else where.append(")");
     //System.err.println((Sql.Plane.selectWhere+where));
     return selectPlanes((Sql.Plane.selectWhere+where));
    }

    public static boolean setPlane(Plane plane)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Plane.update);
        //System.err.println(Sql.Plane.update);
        statement.setString(1, plane.getName());
        statement.setString(2, plane.getNumber());
        statement.setDouble(3, (plane.getFuelConsumption()));
        statement.setInt(4, (plane.getPassengerSeatsCount()));
        statement.setInt(5, (plane.getId()));          
        return statement.executeUpdate()==1;
    }
    public static int addPlane(Plane plane)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Plane.addManual);
        //System.err.println(Sql.Plane.add);
        statement.setInt(1, plane.getId());
        statement.setString(2, plane.getNumber());
        statement.setString(3, plane.getName());
        statement.setDouble(4, plane.getFuelConsumption());
        statement.setInt(5, plane.getPassengerSeatsCount());
        statement.executeUpdate();
        return 0;//тут вернуть id
    }
    public static void addPlaneManual(Plane plane)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Plane.addManual);
        //System.err.println(Sql.Plane.add);
        statement.setInt(1, plane.getId());
        statement.setString(2, plane.getName());
        statement.setString(3, plane.getNumber());
        statement.setDouble(4, plane.getFuelConsumption());
        statement.setInt(5, plane.getPassengerSeatsCount());
        statement.executeUpdate();
      //  return 0;//тут вернуть id
    }
    public static ArrayList<Airport> getAirports() throws SQLException{
        return selectAirports(Sql.Airport.selectAll);
    }
    public static ArrayList<Airport> getAirports(String id,String name,String location
            )throws SQLException{
        StringBuilder where=new StringBuilder("(");
        insertIntArg(where,Sql.Airport.Field.id,id);
        insertStringArg(where,Sql.Airport.Field.airportName,name);
        insertStringArg(where,Sql.Airport.Field.airportLocation,location);
     if(where.toString().equals("("))where=new StringBuilder("true");
     else where.append(")");
     System.err.println((Sql.Airport.selectWhere+where));
     return selectAirports((Sql.Airport.selectWhere+where));
    }
    public static ArrayList<Airport> selectAirports(String sql) throws SQLException{
        ArrayList<Airport> rezult=new ArrayList<Airport>();
        Statement statement = connection.createStatement();
            ResultSet sqlResult = statement.executeQuery(sql);
            //System.err.println(Sql.Airport.selectAll);
        while (sqlResult.next()) {
            int id=sqlResult.getInt(Sql.Airport.Field.id);
            String name=sqlResult.getString(Sql.Airport.Field.airportName);
            String location=sqlResult.getString(Sql.Airport.Field.airportLocation);
            Airport port=new InternationalAirport(id,name,location);
            rezult.add(port);
        }
        return rezult;
    }
    public static Airport getAirport(int id) throws SQLException,RouteNotFoundException{
        Airport rezult=null;
        PreparedStatement statement = connection.prepareStatement(Sql.Airport.select);
        //System.err.println(Sql.Airport.select);
        statement.setInt(1, id);
        ResultSet sqlResult =statement.executeQuery();
        int count=0;
        while (sqlResult.next()) {
            String name=sqlResult.getString(Sql.Airport.Field.airportName);
            String location=sqlResult.getString(Sql.Airport.Field.airportLocation);
            rezult=new InternationalAirport(id,name,location);
            count++;
        }
        if (count==0) throw new AirportNotFoundException(id);
        return rezult;
    }
    public static boolean setAirport(Airport airport)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Airport.update);
        //System.err.println(Sql.Airport.update);
        statement.setString(1, airport.getName());
        statement.setString(2, airport.getLocation());
        statement.setInt(3, (airport.getId()));         
        return statement.executeUpdate()==1;
    }
    public static int addAirport(Airport airport)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Airport.addManual);
        //System.err.println(Sql.Airport.add);
        statement.setInt(1, airport.getId());
        statement.setString(2, airport.getName());
        statement.setString(3, airport.getLocation());
        statement.executeUpdate();
        return 0;
    }
    public static void addAirportManual(Airport airport)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Airport.addManual);
        //System.err.println(Sql.Airport.add);
        statement.setInt(1,airport.getId());
        statement.setString(2, airport.getName());
        statement.setString(3, airport.getLocation());
        statement.executeUpdate();
       // return 0;//тут вернуть id
    }
    public static ArrayList<Route> getRoutes()throws SQLException{
        return selectRoutes(Sql.Route.selectAll);
    }
    public static ArrayList<Route> getRoutes(String id,String takeOffId,
        String landId,String dist)throws SQLException{
        StringBuilder where=new StringBuilder("(");
        insertIntArg(where,Sql.Route.Field.id,id);
        insertIntArg(where,Sql.Route.Field.takeOffPortId,takeOffId);
        insertIntArg(where,Sql.Route.Field.landingPortId,landId);
        insertDoubleArg(where,Sql.Route.Field.distance,dist);
     if(where.toString().equals("("))where=new StringBuilder("true");
     else where.append(")");
     System.err.println((Sql.Route.selectWhere+where));
     return selectRoutes((Sql.Route.selectWhere+where));
    }
     public static ArrayList<Route> selectRoutes(String sql)throws SQLException{
        ArrayList<Route> rezult=new ArrayList<Route>();
        Statement statement = connection.createStatement();
            ResultSet sqlResult = statement.executeQuery(sql);
        while (sqlResult.next()) {
            int id=sqlResult.getInt(Sql.Route.Field.id);
            int takeoffId=sqlResult.getInt(Sql.Route.Field.takeOffPortId);
            int landingId=sqlResult.getInt(Sql.Route.Field.landingPortId);
            double distance=sqlResult.getDouble(Sql.Route.Field.distance);
            rezult.add(new RegularRoute(id,takeoffId,landingId,distance));
        }
        return rezult;
    }   
    public static Route getRoute(int id) throws SQLException,RouteNotFoundException{
        Route route=null;
        PreparedStatement statement = connection.prepareStatement(Sql.Route.select);
        //System.err.println(Sql.Route.select);
        statement.setInt(1, id);
        ResultSet sqlResult = statement.executeQuery();
        int count=0;
        while (sqlResult.next()) {
            int takeoffId=sqlResult.getInt(Sql.Route.Field.takeOffPortId);
            int landingId=sqlResult.getInt(Sql.Route.Field.landingPortId);
            double distance=sqlResult.getDouble(Sql.Route.Field.distance);
            route=new RegularRoute(id,takeoffId,landingId,distance);
            count++;
        }
        if (count==0) throw new RouteNotFoundException(id);
        return route;
    }
    public static boolean setRoute(Route route)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Route.update);
        //System.err.println(Sql.Route.update);
        statement.setInt(1, (route.getTakeOffPort()));
        statement.setInt(2, (route.getLandingPort()));
        statement.setDouble(3, (route.getDistance())); 
        statement.setInt(4, (route.getId())); 
        return statement.executeUpdate()==1;
    }
    public static int addRoute(Route route)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Route.addManual);
        //System.err.println(Sql.Route.add);
        statement.setInt(1, (route.getId()));
        statement.setInt(2, (route.getTakeOffPort()));
        statement.setInt(3, (route.getLandingPort()));
        statement.setDouble(4, (route.getDistance()));
        statement.executeUpdate();
        return 0;//тут вернуть id
    }
    public static void addRouteManual(Route route)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Route.addManual);
        //System.err.println(Sql.Route.add);
        statement.setInt(1, (route.getId()));
        statement.setInt(2, (route.getTakeOffPort()));
        statement.setInt(3, (route.getLandingPort()));
        statement.setDouble(4, (route.getDistance()));
        statement.executeUpdate();
       // return 0;//тут вернуть id
    }
    public static ArrayList<Flight> getFlights()throws SQLException,java.text.ParseException{
        return selectFlights(Sql.Flight.selectAll);
    }
    public static ArrayList<Flight> getFlights(String id,String routeId,
        String planeId,String takeOffTime,String landTime)throws SQLException,java.text.ParseException{
        StringBuilder where=new StringBuilder("(");
        insertIntArg(where,Sql.Flight.Field.id,id);
        insertIntArg(where,Sql.Flight.Field.routeId,routeId);
        insertIntArg(where,Sql.Flight.Field.planeId,planeId);
        insertDateArg(where,Sql.Flight.Field.takeOffTime,takeOffTime);
        insertDateArg(where,Sql.Flight.Field.landingTime,landTime);
     if(where.toString().equals("("))where=new StringBuilder("true");
     else where.append(")");
     //System.err.println((Sql.Flight.selectWhere+where));
     return selectFlights((Sql.Flight.selectWhere+where));
     //getfflight 1 1 1 null null 1
    }
    public static ArrayList<Flight> selectFlights(String sql)throws SQLException,java.text.ParseException{
        ArrayList<Flight> rezult=new ArrayList<Flight>();
        Statement statement = connection.createStatement();
            ResultSet sqlResult = statement.executeQuery(sql);
        while (sqlResult.next()) {
            int id=sqlResult.getInt(Sql.Flight.Field.id);
            int planeId=sqlResult.getInt(Sql.Flight.Field.planeId);
            int routeId=sqlResult.getInt(Sql.Flight.Field.routeId);
            java.util.Date takeoffTime=fromDatabaseformatter.parse(sqlResult.getString(Sql.Flight.Field.takeOffTime));
            java.util.Date landindTime=fromDatabaseformatter.parse(sqlResult.getString(Sql.Flight.Field.landingTime));
            rezult.add(new ReguarFlight(id,planeId,routeId,takeoffTime,landindTime));
        }
        return rezult;
    }
    public static Flight getFlight(int id) throws SQLException,FlightNotFoundException,java.text.ParseException{
        Flight flight=null;
        PreparedStatement statement = connection.prepareStatement(Sql.Flight.select);
        //System.err.println(Sql.Flight.select);
        statement.setInt(1, id);
        ResultSet sqlResult =statement.executeQuery();
        int count=0;
        while (sqlResult.next()) {
            int planeId=sqlResult.getInt(Sql.Flight.Field.planeId);
            int routeId=sqlResult.getInt(Sql.Flight.Field.routeId);
            java.util.Date takeoffTime=fromDatabaseformatter.parse(sqlResult.getString(Sql.Flight.Field.takeOffTime));
            java.util.Date landindTime=fromDatabaseformatter.parse(sqlResult.getString(Sql.Flight.Field.landingTime));
            flight=new ReguarFlight(id,planeId,routeId,takeoffTime,landindTime);
            count++;
        }
        if (count==0) throw new FlightNotFoundException(id);
        return flight;
    }

    //разобраться с датами
    public static boolean setFlight(Flight flight)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Flight.update);
        statement.setInt(1, (flight.getPlane()));
        statement.setInt(2, (flight.getRoute()));
        statement.setTimestamp(3, new java.sql.Timestamp(flight.getTakeOffTimeShedule().getTime()));
        statement.setTimestamp(4, new java.sql.Timestamp(flight.getLandingTimeShedule().getTime()));
        statement.setInt(5, flight.getId());
        return statement.executeUpdate()==1;
    }
    public static int addFlight(Flight flight)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Flight.addManual);
        //System.err.println(Sql.Flight.add);
        statement.setInt(1, (flight.getId()));
        statement.setInt(2, (flight.getPlane()));
        statement.setInt(3, (flight.getRoute()));
        statement.setDate(4, new java.sql.Date(flight.getTakeOffTimeShedule().getTime()));
        statement.setDate(5, new java.sql.Date(flight.getLandingTimeShedule().getTime()));
        statement.executeUpdate();
        return 0;//тут вернуть id
    }
    public static void addFlightManual(Flight flight)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Flight.addManual);
        //System.err.println(Sql.Flight.add);
        statement.setInt(1, (flight.getId()));
        statement.setInt(2, (flight.getPlane()));
        statement.setInt(3, (flight.getRoute()));
        statement.setDate(4, new java.sql.Date(flight.getTakeOffTimeShedule().getTime()));
        statement.setDate(5, new java.sql.Date(flight.getLandingTimeShedule().getTime()));
        statement.executeUpdate();
     //   return 0;//тут вернуть id
    }
    public static boolean deletePlane(int id)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Plane.delete);
        statement.setInt(1, id);
        return statement.executeUpdate()==1;
    }
    public static boolean deleteAirport(int id)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Airport.delete);
        statement.setInt(1, id);
        return statement.executeUpdate()==1;
    }
    public static boolean deleteRoute(int id)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Route.delete);
        statement.setInt(1, id);
        return statement.executeUpdate()==1;
    }
    public static boolean deleteFlight(int id)throws SQLException{
        PreparedStatement statement = connection.prepareStatement(Sql.Flight.delete);
        statement.setInt(1, id);
        return statement.executeUpdate()==1;
    }
    public static boolean dropData()throws SQLException,ClassNotFoundException{
        boolean isOne=true;
        PreparedStatement statement = connection.prepareStatement(Sql.Table.dropFlight);
        if(statement.executeUpdate()!=1)isOne=false;
        statement = connection.prepareStatement(Sql.Table.dropRoute);
        if(statement.executeUpdate()!=1)isOne=false;
        statement = connection.prepareStatement(Sql.Table.dropAirport);
        if(statement.executeUpdate()!=1)isOne=false;
        statement = connection.prepareStatement(Sql.Table.dropPlane);
        if(statement.executeUpdate()!=1)isOne=false;
        closeConnection();
        initConnection();
        return isOne;
        
    }
    private static void insertStringArg(StringBuilder where,String name,String arg){
        insertStringArg( where, name, arg,true);
    }
    private static void insertStringArg(StringBuilder where,String name,String arg,boolean useLike){
        if(!arg.equalsIgnoreCase("null")){
            String logic = " OR ";
        if(!(where.toString().equals("") || where.toString().equals("(")))where.append(logic);
        where.append(name);
            if (useLike)where.append(" LIKE '");
            else where.append("='");
        where.append(arg.replace("'", "\'"));
        where.append("'");
            System.err.println(where);
        }
    }
    private static void insertIntArg(StringBuilder where,String name,String arg){
        if(!arg.equalsIgnoreCase("null")){
        String logic = " OR ";
        if(!(where.toString().equals("") || where.toString().equals("(")))where.append(logic);
        where.append(name);
        where.append("=");
        where.append(Integer.toString(Integer.parseInt(arg)));
        }
    }
    private static void insertDoubleArg(StringBuilder where,String name,String arg){
        if(!arg.equalsIgnoreCase("null")){
            String logic = " OR ";
        if(!(where.toString().equals("") || where.toString().equals("(")))where.append(logic);
        where.append(name);
        where.append("=");
        where.append(Double.toString(Double.parseDouble(arg)));
        }
    }
    private static void insertDateArg(StringBuilder where,String name,String arg) throws java.text.ParseException{
        if(!arg.equalsIgnoreCase("null")){
            String logic = " OR ";
        if(!(where.toString().equals("") || where.toString().equals("(")))where.append(logic);
        where.append(name);
        where.append("='");
        java.util.Date DateTime=fromClientformatter.parse(arg);
        where.append((toDatabaseFormat.format(DateTime)));
        where.append("'");
        }
    }
}
