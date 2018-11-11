package order;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toJson.setToJson;

@WebServlet("/sendInfor")
public class sendInfor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sendInfor() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn=mdb.getconntion();//连接到数据库 
		try {
			String sql="select * from tblSend where tasked='"+0+"'"+" and DATE_FORMAT(sendDate,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') ORDER BY sendDate desc";
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
