package acceptOrder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;

/**
 * Servlet implementation class acceptDgOrder
 */
@WebServlet("/acceptDgOrder")
public class acceptDgOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int userID,workID,tblID,money;
	String type;
 
    public acceptDgOrder() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id1=request.getParameter("userID");
		userID=Integer.parseInt(id1);
		String id2=request.getParameter("workID");
		workID=Integer.parseInt(id2);
		String id3=request.getParameter("tblID");
		tblID=Integer.parseInt(id3);
		type=request.getParameter("type");
		type=new String(type.getBytes("ISO8859-1"), "UTF-8");
		String pay=request.getParameter("pay");	
		money=Integer.parseInt(pay);
		if(checkOrder(tblID)) {
			if(updateTblBuy(tblID)) {			
				if(insertTblAccept()) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");			
					PrintWriter pw=response.getWriter();
					pw.print("success");
					pw.close();
				}else {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");			
					PrintWriter pw=response.getWriter();
					pw.print("fail");
					pw.close();
				}
			}else {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain");			
				PrintWriter pw=response.getWriter();
				pw.print("fail");
				pw.close();
			}
		}else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");			
			PrintWriter pw=response.getWriter();
			pw.print("existed");
			pw.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private boolean insertTblAccept() {
		boolean b=true;
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="insert into tblbuyAccept(userID,acceptType,money,acceptDate,tblID,workID) values(?,?,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ps.setString(2, type);
			ps.setInt(3,money);
			ps.setString(4, null);
			ps.setInt(5, tblID);
			ps.setInt(6, workID);
			ps.executeUpdate();
            ps.close(); 
		}catch(Exception e) {
			b=false;
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	private boolean updateTblBuy(int tblID) {
		boolean a=true;
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="update tblbuy set tasked=1 where buyID='"+tblID+"'";
			PreparedStatement ps=conn.prepareStatement(sql);
            ps.executeUpdate();  
            ps.close();
		}catch(Exception e) {
			a=false;
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return a;
	}
	private boolean checkOrder(int tblID) {
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblbuy where buyID='"+tblID+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			while(r.next()) {
				int s=r.getInt("tasked");
				if(s!=0) {					
					return false;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return true;
	}

}
