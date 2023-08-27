package bo.edu.ucb.sis213.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Query {
    public Connection conectarBDD (){
        Connection connect = null;
        try {
            connect = Conexion.getConnection(); // Reemplaza esto con tu conexión real
        } catch (SQLException ex) {
            System.err.println("No se puede conectar a Base de Datos");
            ex.printStackTrace();
            System.exit(1);
        }
        return connect;
    }
    
    public int getPin(String alias){
        int pin = -1;
        Connection con = conectarBDD();
        String query = "SELECT pin FROM usuarios WHERE alias = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, alias);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pin = resultSet.getInt("pin");
                return pin; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pin;
    }

    public String getAlias(int pin){
        String alias = null;
        Connection con = conectarBDD();
        String query = "SELECT alias FROM usuarios WHERE pin = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                alias = resultSet.getString("alias");
                return alias; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alias;
    }

    public double getSaldo(String alias, int pin){
        double saldo = -1;
        Connection con = conectarBDD();
        String query = "SELECT saldo FROM usuarios WHERE alias = ? AND pin = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, alias);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                saldo = resultSet.getDouble("saldo");
                return saldo; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public String getNombre(String alias, int pin){
        String nombre = null;
        Connection con = conectarBDD();
        String query = "SELECT nombre FROM usuarios WHERE alias = ? AND pin = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, alias);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nombre = resultSet.getString("nombre");
                return nombre; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nombre;
    }

    public int getID(String alias, int pin){
        int id = -1;
        Connection con = conectarBDD();
        String query = "SELECT id FROM usuarios WHERE alias = ? AND pin = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, alias);
            preparedStatement.setInt(2, pin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
                return id; 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean setSaldo(int id, double cantidad){
        Connection con = conectarBDD();
        try {
            con.setAutoCommit(false);
            String Query = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
            PreparedStatement updateStatement = con.prepareStatement(Query);
            updateStatement.setDouble(1, cantidad);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
            
            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertHistorico (int id, String tipo, double cantidad){
        Connection con = conectarBDD();
        try{
            String insertHistoricoQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
            con.setAutoCommit(false);
            PreparedStatement historicoStatement = con.prepareStatement(insertHistoricoQuery);
            historicoStatement.setInt(1, id);
            historicoStatement.setString(2, tipo);
            historicoStatement.setDouble(3, cantidad);
            historicoStatement.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setPin(int id, int pin){
        Connection con = conectarBDD();
        try {
            con.setAutoCommit(false);

            String Query = "UPDATE usuarios SET pin = ? WHERE id = ?";
            PreparedStatement updateStatement = con.prepareStatement(Query);
            updateStatement.setInt(1, pin);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
