package com.ensta.librarymanager.service;

import java.util.ArrayList;
import java.util.List;

import com.ensta.librarymanager.dao.LivreDao;
import com.ensta.librarymanager.exception.DaoException;
import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.modele.Livre;

public class LivreService implements ILivreService {

	private LivreDao livreDao = LivreDao.getInstance();
	
	private static LivreService livreService;
	
	private LivreService() {}
	
	public static LivreService getInstance() {
		if (livreService == null) {
			livreService = new LivreService();
		}
		return livreService;
	}

	@Override
	public List<Livre> getList() throws ServiceException {
		try {
			return this.livreDao.getList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public List<Livre> getListDispo() throws ServiceException {
		List<Livre> ret = new ArrayList<Livre>();
		
		try {
			for (Livre livre: this.livreDao.getList()) {
				if (EmpruntService.getInstance().isLivreDispo(livre.getId())) {
					ret.add(livre);
				}
			}
			
			return ret;
			
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		} 
	}

	@Override
	public Livre getById(int id) throws ServiceException {
		try {
			return this.livreDao.getById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public int create(Livre livre) throws ServiceException {
		if (livre.getAuteur().isEmpty() || livre.getTitre().isEmpty() || livre.getIsbn().isEmpty()) {
			throw new ServiceException();
		}
		
		try {
			return this.livreDao.create(livre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void update(Livre livre) throws ServiceException {
		if (livre.getAuteur().isEmpty() || livre.getTitre().isEmpty() || livre.getIsbn().isEmpty()) {
			throw new ServiceException();
		}
		
		try {
			this.livreDao.update(livre);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	@Override
	public void delete(int id) throws ServiceException {
		try {
			this.livreDao.delete(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}

	}

	@Override
	public int count() throws ServiceException {
		try {
			return this.livreDao.count();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
