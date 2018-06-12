<%@ page import="data.SessionToCSRFMap" %>
<%@ page import="data.AccountDetails" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Transfer funds | Some Secure Bank</title>

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

<body onload="getCSRFToken()">
<%
    //Checking whether user is logged in.
  boolean isLoggedin = false;
  Cookie cookies[] = request.getCookies();
  for(Cookie cookie: cookies){
    if(cookie.getName().equals("STPSesID") && SessionToCSRFMap.getInstance().isLoggedIn(cookie.getValue())){
      isLoggedin =true;
    }
  }
  if(!isLoggedin){
    response.sendRedirect("index.jsp");
  }
%>
<%
    //checking account information
    String balance = Double.toString(AccountDetails.getBalance());
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
        <li class="nav-item">
          <a class="nav-link" href="#">Home
            <span class="sr-only">(current)</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link disabled" href="#">About</a>
        </li>
        <li class="nav-item active">
          <a class="nav-link" href="#">Cash Transfer</a>
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

    <h1>Enter the transfer details.</h1>
    <div class="row">

        <p>Your current balance is :<%=balance%></p>
        <div class="col-lg-12 text-center">
            <form action="transaction" id="transaction_form" method="post">
            <div class ="form-group">
                <label for="amount">Transfer Amount</label>
                <input type="text" name="amount" id="amount" class="form-text" placeholder="0.00" center>
            </div>
            <div class ="form-group">
                <label for="transfer_to">Enter the account number of the receiver.</label>
                <input type="text" name="transfer_to" id="transfer_to" class="form-text" placeholder="Receiver account number" align="center">
            </div>
                <input type="hidden" id="csrf_token" name="csrf_token">
            <button type="submit" class="btn btn-primary">Submit</button>
            </form>
            <br><br><br><br>
            <footer>
                &copy;2000 - 2018 Some Secure Bank. All rights reserved.
            </footer>
        </div>
    </div>
</div>


<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script>
    function getCSRFToken() {

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("csrf_token").setAttribute("value",this.responseText);
                //alert(this.responseText);
            }
        };
        xhttp.open("POST", 'get_csrf_token', false);
        xhttp.send();
    }
</script>

</body>

</html>
