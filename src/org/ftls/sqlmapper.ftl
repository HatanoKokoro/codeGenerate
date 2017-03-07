<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${pack}.dao.interfaces.I${ClassName}Dao">
	
	<resultMap type="${className}" id="baseResult">
		<id  property="${Pk}" column="${pk}" />
	<#list list as p>
		<result property="${p.humpColumnName}" column="${p.column_name}"/>
	</#list>
	</resultMap>
	
	<parameterMap type="${className}" id="baseParam">
		<parameter property="${Pk}"/>
	<#list list as p>
		<parameter property="${p.humpColumnName}"/>
	</#list>
	</parameterMap>
	
	<sql id="columns"> 
		${columns}
	</sql>
	
	<select id="pageList" resultType="java.util.Map">
		${selectPage}
	</select>
	
	<select id="pageTotalCount" resultType="long">
		${count}
	</select>
	
	<delete id="del" parameterType="int">
		${delete}
	</delete>
	
	<update id="modify" parameterType="java.util.Map">
		${update}
	</update>
	
	<insert id="insert" parameterType="java.util.Map" >
		${insert}
	</insert>
	
</mapper>
	