import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;

public class paymentWindow extends JFrame {
    private JLabel l, eroare;
    private JTextField nrContract;
    private JButton search, confirm, back;
    private JTextArea contractDetails;
    private JPanel p;
    private paymentWindow.ControllerpaymentWindow c;
    public paymentWindow(){
        super("Pay insurance");

        l = new JLabel("Enter the contract number");
        nrContract = new JTextField(10);
        search = new JButton("Search");

        eroare = new JLabel();
        eroare.setForeground(new Color(225, 12, 12));
        eroare.setVisible(false);

        contractDetails = new JTextArea(30,38);
        contractDetails.setEditable(false);

        confirm = new JButton("Confirm payment");
        confirm.setEnabled(false);

        back = new JButton("Back");

        p = new JPanel(new FlowLayout(0,20,20));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        c = new paymentWindow.ControllerpaymentWindow();

        search.addActionListener(c);
        confirm.addActionListener(c);
        back.addActionListener(c);

        p.add(l);
        p.add(nrContract);
        p.add(search);
        p.add(eroare);
        p.add(contractDetails);
        p.add(confirm);
        p.add(back);
        this.add(p,BorderLayout.CENTER);
    }

    class ControllerpaymentWindow implements ActionListener {

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

            if(e.getSource() == search){
                int n;
                try {
                    eroare.setVisible(false);
                    p.validate();
                    validate();

                    n = Integer.parseInt(nrContract.getText());

                    String name = "contract " + n + ".txt";

                    BufferedReader br = new BufferedReader(new FileReader("Contracts\\"+ name));
                    contractDetails.read(br, null);
                    br.close();
                    confirm.setEnabled(true);
                    p.validate();
                    validate();

                }catch (IOException | NumberFormatException e1) {
                    if(e1.getClass().equals(NumberFormatException.class)){
                        if (nrContract.getText().equals(""))
                            eroare.setText("You must enter the contract number");
                        else eroare.setText("You have to enter an integer");
                    }
                    else{
                        eroare.setText("The contract doesn't exist");
                    }
                    eroare.setVisible(true);
                    p.validate();
                    validate();
                }
            }

            if(e.getSource() == confirm){
                try{
                    eroare.setVisible(false);
                    p.validate();
                    validate();

                    int n = Integer.parseInt(nrContract.getText());
                    BufferedReader br = new BufferedReader(new FileReader("Contracts\\contractDataBase.txt"));
                    String linie, content = "";
                    String[] data;
                    boolean b = true;
                    linie = br.readLine();
                    content += "Nr,First name,Last name,CNP,PaymentInterval,PaymentDate,CancellingDate";
                    while (true){
                        linie = br.readLine();
                        if(linie == null)
                            break;
                        data = linie.split(",");
                        if(Integer.parseInt(data[0]) == n){
                            b = false;
                            content += "\n" + data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4] + ",";
                            String[] dataPlata = data[5].split("-");
                            LocalDate newDate = LocalDate.of(Integer.parseInt(dataPlata[0]), Integer.parseInt(dataPlata[1]), Integer.parseInt(dataPlata[2]));
                            System.out.println(newDate);
                            if(data[4].equals("Monthly"))
                                newDate = newDate.plusMonths(1);
                            if(data[4].equals("Semestrial"))
                                newDate = newDate.plusMonths(6);
                            if(data[4].equals("Annual"))
                                newDate = newDate.plusYears(1);
                            content += newDate + "," + newDate.plusDays(15);
                            JOptionPane.showMessageDialog(null, "Payment has been confirmed and the next payment date is: " + newDate);
                        }
                        else{
                            content += "\n" + linie;
                        }
                    }
                    FileWriter myWriter = new FileWriter("Contracts\\contractDataBase.txt");
                    myWriter.write(content);
                    myWriter.close();
                    if(b == true){
                        eroare.setText("The contract doesn't exist");
                        eroare.setVisible(true);
                        p.validate();
                        validate();
                    }
                    br.close();
                }catch (IOException | NumberFormatException e1){
                    if(e1.getClass().equals(NumberFormatException.class)){
                        eroare.setText("You must enter an integer");
                        eroare.setVisible(true);
                        p.validate();
                        validate();
                    }
                    else {
                        System.out.println("file not found");
                    }
                }
            }
        }
    }
}
