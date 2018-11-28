<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<html><body>
<c:if test="${empty openChallenges }" var="noOpenChallenges">
<h1>You have no open challenges !</h1>
</c:if>
<c:if test="${not noOpenChallenges }">
<h1>Your Open Challenges</h1>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>Challenge</th>
            <th>Challenger</th>
            <th>Challengee</th>
            <th></th>
           </tr>
    <c:forEach items="${openChallenges }" var="challenge">
        <tr align = center>
            <td>${challenge.id}</td>
            <td>${challenge.challengerId}</td>
            <td>${challenge.challengeeId}</td>
            <td><a href="<c:url value="AcceptChallenge?challengeid=${challenge.id}"/>">Accept</a> or <a href="<c:url value="RefuseChallenge?challengeid=${challenge.id}"/>">Refuse</a></td>
        </tr>
    </c:forEach>
</table>
</br></br></br>
</c:if>

</body></html>