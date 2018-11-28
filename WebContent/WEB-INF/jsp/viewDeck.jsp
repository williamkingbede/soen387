<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<json:object>
	<json:object name="deck">
		<json:property name="id" value="${deck.id}"/>
	  	<json:array name="cards" items="${deck.cards}" var="card">
    		<json:object>
      			<json:property name="t" value="${card.type}"/>
      			<json:property name="n" value="${card.name}"/>
    		</json:object>
  		</json:array>
  	</json:object>
  	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>

<html><body>

<c:if test="${empty deck }" var="noDeck">
<h1>You have no deck !</h1>
</c:if>
<c:if test="${not noDeck }">
<h1>Here are your cards</h1>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>Deck</th>
            <th>type</th>
            <th>name</th>
           </tr>
    <c:forEach items="${deck.cards}" var="card">
        <tr align = center>
            <td>${card.deckId}</td>
            <td>${card.type}</td>
            <td>${card.name}</td>
        </tr>
    </c:forEach>
</table>
</br></br></br>
</c:if>

</body></html>