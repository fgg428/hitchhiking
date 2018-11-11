package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.mdb;
import java.sql.*;
/**
 * Servlet implementation class register
 */
@WebServlet("/register")
@MultipartConfig
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String result="fail";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uName=request.getParameter("uName");
		if((uName==null)|| (uName.equalsIgnoreCase(""))){
			
		}else {
			uName = new String(uName.getBytes("ISO8859-1"), "UTF-8"); 
			System.out.println(uName);
		}
		String telNum=request.getParameter("telNum");
		if((telNum==null)||(telNum.equalsIgnoreCase(""))) {
			
		}else {
			System.out.println(telNum);
		}
		
		String uPassword=request.getParameter("uPassword");
		if((uPassword==null)||(uPassword.equalsIgnoreCase(""))) {
			
		}else {
			System.out.println(uPassword);
		}
		String rName=request.getParameter("rName");
		if((rName==null)||(rName.equalsIgnoreCase(""))) {
			
		}else {
			rName = new String(rName.getBytes("ISO8859-1"), "UTF-8"); 
			System.out.println(rName);
		}
		
		String rLocation=request.getParameter("rLocation");
		if((rLocation==null)||(rLocation.equalsIgnoreCase(""))) {
			
		}else {
			rLocation = new String(rLocation.getBytes("ISO8859-1"), "UTF-8"); 
			System.out.println(rLocation);
		}
		
		Connection conn=mdb.getconntion();//���ӵ����ݿ� 
		if(conn!=null) {
			result="success";
		}
		try {
			String sql="insert into tblUserInfo(userName,Password,telNumber,realName,location,money,experience) values(?,?,?,?,?,?,?)";
		    PreparedStatement pstmt=conn.prepareStatement(sql);
		    pstmt.setString(1, uName);//����sql�еĵ�1��������λ�õ�ֵ
		    pstmt.setString(2, uPassword);//����sql�еĵ�2��������λ�õ�ֵ
		    pstmt.setString(3,telNum);
		    pstmt.setString(4,rName);
		    pstmt.setString(5, rLocation);
		    pstmt.setInt(6, 0);
		    pstmt.setInt(7, 0);
		    pstmt.executeUpdate();  
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
