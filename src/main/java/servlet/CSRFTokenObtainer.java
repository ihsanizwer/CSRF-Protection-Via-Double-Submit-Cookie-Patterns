package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;
import java.io.IOException;
import java.io.PrintWriter;

import data.SessionToCSRFMap;


public class CSRFTokenObtainer extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isValid = true;
        Cookie myCookie = null;

        Cookie cookies [] = req.getCookies();
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("STPSesID")){
                myCookie = cookie;
                if(!(SessionToCSRFMap.getInstance().isLoggedIn(cookie.getValue()))){
                    isValid = false;
                }
            }
        }

        if(isValid) {
            try {
                String csrfToken = SessionToCSRFMap.getInstance().getCSRFToken(myCookie.getValue());
                //String csrfTag = "<input type='hidden' id='csrf_token' name='csrf_token' value='" + csrfToken + "'>";
                resp.setContentType("text/html");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(csrfToken);
            }catch (NullPointerException n){}
        }else {
            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            writer.append("<!DOCTYPE html>")
                    .append("<html>")
                    .append("<body>")
                    .append("<h1>An Error has occured! Contact developer!</h1>")
                    .append("</body>")
                    .append("</html>");
        }
    }
}
