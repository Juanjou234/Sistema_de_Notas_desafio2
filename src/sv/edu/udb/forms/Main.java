package sv.edu.udb.forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel pnlMain;
    private JLabel lblMain;
    private JButton btnEstudiante;
    private JButton btnMateria;
    private JButton btnNota;
    private JButton btnSalir;
    private JButton btnVerNotas;

    public Main(){
        this.setTitle("Main");
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlMain);
        this.setLocationRelativeTo(getParent());

        btnEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameEstudiante = new Estudiante();
                frameEstudiante.setVisible(true);
                dispose();
            }
        });
        btnMateria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameMateria = new Materia();
                frameMateria.setVisible(true);
                dispose();
            }
        });
        btnNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameNota = new Nota();
                frameNota.setVisible(true);
                dispose();
            }
        });
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnVerNotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameCuadroDeNotas = new VerNotas();
                frameCuadroDeNotas.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frameMain = new Main();
        frameMain.setVisible(true);
    }
}
