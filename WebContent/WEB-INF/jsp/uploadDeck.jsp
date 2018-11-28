<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

{
	"status":"${status}",
 	"message":"${message}"
}

<html><body>
<h1>Upload Deck</h1>

<br/><br/><br/>

	<form action="UploadDeck">
		Enter deck: <br/><textarea name="deck" rows=5></textarea><br/>
	<input type="submit"/>
	</form>

</body></html>
