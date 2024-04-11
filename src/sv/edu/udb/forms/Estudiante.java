package sv.edu.udb.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Estudiante extends JFrame {
    private JPanel pnlEstudiante;
    private JLabel lblTitulo;
    private JLabel lblIdEstudiante;
    private JTextField txtIdEstudiante;
    private JLabel lblNombres;
    private JTextField txtNombres;
    private JLabel lblApellidos;
    private JTextField txtApellidos;
    private JLabel lblDireccion;
    private JTextField txtDireccion;
    private JLabel lblTelefono;
    private JTextField txtTelefono;
    private JButton btnGuardar;
    private JButton btnRegresar;

    public Estudiante(){
        this.setTitle("Estudiante");
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlEstudiante);
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
        JFrame frameEstudiante = new Estudiante();
        frameEstudiante.setVisible(true);
    }
}
