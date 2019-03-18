package project3;

import java.math.BigInteger;

/**
 * @author Han Zhang
 * 95771 HW3 - modified RedBlackNode.
 *
 * This RedBlackNode is able to handle String and BigInteger pairs.
 */
public class RedBlackNode {
    public static final int BLACK = 0;
    public static final int RED =1;
    private String data;
    private BigInteger number;
    private int color;
    private RedBlackNode p;
    private RedBlackNode lc;
    private RedBlackNode rc;

    public RedBlackNode(String data, BigInteger number, int color, RedBlackNode p, RedBlackNode lc, RedBlackNode rc){
        this.data = data;
        this.color = color;
        this.p = p;
        this.lc = lc;
        this.rc = rc;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("[data = %s: BigInteger = %s: Color = %s: Parent = %s: LC = %s: RC = %s",
                data,number.toString(), (color== RedBlackNode.RED)?"Red":"Black",p.getData(), lc.getData(), rc.getData());
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public RedBlackNode getP() {
        return p;
    }

    public void setP(RedBlackNode p) {
        this.p = p;
    }

    public RedBlackNode getLc() {
        return lc;
    }

    public void setLc(RedBlackNode lc) {
        this.lc = lc;
    }

    public RedBlackNode getRc() {
        return rc;
    }

    public void setRc(RedBlackNode rc) {
        this.rc = rc;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }


}
