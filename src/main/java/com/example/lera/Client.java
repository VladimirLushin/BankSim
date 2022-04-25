package com.example.lera;

public class Client {
    private String username;
    private String password;
    private String name;
    private String subname;
    private String address;
    private String money;
    private String create_date;
    private String change_date;
    private String IBAN;
    private String type;
    private String[] transactions;
    private String blockstatus;

    public Client(String username,String password,String name,String subname,String address,String money,String create_date,String change_date,String IBAN,String type,String[] transactions,String blockstatus){
        this.username = username;
        this.password = password;
        this.name = name;
        this.subname = subname;
        this.address = address;
        this.money = money;
        this.create_date = create_date;
        this.change_date = change_date;
        this.IBAN = IBAN;
        this.type = type;
        this.transactions = transactions;
        this.blockstatus = blockstatus;
    }
    public Client(){
        this.username = null;
        this.password = null;
        this.name = null;
        this.subname = null;
        this.address = null;
        this.money = "10000";
        this.create_date = null;
        this.change_date = null;
        this.IBAN = null;
        this.type = null;
        this.transactions = null;
        this.blockstatus = null;
    }

    //GETTERS
    public String getBlockStatus() {
        return blockstatus;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSubname() {
        return subname;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getType() {
        return type;
    }

    public String getMoney() {
        return money;
    }

    public String getAddress() {
        return address;
    }

    public String[] getTransactions() {
        return transactions;
    }

    public String getCreateDate() {
        return create_date;
    }

    public String getChangeDate() {
        return change_date;
    }


    //SETTERS
    public void setBlockStatus(String blockstatus) {
        this.blockstatus = blockstatus;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTransactions(String[] transactions) {
        this.transactions = transactions;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setChangeDate(String change_date) {
        this.change_date = change_date;
    }

}

