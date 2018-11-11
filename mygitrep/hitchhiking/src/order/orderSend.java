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
 * Servlet implementation class orderSend
 */
@WebServlet("/orderSend")
public class orderSend extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String result="success";
	Date d = new Date();  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    String dateNowStr = sdf.format(d);
    public orderSend() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		 RequestBody formBody=new FormBody.Builder()
//      .add("userID",id+"")
//      .add("userName",userName)
//      .add("phone",phone)
//      .add("weight",weight)
//      .add("location",location)
//      .add("state",state)
//      .add("company",sp_company)
//      .add("money",sp_money)
//      .add("locationType",rg_selected)
//      .add("goodsType",rg_type_selected)
		String userID=request.getParameter("userID");
		int id=Integer.parseInt(userID);
		
		String userName=request.getParameter("userName");
		userName = new String(userName.getBytes("ISO8859-1"), "UTF-8"); 
		
		String phone=request.getParameter("phone");
		phone=new String(phone.getBytes("ISO8859-1"), "UTF-8");
		
		String weight=request.getParameter("weight");
		int goodsWeight=Integer.parseInt(weight);
		
		String location=request.getParameter("location");
		location=new String(location.getBytes("ISO8859-1"), "UTF-8");
		
		String state=request.getParameter("state");
		state=new String(state.getBytes("ISO8859-1"), "UTF-8");
		
		String company=request.getParameter("company");
		company=new String(company.getBytes("ISO8859-1"), "UTF-8");
		
		String money=request.getParameter("money");
		int rmb=Integer.parseInt(money);
		
		String locationType=request.getParameter("locationType");
		locationType=new String(locationType.getBytes("ISO8859-1"), "UTF-8");
		
		String goodsType=request.getParameter("goodsType");
		goodsType=new String(goodsType.getBytes("ISO8859-1"), "UTF-8");
		
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="insert into tblSend(locationType,sendCompany,contactPerson,phone,goodsType,goodsWeight,sendDate,sendLocation,statement,pay,tasked,userID) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, locationType);
			ps.setString(2, company);
			ps.setString(3, userName);
			ps.setString(4, phone);
			ps.setString(5,goodsType);
			ps.setInt(6, goodsWeight);
			ps.setString(7, null);
			ps.setString(8, location);
			ps.setString(9, state);
			ps.setInt(10, rmb);
			ps.setInt(11, 0);
			ps.setInt(12, id);
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
		String type="快递代拿";
		try {
			if(conn1.isClosed()) {
				conn1=mdb.getconntion();//连接到数据库 
			}
//			//type = new String(type.getBytes("ISO8859-1"), "UTF-8"); 
//			Date d = new Date();  
//	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	        String dateNowStr = sdf.format(d);
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
