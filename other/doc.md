# 面向对象分析与设计项目文档

## 选题及成员

选题：图书管理系统

| 学号 | 姓名 | 分工 |
| -------- | ---- | ---- |
| ... | ... | ...  |

## 目录

[TOC]

## 需求分析

### 图书搜索

可以直接在搜索框搜索。用户可以输入书名进行模糊搜索，也可按图书类型搜索。使用搜索功能不需要登录。

### 登录

用户用 id 和密码登录。登录之后可以查看账户信息，借阅信息，借还书。另外，管理员登录之后还可以进行用户管理及图书管理

### 借书

用户登录之后，可以进行借书。若图书在馆，则可以借阅成功。若图书已被借出，则借阅失败。

### 还书

用户登录之后，可以访问个人页面，对已借阅的图书可以进行还书操作。

### 个人信息查询

用户登录之后，可以访问个人页面查询个人信息。

### 用户管理

管理员登录之后，可以访问个人页面，进行用户管理。可以

- 改变现有用户的等级（普通，高级，管理员）。
- 增加新用户
- 删除用户

### 图书管理

管理员登录之后，可以访问个人页面，进行图书管理。可以

- 增加新图书
- 删除图书（还未归还的图书不可删除）

## 用例分析

### 用例图

![usecases](imgs/usecases.jpg)

### 用例分析文档

![usecasedoc.1](imgs/usecasedoc.1.png)

![usecasedoc.2](imgs/usecasedoc.2.png)

![usecasedoc.3](imgs/usecasedoc.3.png)

![usecasedoc.4](imgs/usecasedoc.4.png)

![usecasedoc.5](imgs/usecasedoc.5.png)

![usecasedoc.6](imgs/usecasedoc.6.png)

![usecasedoc.7](imgs/usecasedoc.7.png)

![usecasedoc.8](imgs/usecasedoc.8.png)

![usecasedoc.9](imgs/usecasedoc.9.png)

## 实体类类图

### 名词分析法

对于上述每个用例

![classanalysis.1](imgs/classanalysis.1.png)

![classanalysis.2](imgs/classanalysis.2.png)

![classanalysis.3](imgs/classanalysis.3.png)

![classanalysis.4](imgs/classanalysis.4.png)

![classanalysis.5](imgs/classanalysis.5.png)

![classanalysis.6](imgs/classanalysis.6.png)

![classanalysis.7](imgs/classanalysis.7.png)

![classanalysis.8](imgs/classanalysis.8.png)

![classanalysis.9](imgs/classanalysis.9.png)

### 仅带有属性的实体类类图

![实体类类图](./类图/实体类图.png)

## 设计模式


在User类及其子类Admin中, 使用了抽象工厂的设计模式
```Java
@Data
public class User{
    
    protected String id;
    
    protected String name;
    
    protected transient String password;
    
    protected UserType userType;
    
    protected final static UserFactory factory = new UserFactory();
    
    public static UserBuilder createNormalUserBuilder(){
        return factory.createNormalUser();
    }
    
    public static UserBuilder createAdvanceUserBuilder(){
        return factory.createAdvanceUser();
    }
    
    ...
}

@Data
public class Admin extends User{
    
    protected UserType userType = UserType.ADMIN;
    
    public static UserBuilder createAdminUser(){
        return factory.createAdminUser();
    }
    
}
```

在这里使用抽象工厂方法主要是为了屏蔽UserBuilder类的userType(UserType type)方法.
这样做可以屏蔽掉普通用户想要创建Admin用户的风险.

```Java
public class UserBuilder{
    
    User user;
    
    UserBuilder(){
        user = new User();
    }
    
    public UserBuilder id(String o){
        user.setId(o);
        return this;
    }
    
    public UserBuilder name(String name){
        user.setName(name);
        return this;
    }
    
    public UserBuilder password(String password){
        user.setPassword(password);
        return this;
    }
    
    UserBuilder type(UserType userType){
        user.setUserType(userType);
        return this;
    }
    
    public User build(){
        return this.user;
    }
    
}
```

## 顺序图

### 登录
ok

### 查看个人信息

### 查看已借阅图书

### 检查是否为Admin

### 所有用户列表

### 所有图书列表

### 增加图书

### 删除图书

### 增加用户
ok

### 删除用户
ok

### 改变用户的类型

### feelLucky

### 查找图书

### 借阅图书

### 归还图书




## 完整类图

### user 类图
![user 类图](./类图/user类图.png)

### book 类图
![book 类图](./类图/book类图.png)

### book 类图
![web 类图](./类图/web类图.png)

## 项目实现

详见 [github项目地址](https://github.com/Shynoo/oo-project-server/tree/frontend)


## 测试

### 借书测试及活动图

```Java
@Test
public void borrowBookTest(){
    userService.borrowBook("1","CS121");
    Book book=bookService.getBookById("CS121");
    List ls=userService.getUserAllBorrowingBooks("1");
    Assert.assertTrue(ls.contains(book));
    Assert.assertTrue(book.getBookStatus().equals(BookStatus.IN_LIBIRARY));
}
```

![活动图](./Test活动图/borrowBookTest活动图.png)

### 用户登录测试及活动图

```Java
@Test
public void userLogInTest(){
    String id = "1";
    String password = "123";
    Result r = sampleConreoller.doLogin(id, password);
    User u = (User) r.get();
    Assert.assertEquals(u, userService.getUserById(id));
    Assert.assertTrue(userService.isOnline(u);
}
```

![活动图](./Test活动图/userLogInTest活动图.png)

## 可扩展性分析

### 加入修改图书信息的功能
功能描述: 管理员可以修改图书的信息, 如图书name, type等.

### 解决方案
1. 页面提供一个功能的入口, 让用户提交图书id和图书的新信息. 
2. controller收到信息后调用userService检查用户是否有修改图书信息的权限.
3. controller调用bookService的updateBookInfo(Book book)方法.
4. bookService调用bookDao的updateBook(Book book)方法
5. 修改图书信息成功 
