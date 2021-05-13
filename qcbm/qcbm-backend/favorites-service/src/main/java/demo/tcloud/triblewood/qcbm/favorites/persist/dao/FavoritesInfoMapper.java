package demo.tcloud.triblewood.qcbm.favorites.persist.dao;

import demo.tcloud.triblewood.qcbm.favorites.entity.FavoritesInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FavoritesInfoMapper {

    @Select("SELECT isbn FROM favorites WHERE user_id = #{userId}")
    List<Long> getUserFavoriteBooks(@Param("userId") Long userId);

    @Select("SELECT count(*) FROM favorites WHERE user_id = #{userId} AND isbn = #{isbn}")
    int checkFavorite(FavoritesInfo favoritesInfo);

    @Insert("INSERT INTO favorites(user_id, isbn) VALUES(#{userId}, #{isbn})")
    int insert(FavoritesInfo favoritesInfo);

    @Delete("DELETE FROM favorites WHERE user_id = #{userId} AND isbn = #{isbn}")
    int deleteFavorite(FavoritesInfo favoritesInfo);

}
