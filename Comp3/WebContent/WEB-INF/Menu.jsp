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
  		<li><a href="IdentificarUsuario?pagina=FiliarAssociacao">1- Filiar Associação</a></li>
  		<li><a href="IdentificarUsuario?pagina=AlterarAssociacao">2- Alterar Associação</a></li>
  		<li><a href="IdentificarUsuario?pagina=ListarLocaisDeCompeticao">3- Listar Locais de Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=CadastrarAtleta">4- Cadastrar Atleta</a></li>
		<li><a href="IdentificarUsuario?pagina=AlterarAtleta">5- Alterar Atleta</a></li>
		<li><a href="IdentificarUsuario?pagina=TransferirAtleta">6- Transferir Atleta</a></li>
		<li><a href="IdentificarUsuario?pagina=InserirAtletaCompeticao">8- Inserir Atleta Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=IncluirLocalCompeticao">10- Incluir Local de Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=AlterarLocalCompeticao">11- Alterar Local de Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=ListarAssociacao">12- Listar Associações</a></li>
		<li><a href="IdentificarUsuario?pagina=CriarCompeticao">13- Criar Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=AlterarCompeticao">14- Alterar Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=ListarCompeticao">16- Listar Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=ListarPontuacaoCompeticao">18- Listar Pontuação Competição</a></li>
		<li><a href="IdentificarUsuario?pagina=ListarPontuacaoFinal">19- Listar Pontuação Final</a></li>
	</ul>

</body>
</html>