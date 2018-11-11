package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Servlet implementation class investMoney
 */
@WebServlet("/investMoney")
public class investMoney extends HttpServlet {
	private static final long serialVersionUID = 1L; 
	private String result="success";
	
    public investMoney() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String money=request.getParameter("money");
		int num=Integer.parseInt(money);
		String userName=request.getParameter("userName");
		String userID=request.getParameter("userID");
		String type=request.getParameter("type");
		
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="update tblUserInfo set money=money+"+num+" where userName='"+userName+"'";
			PreparedStatement ps=conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();  
            insertBill(userID,money,type);         
		}catch(Exception e) {
			result="fail";
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");			
		PrintWriter pw=response.getWriter();
		pw.print(result);
		pw.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	private void insertBill(String userID,String money,String type) {
		Connection conn1=mdb.getconntion();//连接到数据库 	
		try {
			if(conn1.isClosed()) {
				conn1=mdb.getconntion();//连接到数据库 
			}
			type = new String(type.getBytes("ISO8859-1"), "UTF-8"); 
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);
			int id=Integer.parseInt(userID);
			int number=Integer.parseInt(money);
			String sql="insert into tblBill(number,billType,billDate,type,userID) values(?,?,?,?,?)";
			 PreparedStatement pstmt=conn1.prepareStatement(sql);
			    pstmt.setInt(1, number);//设置sql中的第1个？参数位置的值
			    pstmt.setString(2, type+"充值");//设置sql中的第2个？参数位置的值
			    pstmt.setString(3,dateNowStr);
			    pstmt.setInt(4,0);
			    pstmt.setInt(5, id);
			    pstmt.executeUpdate();
			    pstmt.close();
		}catch(Exception e) {
			result="fail";
			e.printStackTrace();
		}finally {
			try {
				conn1.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
