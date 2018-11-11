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
 * Servlet implementation class orderBuy
 */
@WebServlet("/orderBuy")
public class orderBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String result="success";
    public orderBuy() {
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
		
		String goodsType=request.getParameter("type");
		goodsType=new String(goodsType.getBytes("ISO8859-1"), "UTF-8");
		
		String date=request.getParameter("date");
		date=new String(date.getBytes("ISO8859-1"), "UTF-8");
		
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="insert into tblbuy(buyType,buyDate,buyerName,buyerPhone,buyerLocation,statement,pay,tasked,userID) values(?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, goodsType);
			ps.setString(2, date);
			ps.setString(3, userName);
			ps.setString(4, phone);
			ps.setString(5,location);
			ps.setString(6, state);
			ps.setInt(7, rmb);
			ps.setInt(8, 0);
			ps.setInt(9, id);
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
		doGet(request, response);
	}
	private void insertBill(String userID,int money) {
		Connection conn1=mdb.getconntion();//连接到数据库 	
		String type="商品代购";
		try {
			if(conn1.isClosed()) {
				conn1=mdb.getconntion();//连接到数据库 
			}
			//type = new String(type.getBytes("ISO8859-1"), "UTF-8"); 
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);
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
