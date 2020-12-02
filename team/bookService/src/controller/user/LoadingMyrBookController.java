package controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.bookInfo;
import model.rentalBook;
import model.rentalInfo;
import model.service.BookNotFoundException;
import model.service.bookManager;
import model.service.BookInfoManager;

public class LoadingMyrBookController implements Controller {
	 @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
			// �α��� ���� Ȯ��
	    	if (!UserSessionUtils.hasLogined(request.getSession())) {
	            return "redirect:/user/login/form";		// login form ��û���� redirect
	        }
	    	
	    	int bookID = Integer.valueOf(request.getParameter("rbookID"));
	    	
			bookManager  bManager = bookManager.getInstance();
			BookInfoManager biManager = BookInfoManager.getInstance();
			
			bookInfo bInfo = null;
			rentalInfo rInfo = null;
			rentalBook rBook = null;
			
	    	try {
	    		
	    		rBook = bManager.findRentBook(bookID);
	    		bInfo = biManager.findBookInfo(rBook.getBookInfoID());
	    		if(rBook.getState() == 1)
	    			rInfo = bManager.findRentInfo(bookID, rBook.getState());
				
			} catch (BookNotFoundException e) {				
		        return "redirect:/user/myPage";
			}	
			
	    	
	    	request.setAttribute("rBook", rBook);
	    	request.setAttribute("rInfo", rInfo);
	    	request.setAttribute("bInfo", bInfo);
			
			return "/user/myRentalBookInfo.jsp";     
	    }
}