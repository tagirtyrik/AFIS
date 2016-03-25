package model;
import exception.ComandException;
import java.io.IOException;
import java.util.ArrayList;
import db.DataAccessObject;
import java.sql.SQLException;

/**
 * модель управляет источником данных, поддерживает работоспособность контроллера
 * @author Ксю
 */
public class Model {
    public Model(boolean useDataSourse){
        DataAccessObject.setUseDataSourse(useDataSourse);
    }
    public Model(){
    }
    public void close()throws SQLException{
        DataAccessObject.closeConnection();
        
    }
    /**
     * возвращает весь список рейсов
     * @return список рейсов
     */
    public ArrayList getFlights()throws SQLException,java.text.ParseException,IOException, ClassNotFoundException{
        return DataAccessObject.getFlights();
    }
    public ArrayList getFlights(String id,String routeId,String planeId, String takeOffTime,String landingTime)throws SQLException,ComandException,java.text.ParseException{
        return DataAccessObject.getFlights(id,routeId,planeId, takeOffTime,landingTime);
    }
    /**
     * возвращает весь список маршрутов
     * @return список маршрутов
     */
    public ArrayList getRoutes()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getRoutes();
    }
    public ArrayList getRoutes(String id,String takeOffId,String LandId, String distanse)throws SQLException,ComandException{
        return DataAccessObject.getRoutes(id, takeOffId, LandId,distanse);
    }
    /**
     * возвращает весь список аэропортов
     * @return список аэропортов
     */
    public ArrayList getAirports()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getAirports();
    }
    public ArrayList getAirports(String id, String name,String location)throws SQLException,ComandException{
        return DataAccessObject.getAirports(id, name, location);
    }
    /**
     * возвращает весь список самолетов
     * @return список самолетов
     */
    public ArrayList getPlanes()throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getPlanes();
    }
    public ArrayList getPlanes(String id,String name,String number,
        String passengerSeatsCount,String fuelCons) throws SQLException, ComandException, ClassNotFoundException {
        return DataAccessObject.getPlane(id, name, number, passengerSeatsCount, fuelCons);
    }
    /**
     * выбор рейса по его id
     * @param id идентификатор элемента в коллекции
     * @return Рейс
     */
    public Flight takeFlight(int id)throws SQLException,java.text.ParseException{
        return DataAccessObject.getFlight(id);
    }
    /**
     * выбор маршрута по его id
     * @param id идентификатор элемента в коллекции
     * @return маршрут
     */
    public Route takeRoute(int id)throws SQLException{
        return DataAccessObject.getRoute(id);
    }
    /**
     * выбор аэропорта по его id
     * @param id идентификатор элемента в коллекции
     * @return аэропорт
     */
    public Airport takeAirport(int id)throws SQLException{
        return DataAccessObject.getAirport(id);
    }
    /**
     * выбор самолета по его id
     * @param id идентификатор элемента в коллекции
     * @return самолет
     */
    public Plane takePlane(int id)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.getPlane(id);
    }
    public boolean setPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.setPlane(plane);

    }
    public boolean setAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.setAirport(airport);
    }
     public boolean setRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
         return DataAccessObject.setRoute(route);
    } 
     public boolean setFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
         return DataAccessObject.setFlight(flight);
     }
    /**
     * добавляет самолет в список, возвращает его номер в списке
     * (если существует - заменяет кго)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param plane добавлекмый самолет
     * @return индекс самолета в коллекции после добавления
     */
    public int addPlane(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.addPlane(plane);
    }
    /**
     * добавляет аэропорт в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param airport добавляемый аэропорт
     * @return индекс аэропорта в коллекции после добавления
     */
    public int addAirport(Airport airport)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.addAirport(airport);
    }
    /**
     * добавляет маршрут в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param route добавляемый маршрут
     * @return индекс маршрута в коллекции после добавления
     */
     public int addRoute(Route route)throws SQLException,IOException, ClassNotFoundException{
        return DataAccessObject.addRoute(route);
    } 
     /**
     * добавляет рейс в список, возвращает его номер в списке
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param flight добавляемый рейс
     * @return индекс рейса в коллекции после добавления
     */
     public int addFlight(Flight flight)throws SQLException,IOException, ClassNotFoundException{
         return DataAccessObject.addFlight(flight);
     }
    /**
     * добавляет самолет в список, с заданным id
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param plane добавляемый самолет
     * @return 0
     */
    public void addPlaneManual(Plane plane)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.addPlaneManual(plane);
    }
    /**
     * добавляет аэропорт в список, с заданным id
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param port добавляемый аэропорт
     * @return 0
     */
    public void addAirportManual(Airport port)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.addAirportManual(port);
    }
    /**
     * добавляет маршрут в список,с заданным id
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param route добавляемый маршрут
     * @return 0
     */
    public void addRouteManual(Route route)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.addRouteManual(route);
    }

    /**
     * добавляет рейс в список, с заданным id
     * (если существует - заменяет его)
     * если в списке есть объект с таким id - заменяет его, иначе добавляет новый
     * @param flight добавляемый рейс
     * @return 0
     */
    public void addFlightManual(Flight flight)throws SQLException,IOException, ClassNotFoundException{
        DataAccessObject.addFlightManual(flight);
    }
    /**
      * удаляет рейс
      * @param id - идентификатор рейса
      */
     public void deleteFlight(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteFlight(id);
     }
     /**
      * удаляет аэропорт
      * @param id - идентификатор аэропорта
      */
     public void deleteAirport(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteAirport(id);
     }
     /**
      * удаляет маршрут
      * @param id - идентификатор маршрута
      */
     public void deleteRoute(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deleteRoute(id);
     }
     /**
      * удаляет самолет
      * @param id - идентификатор самолета
      */
     public void deletePlane(int id)throws SQLException,IOException, ClassNotFoundException{
         DataAccessObject.deletePlane(id);
     }
    public void loadData() throws IOException, ClassNotFoundException {
            try
            {
                DataAccessObject.initConnection();
            }
            catch(ClassNotFoundException | SQLException e)
            {
                System.err.println(e.toString());
                System.err.println("unable to connect database");
            }
    }
    public void delAll() throws IOException, ClassNotFoundException,SQLException{
        DataAccessObject.dropData();
    }
}
