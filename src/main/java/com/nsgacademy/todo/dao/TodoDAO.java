package com.nsgacademy.todo.dao;

import com.nsgacademy.todo.exception.DAOException;
import com.nsgacademy.todo.model.Todo;
import com.nsgacademy.todo.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    // ----------------------------------------
    // ADD TODO
    // ----------------------------------------
    public void addTodo(Todo todo) {
        String sql = """
                INSERT INTO todo (title, description, priority, status, due_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setString(3, todo.getPriority());
            ps.setString(4, todo.getStatus());
            ps.setObject(5, todo.getDueDate());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Failed to add todo", e);
        }
    }

    // ----------------------------------------
    // UPDATE TODO
    // ----------------------------------------
    public void updateTodo(Todo todo) {
        String sql = """
                UPDATE todo
                SET title = ?, description = ?, priority = ?, status = ?, due_date = ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getDescription());
            ps.setString(3, todo.getPriority());
            ps.setString(4, todo.getStatus());
            ps.setObject(5, todo.getDueDate());
            ps.setInt(6, todo.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Failed to update todo with id " + todo.getId(), e);
        }
    }

    // ----------------------------------------
    // DELETE TODO
    // ----------------------------------------
    public void deleteTodo(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Failed to delete todo with id " + id, e);
        }
    }

    // ----------------------------------------
    // GET TODO BY ID
    // ----------------------------------------
    public Todo getTodoById(int id) {
        String sql = "SELECT * FROM todo WHERE id = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToTodo(rs);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to fetch todo with id " + id, e);
        }

        return null;
    }

    // ----------------------------------------
    // GET ALL TODOS
    // ----------------------------------------
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo ORDER BY created_at DESC";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                todos.add(mapRowToTodo(rs));
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to fetch todo list", e);
        }

        return todos;
    }

    // ----------------------------------------
    // RESULTSET → TODO
    // ----------------------------------------
    private Todo mapRowToTodo(ResultSet rs) throws SQLException {

        Todo todo = new Todo();

        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setDescription(rs.getString("description"));
        todo.setPriority(rs.getString("priority"));
        todo.setStatus(rs.getString("status"));

        Date dueDate = rs.getDate("due_date");
        if (dueDate != null) {
            todo.setDueDate(dueDate.toLocalDate());
        }

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            todo.setCreatedAt(createdAt.toLocalDateTime());
        }

        Timestamp updatedAt = rs.getTimestamp("updated_at");
        if (updatedAt != null) {
            todo.setUpdatedAt(updatedAt.toLocalDateTime());
        }

        return todo;
    }
}
