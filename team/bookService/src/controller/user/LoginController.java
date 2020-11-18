package controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.memberManager;

//import model.service.UserManager;
//db ��Ű�� �� ���ڵ� ����x, �ӽ÷� ���ư��� �ٹ�.
public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		try {
			memberManager manager = memberManager.getInstance();
			
			// ���ǿ� ����� ���̵� ����			
			if (manager.login(userId, password)) {
				HttpSession session = request.getSession();
				session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userId);
				return "redirect:/home";	
			}
			
			request.setAttribute("loginFailed", true);
            return "/user/loginForm.jsp";	
            
		} catch (Exception e) {
			/* UserNotFoundException�̳� PasswordMismatchException �߻� ��
			 * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
            request.setAttribute("loginFailed", true);
			request.setAttribute("exception", e);
            return "/user/loginForm.jsp";			
		}	
    }
}
