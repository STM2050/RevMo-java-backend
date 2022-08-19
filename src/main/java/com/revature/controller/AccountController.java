package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AccountController implements Controller{
    private AccountService accountService;
    private UserService userService;

    public AccountController() {
        accountService = new AccountService();
        userService = new UserService();
    }
    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/accounts", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);

            if (email == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            }else if (myUser.getUserRole().equals("2")) {
                ObjectMapper om = new ObjectMapper();
                Map<String, String> newAccount = om.readValue(ctx.body(), Map.class);
                try {
                    ctx.json(accountService.openAccount(newAccount));
                    ctx.status(201);
                } catch (InvalidParameterException e) {
                    ctx.json(e.getMessages());
                    ctx.status(400);
                }
            }
        });

        app.put("/accounts/{aId}/users", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            int uId = myUser.getUserId();
            ctx.json(accountService.linkUserToAccount(aId, uId));
            ctx.status(200);
        });

        app.delete("/accounts/{aId}/users", ctx -> {
           HttpServletRequest req = ctx.req;
           HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);
           int aId = Integer.parseInt(ctx.pathParam("aId"));
           int uId = myUser.getUserId();
           ctx.json(accountService.unlinkUserFromAccount(aId, uId));
           ctx.status(200);
        });

        app.delete("/accounts/{aId}", ctx -> {
           HttpServletRequest req = ctx.req;
           HttpSession session = req.getSession();
           String email = (String) session.getAttribute("email");
           User myUser = userService.getUserByEmail(email);
           try {
               int aId = Integer.parseInt(ctx.pathParam("aId"));
               ctx.json(accountService.deleteAccount(aId));
               ctx.status(200);
           } catch (InvalidParameterException e) {
               ctx.json(e.getMessages());
               ctx.status(400);
           }

        });

        app.get("/accounts", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);

            if (myUser == null) {
                ctx.result("You are not logged in!");
                ctx.status(404);
            } else {
                ctx.json(accountService.getAccountsByEmail(email));
                ctx.status(200);
            }
        });

        app.get("/accounts/{aId}", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            ctx.json(accountService.getAccountByEmailAndAccountId(email, aId));
            ctx.status(200);
        });

        app.get("/accounts/{aId}/users", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            User myUser = userService.getUserByEmail(email);
            int aId = Integer.parseInt(ctx.pathParam("aId"));
            ctx.json(accountService.obtainListOfAccountOwners(aId));
            ctx.status(200);
        });
    }


}