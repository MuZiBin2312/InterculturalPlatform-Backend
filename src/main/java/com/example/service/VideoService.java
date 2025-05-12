package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Video;
import com.example.mapper.VideoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 视频信息业务处理
 **/
@Service
public class VideoService {

    @Resource
    private VideoMapper videoMapper;

    /**
     * 新增
     */
    public void add(Video video) {
        video.setTime(DateUtil.now());
        videoMapper.insert(video);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        videoMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            videoMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Video video) {
        videoMapper.updateById(video);
    }

    /**
     * 根据ID查询
     */
    public Video selectById(Integer id) {
        return videoMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Video> selectAll(Video video) {
        return videoMapper.selectAll(video);
    }

    /**
     * 分页查询
     */
    public PageInfo<Video> selectPage(Video video, Integer pageNum, Integer pageSize, Integer userId) {
        PageHelper.startPage(pageNum, pageSize);
        System.out.println("用户id"+userId+"class:"+userId.getClass());
        if (userId != -1) {
            video.setUserId(userId); // 假设 Video 类中有 userId 字段及其 setter 方法
        }else {
            video.setUserId(null);
        }

        List<Video> list = videoMapper.selectAll(video);
        return PageInfo.of(list);
    }

    public void updateCount(Integer id) {
        videoMapper.updateCount(id);
    }
}