import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        mainWindow f = new mainWindow();
        f.setSize(400, 200);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
    }
}