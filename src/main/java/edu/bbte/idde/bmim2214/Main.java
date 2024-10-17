package edu.bbte.idde.bmim2214;
import edu.bbte.idde.bmim2214.presentation.*;
import  edu.bbte.idde.bmim2214.business.*;
import  edu.bbte.idde.bmim2214.dataaccess.*;


public class Main {
    public static void main(String[] args) {
        CarDao carDao = new CarMemoryDB();
        CarService carService = new Service(carDao);
        CarFrontend carFrontend = new CarFrontend(carService);
        carFrontend.display();
    }
}