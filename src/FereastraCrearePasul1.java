import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FereastraCrearePasul1 extends JFrame {
    private JButton pasul2;
    private JLabel l1, l2, l3, l4, eroare;
    private JTextField nume, prenume, cnp, nrAccidente;
    private JPanel p1, p2;
    private FereastraCrearePasul1.ControllerFereastraCrearePasul1 c;
    public FereastraCrearePasul1(){
        super("Pasul 1: Datele Clientului");
        this.setLayout(new GridLayout(3,1, 10, 0));

        p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2,20, 20));
//        p1.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));
        p1.setBounds(50, 50, 550, 150);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(2, 2,20, 20));
//        p2.setBorder(BorderFactory.createEmptyBorder(0, 80, 30, 80));
        p2.setBounds(50,220, 550, 100);

        l1 = new JLabel("Nume:");
        l2 = new JLabel("Prenume:");
        l3 = new JLabel("CNP:");
        l4 = new JLabel("Numar de Accidente in ultimii 5 ani:");
        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);

        nume = new JTextField(30);
        prenume = new JTextField(30);
        cnp = new JTextField(30);
        nrAccidente = new JTextField(10);

        pasul2 = new JButton("Pasul urmator");
        pasul2.setSize(60,20);

        c = new FereastraCrearePasul1.ControllerFereastraCrearePasul1();

        pasul2.addActionListener(c);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Imagini\\client.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(550,0,400, 500);
        }catch (IOException e) {
            System.out.println("fisierul nu a fost gasit");
        }

        p1.add(l1);
        p1.add(nume);
        p1.add(l2);
        p1.add(prenume);
        p1.add(l3);
        p1.add(cnp);
        p2.add(l4);
        p2.add(nrAccidente);
        p2.add(pasul2);
        p2.add(eroare);

        this.setLayout(null);
        this.add(p1);
        this.add(p2);
        this.add(picture);
    }

    class ControllerFereastraCrearePasul1 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == pasul2){
                eroare.setVisible(false);

                if(nume.getText().equals("") || prenume.getText().equals("") || cnp.getText().equals("") || nrAccidente.getText().equals("")) {
                    eroare.setText("Unul dintre campuri este gol");
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
                            BufferedReader br = new BufferedReader(new FileReader("Contracte\\bazaContracte.txt"));
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
                                    JOptionPane.showMessageDialog(null, "Exista deja un contract cu acest cnp. Contractul se va suprascrie");
                                    b = true;
                                    break;
                                }
                            }
                            if(b == false){
                                nr++;
                            }
                        }catch (IOException e1){
                            System.out.println("fisierul nu a fost gasit");
                        }

                        try{
                            int nrAcc = Integer.parseInt(nrAccidente.getText());
                            if(!eroare.isVisible()){
                                FereastraCrearePasul2 f = new FereastraCrearePasul2(new Client(nume.getText(),prenume.getText(),cnp.getText(),nrAcc), nr);
                                f.setSize(950, 450);
                                f.setVisible(true);
                                f.setLocationRelativeTo(null);
                                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                dispose();
                            }

                        }catch (NumberFormatException e1){
                            eroare.setText("Trebuie sa introduceti un numar intreg!");
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
