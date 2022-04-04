package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.MembreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Membre;

public class MembreService implements IMembreService{

	private static MembreService membreService;

	private MembreDao membreDao = MembreDao.getInstance();
	
	private MembreService() {}
	
	public static MembreService getInstance() {
		if (membreService == null) {
			membreService = new MembreService();
		}
		return membreService;
	}

	@Override
	public List<Membre> getList() throws ServiceException {
		try {
			return this.membreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
		List<Membre> ret = new ArrayList<Membre>();
		
		try {
			for (Membre membre: this.membreDao.getList()) {
				if (EmpruntService.getInstance().isEmpruntPossible(membre)) {
					ret.add(membre);
				}
			}
			
			return ret;
			
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public Membre getById(int id) throws ServiceException {
		try {
			return this.membreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(Membre membre)
			throws ServiceException {
		
		if (membre.getNom() == "" || membre.getNom() == "") {
			throw new ServiceException();
		}
		
		membre.setNom(membre.getNom().toUpperCase());
		
		try {
			return this.membreDao.create(membre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Membre membre) throws ServiceException {		
		if (membre.getNom() == "" || membre.getNom() == "") {
			throw new ServiceException();
		}
		
		membre.setNom(membre.getNom().toUpperCase());
		
		try {
			this.membreDao.update(membre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.membreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.membreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
