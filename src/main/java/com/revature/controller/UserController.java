package com.revature.controller;


import com.revature.exception.InvalidLoginException;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import com.revature.model.Account;
import com.revature.service.AccountService;

import java.util.List;


public class UserController implements Controller {
    private UserService userService;
    private AccountService accountService;

    public UserController() {
        this.userService = new UserService();
        this.accountService = new AccountService();
    }

    @Override
    public void mapEndpoints(Javalin app) {

        app.post("/login", ctx -> {
            User user = ctx.bodyAsClass(User.class);

            String email = user.getEmail();
            String pass = user.getPassword();
            try {
                User loggedInUser = userService.login(email, pass);

                HttpServletRequest req = ctx.req;
                HttpSession session = req.getSession();
                session.setAttribute("userId", loggedInUser.getUserId());
                session.setAttribute("email", loggedInUser.getEmail());
                session.setAttribute("userRole", loggedInUser.getUserRole());

                ctx.json(loggedInUser);
            } catch (InvalidLoginException | SQLException e) {
                ctx.result(e.getMessage());
                ctx.status(400);
            }

        });

        app.post("/logout", ctx -> {
//            System.out.println("logout");

            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            ctx.result("Successfully logged out");
            session.invalidate();
            ctx.status(201);
        });

        app.get("/logged-in-user", ctx -> {
                    HttpServletRequest req = ctx.req;

                    HttpSession session = req.getSession();
                    User myUser = (User) session.getAttribute("logged_in_user");

                    if (myUser == null) {
                        ctx.result("You are not logged in!");
                        ctx.status(404);
                    } else {

                    }

                });

        // returns currently logged in user's info
        app.get("/user", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");


            //TODO undo when can login!
//            myUser = new User(1, "Bob", "Smith", "jd80@a.ca", "foobar", "666-123-4562", "user");

            if (email == null) {

                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {
                User myUser = userService.getUserByEmail(email);
                List<Account> userAccounts = accountService.getAccountsByEmail(email);

                myUser.setAccounts(userAccounts);

                ctx.json(myUser);
                ctx.status(200);
            }
        });
    }
}
