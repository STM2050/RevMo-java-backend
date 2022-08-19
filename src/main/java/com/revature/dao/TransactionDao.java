package com.revature.dao;
import com.revature.model.*;
import com.revature.utility.ConnectionUtility;
import kotlin.time.TimeSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionDao {
    public String moveAmountBetweenSameOwnerAccounts(Transaction transaction) {
        try (Connection con = ConnectionUtility.createConnection()) {
            con.setAutoCommit(false);

            try (
                    PreparedStatement ps = con.prepareStatement("INSERT INTO transactions (requester_id, " +
                            "sending_id,receiving_id, req_time, res_time, status_id, amount, desc_id, receiving_email) " +
                            "VALUES(?, ?, ?, Now(), Now(),?, ?, ?,?)");
                    PreparedStatement ps1 = con.prepareStatement("UPDATE accounts SET balance = balance - ? " +
                            "WHERE id = ? ");
                    PreparedStatement ps2 = con.prepareStatement("UPDATE accounts SET balance = balance + ? " +
                            "WHERE id = ? ");
            ) {
                // Create insert statement
                ps.setInt(1, transaction.getRequesterId());
                ps.setInt(2, transaction.getSendingId());
                ps.setInt(3, transaction.getReceivingId());
                ps.setInt(4, transaction.getStatusId());
                ps.setLong(5, transaction.getAmount());
                ps.setInt(6, transaction.getDescriptionId());
                ps.setString(7, transaction.getReceivingEmail());
                ps.executeUpdate();
                //change balance for account moving amount out of
                ps1.setLong(1, transaction.getAmount());
                ps1.setInt(2, transaction.getSendingId());
                ps1.executeUpdate();
                // change balance for account receiving amount
                ps2.setLong(1, transaction.getAmount());
                ps2.setInt(2, transaction.getReceivingId());
                ps2.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                System.out.println(e);
                try {
                    // Roll back transaction
                    con.rollback();
                    return "Transaction is being rolled back.";
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Transaction Successful";
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description " +
                    "FROM transactions t " +
                    "JOIN users u ON t.requester_id = u.id " +
                    "JOIN status_types st ON t.status_id  = st.id " +
                    "JOIN transaction_descriptions td ON t.desc_id  = td.id;");

            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }


    public List<Transaction> getAllTransactionsByRequesterId(int requestId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description " +
                    "FROM transactions t " +
                    "JOIN users u ON t.requester_id = u.id " +
                    "JOIN status_types st ON t.status_id  = st.id " +
                    "JOIN transaction_descriptions td ON t.desc_id  = td.id " +
                    "WHERE t.requester_id  = ?");
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsBySenderId(int senderId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description" +
                    " FROM transactions t" +
                    " JOIN users u ON t.requester_id = u.id" +
                    " JOIN status_types st ON t.status_id  = st.id" +
                    " JOIN transaction_descriptions td ON t.desc_id  = td.id" +
                    " WHERE t.sending_id  = ?");
            ps.setInt(1, senderId);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsByRecievingId(int receiveId) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description" +
                    " FROM transactions t" +
                    " JOIN users u ON t.requester_id = u.id" +
                    " JOIN status_types st ON t.status_id  = st.id" +
                    " JOIN transaction_descriptions td ON t.desc_id  = td.id" +
                    " WHERE t.receiving_id  = ?");
            ps.setInt(1, receiveId);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsByStatusName(String statusName) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description" +
                    " FROM transactions t" +
                    " JOIN users u ON t.requester_id = u.id" +
                    " JOIN status_types st ON t.status_id  = st.id" +
                    " JOIN transaction_descriptions td ON t.desc_id  = td.id" +
                    " WHERE st.type_name  = ?");
            ps.setString(1, statusName);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

    public List<Transaction> getAllTransactionsByDescription(String desc) throws SQLException {
        try (Connection con = ConnectionUtility.createConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.requester_id, t.sending_id, t.receiving_id, t.req_time, t.res_time, t.amount, t.receiving_email,  concat_ws(' ', u.first_name,u.last_name) as initiated_by, st.type_name, td.description" +
                    " FROM transactions t" +
                    " JOIN users u ON t.requester_id = u.id" +
                    " JOIN status_types st ON t.status_id  = st.id" +
                    " JOIN transaction_descriptions td ON t.desc_id  = td.id" +
                    " WHERE td.description  = ?");
            ps.setString(1, desc);
            ResultSet rs = ps.executeQuery();
            List<Transaction> transactionsList = new ArrayList<>();
            while (rs.next()) {
                int transactionId = rs.getInt("id");
                int requesterId = rs.getInt("requester_id");
                int sendingId = rs.getInt("sending_id");
                int receivingId = rs.getInt("receiving_id");
                Timestamp reqTime = rs.getTimestamp("req_time");
                Timestamp resTime = rs.getTimestamp("res_time");
                long amount = rs.getLong("amount");
                String receivingEmail = rs.getString("receiving_email");
                String initiatedBy = rs.getString("initiated_by");
                String typeName = rs.getString("type_name");
                String description = rs.getString("description");
                Transaction transaction = new Transaction(transactionId, requesterId,
                        sendingId, receivingId, reqTime, resTime, receivingEmail,
                        initiatedBy, typeName, description, amount);
                transactionsList.add(transaction);
            }
            return transactionsList;
        }
    }

//    public Transaction sendMoneyRequest(Transaction transaction) throws SQLException {
//        try (Connection con = ConnectionUtility.createConnection()) {
//            con.setAutoCommit(false);
//            try (
//                    PreparedStatement ps = con.prepareStatement("INSERT INTO transactions (requester_id, " +
//                            "sending_id,receiving_id, req_time, status_id, amount, desc_id, receiving_email) " +
//                            "VALUES(?, ?, ?, Now(),?, ?, ?,?) RETURNING *");
//            ) {
//                ps.setInt(1, transaction.getRequesterId());
//                ps.setInt(2, transaction.getSendingId());
//                ps.setInt(3, transaction.getReceivingId());
//                ps.setInt(4, transaction.getStatusId());
//                ps.setLong(5, transaction.getAmount());
//                ps.setInt(6, transaction.getDescriptionId());
//                ps.setString(7, transaction.getReceivingEmail());
//                ps.executeUpdate();
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    int transactionId = rs.getInt("id");
//                    int requesterId = rs.getInt("requester_id");
//                    int sendingId = rs.getInt("sending_id");
//                    int receivingId = rs.getInt("receiving_id");
//                    Timestamp reqTime = rs.getTimestamp("req_time");
//                    long amount = rs.getLong("amount");
//                    String receivingEmail = rs.getString("receiving_email");
//                    String initiatedBy = rs.getString("initiated_by");
//                    String typeName = rs.getString("type_name");
//                    String description = rs.getString("description");
//                    Transaction trx = new Transaction(transactionId, requesterId,
//                            sendingId, receivingId, reqTime, receivingEmail,
//                            initiatedBy, typeName, description, amount);
//                    return trx;
//                }
//
//            } catch (SQLException e) {
//                System.out.println(e);
//            }
//            return null;
//        }
//}
            /*
            This method will initiate a money transfer request. The request will then need to be approved by the person
            who would be transferring money to the requester.
             */

    public String completeMoneyRequest() throws SQLException {
        /*
        This method will finish the money transfer request. The sender will approve or deny the money transfer request.
         */
        return null;
    }

    public String cancelTransaction() throws SQLException {
        /*
        This method will cancel a transaction if it has not been approved OR denied.
         */
        return null;
    }
}





// (update(approve/deny) give resolve time, descriptionId, change amount in accounts
// delete-cancel a transaction as long as it hasnt been approved
