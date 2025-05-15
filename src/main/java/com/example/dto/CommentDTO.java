package com.example.dto;

public class CommentDTO {
    // —— 评论信息 ——
    private Integer id;
    private String content;
    private Integer userId;
    private Integer pid;
    private String time;
    private Integer fid;
    private String module;
    private Integer rootId;

    // —— 用户信息 ——
    private String userName;
    private String avatar;
    private String role;
    private String parentUserName;

    // —— 新闻信息 ——
    private Integer newsId;       // 实际就是fid
    private String title;
    private String descr;
    private String category;

    // —— 分类信息（一级分类） ——
    private String topCategoryName; // 一级分类名

    public CommentDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Integer getRootId() {
        return rootId;
    }

    public void setRootId(Integer rootId) {
        this.rootId = rootId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getParentUserName() {
        return parentUserName;
    }

    public void setParentUserName(String parentUserName) {
        this.parentUserName = parentUserName;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTopCategoryName() {
        return topCategoryName;
    }

    public void setTopCategoryName(String topCategoryName) {
        this.topCategoryName = topCategoryName;
    }
// getter/setter...

    public CommentDTO(Integer id, String content, Integer userId, Integer pid, String time, Integer fid, String module, Integer rootId, String userName, String avatar, String role, String parentUserName, Integer newsId, String title, String descr, String category, String topCategoryName) {

        this.id = id;
        this.content = content;
        this.userId = userId;
        this.pid = pid;
        this.time = time;
        this.fid = fid;
        this.module = module;
        this.rootId = rootId;
        this.userName = userName;
        this.avatar = avatar;
        this.role = role;
        this.parentUserName = parentUserName;
        this.newsId = newsId;
        this.title = title;
        this.descr = descr;
        this.category = category;
        this.topCategoryName = topCategoryName;
    }
}
