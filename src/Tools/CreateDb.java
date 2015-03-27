/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;

import db.DataAccessObject;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Airport;
import model.Flight;
import model.Model;
import model.Plane;
import model.Route;
import model.aircraft.Boeing747SP;
import model.airport.InternationalAirport;
import model.flight.ReguarFlight;
import model.route.RegularRoute;

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
       Flight flight = new ReguarFlight(0, plane.getId(), route.getId(),new java.util.Date(0),new java.util.Date(0));
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
