package controller;

import com.google.gson.Gson;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/deleteUser.do")
public class DeleteUser extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String ids = request.getParameter("ids");
		// 2.����dao���д���
		
		UserDao dao = new UserDao();
		boolean success = dao.delete(ids);
		// ��ŷ�����Ϣ��Map
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("code", 0);
			map.put("info", "ɾ���ɹ�!");
		} else {
			map.put("code", 1);
			map.put("info", "ɾ��ʧ��!");
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
