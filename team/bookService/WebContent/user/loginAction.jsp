<%@page contentType="text/html; charset=euc-kr" %>    
<%
try {

	String userId = request.getParameter("userId");
	String password = request.getParameter("password");
	
/* 	//�𵨿� �α��� �۾��� �����Ѵ�.
	UserManager manager = UserManager.getInstance();
	manager.login(user.getUserId(), user.getPassword());	// manager ��ü ȣ��
//	manager.login(userId, password);

	//���������� �α��� �Ǿ��� ��� ���ǿ� ����� ���̵� ����.
	session.setAttribute("userId", user.getUserId());
 */
 	session.setAttribute("userId", userId);
	//��� �۾� �Ϸ��� �̵��� �������� ����.
	response.sendRedirect("homePage01.jsp");
	
} catch (Exception e) {
	%>
	<!--������ �߻��� ��� alert â�� ���� ���� �������� �̵�. -->
	<script>
		alert("<%= e.getMessage() %>");
		history.back();
	</script>
	<%
}
%>