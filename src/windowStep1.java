import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class windowStep1 extends JFrame {
    private JButton step2;
    private JLabel l1, l2, l3, l4, eroare;
    private JTextField firstName, lastName, cnp, accidentsNumber;
    private JPanel p1, p2;
    private windowStep1.ControllerwindowStep1 c;
    public windowStep1(){
        super("Pasul 1: Client's details");
        this.setLayout(new GridLayout(3,1, 10, 0));

        p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2,20, 20));
//        p1.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        p1.setBounds(50, 50, 550, 150);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 2,20, 20));
//        p2.setBorder(BorderFactory.createEmptyBorder(0, 80, 30, 80));
        p2.setBounds(50,220, 550, 100);

        l1 = new JLabel("First Name:");
        l2 = new JLabel("Last Name:");
        l3 = new JLabel("CNP:");
        l4 = new JLabel("Number of accidents in the last 5 years");
        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);

        firstName = new JTextField(30);
        lastName = new JTextField(30);
        cnp = new JTextField(30);
        accidentsNumber = new JTextField(10);

        step2 = new JButton("Next");
        step2.setSize(60,20);

        c = new windowStep1.ControllerwindowStep1();

        step2.addActionListener(c);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Images\\client.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(550,0,400, 500);
        }catch (IOException e) {
            System.out.println("file not found");
        }

        p1.add(l1);
        p1.add(firstName);
        p1.add(l2);
        p1.add(lastName);
        p1.add(l3);
        p1.add(cnp);
        p2.add(l4);
        p2.add(accidentsNumber);
        p2.add(step2);
        p2.add(eroare);

        this.setLayout(null);
        this.add(p1);
        this.add(p2);
        this.add(picture);
    }

    class ControllerwindowStep1 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == step2){
                eroare.setVisible(false);

                if(firstName.getText().equals("") || lastName.getText().equals("") || cnp.getText().equals("") || accidentsNumber.getText().equals("")) {
                    eroare.setText("One of the fields is empty");
                    eroare.setVisible(true);
                }
                else{
                    String eroareCNP = (new CnpValidator()).valideazaCNP(cnp.getText());
                    if(!eroareCNP.equals("")) {
                        eroare.setText(eroareCNP);
                        eroare.setVisible(true);
                    }
                    else{
                        int nr = 0;
                        try {
                            BufferedReader br = new BufferedReader(new FileReader("Contracts\\contractDataBase.txt"));
                            String linie;
                            String[] data;
                            linie = br.readLine();

                            boolean b = false;

                            while (true) {
                                linie = br.readLine();
                                if (linie == null)
                                    break;
                                data = linie.split(",");
                                nr = Integer.parseInt(data[0]);

                                if(data[3].equals(cnp.getText())){
                                    JOptionPane.showMessageDialog(null, "There is a contract with this CNP. The original will be overwritten.");
                                    b = true;
                                    break;
                                }
                            }
                            if(b == false){
                                nr++;
                            }
                        }catch (IOException e1){
                            System.out.println("file not found");
                        }

                        try{
                            int nrAcc = Integer.parseInt(accidentsNumber.getText());
                            if(!eroare.isVisible()){
                                windowStep2 f = new windowStep2(new client(firstName.getText(),lastName.getText(),cnp.getText(),nrAcc), nr);
                                f.setSize(950, 450);
                                f.setVisible(true);
                                f.setLocationRelativeTo(null);
                                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                dispose();
                            }

                        }catch (NumberFormatException e1){
                            eroare.setText("You must use an integer!");
                            eroare.setVisible(true);
                        }
                    }
                }

                p2.validate();
                validate();
            }
        }
    }
}
