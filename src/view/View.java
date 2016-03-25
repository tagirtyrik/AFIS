/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Scanner;
import model.Airport;
import model.Flight;
import model.Plane;
import model.Route;

/**
 *
 * @author Ксю
 */
public interface View {
        public void printSomeInfo(String info);
    /**
     * вводит данные пользователя
     * @return строка, введеная пользователем
     */
    public String giveInput();
    /**
     * выводит информацию по азропорту:<li>Id <li>Тип <li>Имя <li>Местоположение 
     * @param airport аэропорт
     */
    public void printAirport(Airport airport);
    /**
     * выводит информацию по самолету:<li>Id <li>Тип <li>Имя 
     * <li>Бортовой номер <li>Потребление топлива 
     * <li>число пассажирских мест
     * @param plane самолет
     */
    public void printPlane(Plane plane);
    /**
     * выводит информацию по рейсу:<li>Id рейса<li>Тип рейса<li>Id самолета <li> Id маршрута
     * <li>стоимость билета
     * <li>время взлета
     * <li>время посадки
     * <li>время в полете
     * @param flight рейс
     */
    public void printFlight(Flight flight, Route route, Plane plane);
    /**
     * выводит информацию по маршруту:<li>Id<li>Тип<li>Id порта отправления <li> Id порта прибытия
     * @param route маршрут
     */
    public void printRoute(Route route);
        /**
         * печатает число рейсов в БД
         * @param flightCount число рейсов
         */
    public void printFlightCount(int flightCount);
         /**
         * печатает число маршрутов в БД
         * @param routeCount число маршрутов
         */
    public void printRouteCount(int routeCount);
         /**
         * печатает число самолетов в БД
         * @param planeCount число самолетов
         */
    public void printPlaneCount(int planeCount);
            /**
         * печатает число аэропортов в БД
         * @param portCount число аэропортов
         */
    public void printAirportCount(int portCount);
    /**
     * подтверждает выполнение коммадны
     */
    public void flush();
}
