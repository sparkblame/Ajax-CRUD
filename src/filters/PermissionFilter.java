package filters;

import dao.ResourceDao;
import vo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class PermissionFilter implements Filter {

	private String notFilterUri;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		String uri = request.getServletPath();
		System.out.println(uri);
		if (notFilterUri.contains(uri)) { // 当前请求的地址不需要过滤，直接放行
			chain.doFilter(request, resp);
			return;
		}

		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) { // 没有登录 
			request.setAttribute("info", "您必须登录后才能访问该资源！");
			request.setAttribute("path", "login.html");
			request.getRequestDispatcher("/error.jsp").forward(request, resp);

		} else { // 已登录，检查权限
			ResourceDao dao = new ResourceDao();
			List<String> list = dao.getUrlByUserName(currentUser.getUserName());
			if (list.contains(uri)) { // 当前用户有访问当前uri的权限
				chain.doFilter(request, resp);
			} else { // 没有权限
				request.setAttribute("info", "您没有权限访问该资源！");
				request.setAttribute("path", "main.jsp");
				request.getRequestDispatcher("/error.jsp").forward(request,
						resp);
			}

		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		notFilterUri = config.getInitParameter("notFilterUri");
	}

}
