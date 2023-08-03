package bankingApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import databaseInfo.DatabaseInfo;

@WebServlet("/transfer")
public class Transfer extends HttpServlet{
	private Connection con;
	private PreparedStatement pstmt;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		DatabaseInfo db =new DatabaseInfo();
		con=db.con;
		
		HttpSession session = req.getSession();
		
		try {
			Random rand = new Random();
			int senderAccNo = (int) session.getAttribute("accno");
	        String receiverIfsc = req.getParameter("receiver_ifsc");
	        int receiverAccNo = Integer.parseInt(req.getParameter("receiver_acc_no"));
	        int amount = Integer.parseInt(req.getParameter("amount"));
	        int senderPin = Integer.parseInt(req.getParameter("pin"));
			int transId = rand.nextInt(100000);
			
			
	       
			pstmt = con.prepareStatement("select * from bankApp where acc_no=? and pin = ?");
	        pstmt.setInt(1, senderAccNo);
	        pstmt.setInt(2, senderPin);
	        
	        ResultSet senderInfo = pstmt.executeQuery();
	        //first
	        if(senderInfo.next()) {
	        	int senderId = senderInfo.getInt("cust_id");
	        	String senderBankName = senderInfo.getString("bank_name");
	        	String senderIfsc = senderInfo.getString("ifsc_code");
	        			
	        	pstmt = con.prepareStatement("select * from bankApp where ifsc_code=? and acc_no=?");
	        	pstmt.setString(1, receiverIfsc);
	        	pstmt.setInt(2, receiverAccNo);
	        	
	        	ResultSet receiverInfo = pstmt.executeQuery();
	        	//second
	        	if(receiverInfo.next()) {
	        		//third
	        		if(senderInfo.getInt("balance")>amount) {
	        			pstmt = con.prepareStatement("update bankApp set balance = balance - ? where acc_no = ?");
	        			pstmt.setInt(1, amount);
	        			pstmt.setInt(2, senderAccNo);
	        			int updateSenderBalance=pstmt.executeUpdate();
	        			//fourth
	        			if(updateSenderBalance>0) {
	        				pstmt = con.prepareStatement("update bankApp set balance = balance + ? where acc_no =?");
	        				pstmt.setInt(1, amount);
	        				pstmt.setInt(2, receiverAccNo);
	        				int updateReceiverBalance = pstmt.executeUpdate();
	        				//fifth
	        				if(updateReceiverBalance>0) {
	        					pstmt = con.prepareStatement("insert into transferStatus values (?,?,?,?,?,?,?,?)");
	        					pstmt.setInt(1,senderId);
	        					pstmt.setString(2,senderBankName);
	        					pstmt.setString(3,senderIfsc);
	        					pstmt.setInt(4,senderAccNo);
	        					pstmt.setString(5,receiverIfsc);
	        					pstmt.setInt(6,receiverAccNo);
	        					pstmt.setInt(7, amount);
	        					pstmt.setInt(8, transId);
	        					
	        					int updateTransferTable = pstmt.executeUpdate();
	        					
	        					//sixth
	        					if(updateTransferTable>0) {
	        						session.setAttribute("transactionId", transId);
	        						res.sendRedirect("/bankingApp/successTransfer.jsp");
	        					}else {
	        						session.setAttribute("transferFailMsg", "Updation of data Failed!");
	        						res.sendRedirect("/bankingApp/failTransfer.jsp");
	        					}
	        					
	        				}else {
        						session.setAttribute("transferFailMsg", "Money can not be added to receiver account!");
        						res.sendRedirect("/bankingApp/failTransfer.jsp");
        					}
	        			}else {
    						session.setAttribute("transferFailMsg", "Money can't be deducted from Sender's Account!");
    						res.sendRedirect("/bankingApp/failTransfer.jsp");
    					}
	        		}else {
						session.setAttribute("transferFailMsg", "Insufficient Balance!");
						res.sendRedirect("/bankingApp/failTransfer.jsp");
					}
	        	}else {
					session.setAttribute("transferFailMsg", "Unknown Receiver!");
					res.sendRedirect("/bankingApp/failTransfer.jsp");
				}
	        	
	        }else {
				session.setAttribute("transferFailMsg", "Sender Verification failed!");
				res.sendRedirect("/bankingApp/failTransfer.jsp");
			}
	        
	        
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}	
