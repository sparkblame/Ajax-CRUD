package filters;

import dao.UserDao;
import vo.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//自动登录过滤器
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
		if (currentUser == null) { // 没有登录 ，检查是否记录了cookie

			Cookie cookies[] = request.getCookies(); // 读取cookie
			if (cookies != null) { // cookie存在
				String userName = null, password = null;
				for (Cookie cookie : cookies) { // 检索userName=?和password=?的cookie
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
					if (user != null) { // 用户名存在
						if (user.getPassword().equals(password)) {// 密码正确
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
