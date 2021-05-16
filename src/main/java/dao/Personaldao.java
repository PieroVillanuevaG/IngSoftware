
package dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import modelo.Personal;

public class Personaldao {
    
    public static final String SQL_VALIDAR = "select * from personal where dni =?";
    public static final String SQL_LISTAR="select * from personal";
    
    public static Personal listarPersonalXId(int id){
        Connection cn = conexion.conexion.abrir();
        Personal p=null;
        try{
            PreparedStatement stm =   cn.prepareStatement("select * from personal where idPersonal=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while(rs.next()){
                p = new Personal();
                p.setIdPersonal(rs.getInt("idPersonal"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getString("dni"));
                p.setImg(rs.getBinaryStream("img"));
            }
            cn.close();
            stm.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return p;
    }
    public static ArrayList<Personal> listarPersonal() {
        ArrayList<Personal> lista = new ArrayList<>();
        Connection cn = conexion.conexion.abrir();
        Personal p = null;
        try {
            PreparedStatement stm = cn.prepareStatement(SQL_LISTAR);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                 p = new Personal();
                p.setIdPersonal(rs.getInt("idPersonal"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getString("dni"));
                p.setImg(rs.getBinaryStream("img"));   
                lista.add(p);
            }
            cn.close();
            stm.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public static Personal validar(String dni) {
        Personal p = null;
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm = cn.prepareStatement(SQL_VALIDAR);
            stm.setString(1, dni);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                p = new Personal();
                p.setIdPersonal(rs.getInt("idPersonal"));
                p.setNombre(rs.getString("nombre"));
                p.setApellido(rs.getString("apellido"));
                p.setDni(rs.getString("dni"));
                p.setImg(rs.getBinaryStream("img"));
            }
            cn.close();
            stm.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public static void insertarPersonal(Personal p) {
        String sql = "insert into personal (nombre,apellido,dni,img)values(?,?,?,?)";
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            stm.setString(1,p.getNombre());
            stm.setString(2, p.getApellido());
            stm.setString(3, p.getDni());
            stm.setBlob(4, p.getImg());
            stm.executeUpdate();
            cn.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void guardarPersonal(Personal p) {
        String sql = "update personal set nombre=?,apellido=?,dni=? where idPersonal=?";
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            stm.setString(1,p.getNombre());
            stm.setString(2, p.getApellido());
            stm.setString(3, p.getDni());
            stm.setInt(4, p.getIdPersonal());
            stm.executeUpdate();
            cn.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static void deletePersonal(int id) {
        String sql = "delete from personal where idPersonal=?";
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            cn.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     public static void listarImg(int id,HttpServletResponse response){
         String sql = "select * from personal where idPersonal=?";
         Connection cn = conexion.conexion.abrir();
         InputStream inputStream = null;
         OutputStream outputStream = null;
         BufferedInputStream bufferedInputStream= null;
         BufferedOutputStream bufferedOutputStream = null;
         response.setContentType("image/*");
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            
            stm.setInt(1, id);
            outputStream=response.getOutputStream();
            
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                inputStream = rs.getBinaryStream("img");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int i = 0;
            while((i=bufferedInputStream.read())!=-1){
                bufferedOutputStream.write(i);
            }
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
     }
     
     
     
    /*public static void desconectarUsuario(int id) {
        String sql = "update usuario set estado='I' where idUsuario=?";
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            cn.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void conectarUsuario(int id) {
        String sql = "update usuario set estado='A' where idUsuario=?";
        Connection cn = conexion.conexion.abrir();
        try {
            PreparedStatement stm=cn.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            cn.close();
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
}
