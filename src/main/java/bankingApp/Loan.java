package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loan")
public class Loan extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int loanId = Integer.parseInt((String)req.getParameter("loan-type"));
		String url= "jdbc:mysql://localhost:3306/bankingApp";
		String user = "root";
		String pwd = "Dhoni$1234";
		
		HttpSession session = req.getSession();
		
		try {
			
			  Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, pwd);

	            

	            pstmt = con.prepareStatement("select * from loanDetail where loan_id = ?");
	            pstmt.setInt(1, loanId);
	           	            
	            ResultSet result = pstmt.executeQuery();

	            if(result.next()) {
	            	session.setAttribute("loan_type", result.getString("loan_type"));
	            	session.setAttribute("loan_tenure", result.getInt("tenure"));
	            	session.setAttribute("loan_interest", result.getInt("interest"));
	            	session.setAttribute("loan_description", result.getString("description"));
	            	
	                res.sendRedirect("/bankingApp/loanDetail.jsp");
	            }

	            else {
	                res.sendRedirect("/bankingApp/loanFailed.html");
	            }
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			writer.write();
			
		}
	}
}
