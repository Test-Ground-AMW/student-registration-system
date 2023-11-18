package lk.ijse.dep11.web;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/students")
@MultipartConfig(location = "/tmp")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Part picture = req.getPart("picture");

        BasicDataSource pool = (BasicDataSource)getServletContext().getAttribute("connectionPool");
        try {
            Connection connection = pool.getConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement stmStudent = connection.prepareStatement("INSERT INTO student (name, address) VALUES (?,?)");
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
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
