package servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import data.SessionToCSRFMap;

public class SessionDestroyer extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionToCSRFMap ctcm = SessionToCSRFMap.getInstance();
        HttpSession session = request.getSession(false);
        Cookie cookies [] = request.getCookies();
        boolean logoutSuccess = false;

        if(session!=null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("STPSesID")){
                    if(ctcm.isLoggedIn(cookie.getValue())){
                        try{
                            if(!(ctcm.destroySession(cookie.getValue()))){
                                logoutSuccess = false;
                            }
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                            session.invalidate();
                            logoutSuccess = true;
                            response.sendRedirect("index.jsp");
                        }catch (Exception e){
                            logoutSuccess = false;
                        }
                    }
                }
            }
           if(!logoutSuccess){
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.append("<!DOCTYPE html>")
                        .append("<html>")
                        .append("<body>")
                        .append("<h1>LogOut failed! Contact developer!</h1>")
                        .append("</body>")
                        .append("</html>");
           }
        }



    }


}
