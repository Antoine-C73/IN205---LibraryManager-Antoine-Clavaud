package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Abonnement;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.modele.Membre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/membre_details")
public class MembreDetailsServlet extends HttpServlet {

private static final long serialVersionUID = 1L;
	
	private MembreService membreService = MembreService.getInstance();
	private LivreService livreService = LivreService.getInstance();
	private EmpruntService empruntService = EmpruntService.getInstance();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("ListeEmpruntsCurrentMembre", this.empruntService.getListCurrentByMembre(Integer.valueOf(request.getParameter("id"))));
			
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/membre_details.jsp").forward(request, response);		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idUpdate = Integer.valueOf(request.getParameter("id"));
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String adresse = request.getParameter("adresse");
		String email = request.getParameter("email");
		String telephone = request.getParameter("telephone");
		Abonnement abonnement = Abonnement.valueOf(request.getParameter("abonnement"));
		
		try {
			this.membreService.update(new Membre(idUpdate, nom, prenom, adresse, email, telephone, abonnement));
			response.sendRedirect("/TP3Ensta/membre_details?id=" + Integer.toString(idUpdate));
		} catch (ServiceException e) {			
			e.printStackTrace();
			this.doGet(request, response);
			throw new ServletException();
		}				
		
		
	}

}