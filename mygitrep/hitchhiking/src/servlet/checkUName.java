package servlet;

import java.io.IOException;
import java.sql.Connection;
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
 * Servlet implementation class checkUName
 */
@WebServlet("/checkUName")
public class checkUName extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkUName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName=request.getParameter("uName");
		userName=new String(userName.getBytes("ISO8859-1"), "UTF-8");
		if((userName==null)|| (userName.equalsIgnoreCase(""))){
			
		}else {
			userName = new String(userName.getBytes("ISO8859-1"), "UTF-8"); 
			System.out.println(userName);
		}
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblUserInfo where userName='"+userName+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			String result="success";
			while(r.next()) {
				String s=r.getString("userName");
				if(s.equals(userName)) {
					result="fail";	
					break;
				}
			}
			byte[] bs = result.getBytes(); 
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");					
			ServletOutputStream op=response.getOutputStream();
			op.write(bs);
			op.close();
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
