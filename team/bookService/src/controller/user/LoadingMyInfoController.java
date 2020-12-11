package controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.MemberNotFoundException;
import model.service.memberManager;
import model.*;

public class LoadingMyInfoController implements Controller {
	 @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
			// �α��� ���� Ȯ��
	    	if (!UserSessionUtils.hasLogined(request.getSession())) {
	            return "redirect:/user/login/form";		// login form ��û���� redirect
	        }
	    	
	    	String memberID = UserSessionUtils.getLoginUserId(request.getSession());
	    	
			memberManager manager = memberManager.getInstance();
			
			int seller  = -1;
			List<rentalInfo> rInfoList = null;
			List<rentalBook> rBookList = null;
			Member member = null;
			
	    	try {
	    		rInfoList = manager.getRentalInfoList(memberID);
	    		rBookList = manager.getRentalBookList(memberID);
				member = manager.findMember(memberID);	// ����� ���� �˻�
				seller = manager.checkSeller(memberID);
				
			} catch (MemberNotFoundException e) {				
		        return "redirect:/home";
			}	

	    	
	    	request.setAttribute("rInfoList", rInfoList);
			request.setAttribute("rBookList", rBookList);				
			request.setAttribute("curMember", member);
			request.setAttribute("seller", seller);
			

			return "/user/userInfoPage.jsp";     
	    }
}
