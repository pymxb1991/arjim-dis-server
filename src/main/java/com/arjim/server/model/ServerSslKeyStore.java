package com.arjim.server.model;

import java.io.Serializable;

public class ServerSslKeyStore implements Serializable {

    private static final long serialVersionUID = 4863057816051268749L;

    private String type ;
    private String password;
    private  String path;

    public ServerSslKeyStore() {
    }

    public ServerSslKeyStore(String type, String password, String path) {
        this.type = type;
        this.password = password;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
