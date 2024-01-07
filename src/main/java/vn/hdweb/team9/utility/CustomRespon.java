package vn.hdweb.team9.utility;

import lombok.Data;

@Data
public class CustomRespon {
    private String message = "";
    private Object data;
    private int status = 200;
}
