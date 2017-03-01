package com.zepl.challenge.dao;

import com.zepl.challenge.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * H2 In-Memory Database Example shows about storing the database contents into memory.
 * Todos and Tasks will be stored into the embedded H2 Database. In case of restarting the service can recovered the list of todos and tasks.
 */
public class ToDoDao {

    private final static Logger log = LoggerFactory.getLogger(ToDoDao.class);
    private static String DB_DRIVER;
    private static String DB_CONNECTION;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static ToDoDao instance = null;

    private ToDoDao() {
    }

    /**
     * implement a singleton pattern
     * @return the singleton instance
     */
    public static ToDoDao getInstance() {
        if (instance == null) {
            instance = new ToDoDao();
        }
        return instance;
    }
    /**
     * checking if todo and task table are already exist before creating them.
     *
     * @return true if existed or vice versa
     * @throws SQLException
     */
    public static boolean isTablesExist() throws SQLException {
        log.debug("isTablesExist()");
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            stmt.execute("select * from todo");
            stmt.close();
            connection.commit();
            log.info("The tables are already exist()");
            return true;
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        log.info("The tables aren't exist()");
        return false;
    }

    /**
     * initializing todo and task tables if they are not exists.
     *
     * @throws SQLException
     */
    public static void initTablesIfNotExist(String driver, String url, String user, String pass) throws SQLException {
        log.debug("initTablesIfNotExist()");
        DB_DRIVER = driver;
        DB_CONNECTION = url;
        DB_USER = user;
        DB_PASSWORD = pass;
        log.info("DB_DRIVER:" + DB_DRIVER + " DB_CONNECTION:" + DB_CONNECTION + " DB_USER:" + DB_USER + " DB_PASSWORD:" + DB_PASSWORD);
        if (ToDoDao.isTablesExist() == true) return;
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE todo(id_todo varchar(255) primary key, name varchar(255), created varchar(255))");
            stmt.execute("CREATE TABLE task(id_task varchar(255) primary key,id_todo varchar(255),name varchar(255),description varchar(255),status int , created varchar(255),foreign key (id_todo ) REFERENCES todo(id_todo ))");
            stmt.close();
            connection.commit();
            log.info("create tables : todo and task tables are done");
            return;
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


    public static void printAllToDos() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TODO");
            log.info("printing all todos....");
            while (rs.next()) {
                log.info(" id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " created: " + rs.getTimestamp("created"));
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
    }

    public static void printAllTasks() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TASK");
            log.info("printing all tasks....");
            while (rs.next()) {
                log.info(" id_task: " + rs.getString("id_task") + " id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " status: " + rs.getInt("status") + " description: " + rs.getString("description") + " created: " + rs.getTimestamp("created"));
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public boolean insertToDo(ToDo todo) throws SQLException {
        log.debug("inserting a todo:" + todo);
        boolean result = false;
        Connection connection = getDBConnection();
        PreparedStatement insertPreparedStatement = null;
        String InsertQuery = "INSERT INTO TODO(id_todo, name,created) VALUES" + "(?,?,?)";

        try {
            connection.setAutoCommit(false);
            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, todo.id());
            insertPreparedStatement.setString(2, todo.name());
            insertPreparedStatement.setString(3, todo.created());
            result = (insertPreparedStatement.executeUpdate() == 1) ? true : false;
            insertPreparedStatement.close();
            connection.commit();
            log.debug("inserted a todo :" + todo);
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return result;
    }

    public boolean insertTodoTask(String idToDo, Task task) throws SQLException {
        boolean result = false;
        Connection connection = getDBConnection();
        PreparedStatement insertPreparedStatement = null;
        String InsertQuery = "INSERT INTO TASK(id_task,id_todo,name,description,status,created) VALUES" + "(?,?,?,?,?,?)";

        try {
            //todo must be existed before inserting a task
            if (selectTodo(idToDo) == null) return result;
            connection.setAutoCommit(false);
            insertPreparedStatement = connection.prepareStatement(InsertQuery);
            insertPreparedStatement.setString(1, task.id());
            insertPreparedStatement.setString(2, idToDo);
            insertPreparedStatement.setString(3, task.name());
            insertPreparedStatement.setString(4, task.description());
            insertPreparedStatement.setInt(5, task.status().ordinal());
            insertPreparedStatement.setString(6, task.created());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            connection.commit();
            log.debug("inserted a task :" + task);
            result = true;
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return result;
    }

    public boolean updateTask(String idToDo, Task task) throws SQLException {
        boolean result = false;
        Connection connection = getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        String updateQuery = "UPDATE TASK SET name=?,description=?,status=? WHERE id_task= ? AND id_todo = ?";

        try {
            //todo must be existed before inserting a task
            connection.setAutoCommit(false);
            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setString(1, task.name());
            updatePreparedStatement.setString(2, task.description());
            updatePreparedStatement.setInt(3, task.status().ordinal());
            updatePreparedStatement.setString(4, task.id());
            updatePreparedStatement.setString(5, idToDo);

            updatePreparedStatement.executeUpdate();
            updatePreparedStatement.close();
            connection.commit();
            log.debug("updated a task :" + task);
            result = true;
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return result;
    }

    /**
     * deleting a todo and related tasks with id of the todo
     *
     * @param idTodo
     * @return
     * @throws SQLException
     */
    public boolean deleteTodoAndRelatedTasks(String idTodo) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String selectQuery = "DELETE from TASK where  id_todo=?";
        boolean result = false;
        try {
            log.debug("deleting a todo and todo tasks with id: " + idTodo);
            //delete tasks
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTodo);
            stmt.execute();
            stmt.close();
            //delete a todo
            selectQuery = "DELETE from TODO where  id_todo=?";
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTodo);
            stmt.execute();
            stmt.close();
            connection.commit();
            result = true;
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        return result;
    }

    /**
     * deleting a task
     *
     * @param idTodo
     * @return
     * @throws SQLException
     */
    public boolean deleteTask(String idTodo, String idTask) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String deleteQuery = "DELETE from TASK where  id_todo=? and id_task = ?";
        boolean result = false;
        try {
            log.debug("deleting a tasks with id_todo: " + idTodo + " id_task:" + idTask);
            stmt = connection.prepareStatement(deleteQuery);
            stmt.setString(1, idTodo);
            stmt.setString(2, idTask);
            stmt.execute();
            stmt.close();
            connection.commit();
            result = true;
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        return result;
    }

    public ToDo selectTodo(String idTodo) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String selectQuery = "select * from TODO where id_todo = ?";
        ToDo todo = null;
        try {
            log.debug("searching a todo with id: " + idTodo);
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTodo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                log.debug(" found a todo with : id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " created: " + rs.getTimestamp("created"));
                todo = ImmutableToDo.builder().id(rs.getString("id_todo")).name(rs.getString("name")).created(rs.getString("created")).build();
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        return todo;
    }

    public Task selectTask(String idTask) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String selectQuery = "select * from TASK where id_task = ?";
        Task task = null;
        try {
            log.debug("searching a task with id: " + idTask);
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTask);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                log.debug(" found a task with : id_task: " + rs.getString("id_task") + " id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " status: " + rs.getInt("status") + " description: " + rs.getString("description") + " created: " + rs.getTimestamp("created"));
                task = ImmutableTask.builder().id(rs.getString("id_task")).description(rs.getString("description")).name(rs.getString("name")).status(TaskType.values()[rs.getInt("status")]).created(rs.getString("created")).build();
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        return task;
    }

    public List getToDos() throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        List<ToDo> todos = new ArrayList<ToDo>();
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TODO");
            while (rs != null && rs.next()) {
                log.debug(" id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " created: " + rs.getTimestamp("created") + "size: " + todos.size());
                ToDo todo = ImmutableToDo.builder().id(rs.getString("id_todo")).name(rs.getString("name")).created(rs.getString("created")).build();
                todos.add(todo);
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        log.debug(" return the todos with size: " + todos.size());
        return todos;
    }

    public List getTodoTasksByStatus(String idTodo, TaskType tasktype) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String selectQuery = null;
        if (tasktype == null)
            selectQuery = "select * from TASK where id_todo = ?";
        else
            selectQuery = "select * from TASK where id_todo = ? and status = ?";
        List<Task> tasks = new ArrayList<Task>();

        try {
            log.debug("searching tasks with id_todo: " + idTodo + " status:" + tasktype);
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTodo.trim());
            if (tasktype != null) stmt.setInt(2, tasktype.ordinal());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                log.debug(" task with : id_task: " + rs.getString("id_task") + " id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " status: " + rs.getInt("status") + " description: " + rs.getString("description") + " created: " + rs.getTimestamp("created"));
                Task task = ImmutableTask.builder().id(rs.getString("id_task")).description(rs.getString("description")).name(rs.getString("name")).status(TaskType.values()[rs.getInt("status")]).created(rs.getString("created")).build();
                tasks.add(task);
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        log.debug(" return the tasks with size: " + tasks.size());
        return tasks;
    }

    /**
     * get a todo task
     *
     * @param idTodo
     * @param idTask
     * @return
     * @throws SQLException
     */

    public Task getTodoTask(String idTodo, String idTask) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement stmt = null;
        String selectQuery = "select * from TASK where id_todo = ? and id_task = ?";
        Task task = null;
        try {
            log.debug("searching a todo task with id_todo: " + idTodo + " id_task:" + idTask);
            stmt = connection.prepareStatement(selectQuery);
            stmt.setString(1, idTodo.trim());
            stmt.setString(2, idTask.trim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                log.debug(" found a task with : id_task: " + rs.getString("id_task") + " id_todo: " + rs.getString("id_todo") + " name: " + rs.getString("name") + " status: " + rs.getInt("status") + " description: " + rs.getString("description") + " created: " + rs.getTimestamp("created"));
                task = ImmutableTask.builder().id(rs.getString("id_task")).description(rs.getString("description")).name(rs.getString("name")).status(TaskType.values()[rs.getInt("status")]).created(rs.getString("created")).build();
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            log.error("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            connection.close();
        }
        return task;
    }
}
