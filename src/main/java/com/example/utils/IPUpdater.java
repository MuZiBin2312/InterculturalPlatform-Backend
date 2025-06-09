package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.ResultSet;
import java.util.Enumeration;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;
import java.util.Map;

@Component
public class IPUpdater {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取真实的本机 IP 地址
     */
    private String getRealIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr instanceof Inet4Address) {
                        String ip = addr.getHostAddress();
                        if (!ip.startsWith("127")) {
                            return ip;
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return "localhost";
    }

    /**
     * 替换 user 表中 avatar 字段的 IP 地址为当前真实 IP
     */
    public void updateAvatarIp() {
        String currentIp = getRealIp();
        String oldIpPattern = "http://%";  // 匹配旧 IP 的通配符（模糊匹配所有）

        // 查询所有头像字段中包含旧 IP 的记录
        String selectSql = "SELECT id, avatar FROM user WHERE avatar LIKE ?";
        jdbcTemplate.query(selectSql, new Object[]{oldIpPattern}, rs -> {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String avatar = rs.getString("avatar");

                if (avatar != null && avatar.startsWith("http://") && avatar.contains("/files/")) {
                    String newAvatar = avatar;

                    if (avatar.contains("localhost")) {
                        // 替换 localhost 为真实 IP
                        newAvatar = avatar.replace("localhost", currentIp);
                    } else {
                        // 替换其它 IP（比如旧的内网 IP）
                        String filePath = avatar.substring(avatar.indexOf("/files/"));  // /files/xxx.png
                        newAvatar = "http://" + currentIp + ":9090" + filePath;
                    }

                    // 执行更新（如果值有变化才更新）
                    if (!newAvatar.equals(avatar)) {
                        String updateSql = "UPDATE user SET avatar = ? WHERE id = ?";
                        jdbcTemplate.update(updateSql, newAvatar, id);
                        System.out.println("用户 " + id + " 的 avatar 已更新为：" + newAvatar);
                    }
                }
            }
        });

        System.out.println("所有头像 IP 替换完成，当前使用 IP: " + currentIp);
    }
    public void updateIpInUrl(String tableName, String urlField, String ipPlaceholder, int port) {
        String currentIp = getRealIp();
        String oldIpPattern = "http://%";  // 模糊匹配所有 http 开头的url

        // 查询所有指定字段中包含旧 IP 的记录
        String selectSql = String.format("SELECT id, %s FROM %s WHERE %s LIKE ?", urlField, tableName, urlField);

        jdbcTemplate.query(selectSql, new Object[]{oldIpPattern}, (ResultSet rs) -> {
            int count = 0;
            while (rs.next()) {
                count++;
                Long id = rs.getLong("id");
                String url = rs.getString(urlField);

                System.out.println("处理第 " + count + " 条，id=" + id + ", url=" + url);
                String newUrl = url;
                if (url.contains(ipPlaceholder)) {
                    newUrl = url.replace(ipPlaceholder, currentIp);
                } else {
                    String filePath = url.substring(url.indexOf("/files/"));
                    newUrl = "http://" + currentIp + ":" + port + filePath;
                }

                if (!newUrl.equals(url)) {
                    String updateSql = String.format("UPDATE %s SET %s = ? WHERE id = ?", tableName, urlField);
                    jdbcTemplate.update(updateSql, newUrl, id);
                    System.out.println("表 " + tableName + " 中 ID " + id + " 的 " + urlField + " 已更新为：" + newUrl);
                }
            }
            System.out.println("总共处理 " + count + " 条数据");
            return null;
        });
        System.out.println("表 " + tableName + " 中字段 " + urlField + " 的 IP 替换完成，当前使用 IP: " + currentIp);
    }

}


