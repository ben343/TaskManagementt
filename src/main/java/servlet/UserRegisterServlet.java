package servlet;

import manager.UserManager;
import model.User;
import model.UserType;
import sun.security.pkcs11.wrapper.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


@WebServlet(urlPatterns = "/userRegister")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserRegisterServlet extends HttpServlet {
    UserManager userManager = new UserManager();
    private final String UPLOAD_DIR = "C:\\Users\\Admin\\IdeaProjects\\TaskManagementt\\upload";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String type = req.getParameter("type");
        String password = req.getParameter("password");
        StringBuilder msg = new StringBuilder();
        if (name == null || name.length() == 0) {
            msg.append("Name Field is required <br>");
        }
        if (surname == null || surname.length() == 0) {
            msg.append("Surname Field is required <br>");
        }
        if (email == null || email.length() == 0) {
            msg.append("Email Field is required <br>");
        } else if (userManager.getUserByEmailAndPassword(email, password) != null) {
            msg.append("email already exists <br>");
        }
        if (password == null || password.length() == 0) {
            msg.append("Password Field is required <br>");
        }
        if (msg.toString().equals("")) {
            User user = User.builder()
                    .name(name)
                    .surname(surname)
                    .email(email)
                    .password(password)
                    .userType(UserType.valueOf(type))
                    .build();
            for (Part part : req.getParts()) {
                if (getFileName(part) != null) {
                    String fileName = System.currentTimeMillis() + getFileName(part);
                    String fullFileName = UPLOAD_DIR + File.separator + fileName;
                    part.write(fullFileName);
                    user.setPicUrl(fileName);
                }
            }
            userManager.add(user);
            resp.sendRedirect("/managerHome");
        }
    }

    //        String name = req.getParameter("name");
//        String surname = req.getParameter("surname");
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        String type = req.getParameter("type");
//        userManager.add(User.builder()
//                // User user=User.builder()
//                .name(name)
//                .surname(surname)
//                .email(email)
//                .password(password)
//                .userType(UserType.valueOf(type))
//
//                .build());
//        for (Part part : req.getParts()) {
//            if (getFileName(part) != null) {
//                String fileName = getFileName(part);
//                String fullFileName = UPLOAD_DIR + File.separator + System.currentTimeMillis() + fileName;
//                part.write(fullFileName);
//
//            }
//        }
//        resp.sendRedirect("/managerHome");
//
//
//    }

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename"))
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
        }
        return null;
    }
}
