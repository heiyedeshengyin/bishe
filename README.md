# 毕业设计
学生健康管理监控系统  

### 项目简介
本项目采用Java语言编写，采用SpringBoot框架，
学生在该系统上需每天签到录入自己的健康数据，
管理员可以看到所有管理的学生的健康数据。
系统会自动分析学生健康数据，若有异常情况，会做出预警。

### 项目进展
目前已实现学生的登录，注册，签到，查看签到记录，修改个人信息，查看班级及学院信息等功能。  
目前已实现管理员的登录，注册，查看学生签到记录，查看管理的学生信息，修改个人信息，查看班级及学院信息等功能。

### 数据库设计
#### 学院表
```mysql
create table college (
    college_id int primary key auto_increment,
    college_name varchar(64),
    is_college_delete bool not null default false
);
```
#### 班级表
```mysql
create table class (
    class_id int primary key auto_increment,
    class_name varchar(64),
    is_class_delete bool not null default false,
    class_college_id int,
    constraint class_college_fk foreign key (class_college_id) references college(college_id)
);
```
#### 学生表
```mysql
create table student (
    student_id int primary key auto_increment,
    student_login_name varchar(128) unique not null,
    student_password varchar(128) not null,
    student_name varchar(64),
    student_phone varchar(64),
    student_wechat varchar(64),
    student_qq varchar(64),
    student_birthday date,
    student_height varchar(64),
    student_weight varchar(64),
    student_gender int not null default 0,
    is_student_delete bool not null default false,
    student_class_id int,
    constraint student_class_fk foreign key (student_class_id) references class(class_id)
);
```
#### 管理员表
```mysql
create table admin (
    admin_id int primary key auto_increment,
    admin_login_name varchar(128) unique not null,
    admin_password varchar(128) not null,
    admin_name varchar(64),
    admin_phone varchar(64),
    admin_wechat varchar(64),
    admin_qq varchar(64),
    admin_birthday date,
    admin_gender int not null default 0,
    is_admin_delete bool not null default false,
    admin_class_id int,
    constraint admin_class_fk foreign key (admin_class_id) references class(class_id)
);
```
#### 签到表
```mysql
create table checked (
    checked_id int primary key auto_increment,
    checked_time datetime,
    checked_temperature varchar(64),
    is_checked_delete bool not null default false,
    checked_student_id int,
    constraint checked_student_fk foreign key (checked_student_id) references student(student_id)
);
```