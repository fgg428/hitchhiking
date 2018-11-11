package seekOrder;

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
 * Servlet implementation class seekOrder
 */
@WebServlet("/seekOrder")
public class seekOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String sql;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public seekOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selectType=request.getParameter("typeAll");
		selectType=new String(selectType.getBytes("ISO8859-1"), "UTF-8");
		String type=request.getParameter("type");
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			if(type.equals("1")) {
				sql="select * from tblbuy where buyType='"+selectType+"' and tasked='0'";
			}else if(type.equals("2")) {
				sql="select * from tblsend where locationType='"+selectType+"' and tasked='0'";
			}else {
				sql="select * from tblfood where foodType='"+selectType+"' and tasked='0'";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
