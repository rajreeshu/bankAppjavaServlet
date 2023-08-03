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

import com.mysql.cj.Session;

@WebServlet("/changePin")
public class ChangePin extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		int prevPin = Integer.parseInt(req.getParameter("prevPin"));
		int newPin = Integer.parseInt(req.getParameter("newPin"));
		int cnfrmPin = Integer.parseInt(req.getParameter("cnfrmPin"));
		
		HttpSession session = req.getSession();
		
		
		String url= "jdbc:mysql://localhost:3306/bankingApp";
		String user = "root";
		String pwd = "Dhoni$1234";
		try {
			
			  Class.forName("com.mysql.cj.jdbc.Driver");

	            con = DriverManager.getConnection(url, user, pwd);

	            if(newPin==cnfrmPin) {
	            	pstmt = con.prepareStatement("update bankApp set pin = ? where acc_no = ? and pin = ?");
	 	            pstmt.setInt(1, newPin);
	 	            
	 	            pstmt.setInt(2, (int)session.getAttribute("accno"));
	 	           pstmt.setInt(3, prevPin);
	 	            int x = pstmt.executeUpdate();
	 	            if(x>0)
	 	            	res.sendRedirect("/bankingApp/changePasswordSuccess.jsp");
	 	            else
	 	            	res.sendRedirect("/bankingApp/changePasswordFail.jsp");
	    		}else {
	    			res.sendRedirect("/bankingApp/changePasswordFail.jsp");
	    		}

	           
	            

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			writer.write();
			
		}
	}
}
