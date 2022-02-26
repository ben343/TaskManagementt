<%@ page import="org.w3c.dom.stylesheets.LinkStyle" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page import="model.UserType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<style> input[type=text], input[type=date], select {
    width: 100%;
    padding: 12px 20px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 100%;
    background-color: #4CAF50;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #45a049;
}

div {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
}

</style>
<style>
    .styled-table {
        border-collapse: collapse;
        margin: 25px 0;
        font-size: 0.9em;
        font-family: sans-serif;
        min-width: 400px;
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
    }

    .styled-table thead tr {
        background-color: #009879;
        color: #ffffff;
        text-align: left;
    }

    .styled-table th,
    .styled-table td {
        padding: 12px 15px;
    }

    .styled-table tbody tr {
        border-bottom: 1px solid #dddddd;
    }

    .styled-table tbody tr:nth-of-type(even) {
        background-color: #f3f3f3;
    }

    .styled-table tbody tr:last-of-type {
        border-bottom: 2px solid #009879;
    }

    .styled-table tbody tr.active-row {
        font-weight: bold;
        color: #009879;
    }
</style>
<body>
<% List<User> users = (List<User>) request.getAttribute("user");
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    User user = new User();
%>


<a style="color: #a52a4d" href="/logout" >  Logout</a>

<div style="width: 800px;">
    <div style="width: 50%; float: left ">
        <h1> Add User </h1>
        <form action="/userRegister" method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="name"> <br>
            <input type="text" name="surname" placeholder="surname"> <br>
            <input type="text" name="email" placeholder="email"> <br>
            <input type="password" name="password" placeholder="password"> <br>
            <input type="file" name="image"> <br>
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>

            </select><br>


            <input type="submit" value="addUser">
        </form>
    </div>
    <div style="width: 50%; float: left">
        <h1> Add Task</h1>    <br>
        <form action="/addTask" method="post">
            <input type="text" name="name" placeholder="name"> <br>
            <textarea name="description" placeholder="description"> </textarea> <br>
            <input type="date" name="date"> <br>
            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>


            </select><br>
            <select name="user_id">
                <%
                    for (User user2 : users) { %>
                <option value=<%=user2.getId()%>><%=user2.getName()%> <%=user2.getSurname()%>
                </option>
                <%
                    }
                %>
            </select><br>
            <input type="submit" value="addTask">
        </form>


    </div>
</div>
<div>
    <b>All User</b> <br>
    <table class="styled-table">
        <tr>
            <th>name</th>
            <th>surname</th>
            <th>email</th>
            <th>type</th>
            <th>image</th>
            <th>Delete</th>
        </tr>
        <%
            for (User user1 : users) {

        %>
        <tr>
            <td><%=user1.getName()%>
            </td>
            <td><%=user1.getSurname()%>
            </td>
            <td><%=user1.getEmail()%>
            </td>
            <td><%=user1.getUserType().name()%>
            </td>
            <td><img src="/image?path=<%=user1.getPicUrl()%> " style="width: 80px"/>
            </td>

            <td><%if (user1.getUserType() != UserType.MANAGER) {%>
                <a style="display: inline-block" href="/deleteUser?id=<%=user1.getId()%>">Delete</a></td>
            <%
                    }
                } %>

        </tr>
    </table>
    <div>
        <b>All Tasks</b> <br>
        <table class="styled-table">
            <tr>
                <th>name</th>
                <th>description</th>
                <th>deadline</th>
                <th>status</th>
                <th>user</th>
                <th>Delete</th>
            </tr>
            <%
                for (Task task : tasks) { %>
            <tr>
                <td><%=task.getName()%>
                </td>
                <td><%=task.getDescription()%>
                </td>
                <td><%=task.getDeadline()%>
                </td>
                <td><%=task.getTaskStatus().name()%>
                </td>
                <td><%=task.getUser().getName() + " " + task.getUser().getSurname()  %>
                </td>
                <td>
                    <a href="/deleteTask?id=<%=task.getId()%>">Delete</a>
                </td>

            </tr>
            <%
                }
            %>
        </table>
    </div>
</div>


</body>
</html>
