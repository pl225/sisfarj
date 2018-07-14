<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SISFARJ</title>
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>
	
	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>
	<h2>Edite as informações do Atleta <%= rs.getString("nome") %></h2>
	
	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>	
	
	<form action="AlterarAtleta?matriculaAtleta=<%= rs.getInt("matriculaAtleta") %>" method="post">
		
		<label for="numero">Número</label>
		<input type="text" name="numero" value="<%= rs.getString("numeroOficio") %>">
		
		<br/><br/>
		
		<label for="oficio">Data do ofício</label>
		<input type="date" name="oficio" value="<%= rs.getTimestamp("dataOficio").toString().split(" ")[0] %>">
		
		<br/><br/>
		
		<label for="nome">Nome</label>
		<input type="text" name="nome" value="<%= rs.getString("nome") %>">
		
		<br/><br/>
		
		<label for="entrada">Data de entrada</label>
		<input type="date" name="entrada" value="<%= rs.getTimestamp("dataEntrada").toString().split(" ")[0] %>">
		
		<br/><br/>
		
		<input type="submit" value="Enviar">
		
	</form>

</body>
</html>