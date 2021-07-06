package servlet;

import db.PropertiesCreator;
import model.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CompaniesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PsqlStore psqlStore = new PsqlStore(PropertiesCreator.getProperties("app.properties"));
        req.setAttribute("companies", psqlStore.getCompanyList());
        req.getRequestDispatcher("companies.jsp").forward(req, resp);
    }
}
