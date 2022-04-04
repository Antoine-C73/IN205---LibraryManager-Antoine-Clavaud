package Main;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.service.EmpruntService;
import com.ensta.librarymanager.service.LivreService;
import com.ensta.librarymanager.service.MembreService;

public class Main {

	public static void main(String[] args) {
		LivreService livreService = LivreService.getInstance();
		MembreService membreService = MembreService.getInstance();
		EmpruntService empruntService = EmpruntService.getInstance();
		
		try {
			System.out.println(livreService.getListDispo());
			empruntService.create(new Emprunt())
			System.out.println(membreService.getListMembreEmpruntPossible());
			System.out.println(empruntService.getList());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
