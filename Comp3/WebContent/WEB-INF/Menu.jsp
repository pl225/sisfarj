<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Menu principal</title>
</head>
<body>

	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>

	<ul>
  		<li><a href="FiliarAssociacao">1- Filiar Associação</a></li>
  		<li><a href="AlterarAssociacao">2- Alterar Associação</a></li>
  		<li><a href="ListarLocaisDeCompeticao">3- Listar Locais de Competição</a></li>
		<li><a href="CadastrarAtleta">4- Cadastrar Atleta</a></li>
		<li><a href="IncluirLocalCompeticao">10- Incluir Local de Competição</a></li>
		<li><a href="ListarAssociacao">12- Listar Associações</a></li>
		<li><a href="AlterarCompeticao">14- Alterar Competição</a></li>
	</ul>

</body>
</html>