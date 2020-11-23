package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.rentalBook;
import model.rentalInfo;

public class MemberDao {
private JDBCUtil jdbcUtil = null;
public static final int FirstMembergrade = 0;
public static final int FirstPoint = 0;

   
   public MemberDao() {         
      jdbcUtil = new JDBCUtil();   // JDBCUtil ��ü ����
   }
   
   public int register(Member member) throws SQLException{
      String query = "Insert into member(memberID, name, email, phone, password, gender, point, membergrade, address) values(?,?,?,?,?,?,?,?,?)";
      Object[] param = new Object[] {member.getMemberID(),  member.getName(), member.getEmail(), member.getPhone(), member.getPassword(), member.getGender(), FirstPoint, FirstMembergrade, member.getAddress()};
      jdbcUtil.setSqlAndParameters(query, param);
      System.out.println(query + param);
      
      try {
         int result = jdbcUtil.executeUpdate();
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {      
         jdbcUtil.commit();
         jdbcUtil.close();   // resource ��ȯ
      }      
      return 0;
   }
   
   /*���߿� ������ �ʵ嵵 �����ϴ� insert, update�� �ø� ����.*/
   public int update(Member member) throws SQLException {
      String query = "update member set password=?, name=?, address=? "
                  + "where memberID=?";
      
      Object[] param = new Object[] {member.getPassword(), member.getName(), member.getAddress(), member.getMemberID()};
      jdbcUtil.setSqlAndParameters(query, param);
      
      try {            
         int result = jdbcUtil.executeUpdate();   // update �� ����
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();   // resource ��ȯ
      }      
      
      return 0;
      
   }

   /*���� �������� ���� ���� ���  _ �뿩���ִ� å ����Ʈ ����, �뿩 ���� å  ����Ʈ ����  ���� ���� ���� ��������.*/
   public Member findUser(String memberID) throws SQLException { /* memberId String Ÿ������ ���� �����ϱ�!!!!!!!!*/
	   
      String query = "select name, email, phone, password, gender, point, memberGrade, sellerGrade, address "
               + "from member m left outer join seller s on m.memberID = s.memberID "
               + "where m.memberID = ?";
      
      jdbcUtil.setSqlAndParameters(query, new Object[] {memberID});
      
      
      try {  	  
    	  ResultSet rs = jdbcUtil.executeQuery();      // query ����
    	  System.out.println("ddd");
    	  
    	  if (rs.next()) {                  // �л� ���� �߰�
    		  
    		  Member member = new Member(      // User ��ü�� �����Ͽ� �л� ������ ����
    				  memberID,
    				  rs.getString("password"),
    				  rs.getString("name"),
    				  rs.getString("email"),
    				  rs.getString("phone"),
    				  rs.getInt("gender"),
    				  rs.getInt("memberGrade"),               
    				  rs.getInt("sellerGrade"),
    				  rs.getString("address"),
    				  rs.getInt("point"));
    		  
    		  return member;
    	  	}
    	  
      	} catch (Exception ex) {
      		ex.printStackTrace();
      	} finally {
      		jdbcUtil.close();      // resource ��ȯ
      	}
      
      return null;
      
   }
   
   public int remove(String memberID) throws SQLException {
         String sql = "DELETE FROM MEMBER WHERE memberID = ?";      
           jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});   // JDBCUtil�� delete���� �Ű� ���� ����
           
            try {            
               int result = jdbcUtil.executeUpdate();   // delete �� ����
               return result;
            } catch (Exception ex) {
               jdbcUtil.rollback();
               ex.printStackTrace();
            }
            finally {
               jdbcUtil.commit();
               jdbcUtil.close();   // resource ��ȯ
            }      
            return 0;
         
      }
   /* �ش� ȸ���� SELLER�� ���*/   
      public int regiSeller(Member member) throws SQLException{
         String query = "Insert into seller(memberid, sellergrade)VALUES (?, ?)";
         Object[] param = new Object[] {member.getMemberID(), member.getSellerGrade()};
         jdbcUtil.setSqlAndParameters(query, param);
         
         try {
            int result = jdbcUtil.executeUpdate();
            return result;
         } catch (Exception ex) {
            jdbcUtil.rollback();
            ex.printStackTrace();
         } finally {      
            jdbcUtil.commit();
            jdbcUtil.close();   // resource ��ȯ
         }      
         return 0;
      }
      
   /* �ش� ȸ���� �뿩�ҷ��� �÷��� å ���� ����Ʈ ��ȯ */
    public List<rentalBook> getRentalBookList(String memberID) throws SQLException {
    	String query = "select bookid, r.bookinfoid as bookinfoid, image, explain, point, "
    			+ "condition, state, bookname " + 
          		"from rentalBook r join bookinfo b on b.bookinfoID = r.bookinfoID " + 
          		"where r.memberId=?";
    	jdbcUtil.setSqlAndParameters(query, new Object[] {memberID});
      
      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<rentalBook> mRentalBookList = new ArrayList<rentalBook>();
         
         while(rs.next()) {
            rentalBook rbook = new rentalBook();
            
            rbook.setBookID(rs.getInt("bookid"));
            rbook.setBookInfoID(rs.getString("bookinfoid"));
            rbook.setImage(rs.getString("image"));
            rbook.setBookname(rs.getString("bookname"));
            rbook.setCondition(rs.getInt("condition"));
            rbook.setExplain(rs.getString("explain"));
            rbook.setPoint(rs.getInt("point"));
            rbook.setState(rs.getInt("state"));
            rbook.setSellerID(memberID);
            
            mRentalBookList.add(rbook);
         }
         return mRentalBookList;
      }catch(Exception ex) {}
       
       return null;
   }

    /* �ش� ȸ���� �뿩���� å ���� ����Ʈ ��ȯ */ //���⼭ memberID�� rentalerID
    public List<rentalInfo> getRentalInfoList(String memberID) throws SQLException { 
    	String query = "select rentalid, r.bookid as bookid, sellerid, rentalDate, returnDate, bookname, point " + 
          		"from rentalBook r inner join bookinfo b on b.bookinfoID = r.bookinfoID " + 
          		"inner join rentalInfo i on i.bookid = r.bookid " +
          		"where i.rentalerID = ?";
      jdbcUtil.setSqlAndParameters(query, new Object[] {memberID});
      
      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<rentalInfo> mRentalInfoList = new ArrayList<rentalInfo>();
         
         while(rs.next()) {
            rentalInfo rbook = new rentalInfo(
                  rs.getInt("rentalid"),
                  rs.getInt("bookid"),
                  rs.getString("sellerID"),
                  memberID,
                  rs.getDate("rentalDate"),
                  rs.getDate("returnDate"),
                  rs.getString("bookname"),
                  rs.getInt("point")
                 );
            mRentalInfoList.add(rbook);
         }
         return mRentalInfoList;
      }catch(Exception ex) {}
       
       return null;
   }
    
	public boolean existingUser(String memberID) {
		String sql = "SELECT count(*) FROM member WHERE memberid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return false;
	}
   
}
