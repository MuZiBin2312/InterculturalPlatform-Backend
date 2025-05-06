package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 基础前端接口
 */
@RestController
@CrossOrigin

public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    UserService userService;
    @Resource
    QuestionService questionService;
    @Resource
    FeedbackService feedbackService;
    @Resource
    NewsService newsService;
    @Resource
    VideoService videoService;
    @Resource
    AnswerService answerService;
    @Resource
    CategoryService categoryService;


    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        } else if (RoleEnum.USER.name().equals(account.getRole())) {
            account = userService.login(account);
        }
        return Result.success(account);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        } else {
            throw new CustomException(ResultCodeEnum.PARAM_ERROR);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

    @GetMapping("/count")
    public Result selectCount() {
        List<News> news = newsService.selectAll(null);
        List<Question> questions = questionService.selectAll(null);
        List<Feedback> feedbacks = feedbackService.selectAll(null);
        List<User> users = userService.selectAll(null);
        Dict dict = Dict.create()
                .set("newsCount", news.size())
                .set("questionCount", questions.size())
                .set("feedbackCount", feedbacks.size())
                .set("userCount", users.size());
        return Result.success(dict);
    }

    @GetMapping("/selectLine")
    public Result selectLine() {
        List<Answer> answerList = answerService.selectAll(null);
        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -8);
        DateTime end = DateUtil.offsetDay(date, -1);
        List<String> dateList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR).stream()
                .map(DateUtil::formatDate).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        List<Dict> list = new ArrayList<>();
        for (String dateStr : dateList) {
            long count = answerList.stream().filter(answer -> answer.getTime().contains(dateStr)).count();
            Dict dict = Dict.create().set("name", dateStr).set("value", count);
            list.add(dict);
        }
        return Result.success(list);
    }

    @GetMapping("/selectPie")
    public Result selectPie() {
// 1. 查询所有目录信息，构建 id -> name 的映射
        List<Category> categoryList = categoryService.findAll();
        Map<Integer, String> categoryMap = categoryList.stream()
                .collect(Collectors.toMap(Category::getId, Category::getName));
// 2. 获取资讯数据并过滤类型为 "common"
        List<News> newsList = newsService.selectAll(null);
        newsList = newsList.stream()
                .filter(news -> "common".equals(news.getType()))
                .collect(Collectors.toList());

// 3. 统计每个目录ID出现的次数
        Map<Integer, Long> countMap = newsList.stream()
                .collect(Collectors.groupingBy(news -> Integer.valueOf(news.getCategory()), Collectors.counting()));
// 4. 把目录ID和对应名称组装为最终结果
        List<Dict> list = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : countMap.entrySet()) {
            Integer id = entry.getKey();
            String name = categoryMap.getOrDefault(id, "未知分类");
            Long count = entry.getValue();

            Dict dict = Dict.create().set("name", name).set("value", count);
            list.add(dict);
        }

        return Result.success(list);
    }

    @GetMapping("/selectTrendData")
    public Result selectTrendData() {
        List<News> newsList = newsService.selectAll(null);
        List<Video> videoList = videoService.selectAll(null);

        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -9); // 含今天，近10天
        DateTime end = DateUtil.offsetDay(date, 0);

        List<String> dateList = DateUtil.rangeToList(start, end, DateField.DAY_OF_YEAR).stream()
                .map(DateUtil::formatDate)
                .collect(Collectors.toList());

        List<Dict> newsData = new ArrayList<>();
        List<Dict> videoData = new ArrayList<>();

        for (String dateStr : dateList) {
            long newsCount = newsList.stream()
                    .filter(news -> StrUtil.isNotBlank(news.getTime()))
                    .filter(news -> news.getTime().contains(dateStr))
                    .count();
            long videoCount = videoList.stream()
                    .filter(video -> StrUtil.isNotBlank(video.getTime()))
                    .filter(video -> video.getTime().contains(dateStr))
                    .count();

            newsData.add(Dict.create().set("name", dateStr).set("value", newsCount));
            videoData.add(Dict.create().set("name", dateStr).set("value", videoCount));
        }

        Dict result = Dict.create()
                .set("news", newsData)
                .set("video", videoData)
                .set("dates", dateList);  // 可选：方便前端统一横轴

        return Result.success(result);
    }

    @GetMapping("/selectTopReads")
    public Result selectTopReads() {
        List<News> newsList = newsService.selectAll(null);
        List<Video> videoList = videoService.selectAll(null);

        // 处理新闻：按 read_count 倒序取前20
        List<Dict> topNews = newsList.stream()
                .filter(news -> news.getReadCount() != null)
                .sorted(Comparator.comparing(News::getReadCount).reversed())
                .limit(20)
                .map(news -> Dict.create()
                        .set("name", news.getTitle())       // 横轴用标题
                        .set("value", news.getReadCount())  // 对应点击量
                ).collect(Collectors.toList());

        // 处理视频：按 read_count 倒序取前20
        List<Dict> topVideos = videoList.stream()
                .filter(video -> video.getReadCount() != null)
                .sorted(Comparator.comparing(Video::getReadCount).reversed())
                .limit(20)
                .map(video -> Dict.create()
                        .set("name", video.getName())        // 横轴用视频名称
                        .set("value", video.getReadCount())  // 对应点击量
                ).collect(Collectors.toList());

        // 返回统一结构
        Dict result = Dict.create()
                .set("news", topNews)
                .set("video", topVideos);

        return Result.success(result);
    }


    // 获取点击量与时间（按小时）的散点图数据
    // 获取点击量与时间（按小时）的散点图数据
    @GetMapping("/selectClickTrendDataByHour")
    public Result selectClickTrendDataByHour() {
        // 获取所有文章数据
        List<News> newsList = newsService.selectAll(null);
        // 获取所有视频数据
        List<Video> videoList = videoService.selectAll(null);

        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 过去24小时的时间范围
        LocalDateTime start = now.minusHours(23); // 当前时间 - 23小时，得到的时间就是24小时内的最早时刻
        LocalDateTime end = now;  // 当前时间

        // 格式化时间，精确到小时
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

        // 获取过去24小时的小时列表
        List<String> hourList = new ArrayList<>();
        for (LocalDateTime time = start; time.isBefore(end.plusHours(1)); time = time.plusHours(1)) {
            hourList.add(formatter.format(time)); // 格式化为 "yyyy-MM-dd HH"
        }

        // 存放点击量数据
        List<Dict> newsClickData = new ArrayList<>();
        List<Dict> videoClickData = new ArrayList<>();

        // 遍历每个小时，统计每小时的文章点击量和视频点击量
        for (String hourStr : hourList) {
            // 统计每小时的文章点击量总和
            long newsClickCount = newsList.stream()
                    .filter(news -> StrUtil.isNotBlank(news.getTime()))
                    .filter(news -> news.getTime().startsWith(hourStr)) // 使用 startsWith 来匹配准确的小时
                    .mapToLong(news -> news.getReadCount()) // 假设 News 类中有 getReadCount() 方法
                    .sum();

            // 统计每小时的视频点击量总和
            long videoClickCount = videoList.stream()
                    .filter(video -> StrUtil.isNotBlank(video.getTime()))
                    .filter(video -> video.getTime().startsWith(hourStr)) // 使用 startsWith 来匹配准确的小时
                    .mapToLong(video -> video.getReadCount()) // 假设 Video 类中有 getReadCount() 方法
                    .sum();

            // 将统计结果放入字典中
            newsClickData.add(Dict.create().set("name", hourStr).set("value", newsClickCount));
            videoClickData.add(Dict.create().set("name", hourStr).set("value", videoClickCount));
        }

        // 将所有结果打包成一个字典返回给前端
        Dict result = Dict.create()
                .set("newsClickData", newsClickData)
                .set("videoClickData", videoClickData)
                .set("hours", hourList);  // 可选：方便前端统一横轴

        // 返回成功的结果
        return Result.success(result);

    }
}
