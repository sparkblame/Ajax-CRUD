package controller;

import dao.CreateImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet(urlPatterns = "/createVerifyImage.do")
public class CreateVerifyImageController extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ����֤��ͼƬ���ɵĹ��̷�װ��tools���µ�CreateVerificationCodeImage����
		CreateImage createImage = new CreateImage();
		// ������λ����ַ���
		String vCode = createImage.createCode();
		// ��ȡhttpSession����
		HttpSession session = request.getSession();
		// ����������λ����ַ��������session�У������session�е�������һ���Ự��Χ�ڶ������ȫ�ֹ������Ա���֤
		session.setAttribute("verifyCode", vCode);
		
		// ���÷��ص�����
		response.setContentType("img/jpeg");
		// ���÷�װ���෽������ָ����֤���ַ������ڴ�ͼƬ
		BufferedImage image = createImage.CreateImage(vCode);
		//��ȡ�ֽ�������
		ServletOutputStream out = response.getOutputStream();
		//���ڴ�ͼ����������������ʽΪJPEG
		ImageIO.write(image, "JPEG", out);
		//ˢ���������������������������õȴ��������������������磩
		out.flush();
		out.close();
	}
}




