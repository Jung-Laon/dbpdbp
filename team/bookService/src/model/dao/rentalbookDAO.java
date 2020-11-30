package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import model.*;
import model.dao.BookInfoDao;

public class rentalbookDAO {
	private JDBCUtil jdbcUtil = null;
	
	public rentalbookDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil ��ü ����
	}
	
	
	/*rentalBook ��ü�� DAO _ retalBook insert, update, remove*/
	public int insert(rentalBook book) throws SQLException { 
		String sql = "Insert into rentalbook(bookID, memberID, bookInfoId, image, explain, point, condition, state)" + 
		 		"values(seq_bookID.NEXTVAL,?,?,?,?,?,?,?)";      
	      
		Object[] param = new Object[] {book.getSellerID(), book.getBookInfoID(),book.getImage(), 
	                       book.getExplain(), book.getPoint(), book.getCondition(), book.getState()};            
	       
		 jdbcUtil.setSqlAndParameters(sql, param);
	 
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
	   
	   public int update(rentalBook book) throws SQLException {
	      String sql = "UPDATE rentalBook "
	               + "SET memberID = ?, bookInfoId = ?, image = ?, explain = ?, point = ?, condition = ? "
	               + "WHERE bookID = ?";
	      Object[] param = new Object[] {book.getSellerID(), book.getBookInfoID(), 
	            book.getImage(), book.getExplain(), book.getPoint(), book.getCondition(), book.getBookID()};   
	      jdbcUtil.setSqlAndParameters(sql, param);   // JDBCUtil�� update���� �Ű� ���� ����
	         
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

	   public int remove(int bookID) throws SQLException {
		   
         String sql = "DELETE FROM RENTALBOOK WHERE bookID = ?";      
         jdbcUtil.setSqlAndParameters(sql, new Object[] {bookID});   // JDBCUtil�� delete���� �Ű� ���� ����
         
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
	   
	   /* rentalBook�� �̿� �Լ�_ å  �뿩�ϱ�� �ݳ��ϱ�� (retalInfo insert�� rentalBook _ state update ��.*/

	   //rentalBook�� �ִ� ������ bookID�� ã�Ƽ� ��ü ���·� ��ȯ.
		public rentalBook findRentBook(int bookID) throws SQLException{
			String query = "select r.memberID, r.bookInfoID, image, explain, state, point, condition, bookname "
					+	"from rentalBook r join bookinfo b on r.bookinfoid = b.bookinfoid where bookID = ?";
			
			jdbcUtil.setSqlAndParameters(query, new Object[] {bookID});
			
			try {
				ResultSet rs = jdbcUtil.executeQuery();		// query ����
				if (rs.next()) {						
					rentalBook rtbook = new rentalBook (		
						bookID,
						rs.getString("memberID"),
						rs.getString("bookinfoID"),
						rs.getString("image"),
						rs.getString("explain"),
						rs.getInt("state"),					
						rs.getInt("point"),
						rs.getInt("condition"),
						rs.getString("bookname"));
					return rtbook;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			
			return null;
		}
		
		//�뿩 ������ ����ִ� RentalInfo�� ���ο� �뿩������ ����.
		public int insertRentalInfo(int bookID, String memberID) throws SQLException{
			rentalBook rtBook = findRentBook(bookID);
			
			Calendar rentalD = Calendar.getInstance();
			rentalD.setTime(new Date());
			
			Calendar returnD = Calendar.getInstance();
			returnD.setTime(new Date());
			returnD.add(Calendar.DATE, 14);
			
			//rentalInfo insert
			String query1 = "Insert into rentalInfo values(seq_rentalID.NEXTVAL, ?, ?, ?, ?, ?, 1)";
			Object[] param1 = new Object[] { rentalD, returnD, rtBook.getSellerID(), memberID, bookID };
			
			try {	
				jdbcUtil.setSqlAndParameters(query1, param1);
				int result = jdbcUtil.executeUpdate();	
				return result;
			} catch (Exception ex) {
				jdbcUtil.rollback();
				ex.printStackTrace();
			} finally {		
				jdbcUtil.commit();
				jdbcUtil.close();	// resource ��ȯ
			}		
			return 0;
			
		}
		
		// RentalBook�� �뿩���¸� true�� update
		public int updateRentalBook_state(int bookID) throws SQLException{
			String query = "Update rentalBook set state = 1 where bookID = ?";
			Object[] param = new Object[] { bookID };
			
			try {	
				jdbcUtil.setSqlAndParameters(query, param);
				int result = jdbcUtil.executeUpdate();	
				return result;
			} catch (Exception ex) {
				jdbcUtil.rollback();
				ex.printStackTrace();
			} finally {		
				jdbcUtil.commit();
				jdbcUtil.close();	// resource ��ȯ
			}		
			return 0;
		}
		
		//rentBook �뿩�ϱ�. : 1.rentalInfo�� �� ���ڵ� �߰�. 2. rentalBook�� ���¸� true�� �ٲ�. 3. å ������ rentalCnt�� 1 �߰�.
		public void rentBook(int bookID, String memberID) throws SQLException{
			int insertR = insertRentalInfo(bookID, memberID);
			BookInfoDao bookInfoDao = new BookInfoDao();
			
			if(insertR != 0) {
				int updateStateR = updateRentalBook_state(bookID);
				if(updateStateR != 0) {
					int plusCntR = bookInfoDao.plusRentalCnt(bookID);
					if(plusCntR == 0) {
						System.out.println("ERROR! update plus Bookinfo's State Fail!");
					}
				} else {
					System.out.println("ERROR! update RentalBook State Fail!");
				}
			} else {
				System.out.println("ERROR! insert RentalInfo Fail!");
			}
		}
		
		//rentBook �ݳ��ϱ�. : rentalBook�� ���¸� false�� �ٲ�. + ���⿡  rentalInfo�� ���� �ٲ��ִ� �۾� �ʿ�.
		public int returnBook(rentalInfo rInfo) throws SQLException {
			
			String query1 = "Update rentalBook set state = 0 where bookID = ?";
			Object[] param1 = new Object[] { rInfo.getBookID() };
			
			String query2 = "Update rentalInfo set state = 0 where rentalID = ?";
			Object[] param2 = new Object[] { rInfo.getRentalID() };
			
			try {	
				jdbcUtil.setSqlAndParameters(query1, param1);
				int result1 = jdbcUtil.executeUpdate();	
				if(result1 != 1) {
					throw new Exception();
				}
				
				jdbcUtil.setSqlAndParameters(query2, param2);
				int result2 = jdbcUtil.executeUpdate();	
				if(result2 != 1) {
					throw new Exception();
				}
				
				return 1;
			} catch (Exception ex) {
				jdbcUtil.rollback();
				ex.printStackTrace();
			} finally {		
				jdbcUtil.commit();
				jdbcUtil.close();	// resource ��ȯ
			}		
			return 0;
		}


		public rentalInfo findRentInfo(int bookID) {
			
			String query = "select rentalid, sellerid, rentalerid, rentalDate, returnDate, bookname, r.point as point, i.state as state " + 
	          		"from rentalBook r inner join bookinfo b on b.bookinfoID = r.bookinfoID " + 
	          		"inner join rentalInfo i on i.bookid = r.bookid " +
	          		"where i.bookid = ? ";
			jdbcUtil.setSqlAndParameters(query, new Object[] {bookID});
			
			try {
				ResultSet rs = jdbcUtil.executeQuery();		// query ����
				if (rs.next()) {						
					rentalInfo rInfo = new rentalInfo (		
						rs.getInt("rentalid"),
						bookID,
						rs.getString("sellerid"),
						rs.getString("rentalerid"),
						rs.getDate("rentalDate"),
						rs.getDate("returnDate"),
						rs.getString("bookname"),
						rs.getInt("point"),
						rs.getInt("state")
					);
					return rInfo;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				jdbcUtil.close();		// resource ��ȯ
			}
			
			return null;
		}


		public boolean existingBookInfo(String bookInfoID) {
			String sql = "SELECT count(*) FROM bookinfo WHERE bookinfoid=?";      
			jdbcUtil.setSqlAndParameters(sql, new Object[] {bookInfoID});	// JDBCUtil�� query���� �Ű� ���� ����

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
