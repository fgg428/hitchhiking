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
 * Servlet implementation class acceptOrderHistory
 */
@WebServlet("/acceptOrderHistory")
public class acceptOrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int userID; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public acceptOrderHistory() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("userID");
		userID=Integer.parseInt(id);
		Connection conn=mdb.getconntion(); 
		try {
			String sql="select a.* from tblbuyAccept a right OUTER JOIN tblbuy b on a.tblID=b.buyID where a.workID='"+userID+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			JSONArray jsonObj=setToJson.resultSetToJsonObject(r);
			jsonObj=addSend(addFood(jsonObj));
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private JSONArray addFood(JSONArray json) {
		//conn=mdb.getconntion();//连接到数据库 
	//	SELECT a.* FROM tblfoodaccept a LEFT OUTER JOIN tblfood b on b.tasked='1' AND a.tblID=b.foodID
		Connection conn=mdb.getconntion(); 
		try {
			String sql="select a.* from tblfoodAccept a left OUTER JOIN tblfood b on a.tblID=b.foodID where a.workID='"+userID+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			JSONArray jsonObj=setToJson.resultSetToJsonObjectAll(r, json);
			return jsonObj;
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
		return null;
		
	}
	private JSONArray addSend(JSONArray json) {
		Connection conn=mdb.getconntion(); 
		try {
			String sql="select a.* from tblsendAccept a left OUTER JOIN tblsend b on a.tblID=b.sendID where a.workID='"+userID+"'";
			java.sql.Statement stmt=conn.createStatement();
			ResultSet r=stmt.executeQuery(sql);
			JSONArray jsonObj=setToJson.resultSetToJsonObjectAll(r, json);
			return jsonObj;
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
		return null;
	}
}
