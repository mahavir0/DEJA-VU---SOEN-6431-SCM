/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import connection.*;
import java.security.MessageDigest;
import sun.misc.BASE64Encoder;
/**
 *
 * @author ICT
 */
public class facultypasschange extends HttpServlet {

    HttpSession session;
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String current = request.getParameter("passwd");
        String change = request.getParameter("apasswd");
        String userid = session.getAttribute("facultyid").toString();
        ResultSet rs;
        int a=0;
        try
        {
              MessageDigest MD5=MessageDigest.getInstance("MD5");
            MD5.update(current.getBytes(),0,current.getBytes().length);
            byte[] hashvalue=MD5.digest();
            String newcurrent=new BASE64Encoder().encode(hashvalue);
            MD5.update(change.getBytes(),0,change.getBytes().length);
            byte[] hashvaluea=MD5.digest();
            String newchange=new BASE64Encoder().encode(hashvaluea);
            
            Config c = new Config();
            Connection con = c.getcon();
            Statement st = con.createStatement();
            String str = "select count(*) as colname from faculty where facultyID = '"+userid+"' and passwd='"+newcurrent+"'";
            rs = st.executeQuery(str);
            if(rs.next())
            {
                a = Integer.parseInt(rs.getString("colname"));
            }

            if(a==1)
            {
                String query="UPDATE faculty SET passwd='"+newchange+"' WHERE passwd='"+newcurrent+"' AND facultyID='"+userid+"'";
                st.executeUpdate(query);
                con.close();
                response.sendRedirect("facultyprofile.jsp?Success=True");
            }
            else
            {
                con.close();
                response.sendRedirect("facultyprofile.jsp?Failed=True");
            }
            con.close();
        }
        catch(Exception e)
        {
         
           out.println("Error="+e);
        }

  
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
