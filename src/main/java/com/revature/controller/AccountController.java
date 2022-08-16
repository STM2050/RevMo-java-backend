package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exception.InvalidParameterException;
import com.revature.model.User;
import com.revature.service.AccountService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class AccountController implements Controller{
    private AccountService accountService;

    public AccountController() {
        accountService = new AccountService();
    }
    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/account", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ObjectMapper om = new ObjectMapper();
            Map<String, String> newAccount = om.readValue(ctx.body(), Map.class);
            try {
                ctx.json(accountService.openAccount(newAccount));
                ctx.status(201);
            } catch (InvalidParameterException e) {
                ctx.json(e.getMessages());
                ctx.status(400);
            }
        });


    }
}
