package sv.edu.udb.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Nota extends JFrame {
    private JPanel pnlNota;
    private JLabel lblTitulo;
    private JLabel lblEstudiante;
    private JLabel lblMateria;
    private JComboBox cmbEstudiantes;
    private JComboBox cmbMaterias;
    private JLabel lblNotaAsignada;
    private JTextField txtNotaAsignada;
    private JButton btnGuardar;
    private JButton btnRegresar;

    public Nota(){
        this.setTitle("Nota");
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlNota);
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
        JFrame frameNota = new Nota();
        frameNota.setVisible(true);
    }
}
