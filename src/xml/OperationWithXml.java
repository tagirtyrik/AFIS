/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xml;

//import Xml.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import exception.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;



/**
 *
 * @author Ксюша
 */
/**
 * AUHTUNG!!!! перекинул бсе функции по управлению данными из модели сюда (все get- del- add- set- функции)
 * Модель теперь только выбирает поставщика днных - OperationWithXml или DataAccessObject
 * данные модель больше не хранит
 * @author GeneraL
 */
public class OperationWithXml
{

    private static ArrayList<Flight> flights;
    private static ArrayList<Route> routes;
    private static ArrayList<Airport> airports;
    private static ArrayList<Plane> planes;
    private static int lastFlightId;
    private static int lastRouteId;
    private static int lastPlaneId;
    private static int lastAirportId;
    
    public static Flight getFlight(int id){
        for(int i=0;i<flights.size();i++){
                Flight object=(Flight)flights.get(i);
                if(object.getId()==id)return object;
            }
        throw new FlightNotFoundException(id);
    }
    public static Route getRoute(int id){
        for(int i=0;i<routes.size();i++){
            Route object=(Route)routes.get(i);
            if(object.getId()==id)
                return object;
        }
        throw new RouteNotFoundException(id);
    }
    public static Airport getAirport(int id){
        for(int i=0;i<airports.size();i++){
            Airport object=(Airport)airports.get(i);
            if(object.getId()==id)return object;
        }
        throw new AirportNotFoundException(id);
    }
    public static Plane getPlane(int id){
        for(int i=0;i<planes.size();i++){
            Plane object=(Plane)planes.get(i);
            if(object.getId()==id)return object;
        }
        throw new PlaneNotFoundException(id);
    }
    public static boolean setPlane(Plane plane)throws IOException,ClassNotFoundException{
        for(int i=0;i<planes.size();i++){
            Plane object=(Plane)planes.get(i);
            if(object.getId()==plane.getId()){
                planes.set(i, plane);
                saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
                return true;
            }
        }
        throw new PlaneNotFoundException(plane.getId());
    }
    public static boolean setRoute(Route route)throws IOException,ClassNotFoundException{
        for(int i=0;i<routes.size();i++){
            Route object=(Route)routes.get(i);
            if(object.getId()==route.getId()){
                routes.set(i, route);
                saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
                return true;
            }
        }
        throw new RouteNotFoundException(route.getId());
    }
    public static boolean setAirport(Airport airport)throws IOException,ClassNotFoundException{
        for(int i=0;i<airports.size();i++){
            Airport object=(Airport)airports.get(i);
            if(object.getId()==airport.getId()){
                airports.set(i, airport);
                saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
                return true; 
            }
        }
        throw new AirportNotFoundException(airport.getId());
    }
    public static boolean setFlight(Flight flight)throws IOException,ClassNotFoundException{
        for(int i=0;i<flights.size();i++){
            Flight object=(Flight)flights.get(i);
            if(object.getId()==flight.getId()){
                flights.set(i, flight);
                saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
                return true;
            }
        }
        throw new FlightNotFoundException(flight.getId());
    }
    
    public static int addPlane(Plane plane)throws IOException,ClassNotFoundException{
        plane.setId(nextPlaneId());
        planes.add(plane);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
        return plane.getId();
    }
    public static int addRoute(Route route)throws IOException,ClassNotFoundException{
        route.setId(nextRouteId());
        routes.add(route);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
        return route.getId();
    }
    public static int addAirport(Airport airport)throws IOException,ClassNotFoundException{
        airport.setId(nextAirportId());
        airports.add(airport);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
        return 0;
    }
    public static int addFlight(Flight flight)throws IOException,ClassNotFoundException{
        flight.setId(nextFlightId());
        flights.add(flight);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
        return flight.getId();
    }
    public static void addPlaneManual(Plane plane)throws IOException,ClassNotFoundException{
        plane.setId(plane.getId());
        planes.add(plane);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void addRouteManual(Route route)throws IOException,ClassNotFoundException{
        route.setId(route.getId());
        routes.add(route);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void addAirportManual(Airport airport)throws IOException,ClassNotFoundException{
        airport.setId(airport.getId());
        airports.add(airport);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void addFlightManual(Flight flight)throws IOException,ClassNotFoundException{
        flight.setId(flight.getId());
        flights.add(flight);
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }

     public static void deletePlane(int id)throws IOException,ClassNotFoundException{
         for(int i=0;i<planes.size();i++){
             if (planes.get(i).getId()==id){
                 planes.remove(i);
                 break;
             }
         }
         saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void deleteRoute(int id)throws IOException,ClassNotFoundException{
        for(int i=0;i<routes.size();i++){
             if (routes.get(i).getId()==id){
                 routes.remove(i);
                 break;
             }
         }
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void deleteAirport(int id)throws IOException,ClassNotFoundException{
        for(int i=0;i<airports.size();i++){
             if (airports.get(i).getId()==id){
                 airports.remove(i);
                 break;
             }
         }
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }
    public static void deleteFlight(int id)throws IOException,ClassNotFoundException{
        for(int i=0;i<flights.size();i++){
             if (flights.get(i).getId()==id){
                 flights.remove(i);
                 break;
             }
         }
        saveAll(planes, airports, routes, flights, lastPlaneId, lastAirportId, lastRouteId, lastFlightId);
    }       
            
            
    public static int flightCount(){
        return flights.size();
    }
    public static int routeCount(){
        return routes.size();
    }
    public static int airportCount(){
        return airports.size();
    }
    public static int planeCount(){
        return planes.size();
    }
    

    //дальше функции для генерации новых Id
    public static int nextPlaneId(){
        return lastPlaneId++;
    }
    public static int nextRouteId(){
        return lastRouteId++;
    }
    public static int nextAirportId(){
        return lastAirportId++;
    }
    public static int nextFlightId(){
        return lastFlightId++;
    }
    
    
    
    private static final String saveFileName="saveData.xml";//выкинул его сюда для простоты
    
    public static void initData()throws IOException,ClassNotFoundException{//эта штука загрузит данные в оперативку
        planes = OperationWithXml.getPlanes();
        airports = OperationWithXml.getAirports();
        routes = OperationWithXml.getRoutes();
        flights = OperationWithXml.getFlights();
        lastPlaneId = OperationWithXml.getPlaneId();
        lastAirportId = OperationWithXml.getAirportId();
        lastRouteId = OperationWithXml.getAirportId();
        lastFlightId = OperationWithXml.getFlightId();
    }
    //создаем объект "большой список", в котором будут храниться-
    //-все передаваемые списки и последнее id
    public static void saveAll(List<Plane> plane,
            List<Airport> airport,
            List<Route> route,
            List<Flight> flight,
            int lastPlaneId,
            int lastAirportId,
            int lastRouteId, 
            int lastFlightId) throws IOException, ClassNotFoundException {
        //здесь мы сохраним все
        List<Object> bigList = new ArrayList<>();
        bigList.add(plane);
        bigList.add(airport);
        bigList.add(route);
        bigList.add(flight);
        bigList.add(lastPlaneId);
        bigList.add(lastAirportId);
        bigList.add(lastRouteId);
        bigList.add(lastFlightId);
        marshalling(bigList);
    }
    private static List<Object> defaultList(){
        List<Object> list = new ArrayList<>();
        list.add(new ArrayList<Plane>());
                list.add(new ArrayList<Airport>());
                list.add(new ArrayList<Route>());
                list.add(new ArrayList<Flight>());
                list.add(0);
                list.add(0);
                list.add(0);
                list.add(0); 
        return list;
    }
    private static ArrayList<Object> unmarshalling() throws IOException, ClassNotFoundException
    {//чтение из xml
        List<Object> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());//создаем объект
        try{
            
                xStream.alias("Data", List.class);
                xStream.alias("Id", int.class);
                xStream.registerConverter((Converter) new EncodedByteArrayConverter());//какое-то конвертирование
            if(saveIsCorrect()){
                list = (ArrayList<Object>)xStream.fromXML(new File(saveFileName));//считываение объекта "большой список" из файла
            } else{//значения по-умолчанию
                list=defaultList();
            }
        }
        catch(Exception e)
        {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
            saveAll(new ArrayList<Plane>(),
                    new ArrayList<Airport>(), 
                    new ArrayList<Route>(), 
                    new ArrayList<Flight>(), 0, 0, 0, 0);
            System.exit(0);

        }
        return (ArrayList<Object>) list;
    }
    public static ArrayList<Object> addObjects(String xml)
    {
        List<Object> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());//создаем объект
        try{

            xStream.alias("Data", List.class);
            xStream.alias("Id", int.class);
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());//какое-то конвертирование
            if(saveIsCorrect()){
                list = (ArrayList<Object>)xStream.fromXML(xml);//считываение объекта "большой список" из файла
            } else{//значения по-умолчанию
                list=defaultList();
            }
        }
        catch(Exception e)
        {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
            System.exit(0);

        }
        return (ArrayList<Object>) list;
    }





    private static String marshalling(List bigList) throws IOException
    {//запись в xml
        XStream xStream = new XStream(new DomDriver()); //создаем объект XStream
        xStream.alias("Data", List.class);
        xStream.alias("Id", int.class);
    //    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(saveFileName)));//записываем текст xml обычным бафером в файл saveData.xml
        String xml = xStream.toXML(bigList);//запихиваем в xml наш объект "большой список"
        return xml;
    /*    bufferedWriter.write(xml);//записываем xml в файл
        bufferedWriter.close();*/
    }

    public static String savePlane(ArrayList<Plane> planes) throws IOException {

      return marshalling(planes);

    }
    public static String saveAirport(ArrayList<Airport> airports) throws IOException {

        return  marshalling(airports);
    }
    public static String saveRoute(ArrayList<Route> routes) throws IOException {

        return  marshalling(routes);
    }
    public static String saveFlight(ArrayList<Flight> flights) throws IOException {

        return  marshalling(flights);
    }
 /*   public static ArrayList<Plane> recoveryPlane(String xml) throws IOException {

        List<Plane> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());
        try {

            xStream.alias("Data", List.class);
            xStream.alias("Id", int.class);
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());
            if (saveIsCorrect()) {
                list = (ArrayList<Plane>) xStream.fromXML(xml);
                System.out.println(list.get(1).getName());
            }
        } catch (Exception e) {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
        }
        return (ArrayList<Plane>) list;
    }
    public static ArrayList<Airport> recoveryAirport(String xml) throws IOException {

        List<Airport> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());
        try{

            xStream.alias("Data", List.class);
            xStream.alias("Id", int.class);
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());
            if(saveIsCorrect())
            {
                list = (ArrayList<Airport>)xStream.fromXML(xml);
            }
        }
        catch(Exception e)
        {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
        }
        return (ArrayList<Airport>) list;
    }
    public static ArrayList<Route> recoveryRoute(String xml) throws IOException {

        List<Route> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());
        try{

            xStream.alias("Data", List.class);
            xStream.alias("Id", int.class);
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());
            if(saveIsCorrect())
            {
                list = (ArrayList<Route>)xStream.fromXML(xml);
            }
        }
        catch(Exception e)
        {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
        }
        return (ArrayList<Route>) list;
    }
    public static ArrayList<Flight> recoveryFlight(String xml) throws IOException {

        List<Flight> list = new ArrayList<>();
        XStream xStream = new XStream(new DomDriver());
        try{

            xStream.alias("Data", List.class);
            xStream.alias("Id", int.class);
            xStream.registerConverter((Converter) new EncodedByteArrayConverter());
            if(saveIsCorrect())
            {
                list = (ArrayList<Flight>)xStream.fromXML(xml);
            }
        }
        catch(Exception e)
        {
            System.err.println("Извините, ошибка открытия файла. Перезапустите программу.");
        }
        return (ArrayList<Flight>) list;
    }
*/
     //я решила сделать еще отдельные сохранения каждого списка и id,если не надо, то можно убрать
    private static void save(int index, Object object) throws IOException, ClassNotFoundException
    {
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();//получаем наш объект "большой список" из xml
        bigList.set(index, object);//под 0-м индексом у нас хранится список plane, поэтому изменяем его значение
        //вызываем saveAll и снова все сохраняем
        saveAll((List<Plane>)bigList.get(0),(List<Airport>)bigList.get(1),(List<Route>)bigList.get(2),(List<Flight>)bigList.get(3),(int)bigList.get(4),(int)bigList.get(5),(int)bigList.get(6), (int) bigList.get(7));
    }
 
    //получаем мы все элементыпо отдельности
    public static ArrayList<Plane> getPlanes() throws IOException, ClassNotFoundException
    {//например, получение plane, остальное аналогично
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();//получаем "большой список"
        return (ArrayList<Plane>) bigList.get(0);//возвращаем элемент под необходимым индексом
    }
    
    public static ArrayList<Airport> getAirports() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (ArrayList<Airport>) bigList.get(1);
    }
    public static ArrayList<Route> getRoutes() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (ArrayList<Route>) bigList.get(2);
    }
    public static ArrayList<Flight> getFlights() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (ArrayList<Flight>) bigList.get(3);
    }
    public static int getPlaneId() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (int) bigList.get(4);
    }
    public static int getAirportId() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return(int) bigList.get(5);
    }
     public static int getRouteId() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (int) bigList.get(6);
    }
      public static int getFlightId() throws IOException, ClassNotFoundException
    { 
        List<Object> bigList = new ArrayList<>();
        bigList = unmarshalling();
        return (int) bigList.get(7);
    }
      public static void delAll() throws IOException ,ClassNotFoundException{
        
        List<Object> bigList =defaultList();
        marshalling(bigList);
        
        initData();
    }
    //как-то так
      private static boolean saveIsCorrect(){
          return new File(saveFileName).exists();
      }
}
