
package db;

/**
 *ерундень для автоматического формирования sql
 * 
 * @author GeneraL
 */
public class Sql {
    public class Table {
        class Names {
            public static final String plane="Plane";
            public static final String airport="Port";
            public static final String route="Route";
            public static final String flight="Flight";
        }
    public static final String createPlane=
        "CREATE TABLE " +Names.plane+"\n"+
        "(\n" +
        //Plane.Field.id+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +//для derbyDB
        Plane.Field.id+" INT NOT NULL AUTO_INCREMENT,\n"+//для MySQL
        Plane.Field.planeName+" VARCHAR(20),\n" +
        Plane.Field.planeNumber+" VARCHAR(20)NOT NULL ,\n" +
        Plane.Field.fuelConsumption+"  DOUBLE NOT NULL ,\n" +
        Plane.Field.passengerSeatsCount+" INTEGER NOT NULL ,\n" +
        "PRIMARY KEY ("+Plane.Field.id+")\n" +
        ")";

    public static final String createPort=
        "CREATE TABLE "+Names.airport+"\n" +
        "(\n" +
       // Airport.Field.id+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +//для derbyDB
        Airport.Field.id+" INT NOT NULL AUTO_INCREMENT,\n"+//для MySQL
        Airport.Field.airportLocation+" VARCHAR(50) NOT NULL,\n" +
        Airport.Field.airportName+" VARCHAR(50) NOT NULL,\n" +
        "PRIMARY KEY ("+Airport.Field.id+")\n" +
        ")";
    public static final String createRoute=
        "CREATE TABLE "+Names.route+"\n" +
        "(\n" +
        //Route.Field.id+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +//для derbyDB
        Route.Field.id+" INT NOT NULL AUTO_INCREMENT,\n"+//для MySQL
        Route.Field.landingPortId+" INTEGER,\n" +
        Route.Field.takeOffPortId+" INTEGER,\n" +
        Route.Field.distance+" DOUBLE NOT NULL,\n" +
        "FOREIGN KEY ("+Route.Field.takeOffPortId+") REFERENCES "+Names.airport+"("+Airport.Field.id+"),\n" +
        "FOREIGN KEY ("+Route.Field.landingPortId+") REFERENCES "+Names.airport+"("+Airport.Field.id+"),\n" +
        "PRIMARY KEY ("+Route.Field.id+"))";
    public static final String createFlight=
        "CREATE TABLE "+Names.flight+"\n" +
        "(\n" +
        //Flight.Field.id+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +//для derbyDB
        Flight.Field.id+" INT NOT NULL AUTO_INCREMENT,\n"+//для MySQL
        Flight.Field.planeId+" INTEGER,\n" +
        Flight.Field.routeId+" INTEGER,\n" +
        Flight.Field.takeOffTime+" TIMESTAMP NOT NULL,\n" +
        Flight.Field.landingTime+" TIMESTAMP NOT NULL,\n" +
        "FOREIGN KEY ("+Flight.Field.planeId+") REFERENCES "+Names.plane+"("+Plane.Field.id+"),\n" +
        "FOREIGN KEY ("+Flight.Field.routeId+") REFERENCES "+Names.route+"("+Route.Field.id+"),\n" +
        "PRIMARY KEY ("+Flight.Field.id+")\n" +
        ")";
    public static final String dropPlane=
            "DROP TABLE "+Names.plane;
    public static final String dropAirport=
            "DROP TABLE "+Names.airport;
    public static final String dropRoute=
            "DROP TABLE "+Names.route;
    public static final String dropFlight=
            "DROP TABLE "+Names.flight;
    }
    class Plane {
        class Field {
            public static final String id="id";
            public static final String planeName="name";
            public static final String planeNumber="number";
            public static final String fuelConsumption="fuelConsumption";
            public static final String passengerSeatsCount="passengerSeatsCount";
        }
        public static final String selectAll="SELECT * FROM "+Table.Names.plane+"";
        public static final String select="SELECT * FROM "+Table.Names.plane+
                " WHERE ("+Field.id+" = ?)";
        public static final String selectWhere="SELECT * FROM "+Table.Names.plane+
                " WHERE ";
        public static final String update="UPDATE "+Table.Names.plane+
                " SET "+
                Field.planeName + " = ?," +
                Field.planeNumber + " = ?," +
                Field.fuelConsumption + " = ?," +
                Field.passengerSeatsCount + " = ?"+
                " WHERE ("+Field.id+" = ?)";
        public static final String add="INSERT INTO "+Table.Names.plane+"("+
                Field.planeName + ", " +Field.planeNumber + ", " +
                Field.fuelConsumption + ", " + Field.passengerSeatsCount +
                ") VALUES (?, ?, ?, ?)";
        public static final String count="SELECT COUNT(*) FROM "+Table.Names.plane;
        public static final String delete="DELETE FROM "+Table.Names.plane+
                " WHERE ("+Field.id+" = ?)";
    }
    class Airport{
        class Field {
            public static final String id="Id";
            public static final String airportName="Name";
            public static final String airportLocation="Location";
        }
        public static final String selectAll="SELECT * FROM "+Table.Names.airport+"";
        public static final String select="SELECT * FROM "+Table.Names.airport+
                " WHERE ("+Field.id+" = ?)";
        public static final String selectWhere="SELECT * FROM "+Table.Names.airport+
                " WHERE ";
        public static final String update="UPDATE "+Table.Names.airport+
                " SET "+
                Field.airportName + " = ?," +
                Field.airportLocation + " = ?"+
                " WHERE ("+Field.id+" = ?)";
        public static final String add="INSERT INTO "+Table.Names.airport+"("+
                Field.airportName + ", " + Field.airportLocation + 
                ") VALUES (?, ?)";
        public static final String count="SELECT COUNT(*) FROM "+Table.Names.airport;
        public static final String delete="DELETE FROM "+Table.Names.airport+
                " WHERE ("+Field.id+" = ?)";
    }
    class Route{
        class Field {
            public static final String id="Id";
            public static final String takeOffPortId="TakeoffId";
            public static final String landingPortId="LandingId";
            public static final String distance="Distance";
        }
        public static final String selectAll="SELECT * FROM "+Table.Names.route+"";
        public static final String select="SELECT * FROM "+Table.Names.route+
                " WHERE ("+Field.id+" = ?)";
        public static final String selectWhere="SELECT * FROM "+Table.Names.route+
                " WHERE ";
        public static final String update="UPDATE "+Table.Names.route+
                " SET "+
                Field.takeOffPortId + " = ?," +
                Field.landingPortId + " = ?,"+
                Field.distance + " = ?"+
                " WHERE ("+Field.id+" = ?)";
        public static final String add="INSERT INTO "+Table.Names.route+"("+
                Field.takeOffPortId + ", " + Field.landingPortId + ", " + Field.distance+
                ") VALUES (?, ?, ?)";
        public static final String count="SELECT COUNT(*) FROM "+Table.Names.route;
        public static final String delete="DELETE FROM "+Table.Names.route+
                " WHERE ("+Field.id+" = ?)";
    }
    class Flight{
        class Field {
            public static final String id="Id";
            public static final String fuelPrice="Price";//не используется
            public static final String planeId="PlaneId";
            public static final String routeId="RouteId";
            public static final String takeOffTime="TakeoffTime";
            public static final String landingTime="landingTime";
        }
        public static final String selectAll="SELECT * FROM "+Table.Names.flight+"";
        public static final String select="SELECT * FROM "+Table.Names.flight+
                " WHERE ("+Field.id+" = ?)";
        public static final String selectWhere="SELECT * FROM "+Table.Names.flight+
                " WHERE ";
        public static final String update="UPDATE "+Table.Names.flight+
                " SET "+
                Field.planeId + " = ?," +
                Field.routeId + " = ?," +
                Field.takeOffTime + " = ?," +
                Field.landingTime + " = ?" +
                " WHERE ("+Field.id+" = ?)";
        public static final String add="INSERT INTO "+Table.Names.flight+"("+
                Field.planeId + ", " + Field.routeId + ", " + Field.takeOffTime + ", " +Field.landingTime+
                ") VALUES (?, ?, ?, ?)";
        public static final String count="SELECT COUNT(*) FROM "+Table.Names.flight;
        public static final String delete="DELETE FROM "+Table.Names.flight+
                " WHERE ("+Field.id+" = ?)";
    }
}
