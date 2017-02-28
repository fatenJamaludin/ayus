package com.app.tbd.ui.Model.Receive;

import java.util.List;

/**
 * Created by Dell on 9/13/2016.
 */
public class TransactionHistoryReceive {

    private String Message;
    private String Status;
    private List<Transaction> Transaction;



    public TransactionHistoryReceive() {

    }

    public TransactionHistoryReceive(TransactionHistoryReceive data) {
        Message = data.getMessage();
        Status = data.getStatus();
        Transaction = data.getTransaction();
    }


    public List<TransactionHistoryReceive.Transaction> getTransaction() {
        return Transaction;
    }

    public void setTransaction(List<TransactionHistoryReceive.Transaction> transaction) {
        Transaction = transaction;
    }

    public class Transaction{

        private String TxnDate;
        private String Description;
        private String PostDate;
        private String TxnTime;
        private String TxnPts;
        private String MID;
        private String TxnId;
        private String TxnAmt;

        public String getTxnDate() {
            return TxnDate;
        }

        public void setTxnDate(String txnDate) {
            TxnDate = txnDate;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPostDate() {
            return PostDate;
        }

        public void setPostDate(String postDate) {
            PostDate = postDate;
        }

        public String getTxnTime() {
            return TxnTime;
        }

        public void setTxnTime(String txnTime) {
            TxnTime = txnTime;
        }

        public String getTxnPts() {
            return TxnPts;
        }

        public void setTxnPts(String txnPts) {
            TxnPts = txnPts;
        }

        public String getMID() {
            return Description;
        }

        public void setMID(String MID) {
            this.MID = MID;
        }

        public String getTxnId() {
            return TxnId;
        }

        public void setTxnId(String txnId) {
            TxnId = txnId;
        }

        public String getTxnAmt() {
            return TxnAmt;
        }

        public void setTxnAmt(String txnAmt) {
            TxnAmt = txnAmt;
        }


        /*
        "TxnDate":"02\/09\/2016",
         "Description":"hello Test redeem",
         "PostDate":"02\/09\/2016",
         "TxnTime":"17:15",
         "TxnPts":-100,
         "MID":"BUDGET",
         "TxnId":38718962,
         "TxnAmt":300
        */

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
