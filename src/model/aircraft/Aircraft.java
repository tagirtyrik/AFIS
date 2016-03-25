
package model.aircraft;
import model.Plane;
import java.io.Serializable;
/*
    экземпляры этого класса - самолеты модели Boeing747SP со сжожими характеристиками
*/
public class Aircraft implements Plane,Serializable{
    static double fuelConsumptionDefault=20.3;//расход топлива самолета Boeing747SP л/км
    static int passengerSeatsCountDefault=230;// число пассажирских мест в самолете типа Boeing747SP
    int id;
    private String planeName="Boeing_747SP";// имя конкретного самолета 
    private String planeNumber;// бортовой номер конкретного самолета, например AL-4DB,DS-GDD,N1KE,....
    private double fuelConsumption;// настоящее потребление топлива конкретного самолета 
    int passengerSeatsCount;//настоящее количество мест в самолете
    
    public Aircraft(int id, String number){//конструктор экземпляра Boeing747SP, задает бортовой номер самолета(на практике большн и не надо)
        this.planeNumber=number;
        this.id=id;
        fuelConsumption=fuelConsumptionDefault;
        passengerSeatsCount=passengerSeatsCountDefault; 
    }
    //дальше код для реализации интерфейса Plane, не делает ничего интересного
    public void setId(int id){
        this.id=id;
    }
    public int getId(){return id;}
    public String getName(){
        return this.planeName;
    }
    public void setName(String planeName){
        this.planeName=planeName;
    }
    
    public String getNumber(){
        return  this.planeNumber;
    }
    public void setNumber(String planeNumber){
        this.planeNumber=planeNumber;
    }
    
    public double getFuelConsumption(){
        return this.fuelConsumption;
    }
    public void setFuelConsumption(double fuelConsumption){
        this.fuelConsumption=fuelConsumption;
    }
    
    public int getPassengerSeatsCount(){
        return this.passengerSeatsCount;
    }
    public void setPassengerSeatsCount(int passengerSeatsCount){
        this.passengerSeatsCount=passengerSeatsCount;
    }
}
