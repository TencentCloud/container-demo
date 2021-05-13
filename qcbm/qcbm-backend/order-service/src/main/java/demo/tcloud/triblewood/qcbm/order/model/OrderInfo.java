package demo.tcloud.triblewood.qcbm.order.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OrderInfo {

    private Long id;
    private Long userId;
    private String userName;
    private Long isbn;
    private String bookTitle;
    private String author;
    private Float price;
    private Date purchaseDate;
}