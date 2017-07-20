package org.kondrak.archer.bot.dao.mappers;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.kondrak.archer.bot.model.Statistic;

import java.util.List;

public interface StatisticMapper {

    @Select({"SELECT users.username as USERNAME, count(*) as COUNT",
            "FROM message",
                "  JOIN users",
                "    ON message.author = users.user_id",
                "  JOIN channel",
                "    ON message.channel_id = channel.channel_id",
                "  JOIN guild",
                "    ON channel.parent = guild.guild_id",
                "WHERE UPPER(content) LIKE UPPER(CONCAT('%', CONCAT(#{word}, '%')))",
                "AND content NOT LIKE '%!word%'",
                "AND author <> '239471420470591498'",
                "AND guild.guild_id = #{guild}",
                "group by users.username"})
    @Results({
        @Result(column = "USERNAME", property = "key"),
        @Result(column = "COUNT", property = "value")
    })
    List<Statistic> getMessageCountsForGuildByUser(@Param("guild") String guild, @Param("word") String word);
}
