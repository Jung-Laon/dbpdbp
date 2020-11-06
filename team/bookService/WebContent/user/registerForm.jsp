<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ���� </title>
<script>
function userCreate() {
	if (registerForm.userId.value == "") {
		alert("����� ID�� �Է��Ͻʽÿ�.");
		form.userId.focus();
		return false;
	} 
	if (registerForm.password.value == "") {
		alert("��й�ȣ�� �Է��Ͻʽÿ�.");
		form.password.focus();
		return false;
	}
	if (registerForm.password.value != form.password2.value) {
		alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		form.name.focus();
		return false;
	}
	if (registerForm.name.value == "") {
		alert("�̸��� �Է��Ͻʽÿ�.");
		form.name.focus();
		return false;
	}
	if (registerForm.area.value == "") {
		alert("�������� �Է��Ͻʽÿ�.");
		form.name.focus();
		return false;
	}
	var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	if(emailExp.test(form.email.value)==false) {
		alert("�̸��� ������ �ùٸ��� �ʽ��ϴ�.");
		form.email.focus();
		return false;
	}
	var phoneExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
	if(phoneExp.test(form.phone.value)==false) {
		alert("��ȭ��ȣ ������ �ùٸ��� �ʽ��ϴ�.");
		form.phone.focus();
		return false;
	}
	form.submit();
}

function cancel(targetUri) {
	form.action = targetUri;
	form.submit();
}
</script>
<style>
	body, div, span, p, a, font, ul, li, fieldset, form, legend, table {
		margin : 0;
		padding : 0;
		line-height : 130%;
	}
	
	div { display : block; }
	
	ul {
		margin-block-start: 1em;
	    margin-block-end: 1em;
	    margin-inline-start: 0px;
	    margin-inline-end: 0px;
	    padding-inline-start: 40px;
	}
	
	.topBar_wrap {
		position : relative;
		width : 100%;
		border-bottom : 1px solid gray;
	}
	.topBar {
		height : 30px;
		z-index : 100;
		width : 1100px;
		margin : 0 auto;
	}
	.menu {
		float : right;
		margin-top : 8px;
		padding-right : 20px;
		font-size : 11px;
		display : inline-block;
	}
	
	.header_wrap {
		width : 1100px;
		margin : 0 auto;
		overflow : hidden;
		position : relative;
		background-color:#FFFFE0;
	}
	.main_searchForm {
		float: left;
	    margin-top: 25px;
	    margin-left: -20px;
	    margin-right : 5px;
	    height: 50px;
	}
	.main_searchForm fieldset {
		border : 0;
	}
	#container {
		width : 1100px;
		margin: 0 auto 0;
		padding-top : 25px;
		padding-bottom: 50px;
		min-height : 600px;
		background-color:#FFB6C1
	}
	.main {
	    z-index: 5;
	    width: 100%;
	    padding-bottom: 0;
	}
	
	.clearfix{
		display : block;
	}
	
	.section {
		margin-bottom: 9px;
	    zoom: 1;
	    z-index: 2;
	    display : block;
	}

	.list ul {
		list-style:none;
	    margin:0;
	    padding:0;
	    text-align:center
	    
	}
	.list li {
		list-style:none;
		margin: 0 0 5px 5px;
	    padding: 0 0 5px 5px;
	    border : 0;
	    float: left;
	}
	.list {
	    position: relative;
	    margin: 0 auto;
	    display: inline-block;
	    height: 40px;
	    margin-bottom: 16px;
	    padding: 15px 0 15px 0;
	    overflow: hidden;
	}
	.footer_area {
		text-align: center; 
	}
	
	.myinfo {
		float : right;
		padding-top : 50px;
		margin : 0 auto;
		width : 250px;
	}
	#registerForm {
		margin : 20px;
	}
	
	#registerForm fieldset, p {
		padding : 10px;
		margin : 10px;
	}
</style>	
</head>
<body>
	<div id="wrapper">
		<header id="header">
			<!-- topBar : �α����̶� �������� �������� �ٷΰ��� ��(?) ���Ƿ� ����.  -->
			<div class="topBar_wrap">
				<div class="topBar">
					<div class="menu">
						<a href=".1">��������</a>
						<span> | </span>
						<a href=".2">���ã��</a>
					</div>
				</div>
			</div>
			<!--  �ΰ�� search �� �ִ� ��. -->
			<div class="header_wrap">
				<div class="logo" style="margin-top : 25px; float : left; width : 300px">
					<h3><a href="homePage01.jsp">�ΰ��ڸ�</a></h3>
				</div>
				<form name="main_search" class="main_searchForm">
				<!-- form�� action�̶� method ���߿� �ֱ� -->
					<fieldset>
						<legend>���հ˻�</legend>
						���� : 
						<select id="stype" name="stype" title="�󼼰˻�" style="width:76px">
							<option value="all1" selected >��ü</option>
							<option value="subject"  >����</option>
							<option value="intro"  >��ǰ�Ұ�</option>
							<option value="content"  >��ǰ����</option>
							<option  value="member" >�۰�</option>
						</select> &nbsp;
						�帣 : 
						<select id="stype_g" name="stype_g" title="�帣�˻�" style="width:76px">
							<option value="all2" selected >��ü</option>
							<option value="action"  > �׼�</option>
							<option value="fantasy"  > ��Ÿ�� </option>
							<option value="romance"  > �θǽ� </option>
							<option  value="comic" > �ڹ� </option>
							<option value="etc" > ��Ÿ ��� </option>
						</select>
						<input tpye="text" id="search_text" name="search_kw" title = "�˻��� �Է�"
						size="20" class="inputText">
						<input type="button" value="�˻�">
					</fieldset>
				</form>
				<div class = "myinfo">
					<%if (session.getAttribute("userId") == null) { %>
				    	<a href="loginForm.jsp" style="padding-left : 80px;">�α��� </a>
				    <% } else { %>
				    	<a href="userInfoPage.jsp" style="padding-left : 50px;"><%= session.getAttribute("userId") %> ��  ����</a>
				    	<span> | </span>
				    	<a href="logoutAction.jsp"> �α׾ƿ�</a>
					<% } %>
				</div>
			</div>
		</header>
		<div id="container" class="main clearfix">
			<div class="main_content">
				<section class="section">
					<div id = "registerArea">
						<form id = "registerForm" method = "POST" action="<c:url value='/user/register'/>">
						<fieldset> <h5>ȸ������</h5> <p>
							���̵�* : <input type="text" style="width: 540; " name="userId"> <p>
							��й�ȣ* : <input type="password" style="width: 540" name="password"> <p>
							��й�ȣ Ȯ��* : <input type="password" style="width: 540" name="password2"> <p>
							�̸�* : <input type="text" style="width: 540" name="name" > <p>
							�̸��� : <input type="text" style="width: 540" name="email" > <p>
							��ȭ��ȣ : <input type="text" style="width: 540" name="phone" > <p>
							������* : <input type="text" style="width: 540" name="area" > <p>
							<!-- ���� ���� �κ� ���� �ð��� ���� ��� �� �ڼ��� ����� ����.-->
							<p>
							<input type="button" style="postion : relative;" value="ȸ������" onClick="userCreate()">
							<input type="button" style="postion : relative;" value="���" onClick="cancel('<c:url value='/home' />')">							
						</fieldset>
						</form>
					</div>
				</section>
			</div>
		</div>
		<footer id="footer">
			<div class="footer_area">
				<div class="list" >
					<ul style="width:100%" >
						<li> DBP �� ������Ʈ </li>
						<li> ���� ���� ������Ʈ </li>
						<li> ��ǥ : ������ �ٰ��� ���ִ� ���� ��Ա� </li>
					</ul>
				</div>
			</div>
		</footer>
		
	</div>

</body>
</html>