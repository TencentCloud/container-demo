package demo.tcloud.triblewood.qcbm.store.persist.dao;

import org.apache.ibatis.annotations.*;

import demo.tcloud.triblewood.qcbm.store.model.Bookinfo;

import java.util.List;

public interface BookinfoMapper {

    @Select("SELECT * FROM bookinfo WHERE isbn = #{isbn}")
    @Results({
        @Result(property = "publishDate", column = "publish_date"),
        @Result(property = "briefIntro", column = "brief_intro"),
        @Result(property = "imgUrl", column = "img_url")
    })
    Bookinfo getByIsbn(@Param("isbn") Long isbn);

    @Select("SELECT * FROM bookinfo")
    @Results({
        @Result(property = "publishDate", column = "publish_date"),
        @Result(property = "briefIntro", column = "brief_intro"),
        @Result(property = "imgUrl", column = "img_url")
    })
    List<Bookinfo> getAll();

    @Select("SELECT * FROM bookinfo WHERE id = #{id}")
    @Results({
        @Result(property = "publishDate", column = "publish_date"),
        @Result(property = "briefIntro", column = "brief_intro"),
        @Result(property = "imgUrl", column = "img_url")
    })
    Bookinfo getById(Long id);

    @Insert("INSERT INTO bookinfo(isbn, title, author, press, publish_date, price, brief_intro, img_url) " +
            "VALUES(#{isbn}, #{title}, #{author}, #{press}, #{publishDate}, #{price}, #{briefIntro}, #{imgUrl})")
    int insert(Bookinfo bookinfo);

    @Update("UPDATE bookinfo SET isbn=#{isbn},title=#{title},author=#{author},press=#{press},publish_date=#{publishDate}," +
            "price=#{price},brief_intro=#{briefIntro} WHERE id =#{id}")
    int updateById(Bookinfo bookinfo);

    @Update("UPDATE bookinfo SET isbn=#{isbn},title=#{title},author=#{author},press=#{press},publish_date=#{publishDate}," +
            "price=#{price},brief_intro=#{briefIntro} WHERE isbn = #{isbn}")
    int updateByIsbn(Bookinfo bookinfo);

    @Delete("DELETE FROM bookinfo WHERE id =#{id}")
    int deleteByID(Long id);

    @Delete("DELETE FROM bookinfo WHERE isbn =#{isbn}")
    int deleteByIsbn(Long isbn);

}
