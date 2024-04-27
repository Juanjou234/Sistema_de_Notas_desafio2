package sv.edu.udb.forms;

import sv.edu.udb.conexion.Conexion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.SQLException;

public class VerNotas extends JFrame {
    private JPanel pnlVerNotas;
    private JLabel lblTitulo;
    private JTable tblNotas;
    private JButton btnRegresar;

    private DefaultTableModel modeloTBL_Notas = null;

    public VerNotas() {
        this.setTitle("Visualizar Notas");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(pnlVerNotas);
        this.setLocationRelativeTo(getParent());

        // setteamos el modelo de la tabla
        modeloTBL_Notas = new DefaultTableModel();
        tblNotas.setModel(modeloTBL_Notas);

        modeloTBL_Notas.addColumn("Estudiante");
        modeloTBL_Notas.addColumn("Materia");
        modeloTBL_Notas.addColumn("Nota");

        // cargamos datos de la tabla
        cargarDatosNotas();

        btnRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameMain = new Main();
                frameMain.setVisible(true);
                dispose();
            }
        });
    }

    private void cargarDatosNotas() {
        try {
            Connection conn = Conexion.getConnection();
            String sql = "SELECT CONCAT(e.nombres, ' ', e.apellidos) AS nombre_estudiante, m.Materia, n.nota " +
                    "FROM notas n " +
                    "JOIN estudiantes e ON n.IdEstudiante = e.IdEstudiante " +
                    "JOIN materias m ON n.IdMateria = m.IdMateria";
            PreparedStatement stmnt = conn.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            // se debe limpiar el modelo de la tabla antes de settear los datos
            modeloTBL_Notas.setRowCount(0);

            while (rs.next()) {
                String nombreEstudiante = rs.getString("nombre_estudiante");
                String materia = rs.getString("Materia");
                double nota = rs.getDouble("nota");

                // se añaden rows al modelo de la tabla tblNotas
                Object[] fila = {nombreEstudiante, materia, nota};
                modeloTBL_Notas.addRow(fila);
            }

            Conexion.close(rs);
            Conexion.close(stmnt);
            Conexion.close(conn);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frameVisualizarNotas = new VerNotas();
        frameVisualizarNotas.setVisible(true);
    }
}
