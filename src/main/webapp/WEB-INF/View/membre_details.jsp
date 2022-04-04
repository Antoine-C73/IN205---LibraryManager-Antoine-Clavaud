<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import = "com.ensta.librarymanager.service.LivreService" %>
<%@ page import = "com.ensta.librarymanager.service.MembreService" %>
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
        <h1 class="page-announce-text valign">Fiche membre</h1>
      </div>
      <div class="row">
      <div class="container">
      <h5>D�tails du membre n�${param.id}</h5> <!-- TODO : remplacer 007 par l'id du membre -->
        <div class="row">
          <%! LivreService livreService = LivreService.getInstance(); %>
      	  <%! MembreService membreService = MembreService.getInstance(); %>
	      <form action="/TP3Ensta/membre_details?id=${param.id}" method="post" class="col s12"> <!-- TODO : remplacer idDuMembre par l'id du membre -->
	        <div class="row">
	          <div class="input-field col s4">
	            <input id="nom" type="text" value="<%= membreService.getById(Integer.valueOf(request.getParameter("id"))).getNom() %>" name="nom"> <!-- TODO : remplacer nomDuMembre par le nom du membre -->
	            <label for="nom">Nom</label>
	          </div>
	          <div class="input-field col s4">
	            <input id="prenom" type="text" value="<%= membreService.getById(Integer.valueOf(request.getParameter("id"))).getPrenom() %>" name="prenom"> <!-- TODO : remplacer prenomDuMembre par le pr�nom du membre -->
	            <label for="prenom">Pr�nom</label>
	          </div>
	          <div class="input-field col s4">
	            <select name="abonnement" class="browser-default">
	              <!-- TODO : faire en sorte que l'option correspondant � l'abonnement du membre soit s�lectionn�e par d�faut -->
	              <!-- Pour cela, vous devez rajouter l'attribut selecter sur la balise <option> concern�e -->
	              <option value="BASIC" <%= (membreService.getById(Integer.valueOf(request.getParameter("id"))).getAbonnement().name() == "BASIC") ? " selected" : "" %>> Abonnement BASIC</option>
	              <option value="PREMIUM" <%= (membreService.getById(Integer.valueOf(request.getParameter("id"))).getAbonnement().name() == "PREMIUM") ? " selected" : "" %>>Abonnement PREMIUM</option>
	              <option value="VIP" <%= (membreService.getById(Integer.valueOf(request.getParameter("id"))).getAbonnement().name() == "VIP") ? " selected" : "" %>>Abonnement VIP</option>
	            </select>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s12">
	            <input id="adresse" type="text" value="<%= membreService.getById(Integer.valueOf(request.getParameter("id"))).getAdresse() %>" name="adresse"> <!-- TODO : remplacer adresseDuMembre par l'adresse du membre -->
	            <label for="adresse">Adresse</label>
	          </div>
	        </div>
	        <div class="row">
	          <div class="input-field col s6">
	            <input id="email" type="email" value="<%= membreService.getById(Integer.valueOf(request.getParameter("id"))).getEmail() %>" name="email"> <!-- TODO : remplacer emailDuMembre par l'email du membre -->
	            <label for="email">E-mail</label>
	          </div>
	          <div class="input-field col s6">
	            <input id="telephone" type="tel" value="<%= membreService.getById(Integer.valueOf(request.getParameter("id"))).getTelephone() %>" name="telephone"> <!-- TODO : remplacer telephoneDuMembre par le t�l�phone du membre -->
	            <label for="telephone">T�l�phone</label>
	          </div>
	        </div>
	        <div class="row center">
	          <button class="btn waves-effect waves-light" type="submit">Enregistrer</button>
	          <button class="btn waves-effect waves-light orange" type="reset">Annuler</button>
	        </div>
	      </form>
	      
	      <form action="/TP3Ensta/membre_delete" method="get" class="col s12">
	        <input type="hidden" value="${param.id}" name="id"> <!-- TODO : remplacer idDuMembre par l'id du membre -->
	        <div class="row center">
	          <button class="btn waves-effect waves-light red" type="submit">Supprimer le membre
	            <i class="material-icons right">delete</i>
	          </button>
	        </div>
	      </form>
	    </div>
        <div class="row">
          <div class="col s12">
	        <table class="striped">
              <thead>
                <tr>
                  <th>Livre</th>
                  <th>Date d'emprunt</th>
                  <th>Retour</th>
                </tr>
              </thead>
              <tbody id="results">

                <c:forEach var="current" items="${ListeEmpruntsCurrentMembre}" >
			       <tr>
			         <td><%= livreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdLivre()).getTitre() %>,<em> de <%= livreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdLivre()).getAuteur() %></em></td>
			         <td><%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getNom() %> <%= membreService.getById(((Emprunt) pageContext.getAttribute("current")).getIdMembre()).getPrenom() %></td>
			         <td>${current.dateEmprunt}</td>
			         <td> <a href="/TP3Ensta/emprunt_return?id=${current.id}"><ion-icon class="table-item" name="log-in"></a> </td>
			       </tr>
			    </c:forEach>

				<!-- TODO : parcourir la liste des emprunts en cours pour ce membre et les afficher selon la structure d'exemple ci-dessus -->
              </tbody>
            </table>
          </div>
        </div>
      </div>
      </div>
    </section>
  </main>
  <jsp:include page='footer.jsp'></jsp:include>
</body>
</html>