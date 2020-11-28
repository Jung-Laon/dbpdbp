package controller.book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.service.BookInfoManager;
import model.service.ExistingUserException;
import model.service.memberManager;

public class BookSearchController implements Controller{
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {	
    	String stype = request.getParameter("stype");
		String stype_g = request.getParameter("stype_g");
		String text = request.getParameter("text");
		
		try {
			BookInfoManager book = BookInfoManager.getInstance();
			book.getSearchBookList(text);
			request.setAttribute("loginFailed", true);
            return "/user/loginForm.jsp";	
            
		} catch (Exception e) {
			/* UserNotFoundException�̳� PasswordMismatchException �߻� ��
			 * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
			 */
            request.setAttribute("searchFailed", true);
			request.setAttribute("exception", e);
            return "/home";			
		}	
    }
}
