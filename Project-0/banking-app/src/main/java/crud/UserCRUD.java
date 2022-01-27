package crud;

import objectmodels.UserModel;

import java.sql.*;

public class UserCRUD{
    private final Connection connection;


    public UserCRUD(){
        connection = ConnectionManager.getConnection();
    }


    public int createNewUser(UserModel model) {
        String sql = "INSERT INTO users (f_name,l_name,phone,email,username,password) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, model.getFirstName());
            pStatement.setString(2, model.getLastName());
            pStatement.setString(3, model.getPhoneNumber());
            pStatement.setString(4, model.getEmail());
            pStatement.setString(5, model.getUsername());
            pStatement.setString(6, model.getPassword());
            pStatement.executeUpdate();

            ResultSet rs = pStatement.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        ResultSet result;
        try {
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, username);
            pStatement.setString(2, password);
            result = pStatement.executeQuery();

            if (result.next()){
                UserModel user = UserModel.getUserModel();
                user.setId(result.getInt("user_id"));
                user.setFirstName(result.getString("f_name"));
                user.setLastName(result.getString("l_name"));
                user.setPhoneNumber(result.getString("phone"));
                user.setEmail(result.getString("email"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("\n\nError in trying to log you in. (our fault)");
            return false;
        }

        System.out.println("Wrong credentials and/or you may need to register.");
        return false;
    }


    public UserModel read(Integer id) {
        return null;
    }


    public UserModel update(UserModel model) {
        return null;
    }


    public void delete(Integer id) {

    }
}
