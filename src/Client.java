public class Client {
    private String nume, prenume;
    private int nrAccidente;
    private String cnp;

    public Client(String nume, String prenume, String cnp, int nrAccidente){
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.nrAccidente = nrAccidente;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\tNume: " + nume);
        sb.append("\n\tPrenume: " + prenume);
        sb.append("\n\tCNP: " + cnp);
        sb.append("\n\tNumar accidente in ultimii 5 ani: " + nrAccidente);
        return sb.toString();
    }

    public String getNume() {
        return nume;
    }
    public String getPrenume() {
        return prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public int getNrAccidente() { return nrAccidente; }
}
