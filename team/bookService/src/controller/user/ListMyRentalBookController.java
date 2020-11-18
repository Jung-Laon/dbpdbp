package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.memberManager;
import model.*;

public class ListMyRentalBookController implements Controller {
	 @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
			// �α��� ���� Ȯ��
	    	if (!UserSessionUtils.hasLogined(request.getSession())) {
	            return "redirect:/user/login/form";		// login form ��û���� redirect
	        }
	    	
	    	String memberID = UserSessionUtils.getLoginUserId(request.getSession());
	    	
			memberManager manager = memberManager.getInstance();
			List<rentalBook> rBookList = manager.getRentalBookList(memberID);
			// List<User> userList = manager.findUserList(currentPage, countPerPage);

			// userList ��ü�� ���� �α����� ����� ID�� request�� �����Ͽ� ����
			request.setAttribute("rBookList", rBookList);				
			request.setAttribute("curUserId", memberID);		

			// ����� ����Ʈ ȭ������ �̵�(forwarding)
			return "/user/list.jsp";        
	    }
}
