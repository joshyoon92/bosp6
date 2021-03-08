package loginAccount;

import java.io.Serializable;

public class Order implements Serializable {
    private String name;
    private int gCount1;
    private int gCount2;
    private int gCount3;
    private int gCount4;

    public Order (String login, int gC1, int gC2, int gC3, int gC4) {
        name = login;
        gCount1 = gC1;
        gCount2 = gC2;
        gCount3 = gC3;
        gCount4 = gC4;
    }

    public String toString() {
        return name +" "+ gCount1 +" "+ gCount2 +" "+ gCount3 +" "+ gCount4;
    }



}
