package crud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Class used to check if the user input is of correct format when registering.
// (singleton, can be accessed from anywhere with having to create multiple instances of itself).
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
        // Method checks the name attribute of a user to prevent special characters
        // and numbers from being accepted using regular expression (regex)
        // INPUT: name
        // OUTPUT: a boolean value based on if there is a match in the forbidden pattern.
        // RETURN: TRUE if valid, FALSE otherwise.
        if (name.length() == 0 || name.length() > 100){
            System.out.println("\n You left the \"name\" portion of the registration empty or name is too long. Try again.");
            return false;
        }else {
            String regExp = "[^0-9$&+,:;=\\\\?@#|/<>.^*()%!]+";

            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(name);
            boolean result = matcher.matches();
            if (!result){
                System.out.println("\n Invalid characters detected, make sure your name consists of alphabetical characters(a-z,A-z),");
                System.out.println("dashes(-), and apostrophe(')");
                return false;
            }

        }
        return true;
    }

    public boolean isPhoneValid(String phone){
        // Method checks the phone attribute of a user to prevent special
        // and alphabetical characters from being accepted using regular expression (regex)
        // Duplicate check is done against the database as well. (Fails if duplicate)
        // INPUT: phone
        // OUTPUT: a boolean value based on if there is a match in the forbidden pattern/duplicate.
        // RETURN: TRUE if valid, FALSE otherwise.

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
        // Method checks the email attribute of a user to prevent incorrect email format
        // with the use of regular expression (regex).
        // Duplicate check is done against the database as well. (Fails if duplicate)
        // INPUT: email
        // OUTPUT: a boolean value based on if there is a match in the accepted pattern/ duplicate.
        // RETURN: TRUE if valid, FALSE otherwise.

        if (email.length() == 0 || email.length() > 200) {
            System.out.println("\n You left the \"email\" portion of the registration empty or email is too long. Try again.");
            return false;
        } else {
            String regExp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches() && !isThereDuplicate(email, "email"))
            { if (!matcher.matches()) {
                System.out.println("\n Wrong email format");
            }else{
                System.out.println("Duplicate email entered, \nare you sure you haven't created an account? \ntry logging in.");
            }
                return false;
            }

            return true;
        }

    }

    public boolean isUsernameValid(String username){
        // Method checks the username attribute of the user to prevent duplicates.
        // INPUT: username
        // OUTPUT: a boolean value based on if username name is in use.
        // RETURN: TRUE if valid, FALSE otherwise.
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
        // Method checks for duplicates for user attributes: phone,email,username
        // INPUT: user input string value, name of column to check against.
        // OUTPUT: a boolean value based on if there is a duplicate.
        // RETURN: TRUE if duplicate found, FALSE otherwise.
        String sql;
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
                pStatement.executeQuery();
                ResultSet result = pStatement.getResultSet();
                if (result.wasNull()){
                    System.out.println("\n Duplicate in our system detected. Duplicate in " + col + " column." );
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;
    }

}
