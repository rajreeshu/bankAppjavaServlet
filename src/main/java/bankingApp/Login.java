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

import databaseInfo.DatabaseInfo;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private Connection con;

	private PreparedStatement pstmt;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int customer_id = Integer.parseInt(req.getParameter("customer_id"));
		int pin = Integer.parseInt(req.getParameter("pin"));

		HttpSession session = req.getSession();

		try {
			DatabaseInfo db = new DatabaseInfo();
			con = db.con;
			pstmt = con.prepareStatement("select * from bankApp where cust_id = ? and pin = ?");
			pstmt.setInt(1, customer_id);
			pstmt.setInt(2, pin);

			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				session.setAttribute("accno", result.getInt("acc_no"));
				session.setAttribute("cust_name", result.getString("customer_name"));

				res.sendRedirect("/bankingApp/home.jsp");
			}

			else {
				res.sendRedirect("/bankingApp/loginFailed.html");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
