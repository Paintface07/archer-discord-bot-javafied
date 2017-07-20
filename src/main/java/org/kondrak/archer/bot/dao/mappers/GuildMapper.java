package org.kondrak.archer.bot.dao.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface GuildMapper {
    @Select("SELECT 1 FROM guild WHERE guild_id = #{guildId}")
    Integer guildExists(@Param("guildId") String guildId);

    @Insert({"INSERT INTO guild (guild_id, name, icon, iconurl, owner_id) VALUES (?, ?, ?, ?, ?)"})
    void insertGuild(@Param("guildId") String guildId, @Param("name") String name, @Param("icon") String icon,
                     @Param("iconUrl") String iconUrl, @Param("ownerId") String ownerId);
}
