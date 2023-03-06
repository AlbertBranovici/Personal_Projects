import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class mainWindow extends JFrame {
    private JButton create, payment, termen;
    private mainWindow.ControllermainWindow c;
    public mainWindow(){
        super("Car insurance");
        create = new JButton("New contract");
        payment = new JButton("Payment");
        termen = new JButton("Clients late on payment");

        c = new mainWindow.ControllermainWindow();

        create.addActionListener(c);
        payment.addActionListener(c);
        termen.addActionListener(c);


        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout(1,20, 20));
        p1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        p1.add(create);
        p1.add(payment);
        p1.add(termen);

        this.add(p1, BorderLayout.CENTER);
    }

    class ControllermainWindow implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == create){
                windowStep1 f = new windowStep1();
                f.setSize(950, 450);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
            else if (e.getSource()==payment){
                paymentWindow f = new paymentWindow();
                f.setSize(500, 700);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }
            else if (e.getSource()==termen) {
                paymentListWindow f = new paymentListWindow();
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
