package orderHistory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;
import net.sf.json.JSONArray;
import toJson.setToJson;

/**
 * Servlet implementation class orderHistory
 */
@WebServlet("/orderHistory")
public class orderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String sql;  
    public orderHistory() {
        super();
        // TODO Auto-generated constructor stub
    }	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID=request.getParameter("userID");
		int id=Integer.parseInt(userID);
		String type=request.getParameter("type");
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			if(type.equals("1")) {
				sql="select * from tblbuy where userID='"+id+"' order by buyDate desc";
			}else if(type.equals("2")) {
				sql="select * from tblsend where userID='"+id+"' order by sendDate desc";
			}else {
				sql="select * from tblfood where userID='"+id+"' order by foodDate desc";
			}			
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			JSONArray jsonObj=setToJson.resultSetToJsonObject(r);
			System.out.println(jsonObj);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");					
			PrintWriter pw=response.getWriter();
			pw.print(jsonObj);
			pw.close();
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
