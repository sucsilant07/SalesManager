
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class proveedorDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean registrarProveedor(proveedor pd){
        String sql = "INSERT INTO proveedor (ruc, nombre, telefono, direccion, razon) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pd.getRuc());
            ps.setString(2, pd.getNombre());
            ps.setInt(3, pd.getTelefono());
            ps.setString(4, pd.getDireccion());
            ps.setString(5, pd.getRazon());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public List listarProveedor(){
        List<proveedor> listaPd = new ArrayList();
        String sql = "SELECT * FROM proveedor";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                proveedor pd = new proveedor();
                pd.setId(rs.getInt("id"));
                pd.setRuc(rs.getInt("ruc"));
                pd.setNombre(rs.getString("nombre"));
                pd.setTelefono(rs.getInt("telefono"));
                pd.setDireccion(rs.getString("direccion"));
                pd.setRazon(rs.getString("razon"));
                listaPd.add(pd);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listaPd;
    }
    
    public boolean eliminarProveedor(int id){
        String sql = "DELETE FROM proveedor WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }
    
    public boolean modificarProveedor(proveedor pd){
        String sql = "UPDATE proveedor SET ruc=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, pd.getRuc());
            ps.setString(2, pd.getNombre());
            ps.setInt(3, pd.getTelefono());
            ps.setString(4, pd.getDireccion());
            ps.setString(5, pd.getRazon());
            ps.setInt(6, pd.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
}