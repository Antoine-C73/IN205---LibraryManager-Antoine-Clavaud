<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "com.ensta.librarymanager.service.LivreService" %>
<%@ page import = "com.ensta.librarymanager.service.MembreService" %>
<%@ page import = "com.ensta.librarymanager.service.EmpruntService" %>
<%@ page import = "com.ensta.librarymanager.modele.Emprunt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Library Management</title>
  <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
  <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="assets/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
  <jsp:include page='menu.jsp'></jsp:include>
  <main>
    <section class="content">
      <div class="page-announce valign-wrapper">
        <a href="#" data-activates="slide-out" class="button-collapse valign hide-on-large-only"><i class="material-icons">menu</i></a>
        <h1 class="page-announce-text valign">Retour d'un livre</h1>
      </div>
      <div class="row">
      <div class="container">
        <h5>Sélectionnez le livre à retourner</h5>
        <div class="row">
	      <form action="/TP3Ensta/emprunt_return" method="post" class="col s12">
	        <div class="row">
	          <div class="input-field col s12">
	          <%! LivreService livreService = LivreService.getInstance(); %>
              <%! MembreService membreService = MembreService.getInstance(); %>
              <%! EmpruntService empruntService = EmpruntService.getInstance(); %>
                	<c:if test="${not empty param.id}">
                		<select id="idEmprunt" name="idEmprunt" class="browser-default">                
                			<option value="" disabled>---</option>
                			<c:forEach var="current" items="${ListeEmprunts}">
                				<c:if test="${current.id == param.id}">
                					<option value="${current.id}" selected><%= livreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdLivre()).getTitre() %>, emprunté par <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getNom() %> <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getPrenom() %></option>
                				</c:if>
                				<c:if test="${current.id != param.id}">
                					<option value="${current.id}"><%= livreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdLivre()).getTitre() %>, emprunté par <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getNom() %> <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getPrenom() %></option>
                				</c:if>
                			</c:forEach>
			            </select>             		
                	</c:if>
                	<c:if test="${empty param.id}">
                		<select id="idEmprunt" name="idEmprunt" class="browser-default">
                			<option value="" disabled selected>---</option>
                			<c:forEach var="current" items="${ListeEmprunts}">
                				<option value="${current.id}"><%= livreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdLivre()).getTitre() %>, emprunté par <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getNom() %> <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getPrenom() %></option>
                			</c:forEach>
                		</select>	
              	    </c:if>	            
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Retourner le livre</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	    </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
  <script>
    document.addEventListener('DOMContentLoaded', function() {
	  var elems = document.querySelectorAll('select');
	  var instances = M.FormSelect.init(elems, {});
	});
  </script>
</body>
</html>
