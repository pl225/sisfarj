<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Competicao Atleta</title>
<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>

	<h2>Competi��es:</h2>
	
	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Data</th>
				<th>A��es</th>
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("nome") %></td>
				<td><%= rs.getString("dataCompeticao") %></td>
				<td><a href="InserirAtletaCompeticao?dataCompeticao=
				<%= rs.getString("dataCompeticao") %>&endereco=<%= rs.getString("endereco") %>">Selecionar</a></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>

</body>
</html>