package template.demo.mybatis.entity;

/**
 * MySql:
 *
 * CREATE TABLE users(
 *   userName VARCHAR(255) NOT NULL PRIMARY KEY,
 *   password VARCHAR(255) NOT NULL,
 *   phone VARCHAR(255)
 * ) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
 *
 * Oracle:
 *
 * CREATE TABLE users(
 * userName VARCHAR2(255) NOT NULL PRIMARY KEY,
 * password VARCHAR2(255) NOT NULL,
 * phone VARCHAR2(255)
 * );
 *
 */
public class User {

    private String userName;

    private String password;

    private String phone;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
