<%@ page import="data.SessionVector" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Home | Some Secure Bank</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <style>
      body {
        padding-top: 54px;
      }
      @media (min-width: 992px) {
        body {
          padding-top: 56px;
        }
      }

    </style>

  </head>

  <body>
  <%
      boolean isLoggedin = false;
      Cookie cookies[] = request.getCookies();
      for(Cookie cookie: cookies){
          if(cookie.getName().equals("DBLSesID") && SessionVector.getInstance().isLoggedIn(cookie.getValue())){
              isLoggedin =true;
          }
      }
      if(!isLoggedin){
          response.sendRedirect("index.jsp");
      }
  %>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="#">SomeSecure Bank</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home
                <span class="sr-only">(current)</span>
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link disabled" href="#">About</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="transfer.jsp">Cash Transfer</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="logout">Log out</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Page Content -->
    <div class="container">
      <div class="row">
        <div class="col-lg-12 text-center">
          <h1 class="mt-5">Welcome Adam!</h1>
          <p class="lead">Please select what service you would like to use from the top menu.</p>
            <footer>
                &copy;2000 - 2018 Some Secure Bank. All rights reserved.
            </footer>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  </body>

</html>
