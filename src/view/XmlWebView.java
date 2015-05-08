package view;

import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by GeneraL on 02.04.2015.
 */
public class XmlWebView  implements View{
    String info=null;
    ArrayList<Airport> airport=new ArrayList<>();
    ArrayList<Plane> plane=new ArrayList<>();
    ArrayList<Route> route=new ArrayList<>();
    ArrayList<Flight> flight=new ArrayList<>();
    Integer planeCount=null;
    Integer routeCount=null;
    Integer portCount=null;
    Integer flightCount=null;
    public void printSomeInfo(String info){
        if(this.info==null)this.info=info;
        else this.info+=info;
    }
    public String giveInput(){
        return "";
    }
    public void printAirport(Airport airport){
        this.airport.add(airport);
    }
    public void printPlane(Plane plane){
       this.plane.add(plane);
    }
    public void printFlight(Flight flight, Route route, Plane plane){
       this.flight.add(flight);
    }
    public void printRoute(Route route){
        this.route.add(route);
    }
    public void printFlightCount(int flightCount){
        this.flightCount=flightCount;
    }
    public void printRouteCount(int routeCount){
        this.routeCount=routeCount;
    }

    public void printPlaneCount(int planeCount){
        this.planeCount=planeCount;
    }
    public void printAirportCount(int portCount){
        this.portCount=portCount;
    }
    public void flush(){
    }
    public String getResponse(){
        StringBuilder responce=new StringBuilder("<view>\n");

        if(info!=null){
            responce.append("<info>");
            responce.append(info);
            responce.append("</info>\n");
        }
        if(airport.size()>0){
            for(int i=0;i<airport.size();i++) {
              //  responce.append("<airport>");
                    responce.append(airport.get(i).toString());//делайте что хотите
               // responce.append("</airport>\n");
            }
        }
        if(plane.size()>0){
            for(int i=0;i<plane.size();i++) {
               // responce.append("<plane>");
                responce.append(plane.get(i).toString());//делайте что хотите
                responce.append("\n");
                //responce.append("</plane>\n");
            }
        }
        if(route.size()>0){
            for(int i=0;i<route.size();i++) {
               // responce.append("<route>");
                responce.append(route.get(i).toString());//делайте что хотите
                responce.append("\n");
               // responce.append("</route>\n");
            }
        }
        if(flight.size()>0){
            for(int i=0;i<flight.size();i++) {
                //responce.append("<flight>");
                responce.append(flight.get(i).toString());//делайте что хотите
                responce.append("\n");
                //responce.append("</flight>\n");
            }
        }
        if(planeCount!=null){
            responce.append("<planeCount>");
            responce.append(Integer.toString(planeCount));
            responce.append("</planeCount>\n");
        }
        if(routeCount!=null){
            responce.append("<routeCount>");
            responce.append(Integer.toString(routeCount));
            responce.append("</routeCount>\n");
        }
        if(portCount!=null){
            responce.append("<portCount>");
            responce.append(Integer.toString(portCount));
            responce.append("</portCount>\n");
        }
        if(flightCount!=null){
            responce.append("<flightCount>");
            responce.append(Integer.toString(flightCount));
            responce.append("</flightCount>\n");
        }
        responce.append("</view>");
        return new String(responce);
    }
}
