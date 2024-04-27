package sv.edu.udb.forms;

import sv.edu.udb.conexion.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.SQLException;
import java.util.HashMap;

public class Nota extends JFrame {
    private JPanel pnlNota;
    private JLabel lblTitulo;
    private JLabel lblEstudiante;
    private JLabel lblMateria;
    private JComboBox<String> cmbEstudiantes;
    private JComboBox<String> cmbMaterias;
    private JLabel lblNotaAsignada;
    private JTextField txtNotaAsignada;
    private JButton btnGuardar;
    private JButton btnRegresar;
    private JButton btnLimpiar;

    // HashMaps para asociar los nombres de estudiantes y materias con sus IDs
    private HashMap<String, Integer> estudiantesMap;
    private HashMap<String, Integer> materiasMap;

    public Nota() {
        this.setTitle("Nota");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlNota);
        this.setLocationRelativeTo(getParent());

        estudiantesMap = new HashMap<>();
        materiasMap = new HashMap<>();

        cargarEstudiantes();
        cargarMaterias();

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
                guardarNota();
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNotaAsignada.setText("");
            }
        });
    }

    // metodo para cargar ComboBox con nombre de estudiantes
    public void cargarEstudiantes() {
        try {
            Connection conn = Conexion.getConnection();
            String sql = "SELECT IdEstudiante, CONCAT(nombres, ' ', apellidos) AS nombre_completo FROM estudiantes";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdEstudiante");
                String nombreCompleto = rs.getString("nombre_completo");

                // llenar ComboBox y hashMap con datos
                cmbEstudiantes.addItem(nombreCompleto);
                estudiantesMap.put(nombreCompleto, id);
            }

            Conexion.close(rs);
            Conexion.close(stmnt);
            Conexion.close(conn);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    // metodo para cargar materias en ComboBox de materias
    private void cargarMaterias() {
        try {
            Connection conn = Conexion.getConnection();
            String sql = "SELECT IdMateria, Materia FROM materias";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("IdMateria");
                String nombreMateria = rs.getString("Materia");

                // llenar ComboBox y hashMap con datos
                cmbMaterias.addItem(nombreMateria);
                materiasMap.put(nombreMateria, id);
            }

            Conexion.close(rs);
            Conexion.close(stmnt);
            Conexion.close(conn);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    private void guardarNota() {
        // verificacion de nota nula
        String notaAsignada = txtNotaAsignada.getText().trim();
        Double notaAsignadaInt = Double.parseDouble(notaAsignada);

        if (notaAsignada.isEmpty() || notaAsignadaInt>10 || notaAsignadaInt<0) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una nota válida.");
            return;
        }

        try {
            // obtener string de seleccion en comboBox
            // (String) para asignar el tipo de dato que devuelve
            String nombreEstudiante = (String) cmbEstudiantes.getSelectedItem();
            String nombreMateria = (String) cmbMaterias.getSelectedItem();

            // obtener los ids mediante el nombre de estudiante y nombre de materia
            int idEstudiante = estudiantesMap.get(nombreEstudiante);
            int idMateria = materiasMap.get(nombreMateria);

            // guardar nota del respectivo estudiante en la respetiva mateira
            Connection conn = Conexion.getConnection();
            String sql = "INSERT INTO notas (IdEstudiante, IdMateria, nota) VALUES (?, ?, ?)";
            PreparedStatement stmnt = conn.prepareStatement(sql);

            stmnt.setInt(1, idEstudiante);
            stmnt.setInt(2, idMateria);
            stmnt.setDouble(3, notaAsignadaInt);

            stmnt.executeUpdate();

            // Cerrar recursos
            Conexion.close(stmnt);
            Conexion.close(conn);

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(this, "Nota guardada correctamente.");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frameNota = new Nota();
        frameNota.setVisible(true);
    }
}
