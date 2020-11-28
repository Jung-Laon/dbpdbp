package model.service;

import java.sql.SQLException;

import model.bookInfo;
import model.rentalBook;
import model.rentalInfo;
import model.dao.rentalbookDAO;

public class bookManager {
	
	private static bookManager bookMan = new bookManager();
	private rentalbookDAO rBookDAO;

	private bookManager() {
		try {
			rBookDAO = new rentalbookDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static bookManager getInstance() {
		return bookMan;
	}
	/*
	public int createRBook(Member member) throws SQLException, ExistingUserException {
		if (rBookDAO.existingUser(member.getMemberID()) == true) {
			throw new ExistingUserException(member.getMemberID() + "�� �����ϴ� ���̵��Դϴ�.");
		}
		return memberDAO.register(member);
	}
	*/
	
	public int updateRBook(rentalBook rbook) throws SQLException, BookNotFoundException {
		findRentBook(rbook.getBookID());
		return rBookDAO.update(rbook);
	}	

	public int removeRBook(int bookID) throws SQLException, BookNotFoundException {
		findRentBook(bookID);
		return rBookDAO.remove(bookID);
	}

	public rentalBook findRentBook(int bookID)
		throws SQLException, BookNotFoundException {
		rentalBook rbook = rBookDAO.findRentBook(bookID);
		if (rbook == null) {
			throw new BookNotFoundException(bookID + "�� �������� �ʴ� �Ͼ��̵��Դϴ�.");
		}		
		return rbook;
	}	

	public rentalbookDAO getrentalbookDAO() {
		return this.rBookDAO;
	}

	public bookInfo findBookInfo(String bookInfoID) throws SQLException, BookNotFoundException {
		bookInfo bInfo = rBookDAO.findBookInfo(bookInfoID);
		if(bInfo == null) {
			throw new BookNotFoundException(bookInfoID + "�� �������� �ʴ� ���������̵��Դϴ�.");
		}
		return bInfo;
	}

	public rentalInfo findRentInfo(int bookID) throws SQLException, BookNotFoundException {
		rentalInfo rInfo = rBookDAO.findRentInfo(bookID);
		if (rInfo == null) {
			throw new BookNotFoundException(bookID + "�� �������� �ʴ� �Ͼ��̵��Դϴ�.");
		}		
		return rInfo;
	}
	
	public int returniBook(int bookID) throws SQLException, BookNotFoundException {
		rentalInfo rInfo = findRentInfo(bookID);
		return rBookDAO.returnBook(rInfo);
	}

	
}