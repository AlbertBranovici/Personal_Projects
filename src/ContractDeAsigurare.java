import java.time.*;
import java.util.*;

public class ContractDeAsigurare {
    protected int nrContract;
    protected ArrayList<String> tipuriDeRisc;
    protected Client client;
    protected Masina masina;
    protected String intervalPlata;
    protected double pret, rata;
    protected ArrayList<String> modificari;
    protected LocalDate dataCreare, dataPlata;

    public ContractDeAsigurare(ArrayList<String> tipuriDeRisc, Client client, Masina masina, String intervalPlata, ArrayList<String> modificari) {
        this.tipuriDeRisc = tipuriDeRisc;
        this.client = client;
        this.masina = masina;
        this.intervalPlata = intervalPlata;
        this.modificari = modificari;
        this.dataCreare = LocalDate.now();
    }

    public void calculeazaSumaAsigurata(){
        pret = 0;
        if(masina.getCategorie().equals("A") || masina.getCategorie().equals("B")){
            int putere = masina.getPutereMotor();
            if(putere < 50)
                pret += 200;
            else if(putere < 70)
                pret += 300;
            else if(putere < 100)
                pret += 400;
            else if(putere < 200)
                pret += 500;
            else pret += 700;

            pret += masina.getIndicePoluare() * 100;

            if(masina.getCombustibil() == "Diesel")
                pret += 350;
            else pret += 200;

//            if(masina.getCategorie().equals("B"))
                pret += modificari.size() * 180;
        }

        pret += client.getNrAccidente() * 200;
        pret += masina.getPretInitial() * 0.10;

        int varsta = masina.getVarsta();
        if(varsta < 10)
            pret += 200;
        else if(varsta < 15)
            pret += 250;
        else pret += 400;

        this.calculeazaReducere();
    }
    private void calculeazaReducere(){
        if(this.intervalPlata.equals("Semestrial"))
            pret = pret - pret * 0.05;
        if(this.intervalPlata.equals("Anual"))
            pret = pret - pret * 0.10;
    }

    public void calculeazaRata(){
        rata = pret;
        if(intervalPlata.equals("Semestrial"))
            rata = pret / 2;
        else if(intervalPlata.equals("Lunar"))
            rata = pret / 12;
    }

    public void setDataPlata(){
        if(intervalPlata.equals("Lunar"))
            this.dataPlata = dataCreare.plusMonths(1);
        if(intervalPlata.equals("Semestrial"))
            this.dataPlata = dataCreare.plusMonths(6);
        if(intervalPlata.equals("Anual"))
            this.dataPlata = dataCreare.plusYears(1);
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(nrContract == 0)
            sb.append("Contract de asigurare");
        else {
            sb.append("Contract nr." + nrContract);
        }
        sb.append("\nData incheierii contractului: " + dataCreare);

        sb.append("\n\nDatele Clientului:\n" + client.toString());

        sb.append("\n\nSpecificatii Masina:\n");
        sb.append("\tCategorie: " + masina.getCategorie() + "\n\tMarca: " + masina.getMarca() +"\n\tModel: " + masina.getModel() + "\n\tAn Fabricatie: " + masina.getAnFabricatie() + "\n");
        if(!masina.getCategorie().equals("C"))
            sb.append("\tCombustibil: " + masina.getCombustibil() + "\n\tPutere motor: " + masina.getPutereMotor() + "\n\tNorma Poluare: " + masina.getNormaPoluare() + "\n");
        sb.append("\tVarsta: " + masina.getVarsta() + " ani" + "\n\tIndice de risc: " + masina.getIndiceRisc());
        if(!masina.getCategorie().equals("C"))
            sb.append("\n\tIndice poluare: " + masina.getIndicePoluare());

        sb.append("\n\nAlte Specificatii:");
        if(modificari.size() > 0 && !masina.getCategorie().equals("C")) {
            sb.append("\n\tModificari: ");
            for(int i = 0; i < modificari.size(); i++){
                sb.append(modificari.get(i) + ", ");
            }
        }
        if(tipuriDeRisc.size() > 0) {
            sb.append("\n\tTipuri de risc: ");
            for(int i = 0; i < tipuriDeRisc.size(); i++){
                sb.append(tipuriDeRisc.get(i) + ", ");
            }
        }
        sb.append("\n\tInterval Plata: " + intervalPlata);
        sb.append("\n\tSuma asigurata: " + pret + " lei");
        sb.append("\n\tRata: " + rata + " lei");
        return sb.toString();
    }
}
