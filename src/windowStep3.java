import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class windowStep3 extends JFrame {
    private JCheckBox accident, earthquake, fire, radio, mp3, AC;
    private JRadioButton monthly, semestrial, annual;
    private JButton complete;
    private JPanel typesOfRisk, modifications,paymentRate;
    protected client client;
    protected car car;
    protected  int nr;
    private windowStep3.ControllerwindowStep3 c;
    public windowStep3(client client, car car, int nr){
        super("Insurance options");

        this.client = client;
        this.car = car;
        this.nr = nr;

        typesOfRisk = new JPanel();
        typesOfRisk.setBorder(BorderFactory.createTitledBorder("Types of hazards"));
        typesOfRisk.setBounds(100, 140, 300, 70);

        accident = new JCheckBox("Accident");
        earthquake = new JCheckBox("Earthquake");
        fire = new JCheckBox("Fire");

        typesOfRisk.add(accident);
        typesOfRisk.add(earthquake);
        typesOfRisk.add(fire);

        modifications = new JPanel();
        modifications.setBorder(BorderFactory.createTitledBorder("modifications"));
        modifications.setBounds(100, 50, 300, 70);

        radio = new JCheckBox("Radio");
        mp3 = new JCheckBox("mp3 player");
        AC = new JCheckBox("AC unit");

        if(!car.getCategory().equals("B")) {
            if(car.getCategory().equals("C")) {
                radio.setEnabled(false);
                mp3.setEnabled(false);
            }
            AC.setEnabled(false);
        }

        modifications.add(radio);
        modifications.add(mp3);
        modifications.add(AC);

        paymentRate = new JPanel();
        paymentRate.setBorder(BorderFactory.createTitledBorder("Payment interval"));
        paymentRate.setBounds(100, 230, 300, 70);
        ButtonGroup intervalDePlata = new ButtonGroup();

        monthly = new JRadioButton("Monthly", true);
        semestrial = new JRadioButton("Semestrial");
        annual = new JRadioButton("Annual");

        intervalDePlata.add(monthly);
        intervalDePlata.add(semestrial);
        intervalDePlata.add(annual);

        paymentRate.add(monthly);
        paymentRate.add(semestrial);
        paymentRate.add(annual);

        complete = new JButton("Complete");
        complete.setBounds(200, 320, 100, 30);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Images\\rotita.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(500,0,400, 400);
        }catch (IOException e) {
            System.out.println("file not found");
        }

        c = new windowStep3.ControllerwindowStep3();

        complete.addActionListener(c);

        this.setLayout(null);
        this.add(typesOfRisk);
        this.add(modifications);
        this.add(paymentRate);
        this.add(complete);
        this.add(picture);
        this.setVisible(true);
    }

    class ControllerwindowStep3 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == complete){
                ArrayList<String> risk = new ArrayList<>();
                if(accident.isSelected())
                    risk.add(accident.getText());
                if(earthquake.isSelected())
                    risk.add(earthquake.getText());
                if(fire.isSelected())
                    risk.add(fire.getText());

                ArrayList<String> mod = new ArrayList<>();
                if(radio.isSelected())
                    mod.add(radio.getText());
                if(mp3.isSelected())
                    mod.add(mp3.getText());
                if(AC.isSelected())
                    mod.add(AC.getText());

                String interval = "Monthly";
                if(monthly.isSelected())
                    interval = "Monthly";
                if(semestrial.isSelected())
                    interval = "Semestrial";
                if(annual.isSelected())
                    interval = "Annual";

                printContract f = new printContract(new insuranceContract(risk, client, car, interval, mod), nr);
                f.setSize(450, 600);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
        }
    }
}
