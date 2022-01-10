package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;
import javax.servlet.annotation.WebServlet;

import ncu.im3069.demo.app.Train;
import ncu.im3069.demo.app.TrainHelper;
import ncu.im3069.tools.JsonReader;
import java.sql.*;
import java.text.SimpleDateFormat;



@WebServlet("/api/train.do")
public class TrainController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private TrainHelper th =  TrainHelper.getHelper();
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
	        String departureStation = jsr.getParameter("departureStation");
	        String arrivalStation = jsr.getParameter("arrivalStation");
	        String strdate = jsr.getParameter("str1");
	        String strtime1 = jsr.getParameter("str2");
	        String strtime2 = jsr.getParameter("str3");
	        
	        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm:ss");
	        java.util.Date d1 = null;
	        java.util.Date d2 = null;
	        java.util.Date d3 = null;
	        d1 = format1.parse(strdate);
	        d2 = format2.parse(strtime1);
	        d3 = format2.parse(strtime2);
	        Date date = new Date(d1.getTime());
	        Time time1 = new Time(d2.getTime());
	        Time time2 = new Time(d3.getTime());
	        
	        /** 判斷該字串是否存在，若存在代表要取回個別會員之資料，否則代表要取回全部資料庫內會員之資料 */
	        
	        /** 透過MemberHelper物件之getAll()方法取回所有會員之資料，回傳之資料為JSONObject物件 */
	        JSONObject query = th.getTrain(departureStation, arrivalStation, date ,time1, time2);
	            
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "所有會員資料取得成功");
	        resp.put("response", query);
	    
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	        
	    }
	
}
