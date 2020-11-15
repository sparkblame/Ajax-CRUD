package controller;

import com.google.gson.Gson;
import dao.UserDao;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/register.do")
public class Register extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����������������ʽΪutf-8.��ֹ���Ĳ�������
		request.setCharacterEncoding("utf-8");
		// 1.���ձ��ĸ�Ԫ�ص�name����ֵ��ȡ���������ֵ
		String action=request.getParameter("action");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String chrName = request.getParameter("chrName");
		String mail = request.getParameter("mail");
		String provinceCode = request.getParameter("provinceCode");
		String cityCode = request.getParameter("cityCode");

		// 2.����dao���д���
		User user = new User(userName, password, chrName, mail, provinceCode,
				cityCode);
		UserDao dao = new UserDao();
		boolean success = false ;
		String info="";
		if(action.equals("") || action.equals("insert")){
			success = dao.insert(user);
			info="����";
		}
		else if(action.equals("update")){
			success = dao.update(user);
			info="�޸�";
		}
		
		 
		// ��ŷ�����Ϣ��Map
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("code", 0);
			map.put("info", info+"�ɹ�!");
		} else {
			map.put("code", 1);
			map.put("info", info+"ʧ��!");
		}

		// 3.���ùȸ��Gson�⽫map��������ת��Ϊjson�ַ���
		String jsonStr = new Gson().toJson(map);
		// �ַ�������ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();

	}

}
