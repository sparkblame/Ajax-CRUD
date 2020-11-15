package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.UserDao;
import vo.Page;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = "/queryUser.do")
public class QueryUser extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//���տͻ��˲���
		String queryParams = request.getParameter("queryParams");
		String pageParams = request.getParameter("pageParams");
		System.out.println("��ѯ������"+pageParams);
		System.out.println("��ҳ������"+queryParams);
		//��json�ַ�������ֵת��Ϊjava����
		Gson gson = new GsonBuilder().serializeNulls().create();
		HashMap<String, Object> mapPage = gson.fromJson(pageParams, HashMap.class);
		Page page = Page.getPageParams(mapPage); //ת��Ϊpage����
		User user = new User();
		if (queryParams != null) {
			user = gson.fromJson(queryParams, User.class);
		}

		//����daoִ�д���
		UserDao dao = new UserDao();
		ArrayList<User> rows = dao.query(user, page); //��ѯ
		int total = dao.count(user, page);  //��ѯ��¼����

		//ת��Ϊjson����
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		mapReturn.put("rows", rows);
		mapReturn.put("total", total);
		String jsonStr = gson.toJson(mapReturn);

		// �ַ������
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();

	}

}
