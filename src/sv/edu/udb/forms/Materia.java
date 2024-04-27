package sv.edu.udb.forms;

import sv.edu.udb.conexion.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verificacoin si hay algun campo vacio
                String materia = txtMateria.getText().trim();
                if (materia.isEmpty()) {
                    JOptionPane.showMessageDialog(Materia.this, "El campo Materia debe estar lleno", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // verificacion de "nombre de materia" (String) repetido
                try {
                    Connection conn = Conexion.getConnection();

                    // consulta con count para verificar el numero de registros iguales
                    String sql_materia_repetida = "SELECT COUNT(*) FROM materias WHERE Materia = ?";
                    PreparedStatement repetida_stmnt = conn.prepareStatement(sql_materia_repetida);
                    repetida_stmnt.setString(1, materia);

                    ResultSet rs = repetida_stmnt.executeQuery();

                    int repetida = 0;
                    while (rs.next()) {
                        repetida = rs.getInt(1);
                    }

                    // Cerrar statement y resultset
                    Conexion.close(rs);
                    Conexion.close(repetida_stmnt);

                    // validacion si hay mas de 0 materias repetidas (por el nombre)
                    if (repetida > 0) {
                        JOptionPane.showMessageDialog(Materia.this, "La materia ya existe. Ingrese un nombre diferente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        Conexion.close(conn);
                        return;
                    }

                    // consulta de insercion de datos en caso de no haber materia repetida en consulta anterior
                    String sql_insert = "INSERT INTO materias (Materia) VALUES (?)";
                    PreparedStatement stmnt = conn.prepareStatement(sql_insert);

                    stmnt.setString(1, materia);
                    stmnt.executeUpdate();

                    Conexion.close(stmnt);
                    Conexion.close(conn);

                    JOptionPane.showMessageDialog(Materia.this, "Materia guardada correctamente");

                    // Limpieza automatica de txt field de materia
                    txtMateria.setText("");

                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frameMateria = new Materia();
        frameMateria.setVisible(true);
    }
}
