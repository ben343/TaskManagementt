<%--
  Created by IntelliJ IDEA.
  model.UserType: Ben Khachatryan
  Date: 12.02.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home Page</title>
  </head>
  <body>
 <form action="/login" method="post">
   <input type="text" name="email" placeholder="input your email "> <br>
   <input type="password" name="password" placeholder="input your password "><br>
     <input type="submit" value="Login">
 </form>
  </body>
</html>
