package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.User;
import dao.UserDao;

@WebServlet(urlPatterns = "/login.do")
public class LoginController extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ����������������ʽΪutf-8.��ֹ���Ĳ�������
		request.setCharacterEncoding("utf-8");
		// 1.���ձ��ĸ�Ԫ�ص�name����ֵ��ȡ���������ֵ
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		String autoLogin=request.getParameter("autoLogin");
		
		// 2.��ȡHttpSession����
		HttpSession session = request.getSession();
		// ȡ��CreateVerifyImageController�д�ŵ���֤���ַ���
		String saveVcode = (String) session.getAttribute("verifyCode");
		String forwardPath = ""; //ת�������url-pattern
		// �Ƚ��������֤���������ɵ���֤���Ƿ���ͬ
		if (!vcode.equalsIgnoreCase(saveVcode)) { // ��ͬ
			// ����Ҫ���ݵ����ݴ����request��Χ�� ��Ŀ��ת��ҳ����Դ��л�ȡ
			request.setAttribute("info", "��֤�벻��ȷ��");
			request.setAttribute("path", "login.html");
			forwardPath = "/error.jsp";
		} else {// ��֤����ȷ
			UserDao userDao = new UserDao();
			User user=userDao.get(userName);
			if (user == null) { // �û���������
				// ����Ҫ���ݵ����ݴ����request��Χ�� ��Ŀ��ת��ҳ����Դ��л�ȡ
				request.setAttribute("info", "��������û��������ڣ�");
				request.setAttribute("path", "login.html");
				forwardPath = "/error.jsp";
			} else { // �û�������
				if(!user.getPassword().equals(password)){// ���벻��ȷ
					// ����Ҫ���ݵ����ݴ����request��Χ�� ��Ŀ��ת��ҳ����Դ��л�ȡ
					request.setAttribute("info", "����������벻��ȷ��");
					request.setAttribute("path", "login.html");
					forwardPath = "/error.jsp";
				}
				else { // �û���������ȷ
					// ����Ҫ���ݵ����ݴ����session��Χ�� ��һ���Ự�׶ε����г��򶼿��Դ��л�ȡ
					session.setAttribute("currentUser", user);
					
					if(autoLogin!=null){
						//����cookie
						Cookie cookie1=new Cookie("userName",userName);
						cookie1.setMaxAge(7*24*24*24);  //������Ч��Ϊ7��
						response.addCookie(cookie1);
						Cookie cookie2=new Cookie("password",password);
						cookie2.setMaxAge(7*24*24*24);  //������Ч��Ϊ7��
						response.addCookie(cookie2);
					}
		
					forwardPath = "/main.jsp";
				}
			}
		}
		// ��ȡת������
		RequestDispatcher rd = request.getRequestDispatcher(forwardPath);
		// ������ת����Ŀ�����
		rd.forward(request, response);
	}
}


