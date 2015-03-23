package model;
/*
        любой самолет должен уметь делать эти вещи
*/
public interface Plane{
    public int getId();
    public void setId(int id);
    public String getName();
    public void setName(String planeName);
    
    public String getNumber();
    public void setNumber(String planeNumber);
    
    public double getFuelConsumption();
    public void setFuelConsumption(double fuelConsumption);
    
    public int getPassengerSeatsCount();
    public void setPassengerSeatsCount(int passengerSeatsCount);    
    
}
