package manager;

import DB.DBConnectionProvider;
import model.Task;
import model.TaskStatus;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class TaskManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserManager userManager = new UserManager();


    public void addTask(Task task) {
        String sql = "INSERT INTO task(name,description,status,deadline,user_id) values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getName());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getTaskStatus().name());
            preparedStatement.setString(4, sdf.format(task.getDeadline()));
            preparedStatement.setInt(5, task.getUser_id());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                task.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Task> getAllTasks() {
        Statement statement = null;
        List<Task> tasks = new LinkedList<>();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
            tasks = getTaskFromResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }

    public void updateTaskStatus(int taskId, String newStatus) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  task set status=? where id=?");
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Task> getAllTasksByUserId(int userId) {
        PreparedStatement statement = null;
        List<Task> tasks = new LinkedList<>();
        try {
            statement = connection.prepareStatement("SELECT * FROM task where user_id=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            tasks = getTaskFromResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return tasks;

    }

    private List<Task> getTaskFromResultSet(ResultSet resultSet) throws SQLException {

        List<Task> tasks = new LinkedList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            try {
                task.setDeadline(sdf.parse((resultSet.getString("deadline"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            task.setTaskStatus(TaskStatus.valueOf(resultSet.getString("status")));
            task.setUser_id(resultSet.getInt("user_id"));
            task.setUser(userManager.getUserById(task.getUser_id()));
            tasks.add(task);
        }
        return tasks;

    }
    public void deleteTaskById(int id) {
        String sql = "delete from task where id = " + id;

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
