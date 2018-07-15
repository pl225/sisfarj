<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserir Resultado Atleta</title>
</head>
<body>
	
	<h2>Insira o tempo do atleta de matrícula: <%= request.getAttribute("matriculaAtleta") %></h2>
	
	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>
	
	<form method="post" action="InserirResultadoAtleta?matriculaAtleta=
		<%= request.getAttribute("matriculaAtleta") %>">
				
			<label for="tempo">Tempo hh:mm:ss</label>
			<input type="text" name="tempo">
			<br></br>

		<input type="submit" name="botao" value="Adicionar">
	</form>
	<br>
	<form method="post" action="InserirResultadoAtleta">
		<input type="submit" name="botao" value="Finalizar">
	</form>

</body>
</html>
