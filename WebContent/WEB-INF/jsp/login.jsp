<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

{
	"status":"${status}",
 	"message":"${message}"
}

<html><body>

<c:if test="${empty user }" var="noUser">
<h1>Login</h1>

</c:if>
<c:if test="${not noUser }">
<h1>Welcome ${user.username}</h1>

<br>
<form method="link" action="ListChallenges">
    <input type="submit" value="See Challenges"/>
</form>

<form method="link" action="ListPlayers">
    <input type="submit" value="See Players"/>
</form>

<form method="link" action="ListGames">
    <input type="submit" value="See Games"/>
</form>

<form method="link" action="MyOpenChallenges">
    <input type="submit" value="My Open Challenges"/>
</form>

<form method="link" action="ViewDeck">
    <input type="submit" value="My Deck"/>
</form>

<form method="link" action="UploadDeck">
    <input type="submit" value="Upload Deck"/>
</form>

<br>
<form method="link" action="Logout">
    <input type="submit" value="Logout"/>
</form>

</c:if>

<c:if test="${empty user }" var="noUser">
	<form action="Login">
	<h2>Sign In</h2>
		Username: <input name="user"/><br/>
		Password: <input name="pass"/><br/>
	<input type="submit"/>
	</form>
</c:if>

</body></html>