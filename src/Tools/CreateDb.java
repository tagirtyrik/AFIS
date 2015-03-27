/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.java.Tools;

import src.java.db.DataAccessObject;
import java.sql.SQLException;
import java.util.ArrayList;
import src.java.model.Airport;
import src.java.model.Flight;
import src.java.model.Model;
import src.java.model.Plane;
import src.java.model.Route;
import src.java.model.aircraft.Boeing747SP;
import src.java.model.airport.InternationalAirport;
import src.java.model.flight.ReguarFlight;
import src.java.model.route.RegularRoute;

/**
 *
 * @author Ксю
 */
public class CreateDb 
{ 
    public CreateDb() throws SQLException
    {
       Plane plane = new Boeing747SP(0,"plane1");
       Airport port = new InternationalAirport(0, "Kurumoch", "Samara");
       Route route = new RegularRoute(0,0,0,0);
       Flight flight = new ReguarFlight(0, plane, route);
       DataAccessObject.addPlane(plane);
       DataAccessObject.addAirport(port);
       DataAccessObject.addRoute(route);
       DataAccessObject.addFlight(flight);
    }
    public ArrayList<Plane> getPlane() throws SQLException
    {
        return DataAccessObject.getPlanes();
    }
    public ArrayList<Airport> getPort() throws SQLException
    {
        return DataAccessObject.getAirports();
    }
    public ArrayList<Route> getRoute() throws SQLException
    {
        return DataAccessObject.getRoutes();
    }
}
