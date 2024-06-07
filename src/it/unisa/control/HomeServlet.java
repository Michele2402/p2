package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProdottoDao dao = new ProdottoDao();
		
		ArrayList<ArrayList<ProdottoBean>> categorie = new ArrayList<>();
		String redirectedPage = request.getParameter("page");
		
		try {
			ArrayList<ProdottoBean> PS5 = dao.doRetrieveByPiattaforma("PlayStation 5");
			ArrayList<ProdottoBean> XboxSeries = dao.doRetrieveByPiattaforma("Xbox Series");
			ArrayList<ProdottoBean> Switch = dao.doRetrieveByPiattaforma("Nintendo Switch");
			ArrayList<ProdottoBean> PS4 = dao.doRetrieveByPiattaforma("PlayStation 4");
			ArrayList<ProdottoBean> Xone = dao.doRetrieveByPiattaforma("Xbox One");
			
			categorie.add(PS5);
			categorie.add(XboxSeries);
			categorie.add(Switch);
			categorie.add(PS4);
			categorie.add(Xone);

			request.getSession().setAttribute("categorie", categorie);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		if (isValidPage(redirectedPage)) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/" + redirectedPage);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("");
        }
	}
	
	private boolean isValidPage(String page) {
        // Controlla che il parametro "page" non punti a file sensibili
        if (page == null) {
            return false;
        }
        return !page.equals("META-INF/context.xml") && !page.equals("WEB-INF/web.xml");
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
