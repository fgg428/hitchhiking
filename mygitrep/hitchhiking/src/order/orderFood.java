package order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;

/**
 * Servlet implementation class orderFood
 */
@WebServlet("/orderFood")
public class orderFood extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String result="success";
	Date d = new Date();  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    String dateNowStr = sdf.format(d); 
    
    public orderFood() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID=request.getParameter("userID");
		int id=Integer.parseInt(userID);
		
		String userName=request.getParameter("userName");
		userName = new String(userName.getBytes("ISO8859-1"), "UTF-8"); 
		
		String phone=request.getParameter("phone");
		phone=new String(phone.getBytes("ISO8859-1"), "UTF-8");
		
		String location=request.getParameter("location");
		location=new String(location.getBytes("ISO8859-1"), "UTF-8");
		
		String state=request.getParameter("state");
		state=new String(state.getBytes("ISO8859-1"), "UTF-8");
		
		String money=request.getParameter("money");
		int rmb=Integer.parseInt(money);
		
		String locationType=request.getParameter("type");
		locationType=new String(locationType.getBytes("ISO8859-1"), "UTF-8");
		
		String foodType=request.getParameter("food_type");
		foodType=new String(foodType.getBytes("ISO8859-1"), "UTF-8");
		
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="insert into tblFood(foodType,foodLocationType,foodDate,buyerName,buyerPhone,buyerLocation,statement,pay,tasked,userID) values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, foodType);
			ps.setString(2, locationType);
			ps.setString(3, null);
			ps.setString(4, userName);
			ps.setString(5, phone);
			ps.setString(6,location);
			ps.setString(7, state);
			ps.setInt(8, rmb);
			ps.setInt(9, 0);
			ps.setInt(10, id);
            ps.executeUpdate();
            ps.close();  
            insertBill(userID,rmb);
            updateUserInfor(id,rmb);
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void insertBill(String userID,int money) {
		Connection conn1=mdb.getconntion();//连接到数据库 	
		String type="饭堂带饭";
		try {
			if(conn1.isClosed()) {
				conn1=mdb.getconntion();//连接到数据库 
			}	
			int id=Integer.parseInt(userID);
			String sql="insert into tblBill(number,billType,billDate,type,userID) values(?,?,?,?,?)";
			 PreparedStatement pstmt=conn1.prepareStatement(sql);
			    pstmt.setInt(1, money);//设置sql中的第1个？参数位置的值
			    pstmt.setString(2, type);//设置sql中的第2个？参数位置的值
			    pstmt.setString(3,null);
			    pstmt.setInt(4,1);
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
	private void updateUserInfor(int userID,int money) {
		Connection conn2=mdb.getconntion();//连接到数据库 
		try {
			if(conn2.isClosed()) {
				conn2=mdb.getconntion();//连接到数据库 
			}
			 String sql="update tblUserInfo set money=money-"+money+",experience=experience+"+money+" where userID='"+userID+"'";
			 PreparedStatement pstmt=conn2.prepareStatement(sql);
			 pstmt.executeUpdate();
			 pstmt.close();
		}catch(Exception e) {
			result="fail";
			e.printStackTrace();
		}finally {
			try {
				conn2.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
