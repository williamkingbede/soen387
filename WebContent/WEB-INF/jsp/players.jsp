<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
	<json:array name="players" items="${players }" var="player">
		<json:object>
			<json:property name="id" value="${player.id}"/>
    		<json:property name="user" value="${player.username}"/>
		</json:object>
	</json:array>
	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>

<html><body>

<c:if test="${empty players }" var="noPlayers">
<h1>There is no players !</h1>
</c:if>
<c:if test="${not noPlayers }">
<h1>Here are the players</h1>


<table width = 50% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>Players Name</th>
            <th></th>
    <c:forEach items="${players}" var="player">
        <tr align = center>
            <td>${player.username}</td>
            <td><a href="<c:url value="ChallengePlayer?challengeeid=${player.id}"/>">challenge player</a></td>
        </tr>
    </c:forEach>
</table>
</br></br></br>

</c:if>

</body></html>
