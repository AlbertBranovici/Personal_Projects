import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;

public class paymentListWindow extends JFrame {
    private JTextArea listaClienti;
    private JButton back, sterge;
    private paymentListWindow.ControllerPaymentListWindow c;
    public paymentListWindow(){
        super("List clients due to pay");
        this.setLayout(new FlowLayout(1, 20, 20));

        back = new JButton("Back");
        sterge = new JButton("Cancel contracts");

        c = new paymentListWindow.ControllerPaymentListWindow();

        back.addActionListener(c);
        sterge.addActionListener(c);

        listaClienti = new JTextArea(20, 60);
        listaClienti.setEditable(false);

        String clienti= "";
        String[] date, dateSplit;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Contracts\\contractDataBase.txt"));
            String linie = br.readLine();
            int k = 1;
            while(true){
                linie = br.readLine();
                if(linie == null)
                    break;
                date = linie.split(",");
                dateSplit = date[5].split("-");
                LocalDate dataPlata = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                if(Period.between(LocalDate.now(), dataPlata).isNegative()){
                    clienti += k + ". " + date[1] + " " + date[2] + ", Contract number" + date[0] + ", Payment date: " + date[5] + ", Contract cancellation date: " + date[6] + "\n";
                    k++;
                }
            }
            listaClienti.setText(clienti);
        } catch (IOException e) {
            System.out.println("file not found");
        }

        this.add(listaClienti);
        this.add(back);
        this.add(sterge);
        this.setVisible(true);
    }

    class ControllerPaymentListWindow implements ActionListener {

        public void actionPerformed(ActionEvent e){
            if (e.getSource() == back){
                mainWindow f = new mainWindow();
                f.setSize(400, 200);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                dispose();
            }

            if(e.getSource() == sterge){
                String clients= "";
                String[] date, dateSplit;
                try {
                    File file;
                    BufferedReader br = new BufferedReader(new FileReader("Contracts\\contractDataBase.txt"));
                    String line = br.readLine();
                    clients += line;
                    while(true){
                        line = br.readLine();
                        if(line == null)
                            break;
                        date = line.split(",");
                        dateSplit = date[6].split("-");
                        LocalDate dataAnulare = LocalDate.of(Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[2]));
                        if(Period.between(LocalDate.now(), dataAnulare).isNegative()){
                            file = new File("Contracts\\contract " + date[0] + ".txt");
                            file.delete();
                        }
                        else
                            clients += "\n" + line;
                    }
                    FileWriter myWriter = new FileWriter("Contracts\\contractDataBase.txt");
                    myWriter.write(clients);
                    myWriter.close();

                } catch (IOException e1) {
                    System.out.println("file not found");
                }
            }
        }
    }
}
