package bankingApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class Register extends HttpServlet{
	private Connection con;

	private PreparedStatement pstmt;
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int bank_id = Integer.parseInt(req.getParameter("bank_id"));
		String bank_name = req.getParameter("bank_name");
		String ifsc_code = req.getParameter("ifsc_code");

		// Retrieving account_no
		long account_no = Long.parseLong(req.getParameter("account_no"));

		// Retrieving PIN
		int pin = Integer.parseInt(req.getParameter("pin"));

		// Retrieving customer_id
		int customer_id = Integer.parseInt(req.getParameter("customer_id"));

		// Retrieving customer_name
		String customer_name = req.getParameter("customer_name");

		// Retrieving balance
		int balance = Integer.parseInt(req.getParameter("balance"));

		// Retrieving email
		String email = req.getParameter("email");

		// Retrieving phone
		long phone = Long.parseLong(req.getParameter("phone"));
		
		PrintWriter writer = res.getWriter();
		writer.write(bank_id+"-"+bank_name+"-"+ifsc_code+"-"+account_no+"-"+pin+"-"+customer_id+"-"+customer_name+"-"+balance+"-"+email+"-"+phone);
		
		
		String url= "jdbc:mysql://localhost:3306/bankingApp";
		String user = "root";
		String pwd = "Dhoni$1234";
		
		try {
			
			  Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, pwd);

	            

	            pstmt = con.prepareStatement("insert into bankApp values(?,?,?,?,?,?,?,?,?,?)");

	            pstmt.setInt(1, bank_id);

	            pstmt.setString(2, bank_name);

	            pstmt.setString(3, ifsc_code);

	            pstmt.setLong(4, account_no);

	            pstmt.setInt(5, pin);

	            pstmt.setInt(6, customer_id);

	            pstmt.setString(7, customer_name);

	            pstmt.setInt(8, balance);

	            pstmt.setString(9, email);

	            pstmt.setLong(10, phone);

	            writer.write(bank_id+"-"+bank_name+"-"+ifsc_code+"-"+account_no+"-"+pin+"-"+customer_id+"-"+customer_name+"-"+balance+"-"+email+"-"+phone);

	            int x = pstmt.executeUpdate();
	            writer.write(bank_id+"-"+bank_name+"-"+ifsc_code+"-"+account_no+"-"+pin+"-"+customer_id+"-"+customer_name+"-"+balance+"-"+email+"-"+phone);
	            if(x>0) {

	                res.sendRedirect("/bankingApp/RegisterSuccess.html");

	            }

	            else {

	                res.sendRedirect("/bankingApp/RegisterFail.html");

	            }
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			writer.write();
			
		}
	}
}
