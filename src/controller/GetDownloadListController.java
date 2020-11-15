package controller;

import dao.DownloadDao;
import vo.Download;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/queryDownload.do")
public class GetDownloadListController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DownloadDao dao = new DownloadDao();
		ArrayList<Download> list = dao.query();
		request.setAttribute("downloadList", list);
		// ��ȡת������
		RequestDispatcher rd = request.getRequestDispatcher("/download.jsp");
		// ������ת����Ŀ�����
		rd.forward(request, response);
	}

}
