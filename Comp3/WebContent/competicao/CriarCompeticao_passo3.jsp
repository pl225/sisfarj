<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Criar Competição</title>
</head>
<body>

	<%
		if (request.getAttribute("erro") != null) {
	%>
	<p><%= request.getAttribute("erro") %></p>
	<%
		}
	%>
	
	<div id="formulario">
	
		<h2>Insira os Dados da Competição</h2>
	
		<form action="CriarCompeticao?passo3=true" method="post">
			
			<label for="nome">Nome</label>
			<input type="text" name="nome">
			
			<br/><br/>
			
			<label for="dataCompeticao">Data</label>
			<input type="date" name="dataCompeticao">
			
			<br/><br/>
			
			<input type="submit" value="Enviar">
			
		</form>
	
	</div>
	
</body>
</html>