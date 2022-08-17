package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.model.User;
import com.revature.service.TransactionService;
import io.javalin.Javalin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.InvalidParameterException;
import java.util.Map;

public class TransactionController implements Controller {
    private TransactionService transactionService;

    public TransactionController() {
        this.transactionService = new TransactionService();
    }


    @Override
    public void mapEndpoints(Javalin app) {
        app.post("/transaction", ctx -> {
            HttpServletRequest req = ctx.req;

            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ObjectMapper om = new ObjectMapper();
            Map<String, String> newTransaction = om.readValue(ctx.body(), Map.class);
            //DeviceWarranty newWarranty = ctx.bodyAsClass(DeviceWarranty.class);
            //newWarranty.setWarrantyRequester(myUser.getUsername());
//            Transaction addedTransaction = newTransaction;
//            addedTransaction.setTransactionId();
            try {
                ctx.json(transactionService.addTransactionById(newTransaction));
                ctx.status(201);
            } catch (InvalidParameterException e) {
//                ctx.json(e.getMessages());
                ctx.status(400);
            }

        });

        app.get("/transactions", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");
            ctx.json(transactionService.getAllTransactions());
            ctx.status(200);
        });


        app.get("/transaction/requesterId", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ctx.json(transactionService.getAllTransactionsByRequesterId("1"));
            ctx.status(200);
        });


        app.get("/transaction/senderId", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ctx.json(transactionService.getAllTransactionsbySenderId("1"));
            ctx.status(200);
        });


        app.get("/transaction/receivingId", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ctx.json(transactionService.getAllTransactionsByReceivingId("1"));
            ctx.status(200);
        });


        app.get("/transaction/statusId", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ctx.json(transactionService.getAllTransactionsByStatusId("1"));
            ctx.status(200);
        });

        app.get("/transaction/descriptionId", ctx -> {
            HttpServletRequest req = ctx.req;
            HttpSession session = req.getSession();
            User myUser = (User) session.getAttribute("logged_in_user");

            ctx.json(transactionService.getAllTransactionsByDescriptionId("1"));
            ctx.status(200);
        });

//        app.get("/Transaction/approved", ctx -> {
//            HttpServletRequest req = ctx.req;
//            HttpSession session = req.getSession();
//            User myUser = (User) session.getAttribute("logged_in_user");
//
//            ctx.json(transactionService.getAllTransactionsByApproved("1"));
//            ctx.status(200);
//        });


    }
}


