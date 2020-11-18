package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import model.rentalBook;

public class MemberDao {
private JDBCUtil jdbcUtil = null;
   
   public MemberDao() {         
      jdbcUtil = new JDBCUtil();   // JDBCUtil 객체 생성
   }
   
   public int register(Member member) throws SQLException{
      String query = "Insert into member(memberID, name, email, phone, password, gender, point, membergrade, address) values(?,?,?,?,?,?,?,?,?)";
      Object[] param = new Object[] {member.getMemberID(),  member.getName(), member.getEmail(), member.getPhone(), member.getPassword(), member.getGender(), member.getPoint(), member.getMemberGrade(), member.getAddress()};
      jdbcUtil.setSqlAndParameters(query, param);
      
      try {
         int result = jdbcUtil.executeUpdate();
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      } finally {      
         jdbcUtil.commit();
         jdbcUtil.close();   // resource 반환
      }      
      return 0;
   }
   
   /*나중에 나머지 필드도 포함하는 insert, update문 올릴 예정.*/
   public int update(Member member) throws SQLException {
      String query = "update member set password=?, name=?, address=? "
                  + "where memberID=?";
      
      Object[] param = new Object[] {member.getPassword(), member.getName(), member.getAddress(), member.getMemberID()};
      jdbcUtil.setSqlAndParameters(query, param);
      
      try {            
         int result = jdbcUtil.executeUpdate();   // update 문 실행
         return result;
      } catch (Exception ex) {
         jdbcUtil.rollback();
         ex.printStackTrace();
      }
      finally {
         jdbcUtil.commit();
         jdbcUtil.close();   // resource 반환
      }      
      
      return 0;
      
   }

   /*개인 페이지에 개인 정보 출력  _ 대여해주는 책 리스트 정보, 대여 중인 책  리스트 정보  등은 각각 따로 가져오기.*/
   public Member findUser(String memberID) throws SQLException { /* memberId String 타입으로 이후 수정하기!!!!!!!!*/
      String query = "select password, name, point, sellergrade "
               + "from member m left outer join seller s on m.memberID = s.memberID "
               + "where m.memberID = ?";
      
      jdbcUtil.setSqlAndParameters(query, new Object[] {memberID});
      
      try {
         ResultSet rs = jdbcUtil.executeQuery();      // query 실행
         if (rs.next()) {                  // 학생 정보 발견
            Member member = new Member(      // User 객체를 생성하여 학생 정보를 저장
               memberID,
               rs.getString("name"),
               rs.getString("password"),
               rs.getString("email"),
               rs.getString("phone"),
               rs.getInt("gender"),
               rs.getInt("memberGrade"),               
               rs.getInt("sellerGrade"),
               rs.getString("adrress"),
               rs.getInt("point"));
            return member;
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      } finally {
         jdbcUtil.close();      // resource 반환
      }
      
      return null;
      
   }
   
   public int remove(String memberID) throws SQLException {
         String sql = "DELETE FROM MEMBER WHERE memberID = ?";      
           jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});   // JDBCUtil에 delete문과 매개 변수 설정
           
            try {            
               int result = jdbcUtil.executeUpdate();   // delete 문 실행
               return result;
            } catch (Exception ex) {
               jdbcUtil.rollback();
               ex.printStackTrace();
            }
            finally {
               jdbcUtil.commit();
               jdbcUtil.close();   // resource 반환
            }      
            return 0;
         
      }
   /* 해당 회원을 SELLER로 등록*/   
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
            jdbcUtil.close();   // resource 반환
         }      
         return 0;
      }
      
   /* 해당 회원의 대여할려고 올려둔 책 정보 리스트 반환 */
    public List<rentalBook> getRentalBookList(String memberID) throws SQLException {
      String query = "select bookID, bookinfoID, image, explain "
               + "from member m join rentalBook r on m.memberID = r.memberID "
               + "where m.memberID = ?";
      jdbcUtil.setSqlAndParameters(query, new Object[] {memberID});
      
      try {
         ResultSet rs = jdbcUtil.executeQuery();
         List<rentalBook> mRentalBookList = new ArrayList<rentalBook>();
         
         while(rs.next()) {
            rentalBook rbook = new rentalBook(
                  rs.getInt("bookID"),
                  rs.getString("sellerID"),
                  rs.getString("bookinfoID"),
                  rs.getString("image"),
                  rs.getString("explain"),
                  rs.getBoolean("state"),
                  rs.getInt("point"),
                  rs.getInt("condition")
                  );
            mRentalBookList.add(rbook);
         }
         return mRentalBookList;
      }catch(Exception ex) {}
       
       return null;
       
   }

	public boolean existingUser(String memberID) {
		String sql = "SELECT count(*) FROM member WHERE memberid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {memberID});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return false;
	}
   
}
