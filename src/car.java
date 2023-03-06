import java.time.*;

public class car {
    private String category, make, model, fuel, pollution;
    private double initialPrice, pollutionNumber, riskIndex;
    private int yearOfProduction, age, horsepower;

    public car(String[] specs) {
        this.category = specs[0];
        this.make = specs[1];
        this.model = specs[2];
        this.yearOfProduction = Integer.parseInt(specs[3]);
        this.fuel = specs[4];
        this.horsepower = Integer.parseInt(specs[5]);
        this.pollution = specs[6];
        this.initialPrice = Double.parseDouble(specs[7]);
        this.calculateRiskIndex();
        this.calculatePollutionNumber();
        this.calculateAge();
    }

    public void calculateRiskIndex(){
        if(this.category.equals("B")){
            this.riskIndex = 2;
            if(this.horsepower > 150)
                this.riskIndex += 2;
        }
        if(this.category.equals("A"))
            this.riskIndex = 5;
        if(category.equals("C"))
            this.riskIndex = 6;
    }

    public void calculatePollutionNumber(){
        this.pollutionNumber = 0;
        if(category.equals("A") || category.equals("B")){
            this.pollutionNumber = Integer.parseInt(Character.toString(this.pollution.charAt(5)));
            this.pollutionNumber += this.pollutionNumber * 0.6;
        }
    }

    public void calculateAge(){
        this.age = Year.now().getValue() - this.yearOfProduction;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("Category: " + category + "\nMake: " + make +"\nModel: " + model + "\nYear of production: " + yearOfProduction + "\n");
        if(!this.category.equals("C"))
            sb.append("Fuel: " + fuel + "\nHorsepower: " + horsepower + "\nPollution: " + pollution + "\n");
        sb.append("Age: " + age + "\nRisk index: " + riskIndex);
        if(!this.category.equals("C"))
            sb.append( "\nPollution number: " + pollutionNumber);
        sb.append( "\n");
        return sb.toString();
    }

    public String getCategory() { return category; }
    public int getHorsepower() { return horsepower; }
    public double getRiskIndex() { return riskIndex; }
    public String getMake() { return make; }
    public int getYearOfProduction() { return yearOfProduction; }
    public String getModel() { return model; }
    public double getPollutionNumber() {
        return pollutionNumber;
    }
    public int getAge() {
        return age;
    }
    public String getFuel() { return fuel; }
    public String getPollution() { return pollution; }

    public double getInitialPrice() { return initialPrice; }
}
