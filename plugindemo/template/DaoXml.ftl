<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packagename}.${beanName}Mapper">

<#assign allColumns = (primaryKeys + columns)>
    <resultMap id="BaseResultMap" type="${beanPackage}.${beanName}">
    <#if primaryKeys?exists>
        <#list primaryKeys as pk>
            <id column="${pk.name}" jdbcType="${pk.type}" property="${pk.bean.name}"/>
        </#list>
    </#if>
    <#if columns?exists>
        <#list columns as column>
            <result column="${column.name}" jdbcType="${column.type}" property="${column.bean.name}"/>
        </#list>
    </#if>
    </resultMap>

    <sql id="Base_Column_List">
    <#list allColumns?chunk(5) as list>
        <#list list as colo>${colo.name}<#sep>,</#list><#sep>,
    </#list>

    </sql>

<#if primaryKeys?exists>
    <select id="select" parameterType="java.lang.Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where <#list primaryKeys as pk><#if pk?index == 0>${pk.name} = ${r'#'}{${pk.bean.name}}<#else>            and ${pk.name} = ${r'#'}{${pk.bean.name}}</#if>
    </#list>
    </select>
</#if>

<#if primaryKeys?exists>
    <update id="update" parameterType="${beanPackage}.${beanName}">
        update ${tableName}
        set <#list columns as cp><#if cp?index == 0>${cp.name} = ${r'#'}{${cp.bean.name}}<#else>            ${cp.name} = ${r'#'}{${cp.bean.name}}</#if><#sep>,
    </#list>

        where <#list primaryKeys as pk><#if pk?index == 0>${pk.name} = ${r'#'}{${pk.bean.name}}<#else>            and ${pk.name} = ${r'#'}{${pk.bean.name}}</#if></#list>
    </update>
</#if>

<#if primaryKeys?exists>
    <delete id="delete" parameterType="java.lang.Long">
        delete from ${tableName}
        where <#list primaryKeys as pk><#if pk?index == 0>${pk.name} = ${r'#'}{${pk.bean.name}}<#else>            and ${pk.name} = ${r'#'}{${pk.bean.name}}</#if>
    </#list>
    </delete>
</#if>

    <insert id="insert" parameterType="${beanPackage}.${beanName}">
        insert into ${tableName} (
    <#list allColumns?chunk(5) as list>
        <#list list as colo>${colo.name}<#sep>,</#list><#sep>,
    </#list>

        )
        values (
    <#list allColumns?chunk(3) as part>
        <#list part as pc>${r'#'}{${pc.bean.name},jdbcType=${pc.type}}<#sep>,</#list><#sep>,
    </#list>

        )
    </insert>

    <insert id="insertList" parameterType="java.util.List">
        insert into ${tableName} (
    <#list allColumns?chunk(5) as list>
        <#list list as colo>${colo.name}<#sep>,</#list><#sep>,
    </#list>
        )
        values
        <foreach collection="list" item="one" separator=",">
            (
        <#list allColumns?chunk(3) as part>
            <#list part as pc>${r'#'}{one.${pc.bean.name},jdbcType=${pc.type}}<#sep>,</#list><#sep>,
        </#list>

            )
        </foreach>
    </insert>

    <select id="getsByMap" parameterType="java.util.HashMap" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where 1=1
        <foreach collection="map.keys" item="_itemKey" open="" separator="">
            <if test="null != map[_itemKey]">
                and ${r'${_itemKey}'} = ${r'#{map[${_itemKey}]}'}
            </if>
        </foreach>
    </select>
</mapper>