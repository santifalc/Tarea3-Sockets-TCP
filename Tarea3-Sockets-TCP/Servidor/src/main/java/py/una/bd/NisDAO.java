/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package py.una.bd;

import py.una.entidad.Nis;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;

/**
 *
 * @author Santi
 */
public class NisDAO {

    /**
     *
     * @return
     */
    public List<Nis> seleccionar() {
        String query = "SELECT id, estado FROM nis";

        List lista = new ArrayList<Nis>();

        Connection conn = null;
        try {
            conn = Bd.connect();
            ResultSet rs = conn.createStatement().executeQuery(query);

            while (rs.next()) {
                Nis c = new Nis();
                c.setId(null);
                c.setEstado(null);

                lista.add(c);
            }

        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return lista;

    }

    public Nis seleccionarPorIdNis(Long id) {
        String SQL = "SELECT id, estado, consumo FROM nis WHERE id = ? ";

        Nis lista = new Nis();

        Connection conn = null;
        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Nis m = new Nis();
                m.setId(rs.getLong(1));
                m.setEstado(rs.getBoolean(2));
                m.setConsumo(rs.getInt(3));
                lista = m;
            }

        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return lista;

    }

    public ArrayList<Nis> seleccionarPorEstado(Nis c) {
        String SQL = "SELECT id FROM nis WHERE estado = ? ";

        List lista;
        lista = new ArrayList<Nis>();

        Connection conn = null;
        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setBoolean(1, c.getEstado());
            System.out.println(pstmt.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Long m;
                m = rs.getLong(1);
                lista.add(m);
            }

        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return (ArrayList<Nis>) lista;

    }

    public long insertar(Nis c) throws SQLException {

        String SQL = "INSERT INTO nis(id, estado, consumo) "
                + "VALUES(?,?,?)";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId());
            pstmt.setBoolean(2, TRUE);
            pstmt.setInt(3, c.getConsumo());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la insercion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }

        return id;

    }

    public long conectar(Nis c) throws SQLException {

        String SQL = "UPDATE nis SET id= ? , estado= ? , consumo= ? WHERE id = ? ";
        
        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId());
            pstmt.setBoolean(2, Boolean.TRUE);
            pstmt.setInt(3, c.getConsumo());
            pstmt.setLong(4, c.getId());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la actualizacion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return id;
    }
    public long desconectar(Nis c) throws SQLException {

        String SQL = "UPDATE nis SET id= ? , estado= ? , consumo = ?  WHERE id = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, c.getId());
            pstmt.setBoolean(2, Boolean.FALSE);
            pstmt.setInt(3, c.getConsumo());
            pstmt.setLong(4, c.getId());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la actualizacion: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return id;
    }

    public long borrar(long id_nis) throws SQLException {

        String SQL = "DELETE FROM nis WHERE id = ? ";

        long id = 0;
        Connection conn = null;

        try {
            conn = Bd.connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, id_nis);

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try ( ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la eliminación: " + ex.getMessage());
        } finally {
            try {
                conn.close();
            } catch (Exception ef) {
                System.out.println("No se pudo cerrar la conexion a BD: " + ef.getMessage());
            }
        }
        return id;
    }
}
