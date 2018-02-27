/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dalal
 */
public class DBHandler extends HttpServlet {
private Connection myCon;
private Statement myStmt;
   private static MessageDigest md;

public DBHandler (){    
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

// Create the connection to the db
    try {
      myCon = DriverManager.getConnection ("jdbc:mysql://localhost/users?user=root");
	//When you upload, use this statement instead:
      //myCon = DriverManager.getConnection("cjdbc:mysql://localhost:3306/ex_cars?user=ex_cars&password=9a0zns83ve40as98345hnv?autoReconnect=true"); //hostgator
    }
    catch (SQLException e) {
      e.printStackTrace();
    }

// Create the statement for SQL queries
    try {
      myStmt = myCon.createStatement();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

}


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DBHandler</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DBHandler at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
                public boolean authenticate(user u) throws NoSuchAlgorithmException{
                boolean found=false;
                 String email = u.getemail();
                String pass = hashpass(u.getpass());
                           try {
                   ResultSet rs =    myStmt.executeQuery("SELECT * FROM users WHERE email ='"+email+"' AND password='"+pass+"';");
                  if(rs.next()==false){found=false;}
                  else  {found=true;
                  u.setname( rs.getString("name"));}
            } catch (SQLException ex) {
            ex.printStackTrace(); }
                    
                    return found;}
                
public String hashpass (String Password) throws NoSuchAlgorithmException{   
    md = MessageDigest.getInstance("MD5");
        byte[] passBytes = Password.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuffer sb = new StringBuffer();
   for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
       } return sb.toString();}
                
                
 public int addUser(user u) throws NoSuchAlgorithmException {
         int re=0;
            /* TODO output your page here. You may use following sample code. */
         String email = u.getemail();
                String pass = hashpass(u.getpass());
                  String name = u.getname(); 
          //  String query = "INSERT INTO users VALUES(NULL, '"+name+"','"+email+"','"+pass+"');";
                     

                  try {
                      ///////////Depending on your query, if it's an update (INSERT or UPDATE) then use:
              re=    myStmt.executeUpdate("INSERT INTO users VALUES(NULL, '"+name+"','"+email+"','"+pass+"') ;" );
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        }
        return re;}
 
 public boolean doesExist (String email) throws SQLException{
 boolean availble=true;
    ResultSet rs =  myStmt.executeQuery("SELECT * FROM users WHERE email ='"+email+"';");
       while (rs.next()){availble=false;}
        
 return availble;}
 
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
