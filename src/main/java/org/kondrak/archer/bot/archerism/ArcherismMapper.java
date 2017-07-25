package org.kondrak.archer.bot.archerism;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Select;
import org.kondrak.archer.bot.archerism.Archerism;

import java.util.List;

public interface ArcherismMapper {
    @Select("SELECT trigger_tx, msg_tx FROM archerisms WHERE trigger_tx = '!archerism'")
    @ConstructorArgs({
            @Arg(column = "trigger_tx", javaType = String.class),
            @Arg(column = "msg_tx", javaType = String.class)
    })
    List<Archerism> getAllArcherisms();
}
