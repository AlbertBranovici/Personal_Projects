import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class windowStep2 extends JFrame {
    private JComboBox make, yearOfProduciton, model;
    private carList carList;
    private carList sortByMake, sortByYear;
    private  JTextArea caracteristici;
    private JButton pasul3;
    private JLabel eroare;
    private JPanel p1, p2;
    protected car car;
    protected client client;
    protected int nr;
    private windowStep2.ControllerwindowStep2 c;
    public windowStep2(client client, int nr){
        super("Step 2: Car's details");

        this.client = client;
        this.nr = nr;

        p1 = new JPanel();
        p1.setLayout(new FlowLayout(-1,20, 20));
        p1.setBounds(100, 50, 400, 50);

        this.carList = new carList();

        make = new JComboBox<>();
        make.addItem("Make");
        int pp;
        for(car m: carList.list){
            pp = 1;
            for(int i = 1; i < make.getItemCount() && pp == 1; i++)
                if(make.getItemAt(i).equals(m.getMake()))
                    pp = 0;
            if(pp == 1) make.addItem(m.getMake());
        }
        p1.add(make);

        yearOfProduciton = new JComboBox<>();
        yearOfProduciton.addItem("Year of production");
        yearOfProduciton.setEnabled(false);
        p1.add(yearOfProduciton);

        model = new JComboBox<>();
        model.addItem("Model");
        model.setEnabled(false);
        p1.add(model);

        p2 = new JPanel(new FlowLayout(-1, 20, 20));
        p2.setBounds(100, 100, 350, 300);
        p2.setVisible(false);

        caracteristici=new JTextArea(10, 15);
        caracteristici.setEditable(false);
        p2.add(caracteristici);

        pasul3 = new JButton("Next");
        p2.add(pasul3);

        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);
        p2.add(eroare);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Images\\chei.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(500,0,400, 400);
        }catch (IOException e) {
            System.out.println("file not found");
        }

        c = new windowStep2.ControllerwindowStep2();

        make.addActionListener(c);
        yearOfProduciton.addActionListener(c);
        model.addActionListener(c);
        pasul3.addActionListener(c);

        this.setLayout(null);

        this.add(p1);
        this.add(p2);
        this.add(picture);
    }

    class ControllerwindowStep2 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == make) {
                yearOfProduciton.setEnabled(false);
                model.setEnabled(false);

                if(!make.getItemAt(make.getSelectedIndex()).toString().equals("Make")){
                    for(int i = yearOfProduciton.getItemCount()-1; i > 0; i--)
                        yearOfProduciton.removeItemAt(i);
                    sortByMake = carList.searchByMake(make.getItemAt(make.getSelectedIndex()).toString());
                    int p;
                    for(car m: sortByMake.list){
                        p = 1;
                        for(int i = 1; i < yearOfProduciton.getItemCount() && p == 1; i++)
                            if(yearOfProduciton.getItemAt(i).equals(m.getYearOfProduction()))
                                p = 0;
                        if(p == 1) yearOfProduciton.addItem(m.getYearOfProduction());
                    }
                    yearOfProduciton.setEnabled(true);
                }
                model.setEnabled(false);
                p1.validate();
                validate();
            }

            if(e.getSource() == yearOfProduciton){
                model.setEnabled(false);

                if(!yearOfProduciton.getItemAt(yearOfProduciton.getSelectedIndex()).toString().equals("Year of production")){
                    for(int i = model.getItemCount()-1; i > 0; i--)
                        model.removeItemAt(i);

                    sortByYear = sortByMake.searchByYear(make.getItemAt(make.getSelectedIndex()).toString(), Integer.parseInt(yearOfProduciton.getItemAt(yearOfProduciton.getSelectedIndex()).toString()));
                    int p;
                    for(car m: sortByYear.list){
                        p = 1;
                        for(int i = 1; i < model.getItemCount() && p == 1; i++)
                            if(model.getItemAt(i).equals(m.getModel()))
                                p = 0;
                        if(p == 1) model.addItem(m.getModel());
                    }
                    model.setEnabled(true);
                }
                p1.validate();
                validate();
            }

            if(e.getSource() == model){
                try {
                    car = sortByYear.searchCar(make.getItemAt(make.getSelectedIndex()).toString(), Integer.parseInt(yearOfProduciton.getItemAt(yearOfProduciton.getSelectedIndex()).toString()), model.getItemAt(model.getSelectedIndex()).toString());

                    caracteristici.setText(car.toString());

                    p2.setVisible(true);
                    p2.validate();
                    validate();
                }catch (NullPointerException e1){
                    System.out.println();
                }
            }

            if(e.getSource() == pasul3){
                eroare.setVisible(false);
                if(model.isEnabled() == false || model.getItemAt(model.getSelectedIndex()).toString().equals("Model")){
                    eroare.setText("The fields for Make, Year of production and Model must be filled!");
                    eroare.setVisible(true);
                }
                else {
                    windowStep3 f = new windowStep3(client, car, nr);
                    f.setSize(950, 450);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setResizable(false);
                    dispose();
                }
            }
        }
    }
}
