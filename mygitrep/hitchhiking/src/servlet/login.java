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
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String result;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNT=request.getParameter("userNT");
		if((userNT==null)|| (userNT.equalsIgnoreCase(""))){
			
		}else {
			userNT = new String(userNT.getBytes("ISO8859-1"), "UTF-8"); 
			System.out.println(userNT);
		}
		
		String uPassword=request.getParameter("uPassword");
		if((uPassword==null)|| (uPassword.equalsIgnoreCase(""))){
			
		}else {
			System.out.println(uPassword);
		}
		
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblUserInfo where Password = '"+uPassword+"'";
			// String sql1="select * from tblUserInfo where telNumber = '"+userNT+"' and Password='"+uPassword+"'";
			java.sql.Statement stmt=conn.createStatement();
			// java.sql.Statement stmt1=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			// ResultSet r1=stmt1.executeQuery(sql1);
			result="fail";
			while(r.next()) {
				String s=r.getString("userName");
				//String s1=r.getString("telNumber");
				if(s.equals(userNT)) {
					result="success";
					System.out.println(result);
					break;
				}
			}
//			if(result.equals("success")) {
//				
//			}else {
////				String sql1="select * from tblUserInfo where telNumber = '"+userNT+"' and Password='"+uPassword+"'";
////				java.sql.Statement stmt1=conn.createStatement();
////				ResultSet r1=stmt1.executeQuery(sql1);
////				while(r1.next()) {
////					String s=r.getString("telNumber");
////					String s1=r.getString("Password");
////					if(s.equals(userNT) && s1.equals(uPassword)) {
////						result="success";	
////						System.out.println(result);
////						break;
////					}
////				}
//			}
			
		}catch(Exception e) {
			result="数据库出错";
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		byte[] bs = result.getBytes(); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");					
		ServletOutputStream op=response.getOutputStream();
		op.write(bs);
		op.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
