<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Incluir Local de Competição</title>
	<link rel="stylesheet" type="text/css" href="css/formulario.css">
</head>
<body>
	
	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>
	
	<%
		if (request.getAttribute("erro") != null) {
	%>
	<p><%= request.getAttribute("erro") %></p>
	<%
		}
	%>
	
	<div id="formulario">
	
		<h2>Edite os Dados do Local de Competição <%= rs.getString("nome") %></h2>
	
		<form action="AlterarLocalCompeticao" method="post">
			
			<input type="hidden" name="endereco" value="<%= rs.getString("endereco") %>">
			
			<label for="nome">Nome do Local</label>
			<input type="text" name="nome" value="<%= rs.getString("nome") %>">
			
			<br/><br/>
			
			<label for="endereco">Endereço</label>
			<input type="text" name="novoEndereco" value="<%= rs.getString("endereco") %>">
			
			<br/><br/>
			
			<fieldset>
			<legend>Piscinas</legend>
			<label for="piscina25">25 Metros</label>
			<%
				if (rs.getString("piscina25").equals("T")) {
			%>
			<input type="checkbox" name="piscina25" value="T" checked>
			<%
				}
				else{
			%>
			<input type="checkbox" name="piscina25" value="T">
			<%
				}
			%>
			<br>
			<label for="piscina25">50 Metros</label>
				<%
				if (rs.getString("piscina50").equals("T")) {
			%>
			<input type="checkbox" name="piscina50" value="T" checked>
			<%
				}
				else{
			%>
			<input type="checkbox" name="piscina50" value="T">
			<%
				}
			%>
			
			</fieldset>
			
			<br/><br/>
			
			<input type="submit" value="Editar">
			
		</form>
	
	</div>
	
</body>
</html>