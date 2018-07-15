<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inserir Atleta Competicao</title>
</head>
<body>
	
	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rsProvas = (ResultSetAdapter) request.getAttribute("dados");
		
		ResultSetAdapter rsCompeticao = (ResultSetAdapter) request.getAttribute("dadosCompeticao");
		
		
	%>
	
	<h2>Insira a matricula do Atleta a participar na Competição</h2>
	
	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>
	
	<form method="post" action="InserirAtletaCompeticao">
				
			<label for="numero">Número</label>
			<input type="text" name="numero">
			<br></br>

		<input type="submit" name="botao" value="Inscrever">
	</form>
	<form method="post" action="InserirAtletaCompeticao">
		<br></br>		
		<input type="submit" name="botao" value="Finalizar">
	</form>
	
</body>
</html>