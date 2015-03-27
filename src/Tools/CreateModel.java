/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tools;
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
public class CreateModel 
{
    private Model model = new Model();
    public CreateModel()throws java.io.IOException,java.sql.SQLException,java.lang.ClassNotFoundException
    {
       Plane plane = new Boeing747SP(0,"plane1");
       Airport port = new InternationalAirport(0, "Kurumoch", "Samara");
       Route route = new RegularRoute(0,0,0,0);
       Flight flight = new ReguarFlight(0, plane.getId(), route.getId(),new java.util.Date(0),new java.util.Date(0));
       Plane plane1 = new Boeing747SP(1,"plane2");
       model.addPlane(plane);
       model.addPlane(plane1);
       model.addAirport(port);
       model.addRoute(route);
       model.addFlight(flight);
    }
    public ArrayList<Plane> getPlane()throws java.io.IOException,java.sql.SQLException,java.lang.ClassNotFoundException
    {
        return model.getPlanes();
    }
    public ArrayList<Airport> getPort()throws java.io.IOException,java.sql.SQLException,java.lang.ClassNotFoundException
    {
        return model.getAirports();
    }
    public ArrayList<Route> getRoute()throws java.io.IOException,java.sql.SQLException,java.lang.ClassNotFoundException
    {
        return model.getRoutes();
    }
    public ArrayList<Flight> getFlight()throws java.io.IOException,java.sql.SQLException,java.lang.ClassNotFoundException,java.text.ParseException
    {
        return model.getFlights();
    }
}
