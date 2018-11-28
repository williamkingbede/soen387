<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>


<json:object>
	  <json:array name="hand" items="${hand}" var="card">
    		<json:property value="${card.id}"/>
  	</json:array>
  	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>

<html><body>

<c:if test="${empty hand }" var="noHand">
<h1>Your hand is empty !</h1>
</c:if>
<c:if test="${not noHand }">
<h1>Here are your cards in hand</h1>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>card</th>
            <th>type</th>
            <th>name</th>
           </tr>
    <c:forEach items="${hand}" var="card">
        <tr align = center>
            <td>${card.id}</td>
            <td>${card.type}</td>
            <td>${card.name}</td>
        </tr>
    </c:forEach>
</table>
</br></br></br>
</c:if>

</body></html>