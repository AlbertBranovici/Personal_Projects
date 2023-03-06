import java.time.*;
import java.util.*;

public class insuranceContract {
    protected int contractNumber;
    protected ArrayList<String> riskTypes;
    protected client client;
    protected car car;
    protected String paymentInterval;
    protected double price, paymentRate;
    protected ArrayList<String> modifications;
    protected LocalDate initialDate, paymentDate;

    public insuranceContract(ArrayList<String> riskTypes, client client, car car, String paymentInterval, ArrayList<String> modifications) {
        this.riskTypes = riskTypes;
        this.client = client;
        this.car = car;
        this.paymentInterval = paymentInterval;
        this.modifications = modifications;
        this.initialDate = LocalDate.now();
    }

    public void calculateInsurancePrice(){
        price = 0;
        if(car.getCategory().equals("A") || car.getCategory().equals("B")){
            int putere = car.getHorsepower();
            if(putere < 50)
                price += 200;
            else if(putere < 70)
                price += 300;
            else if(putere < 100)
                price += 400;
            else if(putere < 200)
                price += 500;
            else price += 700;

            price += car.getPollutionNumber() * 100;

            if(car.getFuel() == "Diesel")
                price += 350;
            else price += 200;

                price += modifications.size() * 180;
        }

        price += client.getAccidentNumber() * 200;
        price += car.getInitialPrice() * 0.10;

        int varsta = car.getAge();
        if(varsta < 10)
            price += 200;
        else if(varsta < 15)
            price += 250;
        else price += 400;

        this.calculateDiscount();
    }
    private void calculateDiscount(){
        if(this.paymentInterval.equals("Semestrial"))
            price = price - price * 0.05;
        if(this.paymentInterval.equals("Annual"))
            price = price - price * 0.10;
    }

    public void calculatePaymentRate(){
        paymentRate = price;
        if(paymentInterval.equals("Semestrial"))
            paymentRate = price / 2;
        else if(paymentInterval.equals("Monthly"))
            paymentRate = price / 12;
    }

    public void setPaymentDate(){
        if(paymentInterval.equals("Monthly"))
            this.paymentDate = initialDate.plusMonths(1);
        if(paymentInterval.equals("Semestrial"))
            this.paymentDate = initialDate.plusMonths(6);
        if(paymentInterval.equals("Annual"))
            this.paymentDate = initialDate.plusYears(1);
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(contractNumber == 0)
            sb.append("Insurance contract");
        else {
            sb.append("Contract number: " + contractNumber);
        }
        sb.append("\nDate of contract: " + initialDate);

        sb.append("\n\nClient's details:\n" + client.toString());

        sb.append("\n\nCar details:\n");
        sb.append("\tCategory: " + car.getCategory() + "\n\tMake: " + car.getMake() +"\n\tModel: " + car.getModel() + "\n\tYear of production: " + car.getYearOfProduction() + "\n");
        if(!car.getCategory().equals("C"))
            sb.append("\tFuel: " + car.getFuel() + "\n\tHorsepower: " + car.getHorsepower() + "\n\tPollution number: " + car.getPollution() + "\n");
        sb.append("\tAge: " + car.getAge() + " years" + "\n\tRisk index: " + car.getInitialPrice());
        if(!car.getCategory().equals("C"))
            sb.append("\n\tPollution index: " + car.getPollution());

        sb.append("\n\nOther details:");
        if(modifications.size() > 0 && !car.getCategory().equals("C")) {
            sb.append("\n\tModifications: ");
            for(int i = 0; i < modifications.size(); i++){
                sb.append(modifications.get(i) + ", ");
            }
        }
        if(riskTypes.size() > 0) {
            sb.append("\n\tHazard types: ");
            for(int i = 0; i < riskTypes.size(); i++){
                sb.append(riskTypes.get(i) + ", ");
            }
        }
        sb.append("\n\tPayment interval: " + paymentInterval);
        sb.append("\n\tInsurance price: " + price + " RON");
        sb.append("\n\tPayment rate: " + paymentRate + " RON");
        return sb.toString();
    }
}
