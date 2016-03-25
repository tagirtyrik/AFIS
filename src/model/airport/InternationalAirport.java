
package model.airport;
import model.Airport;
import model.flight.ReguarFlight;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/*
    класс международного аэропорта.
*/
@SequenceGenerator(name = "SEQ_ID", sequenceName = "SEQ_ID")
@Entity
@Table(name = "airport")
public class InternationalAirport implements Airport,Serializable{

    @javax.persistence.Id
    @GeneratedValue(generator = "SEQ_ID")
    @Column(name = "port_id")
    private int id;

    @Column(name = "title")
    private String airportName = "ggg";// имя конкретного самолета

    @Column(name = "location")
    private String airportLocation;// борт


   // private String airportName;//имя аэропорта, например "Френсис Интернэйшионал"
  //  private String airportLocation;//место нахождения аэропорта, например "Либерти-Сити"
   // int id;

    public InternationalAirport()
    {

    }
    
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
