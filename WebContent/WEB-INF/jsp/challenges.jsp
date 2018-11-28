<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
	<json:array name="challenges" items="${challenges }" var="challenge">
		<json:object>
			<json:property name="id" value="${challenge.id}"/>
    		<json:property name="challenger" value="${challenge.challengerId}"/>
    		<json:property name="challengee" value="${challenge.challengeeId}"/>
    		<json:property name="status" value="${challenge.status}"/>
		</json:object>
	</json:array>
	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>

<html><body>

<c:if test="${empty challenges }" var="noChallenges">
<h1>There is no challenges !</h1>
</c:if>
<c:if test="${not noChallenges }">
<h1>Here are the challenges</h1>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>Challenge</th>
            <th>Challenger</th>
            <th>Challengee</th>
            <th>Status</th>
           </tr>
    <c:forEach items="${challenges}" var="challenge">
        <tr align = center>
            <td>${challenge.id}</td>
            <td>${challenge.challengerId}</td>
            <td>${challenge.challengeeId}</td>
            <td>${challenge.status}</td>
        </tr>
    </c:forEach>
</table>
</br></br></br>

</c:if>

</body></html>