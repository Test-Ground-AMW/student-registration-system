package lk.ijse.dep11.web;

import lk.ijse.dep11.web.to.Student;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet({"/students",""})
@MultipartConfig(location = "/tmp")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BasicDataSource pool = (BasicDataSource) getServletContext().getAttribute("connectionPool");
        try(Connection connection = pool.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT s.*, p.path FROM student AS s LEFT OUTER JOIN " +
                    "picture AS p ON s.id = p.student_id");
            ArrayList<Student> studentList = new ArrayList<>();
            while (rst.next()){
                String id = String.format("S%03d", rst.getInt("id"));
                String name = rst.getString("name");
                String address = rst.getString("address");
                String path = rst.getString("path");
                studentList.add(new Student(id,name,address,path));
            }
            /*Save studentList as an attribute within request scope*/
            req.setAttribute("studentList",studentList);
            getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req,resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*get the values returned from the form data (name defined data)*/
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Part picture = req.getPart("picture");

        /*get a connection from the connection pool*/
        BasicDataSource pool = (BasicDataSource)getServletContext().getAttribute("connectionPool");

        try {
            /*To start transaction (send data as a set)*/
            Connection connection = pool.getConnection();
            connection.setAutoCommit(false);
            try {
                /*prepared statement used to prevent sql injection*/
                PreparedStatement stmStudent = connection.prepareStatement("INSERT INTO student (name, address) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                stmStudent.setString(1,name);
                stmStudent.setString(2,address);
                stmStudent.executeUpdate();

                if (picture.getSize() > 0){
                    String uploadDirPath = getServletContext().getRealPath("/uploads/");
                    UUID imageID = UUID.randomUUID();
                    String picturePath = uploadDirPath + imageID;

                    ResultSet generatedKeys = stmStudent.getGeneratedKeys();
                    generatedKeys.next();

                    PreparedStatement stmPicture = connection.prepareStatement("INSERT INTO picture (student_id, path) VALUES (?,?)");
                    stmPicture.setInt(1,generatedKeys.getInt(1));
                    stmPicture.setString(2,"uploads/" + imageID);
                    stmPicture.executeUpdate();
                    picture.write(picturePath);
                }
                connection.commit();
            } catch (Throwable t){
                connection.rollback();
                t.printStackTrace();
            } finally {
                /*To end the transaction*/
                connection.setAutoCommit(true);
                /*To release the connection*/
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect("/app");
    }
}
