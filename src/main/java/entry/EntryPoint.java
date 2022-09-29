package entry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import email.EmailHandler;
import sql.SqlHandler;

import java.util.Map;

public class EntryPoint implements RequestHandler<Map<String,Object>, String> {

    EmailHandler emailHandler = new EmailHandler();
    SqlHandler sqlHandler = new SqlHandler();

    @Override
    public String handleRequest(Map<String, Object> stringStringMap, Context context) {

        String response = "200 OK";
        emailHandler.setMessage(sqlHandler.pullFromTable());
        emailHandler.generateMessage();
        return response;
    }
}

