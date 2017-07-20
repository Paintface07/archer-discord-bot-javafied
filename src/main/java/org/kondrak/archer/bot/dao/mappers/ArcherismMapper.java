package org.kondrak.archer.bot.dao.mappers;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.kondrak.archer.bot.model.Archerism;

import java.util.List;

public interface ArcherismMapper {
    @Select("SELECT trigger_tx, msg_tx FROM archerisms WHERE trigger_tx = '!archerism'")
    @ConstructorArgs({
            @Arg(column = "trigger_tx", javaType = String.class),
            @Arg(column = "msg_tx", javaType = String.class)
    })
    List<Archerism> getAllArcherisms();
}
