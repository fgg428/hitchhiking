package order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;

/**
 * Servlet implementation class deleteDgOrder
 */
@WebServlet("/deleteDgOrder")
public class deleteDgOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteDgOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("buyID");
		int tblID=Integer.parseInt(id);
		System.out.println(tblID);
		if(checkOrder(tblID)) {
			if(updateTblfood(tblID)) {
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
			pw.print("accepted");
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
	private boolean checkOrder(int tblID) {
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblbuy where buyID='"+tblID+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			while(r.next()) {
				int s=r.getInt("tasked");
				if(s==0) {					
					return true;
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
		return false;
	}
	private boolean updateTblfood(int tblID) {
		boolean a=true;
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="update tblbuy set tasked=2 where buyID='"+tblID+"'";
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

}
