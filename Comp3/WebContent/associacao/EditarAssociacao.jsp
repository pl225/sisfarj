<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ</title>
</head>
<body>

	<%@ page import = "java.sql.ResultSet" %>

	<%
		ResultSet rs = (ResultSet) request.getAttribute("dados");
		rs.next();
	%>

	<h2>Edite as informações da Associação <%= rs.getString("nome") %></h2>
	
	<form action="AlterarAssociacao?matriculaAssociacao=<%= rs.getInt("matriculaAssociacao") %>" method="post">
		
		<label for="numeroOficio">Nº do ofício</label>
		<input type="text" name="numeroOficio" value="<%= rs.getString("numeroOficio") %>">
		
		<br/><br/>
		
		<label for="dataOficio">Data do ofício</label>
		<input type="date" name="dataOficio" value="<%= rs.getTimestamp("dataOficio").toString().split(" ")[0] %>">
		
		<br/><br/>
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" value="<%= rs.getString("nome") %>">
		
		<br/><br/>
		
		<label for="sigla">Sigla</label>
		<input type="text" name="sigla" value="<%= rs.getString("sigla") %>">
		
		<br/><br/>
		
		<input type="submit" value="Enviar">
		
	</form>

</body>
</html>