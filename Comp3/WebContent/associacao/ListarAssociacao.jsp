<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listar Associações</title>
	<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>
	
	<h2>Associações</h2>
	
	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Matricula</th>
			</tr>
		</thead>
		<tbody>
			
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("nome") %></td>
				<td><%= rs.getString("matriculaAssociacao") %></td>
			</tr>
			
			<%
				}
			%>
		
		</tbody>
	</table>
	
</body>
</html>