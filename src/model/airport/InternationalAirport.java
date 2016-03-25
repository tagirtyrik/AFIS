
package model.airport;
import model.Airport;
import java.io.Serializable;
/*
    класс международного аэропорта.
*/
public class InternationalAirport implements Airport,Serializable{
    private String airportName;//имя аэропорта, например "Френсис Интернэйшионал"
    private String airportLocation;//место нахождения аэропорта, например "Либерти-Сити"
    int id;
    
    public InternationalAirport(int id,String airportName,String airportLocation){
        this.airportName=airportName;
        this.airportLocation=airportLocation;
        this.id=id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){return id;}
    public String getName(){
        return this.airportName;
    }
    public void setName(String airportName){
        this.airportName=airportName;
    }
    public String getLocation(){
        return this.airportLocation;
    }
    public void setLocation(String airportLocation){
        this.airportLocation=airportLocation;
    }
}
