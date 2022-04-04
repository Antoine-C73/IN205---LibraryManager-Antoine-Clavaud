package com.ensta.librarymanager.servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Livre;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private MembreService membreService = MembreService.getInstance();
	private LivreService livreService = LivreService.getInstance();
	private EmpruntService empruntService = EmpruntService.getInstance();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/livre_add.jsp").forward(request, response);		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
		String auteur = request.getParameter("auteur");
		String isbn = request.getParameter("isbn");		
		
		try {
			int id = this.livreService.create(new Livre(-8, titre, auteur, isbn));
			response.sendRedirect("/TP3Ensta/livre_details?id=" + Integer.toString(id));
		} catch (ServiceException e) {			
			e.printStackTrace();
			this.doGet(request, response);
			throw new ServletException();
		}				
		
		
	}

}