package exchange;

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
 * Servlet implementation class exchangeMoney
 */
@WebServlet("/exchangeMoney")
public class exchangeMoney extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String result="success";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public exchangeMoney() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		.add("userID",userID+"")
//        .add("money",result+"")
//        .add("experience",experience+"")
		String money=request.getParameter("money");
		int num=Integer.parseInt(money);
		System.out.println("num:"+num);
		String userID=request.getParameter("userID");
		int id=Integer.parseInt(userID);
		String experience=request.getParameter("experience");
		int ep=Integer.parseInt(experience);
		System.out.println("experience:"+ep);
		String type="积分兑换";
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="update tblUserInfo set money=money+"+num+",experience="+ep+" where userID='"+id+"'";
			PreparedStatement ps=conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();  
            insertBill(id,money,type);         
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void insertBill(int id,String money,String type) {
		Connection conn1=mdb.getconntion();//连接到数据库 	
		try {
			if(conn1.isClosed()) {
				conn1=mdb.getconntion();//连接到数据库 
			}
			Date d = new Date();  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);
			int number=Integer.parseInt(money);
			String sql="insert into tblBill(number,billType,billDate,type,userID) values(?,?,?,?,?)";
			 PreparedStatement pstmt=conn1.prepareStatement(sql);
			    pstmt.setInt(1, number);//设置sql中的第1个？参数位置的值
			    pstmt.setString(2, type);//设置sql中的第2个？参数位置的值
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
