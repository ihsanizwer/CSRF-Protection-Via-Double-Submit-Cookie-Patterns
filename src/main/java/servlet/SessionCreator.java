package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;

import data.SessionVector;


public class SessionCreator extends HttpServlet {
    private byte bytearray [] = new byte[20];
    private SecureRandom random = new SecureRandom();
    private SessionVector stcm = SessionVector.getInstance();


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer sess = new StringBuffer("");
        StringBuffer csrfToken = new StringBuffer("");
        boolean isLogged = false;
        boolean isCookieSet = false;

        HttpSession session = request.getSession(false);

        Cookie [] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("DBLSesID")){
                if(stcm.isLoggedIn(cookie.getValue())){
                    isLogged = true;
                }
            }else if(cookie.getName().equals("CSRFDubSub")){

            }
        }

        if(isLogged==false) {
            //Will be authenticating user and Making a new session since We don't have a session at this moment
            String username = request.getParameter("user");
            String password = request.getParameter("pass");

            if (username.equals("Adam") && password.equals("helloCSRF")) {
                random.nextBytes(bytearray);
                for (int i = 0; i < 20; i++)
                  sess.append(bytearray[i]);
                //sess = new String(bytearray);


                random.nextBytes(bytearray);
                for (int i = 0; i < 20; i++)
                    csrfToken.append(bytearray[i]);


                stcm.setSession(sess.toString());
                //creating a new session if not available
                session = request.getSession();
                session.setAttribute("SomeBankSes_ID", sess);
                Cookie ses = new Cookie("DBLSesID", sess.toString());
                Cookie csrf = new Cookie("CSRFDubSub",csrfToken.toString());
                //Setting cookie expiry
                ses.setMaxAge(30 * 60);
                csrf.setMaxAge(30 * 60);
                response.addCookie(ses);
                response.addCookie(csrf);
                //User authentication complete, redirecting user to the homepage
                response.sendRedirect("home.jsp");
            } else {
                //wrong username and password combo
                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.append("!DOCTYPE html\r\n")
                        .append("<html>\r\n")
                        .append("<body>\r\n")
                        .append("<h1>Error: Invalid credentials! Please <a href='index.jsp'> try again!</a>\r\n")
                        .append("</body>\r\n")
                        .append("</html>\r\n");
            }

        }
        /*else if(isLogged==true){

                response.sendRedirect("home.jsp");

                response.setContentType("text/html");
                response.setCharacterEncoding("UTF-8");

                PrintWriter writer = response.getWriter();
                writer.append("!DOCTYPE html\r\n")
                        .append("<html>\r\n")
                        .append("<body>\r\n")
                        .append("<h1>Error: Please delete your browser cookies and <a href='index.jsp'> try again!</a>\r\n")
                        .append("</body>\r\n")
                        .append("</html>\r\n");


        }*/


    }
    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie cookies [] = req.getCookies();
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("STPSesID")){
                if(stcm.isLoggedIn(cookie.getValue())){
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("UTF-8");
                    resp.getWriter().write("home.jsp");
                }
            }
        }
    }*/
}
