import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class FereastraCrearePasul3 extends JFrame {
    private JCheckBox accident, cutremur, incendiu, radio, mp3, aerConditionat;
    private JRadioButton lunar, semestrial, anual;
    private JButton finalizare;
    private JPanel tipuriRisc, modificari,intervalPlata;
    protected Client client;
    protected Masina masina;
    protected  int nr;
    private FereastraCrearePasul3.ControllerFereastraCrearePasul3 c;
    public FereastraCrearePasul3(Client client, Masina masina, int nr){
        super("Optiuni Asigurare");

        this.client = client;
        this.masina = masina;
        this.nr = nr;

        tipuriRisc = new JPanel();
        tipuriRisc.setBorder(BorderFactory.createTitledBorder("Tipuri de risc"));
        tipuriRisc.setBounds(100, 140, 300, 70);

        accident = new JCheckBox("Accident");
        cutremur = new JCheckBox("Cutremur");
        incendiu = new JCheckBox("Incendiu");

        tipuriRisc.add(accident);
        tipuriRisc.add(cutremur);
        tipuriRisc.add(incendiu);

        modificari = new JPanel();
        modificari.setBorder(BorderFactory.createTitledBorder("Modificari"));
        modificari.setBounds(100, 50, 300, 70);

        radio = new JCheckBox("Radio");
        mp3 = new JCheckBox("mp3 player");
        aerConditionat = new JCheckBox("Aer Conditionat");

        if(!masina.getCategorie().equals("B")) {
            if(masina.getCategorie().equals("C")) {
                radio.setEnabled(false);
                mp3.setEnabled(false);
            }
            aerConditionat.setEnabled(false);
        }

        modificari.add(radio);
        modificari.add(mp3);
        modificari.add(aerConditionat);

        intervalPlata = new JPanel();
        intervalPlata.setBorder(BorderFactory.createTitledBorder("Interval de Plata"));
        intervalPlata.setBounds(100, 230, 300, 70);
        ButtonGroup intervalDePlata = new ButtonGroup();

        lunar = new JRadioButton("Lunar", true);
        semestrial = new JRadioButton("Semestrial");
        anual = new JRadioButton("Anual");

        intervalDePlata.add(lunar);
        intervalDePlata.add(semestrial);
        intervalDePlata.add(anual);

        intervalPlata.add(lunar);
        intervalPlata.add(semestrial);
        intervalPlata.add(anual);

        finalizare = new JButton("Finalizeaza");
        finalizare.setBounds(200, 320, 100, 30);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Imagini\\rotita.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(500,0,400, 400);
        }catch (IOException e) {
            System.out.println("fisierul nu a fost gasit");
        }

        c = new FereastraCrearePasul3.ControllerFereastraCrearePasul3();

        finalizare.addActionListener(c);

        this.setLayout(null);
        this.add(tipuriRisc);
        this.add(modificari);
        this.add(intervalPlata);
        this.add(finalizare);
        this.add(picture);
//        this.setLayout(new FlowLayout(1, 10, 10));
        this.setVisible(true);
    }

    class ControllerFereastraCrearePasul3 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == finalizare){
                ArrayList<String> risc = new ArrayList<>();
                if(accident.isSelected())
                    risc.add(accident.getText());
                if(cutremur.isSelected())
                    risc.add(cutremur.getText());
                if(incendiu.isSelected())
                    risc.add(incendiu.getText());

                ArrayList<String> mod = new ArrayList<>();
                if(radio.isSelected())
                    mod.add(radio.getText());
                if(mp3.isSelected())
                    mod.add(mp3.getText());
                if(aerConditionat.isSelected())
                    mod.add(aerConditionat.getText());

                String interval = "Lunar";
                if(lunar.isSelected())
                    interval = "Lunar";
                if(semestrial.isSelected())
                    interval = "Semestrial";
                if(anual.isSelected())
                    interval = "Anual";

                FereastraPrintContract f = new FereastraPrintContract(new ContractDeAsigurare(risc, client, masina, interval, mod), nr);
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
