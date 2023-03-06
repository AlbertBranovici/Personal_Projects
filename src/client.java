public class client {
    private String firstName, lastName;
    private int accidentNumber;
    private String cnp;

    public client(String firstName, String lastName, String cnp, int accidentNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.cnp = cnp;
        this.accidentNumber = accidentNumber;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("\tFirst name: " + firstName);
        sb.append("\n\tLast name: " + lastName);
        sb.append("\n\tCNP: " + cnp);
        sb.append("\n\tNumber of accidents in the last 5 years: " + accidentNumber);
        return sb.toString();
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public int getAccidentNumber() { return accidentNumber; }
}
