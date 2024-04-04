package airline;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
 * Servlet implementation class TicketServlet
 */
@WebServlet("/ticket")
public class TicketServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TicketServlet() {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt3 = con.prepareStatement("select NAME,PHONENUMBER,EMAIL,SOURCE,DESTINATION,TRAVELDATE from passengers");
			ResultSet rs3 = pstmt3.executeQuery();
			ResultSetMetaData rm3 = rs3.getMetaData();
			int n3 = rm3.getColumnCount();
			PrintWriter pw = response.getWriter();
			RequestDispatcher rd = request.getRequestDispatcher("cancel.html");
			rd.include(request, response);
			pw.println("<html> <body>");
			pw.println("<table style=' background-color:skyblue; opacity:0.6; border:1px solid black; border-collapse:collapse; margin-top:1%; margin-left:46%;'>");
			/*pw.println("<tr>");
			for(int i=1;i<=n;i++) {
				pw.println("<th style='border: 1px solid black;'>"+rm.getColumnName(i)+"<th>");
			}
			pw.println("</tr>");*/
			pw.println("<tr>");
			pw.println("<th style='border: 1px solid black;'>NAME</th>"
					+ "<th style='border: 1px solid black;'>PHONENUMBER</th>"
					+ "<th style='border: 1px solid black;'>EMAIL</th>"
					+ "<th style='border: 1px solid black;'>SOURCE</th>"
					+ "<th style='border: 1px solid black;'>DESTINATION</th>"
					+ "<th style='border: 1px solid black;'>TRAVELDATE</th>");
			pw.println("</tr>");
			while(rs3.next()) {
				pw.println("<tr>");
				for(int i=1;i<=n3;i++) {
					pw.print("<td style='border: 1px solid black;'>"+rs3.getString(i)+"</td>");
				}
				pw.println("</tr>");
			}
			pw.println("</table>");
			pw.println("<div style='margin: auto; text-align:center; margin-top: 3%; margin-left:40%;'>");
			pw.println("<a href='index.html' style='border:2px solid black; border-radius:10px; padding:2%; text-decoration:none; color:black; background-color:grey;'><b> HOME </b></a>");
			pw.println("</div></body></html>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
