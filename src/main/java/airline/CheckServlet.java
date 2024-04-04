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
 * Servlet implementation class CheckServlet
 */
@WebServlet("/check")
public class CheckServlet extends HttpServlet {
	Connection con;
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CheckServlet() {
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
			PreparedStatement pstmt2 = con.prepareStatement("select * from passengers where NAME=? and PHONENUMBER=? and EMAIL=?");
			pstmt2.setString(1, s1);
			pstmt2.setString(2, s2);
			pstmt2.setString(3, s3);
			ResultSet rs2 = pstmt2.executeQuery();
			ResultSetMetaData rm2 = rs2.getMetaData();
			int n2 = rm2.getColumnCount();
			int i=0;
			String b[][] = new String[10][n2];
			while(rs2.next()) {
				for(int j=1;j<=n2;j++) {
					b[i][j-1]=rs2.getString(j);
				}
				++i;
			}
			PrintWriter pw = response.getWriter();
			pw.println("<html> <head> <style>");
			pw.println("body{background-image: url('./images/s_background.jpg'); background-repeat:no-repeat; background-attachment: fixed; background-size: 100% 100%; background-position: center;}");
			pw.println("</style> </head> <body>");
			pw.println("<h2 style='text-align: center; margin-top: 5%; margin-bottom: 3%;'>Your Tickets</h2>");
			i--;
			for(;i>=0;i--) {
				int j=0;	
				pw.println("<div style='margin:auto; width:50%; border:2px solid black; border-radius: 20px; background-color: pink;'>");
				pw.println("<h4 style='margin: auto; text-align: center; padding: 1%;'>BOARDING PASS</h4><hr>");
				pw.println("<div style='display:flex; justify-content:space-around;'>");
				pw.println("<h5 style='transform: rotateZ(270deg); width: 10%; margin:auto; margin-top:8%; margin-bottom: 5%;'>FLIGHT_TICKET</h5>");
				pw.println("<div style='border-left: 2px dotted blue;'></div>");
				pw.println("<div style='margin:auto; width: 45%; margin-left:3%;'>");
				pw.println("<div style='display:flex; gap:20%;'>");
				pw.println("<div> <b>FLIGHT </b><br> "+b[i][j+9]+" </div>");
				pw.println("<div> <b>TRAVEL DATE </b><br> "+b[i][j+5]+" </div>");
				pw.println("</div> <br>");
				pw.println("<div style='display:flex; gap:15%;'> <table>");
				pw.println("<tr><td><b>FROM : </b></td><td> "+b[i][j+7]+" </td></tr>");
				pw.println("<tr><td><b>TO : </b></td><td> "+b[i][j+8]+" </td></tr>");
				pw.println("</table>");
				pw.println("<div> <b>PRICE </b><br> "+b[i][j+10]+" </div> </div>");
				pw.println("</div> <div style='border-left: 2px dotted blue;'></div>");
				pw.println("<div style='margin:auto; width: 45%; margin-left: 3%;'> <table>");
				pw.println("<tr><td><b>Passenger Name </b></td><td>: "+s1+" </td></tr>");
				pw.println("<tr><td><b>FROM </b></td><td>: "+b[i][j+7]+" </td></tr>");
				pw.println("<tr><td><b>TO </b></td><td>: "+b[i][j+7]+" </td></tr>");
				pw.println("<tr><td><b>TRAVEL DATE </b></td><td>: "+b[i][j+5]+" </td></tr>");
				pw.println("</table> </div> </div> </div> <br>");
				j++;
			}
			pw.println("<div style='margin: auto; text-align:center; margin-top: 3%;'>");
			pw.println("<a href='index.html' style='border:2px solid black; border-radius:10px; padding:1%; text-decoration:none; color:black; background-color:grey;'><b> HOME </b></a>");
			pw.println("</div></body></html>");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
