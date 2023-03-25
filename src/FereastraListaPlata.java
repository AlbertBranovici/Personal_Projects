import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;

public class FereastraListaPlata extends JFrame {
    private JTextArea listaClienti;
    private JButton back, sterge;
    private FereastraListaPlata.ControllerFereastraListaPlata c;
    public FereastraListaPlata(){
        super("Lista Clientilor care trebuie sa plateasca");
        this.setLayout(new FlowLayout(1, 20, 20));

        back = new JButton("Inapoi la Meniu");
        sterge = new JButton("Anuleaza contracte");

        c = new FereastraListaPlata.ControllerFereastraListaPlata();

        back.addActionListener(c);
        sterge.addActionListener(c);

        listaClienti = new JTextArea(20, 60);
        listaClienti.setEditable(false);

        String clienti= "";
        String[] data, dataSplit;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Contracte\\bazaContracte.txt"));
            String linie = br.readLine();
            int k = 1;
            while(true){
                linie = br.readLine();
                if(linie == null)
                    break;
                data = linie.split(",");
                dataSplit = data[5].split("-");
                LocalDate dataPlata = LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
                if(Period.between(LocalDate.now(), dataPlata).isNegative()){
                    clienti += k + ". " + data[1] + " " + data[2] + ", Contract nr." + data[0] + ", Deadline Plata: " + data[5] + ", Data Anularii Contractului: " + data[6] + "\n";
                    k++;
                }
            }
            listaClienti.setText(clienti);
        } catch (IOException e) {
            System.out.println("fisierul nu a fost gasit");
        }

        this.add(listaClienti);
        this.add(back);
        this.add(sterge);
        this.setVisible(true);
    }

    class ControllerFereastraListaPlata implements ActionListener {

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

            if(e.getSource() == sterge){
                String clienti= "";
                String[] data, dataSplit;
                try {
                    File file;
                    BufferedReader br = new BufferedReader(new FileReader("Contracte\\bazaContracte.txt"));
                    String linie = br.readLine();
                    clienti += linie;
                    while(true){
                        linie = br.readLine();
                        if(linie == null)
                            break;
                        data = linie.split(",");
                        dataSplit = data[6].split("-");
                        LocalDate dataAnulare = LocalDate.of(Integer.parseInt(dataSplit[0]), Integer.parseInt(dataSplit[1]), Integer.parseInt(dataSplit[2]));
                        if(Period.between(LocalDate.now(), dataAnulare).isNegative()){
                            file = new File("Contracte\\contract " + data[0] + ".txt");
                            file.delete();
                        }
                        else
                            clienti += "\n" + linie;
                    }
                    FileWriter myWriter = new FileWriter("Contracte\\bazaContracte.txt");
                    myWriter.write(clienti);
                    myWriter.close();

                } catch (IOException e1) {
                    System.out.println("fisierul nu a fost gasit");
                }
            }
        }
    }
}
