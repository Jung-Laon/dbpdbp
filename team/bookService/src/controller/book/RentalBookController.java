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
		//�������� �ϴ� �뿩������ �⺻Ű(bookID)��, ���� �ش絵���� �������� ȸ���� id ���� ������.
		int bookID = Integer.valueOf(request.getParameter("rBookid"));
		String memberID = UserSessionUtils.getLoginUserId(request.getSession());
		
		rentalBook rbook = null;
		Member seller = null;
		Member rentaler = null;
		
		try {
			//�������� �뿩 ���� ��ü�� ������.
			bookManager bManager = bookManager.getInstance();
			rbook = bManager.findRentBook(bookID);
			
			//�Ǹ����� ȸ�� ��ü�� �뿩�� ȸ���� ��ü�� ���� ������.
			memberManager mManager = memberManager.getInstance();
			seller = mManager.findMember(rbook.getSellerID()); //�Ǹ��� ���̵�� �뿩���� ��ü���� ����.
			rentaler = mManager.findMember(memberID);
    		
			//���� �뿩���� ���� ����Ʈ�� �뿩 ������ ����Ʈ ���� ���ٸ� ���� �߻�.
			if(rentaler.getPoint() < rbook.getPoint()){
                throw new RentalException("bookID : " + bookID + " _å�� �����⿡ rentaler�� ���� ����Ʈ�� �����մϴ�.");
            }
			
			//���� �뿩�ڰ� �ڽ��� �ø� �뿩 ������ �뿩�Ϸ��Ѵٸ� ���� �߻�
			if(rentaler.getMemberID().equalsIgnoreCase(seller.getMemberID())) {
				 throw new RentalException("bookID : " + bookID + " _�ڽ��� �ø� å�� �뿩�Ͻ� �� �����ϴ�.");
			}
			
			//bookManager�� ���� �뿩 ���� DAO ȣ��.
			if(bManager.rental(rbook.getBookID(), seller.getMemberID(), rentaler.getMemberID()) > 0) {
				request.setAttribute("rentalOK", true);
				request.setAttribute("bookID", rbook.getBookInfoID());
				
				if(mManager.updateSellerGrade(seller.getMemberID()) != 1) {
					throw new Exception();
				}
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
		
		//�뿩 ����  Ȥ�� ���� �� �ϴ� ������� ������ ���� ��������  /book/info �������� ���ư�.
    }
}
