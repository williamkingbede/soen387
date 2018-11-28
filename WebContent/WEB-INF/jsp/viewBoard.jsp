<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<c:if test="${empty game }" var="noGame">
<json:object> 
  	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
</json:object>
</c:if>
<c:if test="${not noGame }">
<json:object> 

  <json:object name="board">
  
	<json:property name="id" value="${game.id}"/>
  	
  	<json:array name="players">
    	<json:property value="${game.challengerId}"/>
    	<json:property value="${game.challengeeId}"/>
  	</json:array>
  
  	<json:array name="decks">
    	<json:property value="${deck1}"/>
    	<json:property value="${deck2}"/>
  	</json:array>
  	
  	<json:object name="play">
  		<json:object name="${game.challengerId }">
  			<json:property name="status" value="${status1 }"/>
  			<json:property name="handsize" value="${handsize1 }"/>
  			<json:property name="decksize" value="${decksize1 }"/>
  			<json:property name="discardsize" value="${discardsize1 }"/>
  			<json:array name="bench" items="${bench1}" var="card">
    			<json:property value="${card.id}"/>
  			</json:array>
  		</json:object>
  		<json:object name="${game.challengeeId }">
  		  	<json:property name="status" value="${status2 }"/>
  			<json:property name="handsize" value="${handsize2 }"/>
  			<json:property name="decksize" value="${decksize2 }"/>
  			<json:property name="discardsize" value="${discardsize2 }"/>
  			<json:array name="bench" items="${bench2}" var="card">
    			<json:property value="${card.id}"/>
  			</json:array>
  		</json:object>
  	</json:object>
  
  </json:object>
  
  	<json:property name="status" value="${status}"/>
	<json:property name="message" value="${message}"/>
  
</json:object>
</c:if>

<html><body>

<c:if test="${empty game }" var="noGame">
<h1>No Board</h1>
</c:if>
<c:if test="${not noGame }">
<h1>View Board</h1>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>player</th>
            <th>status</th>
            <th>handsize</th>
            <th>decksize</th>
            <th>bench</th>
           </tr>
        <tr align = center>
            <td>${game.challengerId}</td>
            <td>${status1}</td>
            <td>${handsize1}</td>
            <td>${decksize1}</td>
            <td><c:forEach items="${bench1}" var="card">${card.id}${" "}</c:forEach></td>
        </tr>
</table>

</br>

<table width = 75% border = 1 align = center>
         <tr bgcolor = #949494> 
            <th>player</th>
            <th>status</th>
            <th>handsize</th>
            <th>decksize</th>
            <th>bench</th>
           </tr>
        <tr align = center>
            <td>${game.challengeeId}</td>
            <td>${status2}</td>
            <td>${handsize2}</td>
            <td>${decksize2}</td>
            <td><c:forEach items="${bench2}" var="card">${card.id}${" "}</c:forEach></td>
        </tr>
</table>
</br></br></br>

<a href="<c:url value="ViewHand?gameid=${game.id}"/>"><button>View Hand</button></a>
<a href="<c:url value="DrawCard?gameid=${game.id}"/>"><button>Draw Card</button></a>

</c:if>

</br></br></br>

</body></html>