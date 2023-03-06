import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;

public class printContract extends JFrame {
    private JTextArea dateContract;
    private JButton print, cancel, printF;
    private ControllerFereastraPrintContract c;
    public printContract(insuranceContract contract, int nr){
        super("Confirm contract");
        contract.contractNumber = nr;
        contract.calculateInsurancePrice();
        contract.calculatePaymentRate();
        contract.setPaymentDate();
        dateContract = new JTextArea(contract.toString(),30,35);
        dateContract.setEditable(false);

        print = new JButton("Print");
        cancel = new JButton("Cancel");
        printF = new JButton("Print to file");

        c = new ControllerFereastraPrintContract(contract);

        print.addActionListener(c);
        cancel.addActionListener(c);
        printF.addActionListener(c);

        this.add(dateContract);
        this.add(print);
        this.add(printF);
        this.add(cancel);
        this.setLayout(new FlowLayout(1, 20, 20));
        this.setVisible(true);
    }

    class ControllerFereastraPrintContract implements ActionListener {
        private insuranceContract contract;
        public ControllerFereastraPrintContract(insuranceContract contract){
            this.contract = contract;
        }

        public void actionPerformed(ActionEvent e){
            if (e.getSource()==print){
                PrinterJob printer=PrinterJob.getPrinterJob();
                Book bk=new Book();
                bk.append(new contentPanel(),printer.defaultPage());
                printer.setPageable(bk);
                if(printer.printDialog()){
                    try{
                        printer.print();
                    }catch (PrinterException pe){
                        dateContract.append("Printer is not connected");
                        dateContract.repaint();
                    }
                    catch(ArrayIndexOutOfBoundsException ae){
                        System.out.println("Array out of bounds");
                    }
                }
            }
            else if (e.getSource()==printF){
                try {
                    File file = new File("Contracts\\contract " + contract.contractNumber + ".txt");
                    file.createNewFile();

                    FileWriter myWriter = new FileWriter(file);
                    myWriter.write(contract.toString());
                    myWriter.close();

                }catch (IOException e1){
                    try{
                        System.out.println("file exists already");
                        FileWriter myWriter = new FileWriter("Contracts\\contract " + contract.contractNumber + ".txt");
                        myWriter.write(contract.toString());
                        myWriter.close();
                    }catch (IOException e2){
                        System.out.println("file not found");
                    }
                }

                try{
                    BufferedReader br = new BufferedReader(new FileReader("Contracts\\contractDataBase.txt"));
                    String linie, linieNoua, content = "";
                    linieNoua = contract.contractNumber + "," + contract.client.getFirstName() + "," + contract.client.getLastName() + "," + contract.client.getCnp() + "," + contract.paymentInterval + "," + contract.paymentDate + "," + contract.paymentDate.plusDays(15);
                    String[] data;
                    boolean b = true;
                    linie = br.readLine();
                    content += linie;
                    while (true){
                        linie = br.readLine();
                        if(linie == null)
                            break;
                        data = linie.split(",");
                        if(Integer.parseInt(data[0]) == contract.contractNumber){
                            b = false;
                            content += "\n" + linieNoua;
                        }
                        else {
                            content += "\n" + linie;
                        }
                    }
                    if(b == true)
                        content += "\n" + linieNoua;

                    FileWriter myWriter = new FileWriter("Contracts\\contractDataBase.txt");
                    myWriter.write(content);
                    myWriter.close();
                }catch (IOException e1){
                    System.out.println("file not found");
                }
            }
                else if (e.getSource()==cancel) {
                    mainWindow f = new mainWindow();
                    f.setSize(400, 200);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setResizable(false);
                    dispose();
                }
        }
    }
    class contentPanel extends JPanel implements Printable {
        private BufferedReader br;
        private String sir = "";
        public int print(Graphics g, PageFormat pf, int pageIndex)  throws PrinterException{
            g.setColor(Color.black);
            try{
                StringReader content = new StringReader(dateContract.getText());
                br = new BufferedReader(content);
                int i = 0;
                while((sir = br.readLine()) != null) {
                    if (sir.length() == 0) sir = " ";
                    g.drawString(sir,100,100 + i);
                    i += 20;
                }
            }catch(IOException io){}
            catch (IllegalArgumentException ie){}
            return Printable.PAGE_EXISTS;
        }
    }
}
