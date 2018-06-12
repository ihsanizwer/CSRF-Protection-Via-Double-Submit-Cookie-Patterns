package servlet;

import data.AccountDetails;
import validation.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Transaction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean isValidTransaction = false;
        boolean isloggedIn =false;
        Cookie cookies[] = req.getCookies();
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("DBLSesID")){
                if(Validator.isValidSession(cookie.getValue())){
                    isloggedIn = true;
                }
            }
        }
        for(Cookie cookie:cookies){
            if(isloggedIn){
                if(cookie.getName().equals("CSRFDubSub")){
                    if(cookie.getValue().equals(req.getParameter("csrf_token"))){
                        isValidTransaction = true;
                    }
                }
            }
        }

        if(isValidTransaction){
            Double amount =  Double.parseDouble(req.getParameter("amount"));
            Double newBalance = AccountDetails.getBalance() - amount;
            if(newBalance < 0.00){
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType("text/html");
                PrintWriter writer = resp.getWriter();
                writer.append("<!DOCTYPE html>")
                        .append("<html>")
                        .append("<body>")
                        .append("<h1>You do not have enough balance to perform this transaction!</h1>")
                        .append("</body>")
                        .append("</html>");
            }else{
                AccountDetails.setBalance(newBalance);
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                writer.append("<!DOCTYPE html>")
                        .append("<html>")
                        .append("<head><meta http-equiv='Refresh' content='5;url=home.jsp'></head>")
                        .append("<body>")
                        .append("<h1>Transaction complete! new balance is"+AccountDetails.getBalance()+"</h1>")
                        .append("<p>Please click <a href='home.jsp'>here</a> if you are not redirected to the home page.</p>")
                        .append("</body>")
                        .append("</html>");
            }
            //Add transfer amount code
            /*resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<body>")
                    .append("<h1>Transfer successful. The following amount of credits has been transferred : "+amount+"</h1>")
                    .append("</body>")
                    .append("</html>");*/
        }else{
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<body>")
                    .append("<h1>An Error has occured! Contact vendor!</h1>")
                    .append("</body>")
                    .append("</html>");
        }
    }
}
