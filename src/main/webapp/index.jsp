<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16.06.2021
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.store.PsqlStore" %>
<%@ page import="db.PropertiesCreator" %>
<%@ page import="model.Company" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Properties" %>
<%@ page import="model.Price" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Работа мечты</title>
</head>

<%
    PsqlStore store = new PsqlStore(PropertiesCreator.getProperties("app.properties"));
    List<Company> companies = store.getCompanyList();
%>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Companies
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Ticker</th>
                        <th scope="col">Name</th>
                        <th scope="col">Industry</th>
                        <th scope="col">Sector</th>
                        <th scope="col">Last price</th>
                        <th scope="col">All prices</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Company comp : companies) {
                        List<Price> prices = store.getCompanyPriceList(comp.getTicker());                                 %>
                    <tr>
                        <td>
                            <%= comp.getTicker() %>
                        </td>
                        <td>
                            <%= comp.getName() %>
                        </td>
                        <td>
                            <%= comp.getIndustry() %>
                        </td>
                        <td>
                            <%= comp.getSector() %>
                        </td>

                        <td>
                            <%=String.format("%.2f", prices.get(0).getLowPrice())%>
                        </td>

                        <td>
                            <a class="nav-link" href="<%=request.getContextPath()%>/allprices.jsp?ticker=<%=comp.getTicker()%>">click</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
