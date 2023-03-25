import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FereastraPrincipala extends JFrame {
    private JButton creare, plata, termen;
    private FereastraPrincipala.ControllerFereastraPrincipala c;
    public FereastraPrincipala(){
        super("E-Asigurai Masini");
        creare = new JButton("Creare contract");
        plata = new JButton("Plata Asigurare");
        termen = new JButton("Lista clienti cu rata neplatita la timp");

        c = new FereastraPrincipala.ControllerFereastraPrincipala();

        creare.addActionListener(c);
        plata.addActionListener(c);
        termen.addActionListener(c);


        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(1,20, 20));
        p1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        p1.add(creare);
        p1.add(plata);
        p1.add(termen);

        this.add(p1, BorderLayout.CENTER);
    }

    class ControllerFereastraPrincipala implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == creare){
                FereastraCrearePasul1 f = new FereastraCrearePasul1();
                f.setSize(950, 450);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
            else if (e.getSource()==plata){
                FereastraPlata f = new FereastraPlata();
                f.setSize(500, 700);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
            else if (e.getSource()==termen) {
                FereastraListaPlata f = new FereastraListaPlata();
                f.setSize(700, 450);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
        }
    }
}
