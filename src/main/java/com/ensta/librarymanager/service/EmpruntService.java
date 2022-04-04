package com.ensta.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.dao.EmpruntDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Emprunt;
import com.ensta.librarymanager.modele.Membre;

public class EmpruntService implements IEmpruntService {
	
	private EmpruntDao empruntDao = EmpruntDao.getInstance();

	private static EmpruntService empruntService;
	
	private EmpruntService() {}
	
	public static EmpruntService getInstance() {
		if (empruntService == null) {
			empruntService = new EmpruntService();
		}
		return empruntService;
	}
	

	@Override
	public List<Emprunt> getList() throws ServiceException {
		try {
			return this.empruntDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrent() throws ServiceException {
		try {
			return this.empruntDao.getListCurrent();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByMembre(idMembre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByLivre(idLivre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Emprunt getById(int id) throws ServiceException {
		try {
			return this.empruntDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(Emprunt emprunt) throws ServiceException {
		try {
			return this.empruntDao.create(emprunt);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void returnBook(int id) throws ServiceException {
		try {
			Emprunt e = this.empruntDao.getById(id);
			e.setDateRetour(LocalDate.now());
			this.empruntDao.update(e);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.empruntDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
		try {
			return this.empruntDao.getListCurrentByLivre(idLivre).size() == 0; 
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
		try {
			switch(membre.getAbonnement()) {
			case BASIC:
				return this.empruntDao.getListCurrentByMembre(membre.getId()).size() < 2;
			case PREMIUM:
				return this.empruntDao.getListCurrentByMembre(membre.getId()).size() < 5;
			case VIP:
				return this.empruntDao.getListCurrentByMembre(membre.getId()).size() < 20;
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return false;
	}

}
