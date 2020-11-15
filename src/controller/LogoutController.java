package controller;

import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = "/logout.do")
public class LogoutController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(); // ��ȡsesson����
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser != null) {
			// ɾ��cookie
			Cookie cookie1 = new Cookie("userName","");
			cookie1.setMaxAge(0);
			response.addCookie(cookie1);
			Cookie cookie2 = new Cookie("password","");
			cookie2.setMaxAge(0);
			response.addCookie(cookie2);
			
			// ��������session����ΪcurrentUser����Ϣ
			session.removeAttribute("currentUser"); 
			session.invalidate();
		}

		response.sendRedirect("login.html"); // �ض���login.htmlҳ��

	}

}
