<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user Info Page</title>
<style>
	body, div, span, p, a, font, ul, li, fieldset, form, legend, table {
		margin : 0;
		padding : 0;
		border : 0;
		line-height : 130%;
		border-radius: 10px 10px 10px 10px ;
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
		border-radius: 10px 10px 10px 10px ;
		margin-bottom : 10px;
		margin-top : 10px;
		background-color : #d5e3d5;
		color : #FFFFFF;		
		font-weight : bolder;
	}
	
	.main_searchForm {
		float: left;
	    margin-top: 25px;
	    margin-left: -20px;
	    margin-right : 5px;
	    height: 50px;
	}
	#container {
		border-radius: 10px 10px 10px 10px ;
		postion : relative;
		width : 1100px;
		margin: 0 auto 0;
		padding-top : 25px;
		padding-bottom: 50px;
		min-height : 600px;
		background-color:#bbd4b8;
	}
	.main {
		position: relative;
	    z-index: 5;
	    width: 100%;
	    padding-bottom: 0;
	}
	
	.clearfix{
		display : block;
	}
	
	.section {
		margin-left : 5px;
		margin-right : 5px;
		margin-bottom: 9px;
	    zoom: 1;
	    position: relative;
	    z-index: 2;
	    display : block;
	}
	
	#userImg {
		background-color:#d5e3d5;
		margin : 5px;
		position: relative;
		float : left;
		width : 250px;
		min-height : 400px;
		overflow: hidden;
		color : #FFFFFF;
		font-weight : bolder;
		padding : 5px;
		padding-left : 10px;
		padding-top : 10px;
	}
	#userBookList {
		background-color:#FFFFFF;
		margin : 5px;
		float : left;
		width : 800px;
		min-height : 350px;
		overflow: hidden;
	}
	#lendBookList {
		background-color: #d5e3d5;
		min-height : 175px;
		color : #FFFFFF;
		font-weight : bolder;
		margin : 15px;
		padding : 5px;
		padding-left : 10px;
		padding-top : 10px;
	}
	#borrowBookList {
		background-color:#d5e3d5;
		color : #FFFFFF;
		min-height : 175px;
		font-weight : bolder;
		margin : 15px;
		padding : 5px;
		padding-left : 10px;
		padding-top : 10px;
	}
	#userInfo {
		width : 1050px;
		min-height : 200px;
		background-color:#FFFFFF;
		margin-left : 20px;
		margin-right : 10px;
		color : #FFFFFF;
		font-weight : bolder;
	}
	
	.frame1 {
		min-height : 440px;
		margin : 0 auto;
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
</style>
</head>
<body>
	<div id="wrapper">
		<header id="header">
			<!-- topBar : 로그인이랑 공지사항 같은데로 바로가기 등(?) 임의로 넣음.  -->
			<div class="topBar_wrap">
				<div class="topBar">
					<div class="menu">
						<a href=".1">공지사항</a>
						<span> | </span>
						<a href=".2">즐겨찾기</a>
					</div>
				</div>
			</div>
			<!--  로고랑 search 바 있는 곳. -->
			<div class="header_wrap">
				<div class="logo" style="margin-top : 25px; float : left; width : 300px">
					<h3><a href="homePage01.jsp">로고자리</a></h3>
				</div>
				<form name="main_search" class="main_searchForm">
				<!-- form에 action이랑 method 나중에 넣기 -->
					<fieldset>
						<legend>통합검색</legend>
						구분 : 
						<select id="stype" name="stype" title="상세검색" style="width:76px">
							<option value="all1" selected >전체</option>
							<option value="subject"  >제목</option>
							<option value="intro"  >작품소개</option>
							<option value="content"  >작품내용</option>
							<option  value="member" >작가</option>
						</select> &nbsp;
						장르 : 
						<select id="stype_g" name="stype_g" title="장르검색" style="width:76px">
							<option value="all2" selected >전체</option>
							<option value="action"  > 액션</option>
							<option value="fantasy"  > 판타지 </option>
							<option value="romance"  > 로맨스 </option>
							<option  value="comic" > 코믹 </option>
							<option value="etc" > 기타 등등 </option>
						</select>
						<input tpye="text" id="search_text" name="search_kw" title = "검색어 입력"
						size="20" class="inputText">
						<input type="button" value="검색">
					</fieldset>
				</form>
				<div class = "myinfo">
					<%if (session.getAttribute("userId") == null) { %>
				    	<a href="loginForm.jsp" style="padding-left : 80px;">로그인 </a>
				    <% } else { %>
				    	<a href="<c:url value='/user/myPage'/>" style="padding-left : 50px;"><%= session.getAttribute("userId") %> 님  정보</a>
				    	<span> | </span>
				    	<a href="<c:url value='/user/logout'/>"> 로그아웃</a>
					<% } %>
				</div>
			</div>
		</header>
		<div id="container" class="main clearfix">
			<div class="main_content">
				<section class="section">
					<div class = "frame1">
						<div id="userImg">
							 유저 대표 이미지<p>
							 <img src="./images/쏼라쏼라" alt="기본 이미지"> 
						 </div>
						<div id="userBookList">				
							<div id="lendBookList"> 
								대여하는 책
								<table>
									 <tr>
										<c:forEach var="rbook" items="${rBookList}">  			  	
							  		 		<td align="center"> ${rbook.bookname} , </td>
							  		 		<!-- bookname에 팝업 -->
							  			</c:forEach>
							  		</tr>
							  	</table> 
							</div>
							<div id="borrowBookList">
								대여 중인 책
								<table>
									 <tr>
										<c:forEach var="ibook" items="${rInfoList}">  			  	
							  		 		<td align="center"> ${ibook.bookname} </td>
							  		 		<!-- bookname에 팝업 -->
							  			</c:forEach>
							  		</tr>
							  	</table> 
							</div>
						</div>
					</div>
					<div id="userInfo" style="colocr : black;"> 
						<p>개인 정보</p>
						<p>
						이름 : ${curMember.name} </br>
						이메일 : ${curMember.email}
						</p>
					</div>
				</section>
			</div>
		</div>
		<footer id="footer">
			<div class="footer_area">
				<div class="list" >
					<ul style="width:100%" >
						<li> DBP 팀 프로젝트 </li>
						<li> 도서 관련 웹사이트 </li>
						<li> 목표 : 끝나고 다같이 맛있는 점심 사먹기 </li>
					</ul>
				</div>
			</div>
		</footer>
		
	</div>
</body>
</html>	
