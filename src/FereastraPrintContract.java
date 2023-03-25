import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;

public class FereastraPrintContract extends JFrame {
    private JTextArea dateContract;
    private JButton print, cancel, printF;
    private ControllerFereastraPrintContract c;
    public FereastraPrintContract(ContractDeAsigurare contract, int nr){
        super("Finalizare Contract");
        contract.nrContract = nr;
        contract.calculeazaSumaAsigurata();
        contract.calculeazaRata();
        contract.setDataPlata();
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
        private ContractDeAsigurare contract;
        public ControllerFereastraPrintContract(ContractDeAsigurare contract){
            this.contract = contract;
        }

        public void actionPerformed(ActionEvent e){
            if (e.getSource()==print){
                PrinterJob imprimanta=PrinterJob.getPrinterJob();
                Book bk=new Book();
                bk.append(new PanouContinut(),imprimanta.defaultPage());
                imprimanta.setPageable(bk);
                if(imprimanta.printDialog()){
                    try{
                        imprimanta.print();
                    }catch (PrinterException pe){
                        dateContract.append("Imprimanta nu exista");
                        dateContract.repaint();
                    }
                    catch(ArrayIndexOutOfBoundsException ae){
                        System.out.println("Ce se printeaza???");
                    }
                }
            }
            else if (e.getSource()==printF){
                try {
                    File file = new File("Contracte\\contract " + contract.nrContract + ".txt");
                    file.createNewFile();

                    FileWriter myWriter = new FileWriter(file);
                    myWriter.write(contract.toString());
                    myWriter.close();

                }catch (IOException e1){
                    try{
                        System.out.println("fisierul exista deja");
                        FileWriter myWriter = new FileWriter("Contracte\\contract " + contract.nrContract + ".txt");
                        myWriter.write(contract.toString());
                        myWriter.close();
                    }catch (IOException e2){
                        System.out.println("fisierul nu exista");
                    }
                }

                try{
                    BufferedReader br = new BufferedReader(new FileReader("Contracte\\bazaContracte.txt"));
                    String linie, linieNoua, continut = "";
                    linieNoua = contract.nrContract + "," + contract.client.getNume() + "," + contract.client.getPrenume() + "," + contract.client.getCnp() + "," + contract.intervalPlata + "," + contract.dataPlata + "," + contract.dataPlata.plusDays(15);
                    String[] data;
                    boolean b = true;
                    linie = br.readLine();
                    continut += linie;
                    while (true){
                        linie = br.readLine();
                        if(linie == null)
                            break;
                        data = linie.split(",");
                        if(Integer.parseInt(data[0]) == contract.nrContract){
                            b = false;
                            continut += "\n" + linieNoua;
                        }
                        else {
                            continut += "\n" + linie;
                        }
                    }
                    if(b == true)
                        continut += "\n" + linieNoua;

                    FileWriter myWriter = new FileWriter("Contracte\\bazaContracte.txt");
                    myWriter.write(continut);
                    myWriter.close();
                }catch (IOException e1){
                    System.out.println("fisierul nu a fost gasit");
                }

            }
                else if (e.getSource()==cancel) {
                    FereastraPrincipala f = new FereastraPrincipala();
                    f.setSize(400, 200);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setResizable(false);
                    dispose();
                }
        }
    }
    class PanouContinut extends JPanel implements Printable {
        private BufferedReader br;
        private String sir = "";
        public int print(Graphics g, PageFormat pf, int pageIndex)  throws PrinterException{
            g.setColor(Color.black);
            try{
                StringReader continut = new StringReader(dateContract.getText());
                br = new BufferedReader(continut);
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
