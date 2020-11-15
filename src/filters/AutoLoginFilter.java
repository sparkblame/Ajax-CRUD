package filters;

import dao.UserDao;
import vo.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//�Զ���¼������
public class AutoLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) { // û�е�¼ ������Ƿ��¼��cookie

			Cookie cookies[] = request.getCookies(); // ��ȡcookie
			if (cookies != null) { // cookie����
				String userName = null, password = null;
				for (Cookie cookie : cookies) { // ����userName=?��password=?��cookie
					if ("userName".equals(cookie.getName())) {
						userName = cookie.getValue();
					}
					if ("password".equals(cookie.getName())) {
						password = cookie.getValue();
					}
				}
				if (userName != null && password != null) {
					UserDao userDao = new UserDao();
					User user = userDao.get(userName);
					if (user != null) { // �û�������
						if (user.getPassword().equals(password)) {// ������ȷ
							session.setAttribute("currentUser", user);
							chain.doFilter(request, resp);
							return;
						}
					}
				}

			}

		}

		chain.doFilter(request, resp);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
