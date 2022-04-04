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
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private MembreService membreService = MembreService.getInstance();
	private LivreService livreService = LivreService.getInstance();
	private EmpruntService empruntService = EmpruntService.getInstance();
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setAttribute("ListeLivreDispo", this.livreService.getListDispo());
			request.setAttribute("ListeMembresPeuventEmprunter", this.membreService.getListMembreEmpruntPossible());
			
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp").forward(request, response);		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idMembre = Integer.valueOf(request.getParameter("idMembre"));
		int idLivre = Integer.valueOf(request.getParameter("idLivre"));
		
		try {
			this.empruntService.create(new Emprunt(-8, idMembre, idLivre, LocalDate.now(), null));
			response.sendRedirect("/TP3Ensta/emprunt_list");
		} catch (ServiceException e) {			
			e.printStackTrace();
			this.doGet(request, response);
			throw new ServletException();
		}				
		
		
	}

}
