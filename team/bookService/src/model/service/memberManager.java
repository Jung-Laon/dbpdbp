package model.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.rentalBook;
import model.rentalInfo;
import model.dao.MemberDao;

public class memberManager {
	
	private static memberManager memMan = new memberManager();
	private MemberDao memberDAO;

	private memberManager() {
		try {
			memberDAO = new MemberDao();
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static memberManager getInstance() {
		return memMan;
	}
	
	public int create(Member member) throws SQLException, ExistingUserException {
		if (memberDAO.existingUser(member.getMemberID()) == true) {
			throw new ExistingUserException(member.getMemberID() + "�� �����ϴ� ���̵��Դϴ�.");
		}
		return memberDAO.register(member);
	}
	
	public int update(Member member) throws SQLException, MemberNotFoundException {
		findMember(member.getMemberID());
		return memberDAO.update(member);
	}	

	public int remove(String memberID) throws SQLException, MemberNotFoundException {
		findMember(memberID);
		return memberDAO.remove(memberID);
	}

	public Member findMember(String memberID)
		throws SQLException, MemberNotFoundException {
		Member member = memberDAO.findUser(memberID);
		
		if (member == null) {
			throw new MemberNotFoundException(memberID + "�� �������� �ʴ� ���̵��Դϴ�.");
		}		
		return member;
	}	
	
	public boolean login(String memberID, String password) throws SQLException, MemberNotFoundException {
		Member member = findMember(memberID);
		
		if (member.getPassword().equals(password))
		{
			System.out.println("True!");
			return true;
		}
		
		System.out.println("False!");
		return false;
	}

	public MemberDao getMemberDAO() {
		return this.memberDAO;
	}
	
	public int regiseller(String memberID) throws SQLException {
		return memberDAO.regiSeller(memberID);
	}
	
	public List<Member> findMemberList() throws SQLException {
		return memberDAO.findMemberList();
	}
	public List<rentalBook> getRentalBookList(String memberID) throws SQLException {
		return memberDAO.getRentalBookList(memberID);
	}
	
	public List<rentalInfo> getRentalInfoList(String memberID) throws SQLException {
		return memberDAO.getRentalInfoList(memberID);
	}
	
	public boolean existingUser(String memberID) {
		return memberDAO.existingUser(memberID);
	}

	public int checkSeller(String memberID) {
		// TODO Auto-generated method stub
		return memberDAO.checkSeller(memberID);
	}

	public int updateSellerGrade(String memberID) {
		return memberDAO.updateSellerGrade(memberID);
	}
	
}