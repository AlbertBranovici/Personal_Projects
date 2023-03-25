import java.time.*;

public class Masina {
    private String categorie, marca, model, combustibil, normaPoluare;
    private double pretInitial, indicePoluare, indiceRisc;
    private int anFabricatie, varsta, putereMotor;

    public Masina(String[] specificatii) {
        this.categorie = specificatii[0];
        this.marca = specificatii[1];
        this.model = specificatii[2];
        this.anFabricatie = Integer.parseInt(specificatii[3]);
        this.combustibil = specificatii[4];
        this.putereMotor = Integer.parseInt(specificatii[5]);
        this.normaPoluare = specificatii[6];
        this.pretInitial = Double.parseDouble(specificatii[7]);
        this.calculeazaIndiceRisc();
        this.calculeazaIndicePoluare();
        this.calculeazaVarsta();
    }

    public void calculeazaIndiceRisc(){
        if(this.categorie.equals("B")){
            this.indiceRisc = 2;
            if(this.putereMotor > 150)
                this.indiceRisc += 2;
        }
        if(this.categorie.equals("A"))
            this.indiceRisc = 5;
        if(categorie.equals("C"))
            this.indiceRisc = 6;
    }

    public void calculeazaIndicePoluare(){
        this.indicePoluare = 0;
        if(categorie.equals("A") || categorie.equals("B")){
            this.indicePoluare = Integer.parseInt(Character.toString(this.normaPoluare.charAt(5)));
            this.indicePoluare += this.indicePoluare * 0.6;
        }
    }

    public void calculeazaVarsta(){
        this.varsta = Year.now().getValue() - this.anFabricatie;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Categorie: " + categorie + "\nMarca: " + marca +"\nModel: " + model + "\nAn Fabricatie: " + anFabricatie + "\n");
        if(!this.categorie.equals("C"))
            sb.append("Combustibil: " + combustibil + "\nPutere motor: " + putereMotor + "\nNorma Poluare: " + normaPoluare + "\n");
        sb.append("Varsta: " + varsta + "\nIndice de risc: " + indiceRisc);
        if(!this.categorie.equals("C"))
            sb.append( "\nIndice poluare: " + indicePoluare);
        sb.append( "\n");
        return sb.toString();
    }

    public String getCategorie() { return categorie; }
    public int getPutereMotor() { return putereMotor; }
    public double getIndiceRisc() { return indiceRisc; }
    public String getMarca() { return marca; }
    public int getAnFabricatie() { return anFabricatie; }
    public String getModel() { return model; }
    public double getIndicePoluare() {
        return indicePoluare;
    }
    public int getVarsta() {
        return varsta;
    }
    public String getCombustibil() { return combustibil; }
    public String getNormaPoluare() { return normaPoluare; }

    public double getPretInitial() { return pretInitial; }
}
