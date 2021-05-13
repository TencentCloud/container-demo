package demo.tcloud.triblewood.qcbm.store.model;

import lombok.Data;

import java.util.Date;


@Data
public class Bookinfo {

    private Long id;
    private Long isbn;
    private String title;
    private String author;
    private String press;
    private Date publishDate;
    private Float price;
    private String briefIntro;
    private String imgUrl;

}
