package org.kondrak.archer.bot.configuration;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ConfigMapper {

    @Select({
            "SELECT configuration_assignment.assignment_id as assignment_id,",
                    "configuration_assignment.configuration_id as configuration_id,",
                    "configuration_assignment.configuration_scope as configuration_scope,",
                    "configuration_assignment.configuration_fkey as configuration_fkey ,",
                    "configuration.configuration_name as configuration_name,",
                    "configuration.configuration_data_type as configuration_data_type,",
                    "configuration.configuration_value as configuration_value",
            "FROM configuration_assignment",
            "JOIN configuration",
            "ON configuration_assignment.configuration_id = configuration.configuration_id",
            "WHERE configuration.configuration_name = #{type}",
            "AND configuration_assignment.configuration_scope = #{scope}",
            "AND configuration_assignment.configuration_fkey = #{fkey}"
    })
    @Results({
            @Result(column = "assignment_id", property = "assignmentId"),
            @Result(column = "configuration_id", property = "configurationId"),
            @Result(column = "configuration_scope", property = "scope"),
            @Result(column = "configuration_fkey", property = "assignedKey"),
            @Result(column = "configuration_name", property = "configType"),
            @Result(column = "configuration_data_type", property = "configDatatype"),
            @Result(column = "configuration_value", property = "configValue")
    })
    List<Configuration> getConfigurationByNameScopeAndType(@Param("type") ConfigType type, @Param("scope") ConfigScope scope,
            @Param("fkey") String fkey);

    @Insert({"INSERT INTO () VALUES ()"})
    void addConfiguration(@Param("parameter") ConfigType parameter, @Param("scope") ConfigScope scope, @Param("msg") String msg);
}
