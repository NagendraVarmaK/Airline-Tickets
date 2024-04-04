package airline;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CancelServlet
 */
@WebServlet("/cancel")
public class CancelServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CancelServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","ar","KNV");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String s1 = request.getParameter("name");
			String s2 = request.getParameter("phone");
			String s3 = request.getParameter("email");
			String s4 = request.getParameter("from");
			String s5 = request.getParameter("to");
			PreparedStatement pstmt4 = con.prepareStatement("delete from passengers where NAME=? and PHONENUMBER=? and EMAIL=? and SOURCE=? and DESTINATION=?");
			pstmt4.setString(1, s1);
			pstmt4.setString(2, s2);
			pstmt4.setString(3, s3);
			pstmt4.setString(4, s4);
			pstmt4.setString(5, s5);
			pstmt4.executeUpdate();
			PrintWriter pw = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("cancel.html");
			rd.include(request, response);
			pw.println("<html><body><div style='margin:auto; margin-top:2%; margin-left:50%; width:40%; background-color: yellow; border:2px solid black; border-radius:10px;'>");
			pw.println("<h2 style='margin:auto; text-align:center; margin:2%;'>Your Ticket has been cancelled Successfully</h2>");
			pw.println("</div><div style='margin-left:67%; margin-top:2.5%;'>");
			pw.println("<a href='index.html' style='border:2px solid black; border-radius:10px; padding:3%; text-decoration:none; color:black; background-color:grey;'><b> HOME </b></a>");
			pw.println("<div></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
