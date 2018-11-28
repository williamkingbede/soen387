<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
	<json:array name="games" items="${games }" var="game">
		<json:object>
			<json:property name="id" value="${game.id}"/>
    		 <json:array name="players">
    			<json:property value="${game.challengerId}"/>
    			<json:property value="${game.challengeeId}"/>
  			</json:array>
		</json:object>
	</json:array>
	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>

<html><body>

<c:if test="${empty games }" var="noGames">
<h1>There are no games !</h1>
</c:if>
<c:if test="${not noGames }">
<h1>Here are the games</h1>


<table width = 50% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>Challenger</th>
            <th>Challengee</th>
            <th></th>
    <c:forEach items="${games }" var="game">
        <tr align = center>
            <td>${game.challengerId}</td>
            <td>${game.challengeeId}</td>
            <td><a href="<c:url value="ViewBoard?gameid=${game.id}"/>">View Board</a></td>
        </tr>
    </c:forEach>
</table>
</br></br></br>

</c:if>

</body></html>
