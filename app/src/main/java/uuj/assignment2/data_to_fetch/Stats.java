
package uuj.assignment2.data_to_fetch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("p2")
    @Expose
    private P2 p2;
    @SerializedName("p10")
    @Expose
    private P10 p10;
    @SerializedName("p9")
    @Expose
    private P9 p9;
    @SerializedName("curr_p2")
    @Expose
    private CurrP2 currP2;
    @SerializedName("curr_p10")
    @Expose
    private CurrP10 currP10;
    @SerializedName("curr_p9")
    @Expose
    private CurrP9 currP9;

    public P2 getP2() {
        return p2;
    }

    public void setP2(P2 p2) {
        this.p2 = p2;
    }

    public P10 getP10() {
        return p10;
    }

    public void setP10(P10 p10) {
        this.p10 = p10;
    }

    public P9 getP9() {
        return p9;
    }

    public void setP9(P9 p9) {
        this.p9 = p9;
    }

    public CurrP2 getCurrP2() {
        return currP2;
    }

    public void setCurrP2(CurrP2 currP2) {
        this.currP2 = currP2;
    }

    public CurrP10 getCurrP10() {
        return currP10;
    }

    public void setCurrP10(CurrP10 currP10) {
        this.currP10 = currP10;
    }

    public CurrP9 getCurrP9() {
        return currP9;
    }

    public void setCurrP9(CurrP9 currP9) {
        this.currP9 = currP9;
    }

}
