package demo.tcloud.triblewood.qcbm.common.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;


@Data
@AllArgsConstructor
public class QcbmPoint implements Serializable {

    public static final String ADD_POINT = "ADD";
    public static final String DEC_POINT = "DEC";

    private String op; // ADD, DEC
    private long userId;
    private int points;

}