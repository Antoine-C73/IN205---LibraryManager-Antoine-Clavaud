package com.ensta.librarymanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.persistence.ConnectionManager;

public class EmpruntDao implements IEmpruntDao {

	private static EmpruntDao empruntDao;
	
	private EmpruntDao() {}
	
	public static EmpruntDao getInstance() {
		if (empruntDao == null) {
			empruntDao = new EmpruntDao();
		}
		return empruntDao;
	}
	

	@Override
	public List<Emprunt> getList() throws DaoException {		
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour	FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;");
				ResultSet rs = pstmt.executeQuery();
			)
			
		{
			List<Emprunt> ret = new ArrayList<Emprunt>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				Date dRetour = rs.getDate("dateRetour");
				LocalDate dateRetour = (dRetour == null) ? null : dRetour.toLocalDate();
				
				ret.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}
			
			return ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour	FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;");
				ResultSet rs = pstmt.executeQuery();
			)
			
		{
			List<Emprunt> ret = new ArrayList<Emprunt>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				int idLivre = rs.getInt("idLivre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				Date dRetour = rs.getDate("dateRetour");
				LocalDate dateRetour = (dRetour == null) ? null : dRetour.toLocalDate();
				
				ret.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}
			
			return ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;");
			)
			
		{	
			pstmt.setInt(1, idMembre);
			ResultSet rs = pstmt.executeQuery();
			
			List<Emprunt> ret = new ArrayList<Emprunt>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idLivre = rs.getInt("idLivre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				Date dRetour = rs.getDate("dateRetour");
				LocalDate dateRetour = (dRetour == null) ? null : dRetour.toLocalDate();
				
				ret.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}
			
			return ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;");
			)
			
		{	
			pstmt.setInt(1, idLivre);
			ResultSet rs = pstmt.executeQuery();
			
			List<Emprunt> ret = new ArrayList<Emprunt>();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idMembre = rs.getInt("idMembre");
				LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
				Date dRetour = rs.getDate("dateRetour");
				LocalDate dateRetour = (dRetour == null) ? null : dRetour.toLocalDate();
				
				ret.add(new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour));
			}
			
			return ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	@Override
	public Emprunt getById(int id) throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt,	dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;");
			)
			
		{	
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			int idLivre = rs.getInt("idLivre");
			int idMembre = rs.getInt("idMembre");
			LocalDate dateEmprunt = rs.getDate("dateEmprunt").toLocalDate();
			Date dRetour = rs.getDate("dateRetour");
			LocalDate dateRetour = (dRetour == null) ? null : dRetour.toLocalDate();
				
			return new Emprunt(id, idMembre, idLivre, dateEmprunt, dateRetour);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		
	}

	@Override
	public int create(Emprunt emprunt) throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);");
			)
			
		{	
			pstmt.setInt(1, emprunt.getIdMembre());
			pstmt.setInt(2, emprunt.getIdLivre());
			pstmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, (emprunt.getDateRetour() == null) ? null : java.sql.Date.valueOf(emprunt.getDateRetour()));
			
			return pstmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
		

	}

	@Override
	public void update(Emprunt emprunt) throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?;");
			)
			
		{	
			pstmt.setInt(1, emprunt.getIdMembre());
			pstmt.setInt(2, emprunt.getIdLivre());
			pstmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt()));
			pstmt.setDate(4, (emprunt.getDateRetour() == null) ? null : java.sql.Date.valueOf(emprunt.getDateRetour()));
			pstmt.setInt(5, emprunt.getId());
			
			pstmt.executeUpdate();			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	@Override
	public int count() throws DaoException {
		try (
				Connection conn = ConnectionManager.getConnection();			
				PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(id) AS count FROM emprunt;");				
				ResultSet res = pstmt.executeQuery();
			)
		
		{			
			res.next();
			return res.getInt(1);
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

}
