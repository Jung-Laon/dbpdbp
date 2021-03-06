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

public class LoadingMyiBookController implements Controller {
	 @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
			// 로그인 여부 확인
	    	if (!UserSessionUtils.hasLogined(request.getSession())) {
	            return "redirect:/user/login/form";		// login form 요청으로 redirect
	        }
	    	
	    	int bookID = Integer.valueOf(request.getParameter("ibookID"));
	    	int rentalID = Integer.valueOf(request.getParameter("irentalID"));
	    	
			bookManager  bManager = bookManager.getInstance();
			BookInfoManager biManager = BookInfoManager.getInstance();
			
			bookInfo bInfo = null;
			rentalInfo rInfo = null;
			rentalBook rBook = null;
			
	    	try {
	    		rBook = bManager.findRentBook(bookID);
	    		bInfo = biManager.findBookInfo(rBook.getBookInfoID());
	    		rInfo = bManager.findRentInfo(rentalID);
	    		
			} catch (BookNotFoundException e) {				
		        return "redirect:/user/myPage";
			}	
			
	    	
	    	request.setAttribute("rBook", rBook);
	    	request.setAttribute("rInfo", rInfo);
	    	request.setAttribute("bInfo", bInfo);
			
			return "/user/myRentalInfoPage.jsp";     
	    }
}