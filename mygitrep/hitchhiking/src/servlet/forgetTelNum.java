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
 * Servlet implementation class forgetTelNum
 */
@WebServlet("/forgetTelNum")
public class forgetTelNum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public forgetTelNum() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String telNum=request.getParameter("telNum");
		if((telNum==null)|| (telNum.equalsIgnoreCase(""))){
			System.out.println("111");
		}else {
			System.out.println(telNum);
		}
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblUserInfo where telNumber='"+telNum+"'";
			java.sql.Statement stmt=conn.createStatement();
			String result="fail";
			ResultSet r=stmt.executeQuery(sql);
			while(r.next()) {
				String s=r.getString("telNumber");
				String n=r.getString("userName");
				if(s.equals(telNum)) {
					result=n;
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
