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
      String query = "update member set password=?, name=?, address=?, phone=?, email = ?"
                  + "where memberID=?";
      
      Object[] param = new Object[] {member.getPassword(), member.getName(), member.getAddress(), member.getPhone(), member.getEmail(), member.getMemberID()};
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
	   		String sql1 = "DELETE FROM MEMBER WHERE memberID = ?";   
	   		Object[] param1 = new Object[] {memberID};
	   		
            String sql2 = "DELETE FROM SELLER WHERE memberID = ?";   
            Object[] param2 = new Object[] {memberID};  // JDBCUtil�� delete���� �Ű� ���� ����
           
            try {            
            	jdbcUtil.setSqlAndParameters(sql2, param2);
				int result1 = jdbcUtil.executeUpdate();	
				if(result1 != 1) {
					throw new Exception();
				}
				
				jdbcUtil.setSqlAndParameters(sql1, param1);
				int result2 = jdbcUtil.executeUpdate();	
				if(result2 != 1) {
					throw new Exception();
				}
				
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
      public int regiSeller(String memberID) throws SQLException{
         String query = "Insert into seller(memberid, sellergrade)VALUES (?, ?)";
         Object[] param = new Object[] {memberID, 0};
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
      }catch(Exception ex) {
    	  ex.printStackTrace();
      } finally {
			jdbcUtil.close();		// resource ��ȯ
		}
       
       return null;
   }
    
    /*������ Member list�� ���� ��ü*/
    public List<Member> findMemberList() throws SQLException {
        String sql = "SELECT memberId, name, email, phone, address "+ "FROM member ";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Member> memberList = new ArrayList<Member>();	// ������� ����Ʈ ����
			while (rs.next()) {
				Member member = new Member();			// Member��ü�� �����Ͽ� ���� ���� ������ ����
					member.setMemberID(rs.getString("memberId"));
					member.setName(rs.getString("name"));
					member.setEmail(rs.getString("email")); 
					member.setPhone(rs.getString("phone"));
					member.setAddress(rs.getString("address"));
					
				memberList.add(member);				// List�� User ��ü ����
			}		
			return memberList;						
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}

    /* �ش� ȸ���� �뿩���� å ���� ����Ʈ ��ȯ */ //���⼭ memberID�� rentalerID
    public List<rentalInfo> getRentalInfoList(String memberID) throws SQLException { 
    	String query = "select rentalid, r.bookid as bookid, sellerid, rentalDate, returnDate, bookname, r.point as point, i.state as state " + 
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
                  rs.getInt("point"),
                  rs.getInt("state")
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

	public int checkSeller(String memberID) {
		String sql = "SELECT count(*) from seller where memberid = ? ";
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? 1 : 0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return 0;
	}
   
	//sellerGrade : ���� �뿩������ ���� �� �ش� å�� �Ǹ����� �뿩���� ������ �뿩 ���� å ������ sellerGrade ����. 
	public int updateSellerGrade (String memberID) {
		int count = 0;
		int update = 0;
		String sql = "selet count(*) from rentalbook where memberid = ? and state = 1";
		String sql2 = "selet count(*) from rentalbook where memberid = ? and state = 0";
		
		String sql3 = "update seller set sellergrade = sellergrade + ? where memberid = ?";
		   
	    try {            
	       jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});
	       ResultSet rs = jdbcUtil.executeQuery();		// query ����
		   if (rs.next()) {
				count = rs.getInt(1); //������� ���� �ŷ����� �뿩����
		   } 
		   jdbcUtil.setSqlAndParameters(sql2, new Object[] {memberID});
	       rs = jdbcUtil.executeQuery();		// query ����
		   if (rs.next()) {
				update = (int) Math.floor(rs.getInt(1) * 0.1 + count * 0.3); 
		   } 
		   
	       Object[] param = new Object[] {update, memberID};
		   jdbcUtil.setSqlAndParameters(sql3, param);
		   
		   int result = jdbcUtil.executeUpdate();
	       if(result != 1) {
	    	   throw new Exception();
	       }
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
}
