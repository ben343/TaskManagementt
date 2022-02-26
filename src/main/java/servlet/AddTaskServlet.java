package servlet;

import manager.TaskManager;
import model.Task;
import model.TaskStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = "/addTask")
public class AddTaskServlet extends HttpServlet {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    TaskManager taskManager=new TaskManager();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String status = req.getParameter("status");
        String date = req.getParameter("date");
        int  user_id =Integer.parseInt(req.getParameter("user_id")) ;

        try {
            taskManager.addTask(Task.builder()
                    .name(name)
                    .description(description)
                    .taskStatus(TaskStatus.valueOf(status))
                    .deadline(sdf.parse(date))
                    .user_id(user_id)
                    .build());
            resp.sendRedirect("/managerHome");
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
