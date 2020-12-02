package controller.book;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import controller.user.UserSessionUtils;
import model.Member;
import model.rentalBook;
import model.service.RentalException;
import model.service.bookManager;
import model.service.memberManager;

public class RentalBookController implements Controller {
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		int bookID = Integer.valueOf(request.getParameter("rBookid"));
		String memberID = UserSessionUtils.getLoginUserId(request.getSession());
		//String bookinfoID = request.getParameter("bookinfoID");
		
		rentalBook rbook = null;
		Member seller = null;
		Member rentaler = null;
		
		try {
			bookManager bManager = bookManager.getInstance();
			rbook = bManager.findRentBook(bookID);
			
			memberManager mManager = memberManager.getInstance();
			seller = mManager.findMember(rbook.getSellerID());
			rentaler = mManager.findMember(memberID);
    		
			if(rentaler.getPoint() < rbook.getPoint()){
                throw new RentalException("<����> bookID : " + bookID + " _å�� �����⿡ rentaler�� ���� ����Ʈ�� �����մϴ�.");
            }
			
			if(rentaler.getMemberID().equalsIgnoreCase(seller.getMemberID())) {
				 throw new RentalException("<����> bookID : " + bookID + " _�ڽ��� �ø� å�� �뿩�Ͻ� �� �����ϴ�.");
			}
			
			if(bManager.rental(rbook.getBookID(), seller.getMemberID(), rentaler.getMemberID()) > 0) {
				request.setAttribute("rentalOK", true);
				request.setAttribute("bookID", rbook.getBookInfoID());
				return "/book/info";
			} else {
				throw new RentalException("<����> bookID : " + bookID + " _�뿩 ���� _ �����ڿ��� �����ּ���");
			}
			
		} catch (RentalException e) {
			request.setAttribute("RentalException", true);
			request.setAttribute("exception", e);
			request.setAttribute("bookID", rbook.getBookInfoID());
            return "/book/info";	
		} catch (Exception e) {
			request.setAttribute("Exception", true);
			request.setAttribute("exception", e);
			request.setAttribute("bookID", rbook.getBookInfoID());
			return "/book/info";
		}
		
		
    }
}
