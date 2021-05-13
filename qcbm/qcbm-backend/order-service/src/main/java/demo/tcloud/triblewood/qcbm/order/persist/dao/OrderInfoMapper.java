package demo.tcloud.triblewood.qcbm.order.persist.dao;

import demo.tcloud.triblewood.qcbm.order.model.OrderInfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface OrderInfoMapper {

    @Insert("INSERT INTO orders(user_id, user_name, isbn, book_title, author, price, purchase_date)" +
            "VALUES(#{userId}, #{userName}, #{isbn}, #{bookTitle}, #{author}, #{price}, #{purchaseDate})")
    int insert(OrderInfo orderInfo);


    @Select("SELECT * FROM orders WHERE user_id=#{userId}")
    @Results({
        @Result(property = "userId", column = "user_id"),
        @Result(property = "userName", column = "user_name"),
        @Result(property = "bookTitle", column = "book_title"),
        @Result(property = "purchaseDate", column = "purchase_date")
    })
    List<OrderInfo> selectByUserId(Long userId);
}