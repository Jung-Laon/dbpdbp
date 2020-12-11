package controller.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Member;
import model.rentalBook;
import model.rentalInfo;
import model.service.MemberNotFoundException;
import model.service.memberManager;

public class UpdateUserController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

	 @Override
	 public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 	if (request.getMethod().equals("GET")) {	
		 		if (!UserSessionUtils.hasLogined(request.getSession())) {
		            return "redirect:/user/login/form";		// login form ��û���� redirect
		        }
		    	
		    	String updateId = UserSessionUtils.getLoginUserId(request.getSession());
		    	
				log.debug("UpdateForm Request : {}", updateId);
	    		
	    		memberManager manager = memberManager.getInstance();
				Member member = manager.findMember(updateId);	// �����Ϸ��� ����� ���� �˻�
				request.setAttribute("member", member);	
				
				return "/user/UpdateUser.jsp"; 
	
		    }	
	    	
	    	// POST request (ȸ�������� parameter�� ���۵�)
	    	Member updateMember = new Member(
	    			request.getParameter("memberid"),
	    			request.getParameter("passwd"),
	    			new String(request.getParameter("name").getBytes("8859_1"), "UTF-8"),
	    			request.getParameter("email"),
	    			request.getParameter("phone"),
	    			Integer.valueOf(request.getParameter("gender")),
	    			Integer.valueOf(request.getParameter("memberGrade")),
	    			Integer.valueOf(request.getParameter("sellerGrade")),
	    			new String(request.getParameter("address").getBytes("8859_1"), "UTF-8"),
	    			Integer.valueOf(request.getParameter("point"))
	    	);
	    
	    	log.debug("Update User : {}", updateMember);
	
			memberManager manager = memberManager.getInstance();
			manager.update(updateMember);	
			
	        return "redirect:/user/myPage";	   
	 }
	 
}
