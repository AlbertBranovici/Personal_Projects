import java.io.*;
import java.util.*;

public class ListaMasini {
    protected ArrayList<Masina> lista;
    public ListaMasini(){
        this.lista = new ArrayList<>();
        this.citesteMasini();
    }

    public ListaMasini(ArrayList l){
        lista = l;
    }

    public ListaMasini cautaDupaMarca(String marca){
        ArrayList<Masina> l = new ArrayList<>();
        for(Masina m: lista){
            if(m.getMarca().equals(marca)){
                l.add(m);
            }
        }
        return new ListaMasini(l);
    }

    public ListaMasini cautaDupaAn(String marca, int an){
        ArrayList<Masina> l = new ArrayList<>();
        for(Masina m: lista){
            if(m.getMarca().equals(marca) && m.getAnFabricatie() == an){
                l.add(m);
            }
        }
        return new ListaMasini(l);
    }

    public Masina cautaMasina(String marca, int an, String model){
        for(Masina m: lista){
            if(m.getMarca().equals(marca) && m.getAnFabricatie() == an && m.getModel().equals(model)){
                return m;
            }
        }
        return null;
    }

    public void citesteMasini(){
        try{
            BufferedReader br =new BufferedReader(new FileReader("Masini.txt"));
            String linie;
            String[] specificatii;
            linie = br.readLine();
            while (true){
                linie = br.readLine();
                if(linie == null)
                    break;;
                specificatii = linie.split("/");
                this.lista.add(new Masina(specificatii));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
