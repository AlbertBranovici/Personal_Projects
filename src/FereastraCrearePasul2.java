import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FereastraCrearePasul2 extends JFrame {
    private JComboBox marca, anFabricatie, model;
    private ListaMasini listaMasini;
    private ListaMasini listaDupaMarca, listaDupaAn;
    private  JTextArea caracteristici;
    private JButton pasul3;
    private JLabel eroare;
    private JPanel p1, p2;
    protected Masina masina;
    protected Client client;
    protected int nr;
    private FereastraCrearePasul2.ControllerFereastraCrearePasul2 c;
    public FereastraCrearePasul2(Client client, int nr){
        super("Pasul 2: Datele masinii");

        this.client = client;
        this.nr = nr;

        p1 = new JPanel();
        p1.setLayout(new FlowLayout(-1,20, 20));
        p1.setBounds(100, 50, 400, 50);

        this.listaMasini = new ListaMasini();

        marca = new JComboBox<>();
        marca.addItem("Marca");
        int pp;
        for(Masina m: listaMasini.lista){
            pp = 1;
            for(int i = 1; i < marca.getItemCount() && pp == 1; i++)
                if(marca.getItemAt(i).equals(m.getMarca()))
                    pp = 0;
            if(pp == 1) marca.addItem(m.getMarca());
        }
        p1.add(marca);

        anFabricatie = new JComboBox<>();
        anFabricatie.addItem("An Fabricatie");
        anFabricatie.setEnabled(false);
        p1.add(anFabricatie);

        model = new JComboBox<>();
        model.addItem("Model");
        model.setEnabled(false);
        p1.add(model);

        p2 = new JPanel(new FlowLayout(-1, 20, 20));
//        p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        p2.setBounds(100, 100, 350, 300);
        p2.setVisible(false);

        caracteristici=new JTextArea(10, 15);
        caracteristici.setEditable(false);
        p2.add(caracteristici);

        pasul3 = new JButton("Pasul urmator");
        p2.add(pasul3);

        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);
        p2.add(eroare);

        BufferedImage image;
        JLabel picture = null;
        try{
            image = ImageIO.read(new File("Imagini\\chei.png"));
            picture =  new JLabel(new ImageIcon(image));
            picture.setBounds(500,0,400, 400);
        }catch (IOException e) {
            System.out.println("fisierul nu a fost gasit");
        }

        c = new FereastraCrearePasul2.ControllerFereastraCrearePasul2();

        marca.addActionListener(c);
        anFabricatie.addActionListener(c);
        model.addActionListener(c);
        pasul3.addActionListener(c);

        this.setLayout(null);

        this.add(p1);
        this.add(p2);
        this.add(picture);
    }

    class ControllerFereastraCrearePasul2 implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == marca) {
                anFabricatie.setEnabled(false);
                model.setEnabled(false);

                if(!marca.getItemAt(marca.getSelectedIndex()).toString().equals("Marca")){
                    for(int i = anFabricatie.getItemCount()-1; i > 0; i--)
                        anFabricatie.removeItemAt(i);
                    listaDupaMarca = listaMasini.cautaDupaMarca(marca.getItemAt(marca.getSelectedIndex()).toString());
                    int p;
                    for(Masina m: listaDupaMarca.lista){
                        p = 1;
                        for(int i = 1; i < anFabricatie.getItemCount() && p == 1; i++)
                            if(anFabricatie.getItemAt(i).equals(m.getAnFabricatie()))
                                p = 0;
                        if(p == 1) anFabricatie.addItem(m.getAnFabricatie());
                    }
                    anFabricatie.setEnabled(true);
                }
                model.setEnabled(false);
                p1.validate();
                validate();
            }

            if(e.getSource() == anFabricatie){
                model.setEnabled(false);

                if(!anFabricatie.getItemAt(anFabricatie.getSelectedIndex()).toString().equals("An Fabricatie")){
                    for(int i = model.getItemCount()-1; i > 0; i--)
                        model.removeItemAt(i);

                    listaDupaAn = listaDupaMarca.cautaDupaAn(marca.getItemAt(marca.getSelectedIndex()).toString(), Integer.parseInt(anFabricatie.getItemAt(anFabricatie.getSelectedIndex()).toString()));
                    int p;
                    for(Masina m: listaDupaAn.lista){
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
                    masina = listaDupaAn.cautaMasina(marca.getItemAt(marca.getSelectedIndex()).toString(), Integer.parseInt(anFabricatie.getItemAt(anFabricatie.getSelectedIndex()).toString()), model.getItemAt(model.getSelectedIndex()).toString());

                    caracteristici.setText(masina.toString());

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
                    eroare.setText("Campurile Marca, An Fabricatie si Model sunt obligatorii!");
                    eroare.setVisible(true);
                }
                else {
                    FereastraCrearePasul3 f = new FereastraCrearePasul3(client, masina, nr);
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
