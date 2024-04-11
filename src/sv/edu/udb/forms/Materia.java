package sv.edu.udb.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Materia extends JFrame {
    private JPanel pnlMateria;
    private JLabel lblTitulo;
    private JLabel lblMateria;
    private JTextField txtMateria;
    private JButton btnGuardar;
    private JButton btnRegresar;

    public Materia(){
        this.setTitle("Materia");
        this.setSize(600,250);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlMateria);
        this.setLocationRelativeTo(getParent());
        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameMain = new Main();
                frameMain.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frameMateria = new Materia();
        frameMateria.setVisible(true);
    }
}
