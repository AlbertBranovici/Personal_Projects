import java.io.*;
import java.util.*;

public class carList {
    protected ArrayList<car> list;
    public carList(){
        this.list = new ArrayList<>();
        this.getCars();
    }

    public carList(ArrayList l){
        list = l;
    }

    public carList searchByMake(String make){
        ArrayList<car> l = new ArrayList<>();
        for(car m: list){
            if(m.getMake().equals(make)){
                l.add(m);
            }
        }
        return new carList(l);
    }

    public carList searchByYear(String make, int year){
        ArrayList<car> l = new ArrayList<>();
        for(car m: list){
            if(m.getMake().equals(make) && m.getYearOfProduction() == year){
                l.add(m);
            }
        }
        return new carList(l);
    }

    public car searchCar(String make, int year, String model){
        for(car m: list){
            if(m.getMake().equals(make) && m.getYearOfProduction() == year && m.getModel().equals(model)){
                return m;
            }
        }
        return null;
    }

    public void getCars(){
        try{
            BufferedReader br =new BufferedReader(new FileReader("Masini.txt"));
            String line;
            String[] specs;
            line = br.readLine();
            while (true){
                line = br.readLine();
                if(line == null)
                    break;;
                specs = line.split("/");
                this.list.add(new car(specs));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
