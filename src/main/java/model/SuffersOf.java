package model;

import com.google.gson.annotations.SerializedName;

public class SuffersOf {

    public SuffersOf(){};
    public SuffersOf(Integer IdU,Integer IdI){
    this.IdI = IdI;
    this.IdU = IdU;
    }

    @SerializedName("idu")
    private Integer IdU;

    /**
     *
     */
    @SerializedName("idi")
    private Integer IdI;

    public Integer getIdU() {
        return IdU;
    }

    public void setIdU(Integer idU) {
        IdU = idU;
    }

    public Integer getIdI() {
        return IdI;
    }

    public void setIdI(Integer idI) {
        IdI = idI;
    }

    @Override
    public String toString() {
        return "SuffersOf{" +
                "IdU=" + IdU +
                ", IdI=" + IdI +
                '}';
    }
}
