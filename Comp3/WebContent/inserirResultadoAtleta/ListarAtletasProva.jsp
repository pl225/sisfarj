<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Atletas Participantes</title>
<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>
	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>
	
	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>

	<h2>Atletas da Prova</h2>

	<table>
		<thead>
			<tr>
				<th>Matrícula</th>
				<th>Nome</th>
				<th>Ações</th>				
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getInt("matriculaAtleta") %></td>
				<td><%= rs.getString("nome") %></td>
				<td><a href="InserirResultadoAtleta?matriculaAtleta=
				<%= rs.getInt("matriculaAtleta") %>">Selecionar</a></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>
	
</body>
</html>