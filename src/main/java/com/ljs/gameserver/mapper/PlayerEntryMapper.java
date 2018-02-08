package com.ljs.gameserver.mapper;

import com.ljs.gameserver.entry.PlayerEntry;
import com.ljs.gameserver.entry.PlayerEntrySimpleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerEntryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table players
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table players
     *
     * @mbg.generated
     */
    int insert(PlayerEntry record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table players
     *
     * @mbg.generated
     */
    PlayerEntry selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table players
     *
     * @mbg.generated
     */
    List<PlayerEntry> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table players
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PlayerEntry record);

    List<PlayerEntrySimpleInfo> selectAcountPlayerList(String accountId);
}