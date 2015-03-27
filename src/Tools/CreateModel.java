/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.java.Tools;

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
public class CreateModel 
{
    private Model model = new Model();
    public CreateModel()
    {
       Plane plane = new Boeing747SP(0,"plane1");
       Airport port = new InternationalAirport(0, "Kurumoch", "Samara");
       Route route = new RegularRoute(0,0,0,0);
       Flight flight = new ReguarFlight(0, plane, route);
       Plane plane1 = new Boeing747SP(1,"plane2");
       model.addPlane(plane);
       model.addPlane(plane1);
       model.addAirport(port);
       model.addRoute(route);
       model.addFlight(flight);
    }
    public ArrayList<Plane> getPlane()
    {
        return model.getPlanes();
    }
    public ArrayList<Airport> getPort()
    {
        return model.getAirports();
    }
    public ArrayList<Route> getRoute()
    {
        return model.getRoutes();
    }
    public ArrayList<Flight> getFlight()
    {
        return model.getFlights();
    }
}
