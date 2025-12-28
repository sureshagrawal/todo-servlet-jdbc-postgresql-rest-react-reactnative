package com.nsgacademy.todo.web;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nsgacademy.todo.dao.TodoDAO;
import com.nsgacademy.todo.exception.DAOException;
import com.nsgacademy.todo.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/api/todos/*")
public class TodoServlet extends HttpServlet {

    private final TodoDAO todoDAO = new TodoDAO();
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);



    // ----------------------------------------
    // GET (all / by id)
    // ----------------------------------------
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String pathInfo = req.getPathInfo();

            // GET /api/todos
            if (pathInfo == null || "/".equals(pathInfo)) {
                List<Todo> todos = todoDAO.getAllTodos();
                resp.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(resp.getWriter(), todos);
                return;
            }

            // GET /api/todos/{id}
            int id = Integer.parseInt(pathInfo.substring(1));
            Todo todo = todoDAO.getTodoById(id);

            if (todo == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                mapper.writeValue(resp.getWriter(),
                        error("Todo not found"));
                return;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getWriter(), todo);

        } catch (DAOException e) {
            sendServerError(resp, e);
        } catch (Exception e) {
            sendBadRequest(resp, "Invalid request");
        }
    }

    // ----------------------------------------
    // POST (create)
    // ----------------------------------------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            Todo todo = mapper.readValue(req.getReader(), Todo.class);
            todoDAO.addTodo(todo);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            mapper.writeValue(resp.getWriter(),
                    message("Todo created successfully"));

        } catch (DAOException e) {
            sendServerError(resp, e);
        }
    }

    // ----------------------------------------
    // PUT (update)
    // ----------------------------------------
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String pathInfo = req.getPathInfo();
            int id = Integer.parseInt(pathInfo.substring(1));

            Todo todo = mapper.readValue(req.getReader(), Todo.class);
            todo.setId(id);

            todoDAO.updateTodo(todo);

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getWriter(),
                    message("Todo updated successfully"));

        } catch (DAOException e) {
            sendServerError(resp, e);
        } catch (Exception e) {
            sendBadRequest(resp, "Invalid request");
        }
    }

    // ----------------------------------------
    // DELETE
    // ----------------------------------------
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String pathInfo = req.getPathInfo();
            int id = Integer.parseInt(pathInfo.substring(1));

            todoDAO.deleteTodo(id);

            resp.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(resp.getWriter(),
                    message("Todo deleted successfully"));

        } catch (DAOException e) {
            sendServerError(resp, e);
        } catch (Exception e) {
            sendBadRequest(resp, "Invalid request");
        }
    }

    // ----------------------------------------
    // Helper methods
    // ----------------------------------------
    private void sendServerError(HttpServletResponse resp, DAOException e)
            throws IOException {

        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        mapper.writeValue(resp.getWriter(),
                error(e.getMessage()));
    }

    private void sendBadRequest(HttpServletResponse resp, String message)
            throws IOException {

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        mapper.writeValue(resp.getWriter(),
                error(message));
    }


    private static Object message(String msg) {
        return new Object() {
            public final String message = msg;
        };
    }

    private static Object error(String msg) {
        return new Object() {
            public final String error = msg;
        };
    }
}
