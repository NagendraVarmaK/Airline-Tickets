package airline;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationServlet
 */
@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ReservationServlet() {
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
			String s2 = request.getParameter("age");
			String s3 = request.getParameter("gender");
			String s4 = request.getParameter("phone");
			String s5 = request.getParameter("email");
			String s6 = request.getParameter("traveldate");
			String s7 = request.getParameter("airline");
			String s8 = request.getParameter("from");
			String s9 = request.getParameter("to");
			/* it is used if date format is not in (dd-mm-yyyy).
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
			java.util.Date d =sdf.parse(s4);
			long ms = d.getTime();
			java.sql.Date td = new java.sql.Date(ms);  */
			java.sql.Date td = java.sql.Date.valueOf(s6);
	
			PreparedStatement pstmt = con.prepareStatement("select flight_id,price from flights where airline=? and source=? and destination=?");
			pstmt.setString(1, s7);
			pstmt.setString(2, s8);
			pstmt.setString(3, s9);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rm = rs.getMetaData();
			int n = rm.getColumnCount();
			int j=0;
			String a[][] = new String[10][n];			
			while(rs.next()) {
				for(int i=1;i<=n;i++) {
					a[j][i-1] = rs.getString(i);
				}
				++j;
			}
			PrintWriter pw = response.getWriter();
			pw.println("<html> <head> <style>");
			pw.println("body{background-image: url('./images/r_background.jpg'); background-repeat:no-repeat; background-attachment: fixed; background-size: 100% 100%; background-position: center;}");
			pw.println("</style> </head> <body>");
			pw.println("<h2 style='text-align: center; margin-top: 5%; margin-bottom: 3%;'>Ticket Booked Successfully</h2>");
			j--;
			for(;j>=0;j--) {
				int i=0;
				PreparedStatement pstmt1 = con.prepareStatement("insert into passengers values(?,?,?,?,?,?,?,?,?,?,?)");
				pstmt1.setString(1, s1);
				pstmt1.setString(2, s2);
				pstmt1.setString(3, s3);
				pstmt1.setString(4, s4);
				pstmt1.setString(5, s5);
				pstmt1.setDate(6, td);
				pstmt1.setString(7, s7);
				pstmt1.setString(8, s8);
				pstmt1.setString(9, s9);
				pstmt1.setString(10, a[j][i]);
				pstmt1.setString(11, a[j][i+1]);
				pstmt1.executeUpdate();
				
				pw.println("<div style='margin:auto; width:50%; border:2px solid black; border-radius: 20px; background-color: pink;'>");
				pw.println("<h4 style='margin: auto; text-align: center; padding: 1%;'>BOARDING PASS</h4><hr>");
				pw.println("<div style='display:flex; justify-content:space-around;'>");
				pw.println("<h5 style='transform: rotateZ(270deg); width: 10%; margin:auto; margin-top:8%; margin-bottom: 5%;'>FLIGHT_TICKET</h5>");
				pw.println("<div style='border-left: 2px dotted blue;'></div>");
				pw.println("<div style='margin:auto; width: 45%; margin-left:3%;'>");
				pw.println("<div style='display:flex; gap:20%;'>");
				pw.println("<div> <b>FLIGHT_ID </b><br> "+a[j][i]+" </div>");
				pw.println("<div> <b>TRAVEL DATE </b><br> "+td+" </div>");
				pw.println("</div> <br>");
				pw.println("<div style='display:flex; gap:15%;'> <table>");
				pw.println("<tr><td><b>FROM : </b></td><td> "+s8+" </td></tr>");
				pw.println("<tr><td><b>TO : </b></td><td> "+s9+" </td></tr>");
				pw.println("</table>");
				pw.println("<div> <b>PRICE </b><br> "+a[j][i+1]+" </div> </div>");
				pw.println("</div> <div style='border-left: 2px dotted blue;'></div>");
				pw.println("<div style='margin:auto; width: 45%; margin-left: 3%;'> <table>");
				pw.println("<tr><td><b>Passenger Name </b></td><td>: "+s1+" </td></tr>");
				pw.println("<tr><td><b>FROM </b></td><td>: "+s8+" </td></tr>");
				pw.println("<tr><td><b>TO </b></td><td>: "+s9+" </td></tr>");
				pw.println("<tr><td><b>TRAVEL DATE </b></td><td>: "+td+" </td></tr>");
				pw.println("</table> </div> </div> </div>");
				i++;
			}
			pw.println("<div style='margin: auto;text-align:center; margin-top: 3%;'>");
			pw.println("<a href='index.html' style='border:2px solid black; border-radius:10px; padding:1%; text-decoration:none; color:black; background-color:grey;'><b> HOME </b></a>");
			pw.println("</div></body></html>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
