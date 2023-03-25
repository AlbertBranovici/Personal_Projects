import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;

public class FereastraPlata extends JFrame {
    private JLabel l, eroare;
    private JTextField nrContract;
    private JButton cautare, confirmare, back;
    private JTextArea dateContract;
    private JPanel p;
    private FereastraPlata.ControllerFereastraPlata c;
    public FereastraPlata(){
        super("Plata asigurare");

        l = new JLabel("Introduceti numarul contractului");
        nrContract = new JTextField(10);
        cautare = new JButton("Cautare");

        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);

        dateContract = new JTextArea(30,38);
        dateContract.setEditable(false);

        confirmare = new JButton("Confirma plata");
        confirmare.setEnabled(false);

        back = new JButton("Inapoi la Meniu");

        p = new JPanel(new FlowLayout(0,20,20));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        c = new FereastraPlata.ControllerFereastraPlata();

        cautare.addActionListener(c);
        confirmare.addActionListener(c);
        back.addActionListener(c);

        p.add(l);
        p.add(nrContract);
        p.add(cautare);
        p.add(eroare);
        p.add(dateContract);
        p.add(confirmare);
        p.add(back);
        this.add(p,BorderLayout.CENTER);
    }

    class ControllerFereastraPlata implements ActionListener {

        public void actionPerformed(ActionEvent e){

            if (e.getSource() == back){
                FereastraPrincipala f = new FereastraPrincipala();
                f.setSize(400, 200);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }

            if(e.getSource() == cautare){
                int n;
                try {
                    eroare.setVisible(false);
                    p.validate();
                    validate();

                    n = Integer.parseInt(nrContract.getText());

                    String nume = "contract " + n + ".txt";

                    BufferedReader br = new BufferedReader(new FileReader("Contracte\\"+ nume));
                    dateContract.read(br, null);
                    br.close();
                    confirmare.setEnabled(true);
                    p.validate();
                    validate();

                }catch (IOException | NumberFormatException e1) {
                    if(e1.getClass().equals(NumberFormatException.class)){
                        if (nrContract.getText().equals(""))
                            eroare.setText("Trebuie sa introduceti numarul contractului");
                        else eroare.setText("Trebuie introdus un numar intreg");
                    }
                    else{
                        eroare.setText("Contractul nu exista");
                    }
                    eroare.setVisible(true);
                    p.validate();
                    validate();
                }
            }

            if(e.getSource() == confirmare){
                try{
                    eroare.setVisible(false);
                    p.validate();
                    validate();

                    int n = Integer.parseInt(nrContract.getText());
                    BufferedReader br = new BufferedReader(new FileReader("Contracte\\bazaContracte.txt"));
                    String linie, continut = "";
                    String[] data;
                    boolean b = true;
                    linie = br.readLine();
                    continut += "Nr,Nume,Prenume,CNP,IntervalPlata,DataPlata,DataAnulare";
                    while (true){
                        linie = br.readLine();
                        if(linie == null)
                            break;
                        data = linie.split(",");
                        if(Integer.parseInt(data[0]) == n){
                            b = false;
                            continut += "\n" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + ",";
                            String[] dataPlata = data[5].split("-");
                            LocalDate dataNoua = LocalDate.of(Integer.parseInt(dataPlata[0]), Integer.parseInt(dataPlata[1]), Integer.parseInt(dataPlata[2]));
                            if(data[4].equals("Lunar"))
                                dataNoua = dataNoua.plusMonths(1);
                            if(data[4].equals("Semestrial"))
                                dataNoua = dataNoua.plusMonths(6);
                            if(data[4].equals("Anual"))
                                dataNoua = dataNoua.plusYears(1);
                            continut += dataNoua + "," + dataNoua.plusDays(15);
                            JOptionPane.showMessageDialog(null, "Plata a fost efectuata, iar termenul urmatoarei plati este: " + dataNoua);
                        }
                        else{
                            continut += "\n" + linie;
                        }
                    }
                    FileWriter myWriter = new FileWriter("Contracte\\bazaContracte.txt");
                    myWriter.write(continut);
                    myWriter.close();
                    if(b == true){
                        eroare.setText("Contractul nu exista");
                        eroare.setVisible(true);
                        p.validate();
                        validate();
                    }
                    br.close();
                }catch (IOException | NumberFormatException e1){
                    if(e1.getClass().equals(NumberFormatException.class)){
                        eroare.setText("Trebuie introdus un numar intreg");
                        eroare.setVisible(true);
                        p.validate();
                        validate();
                    }
                    else {
                        System.out.println("fisierul nu a fost gasit");
                    }
                }
            }
        }
    }
}
