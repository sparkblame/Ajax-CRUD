package controller;

import dao.DownloadDao;
import vo.Download;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@WebServlet(urlPatterns = "/download.do")
public class DownloadController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id=request.getParameter("id");
		DownloadDao dao = new DownloadDao();
		Download download=dao.get(Integer.parseInt(id));
		
		/**
		 * ���ļ����ع�����web�����о���ʹ�õ��Ĺ��ܣ�ʹ��HttpServletResponse����Ϳ���ʵ���ļ�������
		 * getOutputStream()����ͻ��˷������ݣ��ֽ����� getWriter()����ͻ��˷������ݣ��ַ�����
		 */
		// 1.��ȡҪ���ص��ļ��ľ���·��
		String path = request.getServletContext().getRealPath("/WEB-INF/"+download.getPath());
		// 2.��ȡҪ���ص��ļ���
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		// 3.����content-disposition��Ӧͷ��������������ص���ʽ���ļ�
		// ����context-disposition��Ӧͷ�������������������ʽ�򿪣�����ע���ļ��ַ��������ʽ������utf-8����Ȼ���������
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(fileName, "UTF-8"));
		// 4.��ȡҪ���ص��ļ�������
		// �ַ���������FileReader in = new FileReader(path);
		InputStream in = new FileInputStream(path);
		int len = 0;
		// 5.�������ݻ�����
		byte[] buffer = new byte[1024];
		// 6.ͨ��response�����ȡOutputStream��
		// ��д�ļ����ع���ʱ�Ƽ�ʹ��OutputStream��������ʹ��PrintWriter����
		// ��ΪOutputStream�����ֽ��������Դ����������͵����ݣ�
		// ��PrintWriter�����ַ�����ֻ�ܴ����ַ����ݣ�������ַ��������ֽ����ݣ��ᵼ�����ݶ�ʧ
		// �ַ���д������PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		// 7.��FileInputStream��д�뵽buffer������
		while ((len = in.read(buffer)) != -1) {
			// 8.ʹ��OutputStream��������������������ͻ��������
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}
}


