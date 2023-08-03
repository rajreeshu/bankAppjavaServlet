package bankingApp;

import java.io.IOException;
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

@WebServlet("/checkBalance")
public class CheckBalance extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		int accNo = (int) session.getAttribute("accno");
		String attribute2 = (String) session.getAttribute("cust_name");
		
		String url= "jdbc:mysql://localhost:3306/bankingApp";
		String user = "root";
		String pwd = "Dhoni$1234";
		try {
			
			  Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, pwd);

	            

	            pstmt = con.prepareStatement("select * from bankApp where acc_no = ?");
	            pstmt.setInt(1, accNo);
	            
	            
	            ResultSet result = pstmt.executeQuery();

	            if(result.next()) {
//	            	session.setAttribute("accno", result.getInt("acc_no"));
//	            	session.setAttribute("cust_name", result.getString("customer_name"));
	            	session.setAttribute("balance", result.getInt("balance"));
	                res.sendRedirect("/bankingApp/checkBalance.jsp");

	            }

	            else {

	                res.sendRedirect("/bankingApp/balanceFail.html");

	            }
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			writer.write();
			
		}
	}
}
