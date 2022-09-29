import email.EmailHandler;
import sql.SqlHandler;

public class Main {

    public static void main(String[] args) {

        EmailHandler emailHandler = new EmailHandler();
        SqlHandler sqlHandler = new SqlHandler();

        emailHandler.setMessage(sqlHandler.pullFromTable());
        emailHandler.generateMessage();
    }
}
