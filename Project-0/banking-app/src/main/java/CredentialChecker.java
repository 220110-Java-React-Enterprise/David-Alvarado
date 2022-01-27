import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CredentialChecker {
    private static CredentialChecker credentialChecker;
    private final Connection connection = ConnectionManager.getConnection();


    CredentialChecker(){

    }

    public static CredentialChecker getCredentialChecker(){
        if (credentialChecker == null ){
            credentialChecker = new CredentialChecker();
        }
        return  credentialChecker;
    }

    public boolean isNameValid(String name){
        if (name.length() == 0 || name.length() > 100){
            System.out.println("\n You left the \"name\" portion of the registration empty or name is too long. Try again.");
            return false;
        }else {
            String regExp = "[^0-9$&+,:;=\\\\?@#|/<>.^*()%!]+";

            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(name);
            boolean result = matcher.matches();
            if (result){
                System.out.println("\n Invalid characters detected, make sure your name consists of alphabetical characters(a-z,A-z),");
                System.out.println("dashes(-), and apostrophe(')");
                return false;
            }

        }
        return true;
    }

    public boolean isPhoneValid(String phone){
        if (phone.length() != 10){
            System.out.println("\n You're phone number has more than 10 digits or is empty. Please try again.");
            return false;
        }else {
            String regExp = "[A-a-zz^$&+,:;=\\?@#|/'<>.*()%!-]+";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(phone);
            boolean result = matcher.matches();
            if (result) {
                System.out.println("\n Invalid characters used. A phone number should only consist of whole numbers.");
                return false;
            } else if (isThereDuplicate(phone, "phone")){
                return false;
            }

        }
        return true;
    }

    public boolean isEmailValid(String email) {
        if (email.length() == 0 || email.length() > 200) {
            System.out.println("\n You left the \"email\" portion of the registration empty or email is too long. Try again.");
            return false;
        } else {
            String regExp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                return false;
            } else if (isThereDuplicate(email, "email")){
                return false;
            }
        }
        return true;
    }
    public boolean isUsernameValid(String username){
        if (username.length() == 0 || username.length() > 100){
            System.out.println("\n You left the \"username\" portion of the registration empty or email too long. Try again.");
            return false;
        }
            else{
                if (isThereDuplicate(username, "username")){
                    return false;
                }
            }
            return true;
    }

    public boolean isThereDuplicate(String str, String col){

        String sql = null;
        switch (col) {
            case "phone":
                sql = "SELECT * from users WHERE phone = ?";
                break;
            case "email":
                sql = "SELECT * from users WHERE email = ?";
                break;
            case "username":
                sql = "SELECT * from users WHERE username = ?";
                break;
            default: sql = "null";

        }
        if (sql.equals("null")){
            System.out.println("SQL command error.");
            return true;
        }
            try {
                PreparedStatement pStatement = connection.prepareStatement(sql);
                pStatement.setString(1,str);
                ResultSet result = pStatement.executeQuery();
                if (!result.wasNull()){
                    System.out.println("\n Duplicate in our system detected. Duplicate in " + col + " column." );
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

}
