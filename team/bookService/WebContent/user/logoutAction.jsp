<%@page contentType="text/html; charset=euc-kr" %>
<%
	//���ǿ� ����� ����� ���̵� �� ���� ����
	session.removeAttribute("userId");
	session.invalidate();

	//��� �۾� �Ϸ��� �̵��� �������� ����.
	response.sendRedirect("homePage01.jsp");
%>