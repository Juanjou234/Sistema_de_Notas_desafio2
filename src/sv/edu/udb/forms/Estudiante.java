package sv.edu.udb.forms;

import sv.edu.udb.conexion.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
    private JButton btnLimpiar;

    public Estudiante() {
        this.setTitle("Estudiante");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlEstudiante);
        this.setLocationRelativeTo(getParent());

        btnRegresar.addActionListener(e -> {
            JFrame frameMain = new Main();
            frameMain.setVisible(true);
            dispose();
        });

        btnGuardar.addActionListener(e -> {
            // verificacion si algún campo está vacío
            // .trim() es para borrar los espacios en blanco del String obtenido del textField
            if (txtIdEstudiante.getText().trim().isEmpty() ||
                    txtNombres.getText().trim().isEmpty() ||
                    txtApellidos.getText().trim().isEmpty() ||
                    txtDireccion.getText().trim().isEmpty() ||
                    txtTelefono.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(Estudiante.this, "Todos los campos deben estar llenos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // rompe el ciclo del codigo
            }

            String idEstudianteText = txtIdEstudiante.getText().trim();
            // validar si el ID del estudiante  tiene más de 3 dígitos

            if (idEstudianteText.length() > 3) {
                JOptionPane.showMessageDialog(Estudiante.this, "El ID del estudiante no puede tener más de 3 dígitos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // obtenemos los datos de los textField y los guardamos en variables
            int idEstudiante = Integer.parseInt(idEstudianteText);
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String telefono = txtTelefono.getText().trim();

            // verificar si el ID del estudiante ya existe en la base de datos
            try {
                Connection conn = Conexion.getConnection();

                // consulta para contar el numero de estudiantes con el id que ha sido ingresado
                String sql_id_repetido = "SELECT COUNT(*) FROM estudiantes WHERE IdEstudiante = ?";
                PreparedStatement repetido_stmnt = conn.prepareStatement(sql_id_repetido);
                repetido_stmnt.setInt(1, idEstudiante);

                int repetido = 0;
                ResultSet rs = repetido_stmnt.executeQuery();
                while (rs.next()) {
                    repetido = rs.getInt(1);
                }

                // cerrar statement y resultset
                Conexion.close(rs);
                Conexion.close(repetido_stmnt);

                // si el id esta repetido al menos una vez, rompera el ciclo del codigo
                if (repetido > 0) {
                    JOptionPane.showMessageDialog(Estudiante.this, "El ID del estudiante ya existe. Por favor, ingrese un ID diferente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    Conexion.close(conn); // cerramos conexion porque no sera utilizada mas
                    return;
                }

                // si no hay id repetido, ejecutar query para insertar datos en la base de datos
                // tabla: estudiantes
                String sql_insert = "INSERT INTO estudiantes (IdEstudiante, nombres, apellidos, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmnt = conn.prepareStatement(sql_insert);

                // Establecer los valores de los parámetros de la consulta
                stmnt.setInt(1, idEstudiante);
                stmnt.setString(2, nombres);
                stmnt.setString(3, apellidos);
                stmnt.setString(4, direccion);
                stmnt.setString(5, telefono);

                // Ejecutar la consulta de inserción
                stmnt.executeUpdate();

                // Cerrar recursos
                Conexion.close(stmnt);
                Conexion.close(conn);

                JOptionPane.showMessageDialog(Estudiante.this, "Datos guardados correctamente");

            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }); // cierre de boton guardar

        btnLimpiar.addActionListener(e -> {
            txtIdEstudiante.setText("");
            txtNombres.setText("");
            txtApellidos.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
        });
    }

    public static void main(String[] args) {
        JFrame frameEstudiante = new Estudiante();
        frameEstudiante.setVisible(true);
    }
}
