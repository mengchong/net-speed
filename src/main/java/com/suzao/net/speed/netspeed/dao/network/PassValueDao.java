package com.suzao.net.speed.netspeed.dao.network;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name PassValueDao
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Mapper
@Repository
public interface PassValueDao extends BaseMapper<PassValueBo> {

    int insert(List<PassValueBo> bo);

    PassValueBo selectAll();
}
