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
		if (notFilterUri.contains(uri)) { // ��ǰ����ĵ�ַ����Ҫ���ˣ�ֱ�ӷ���
			chain.doFilter(request, resp);
			return;
		}

		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) { // û�е�¼ 
			request.setAttribute("info", "�������¼����ܷ��ʸ���Դ��");
			request.setAttribute("path", "login.html");
			request.getRequestDispatcher("/error.jsp").forward(request, resp);

		} else { // �ѵ�¼�����Ȩ��
			ResourceDao dao = new ResourceDao();
			List<String> list = dao.getUrlByUserName(currentUser.getUserName());
			if (list.contains(uri)) { // ��ǰ�û��з��ʵ�ǰuri��Ȩ��
				chain.doFilter(request, resp);
			} else { // û��Ȩ��
				request.setAttribute("info", "��û��Ȩ�޷��ʸ���Դ��");
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
