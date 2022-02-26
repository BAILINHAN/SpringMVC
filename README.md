# SpringMVC学习笔记
## 目录
[toc]

---
## 一、web.xml文件
- 在目录/src/main 下新建文件夹webapp,在Project Structure中为对应的model添加web模块，并添加web.xml配置文件，可以在其中配置Spring的配置信息

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!-- 配置SringMVC的前端控制器,对浏览器发送的请求进行统一处理 -->
    <servlet>
        <servlet-name>SpringMVC</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!-- classpath 对应的是类路径 -->
            <param-value>classpath:springMVC.xml</param-value>
        </init-param>
        <!-- 将前端控制器DispatcherServlet的初始化时间提前到服务器启动时 -->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>SpringMVC</servlet-name>
        <!--
            设置SpringMVC的核心控制器所能处理的请求的请求路径
            /所匹配的请求可以是/login或.html或.js或.css方式的请求路径
            但是/不能匹配.jsp请求的请求路径
            因为.jsp本身上就是一个Servlet,不需要DispatcherServlet来处理,有特殊的Servlet来处理
        -->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```
---

## 二、将控制器作为SpringMVC中的一个IOC组件
**1. 通过注解 + 扫描的方式来配置控制器**

    - @Component 标识成一个普通组件
    - @Controller 标识成一个控制层组件
    - @Service 标识成一个业务层组件
    - @Repository 标识成一个持久层组件

**2. 配置视图解析器**

- 在springMVC.xml中配置视图解析器Thymeleaf
- 在springMVC.xml中添加扫描组件<context:component-scan />

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 扫描组件 -->
    <context:component-scan base-package="com.solo.controller"></context:component-scan>

    <!-- 配置Thymeleaf视图解析器 -->
    <bean id="viewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
        <property name="order" value="1"></property>
        <property name="characterEncoding" value="UTF-8"></property>
        <property name="templateEngine">
            <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                <property name="templateResolver">
                    <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                        <!-- 视图前缀 -->
                        <property name="prefix" value="/WEB-INF/templates/"></property>
                        <!-- 视图后缀 -->
                        <property name="suffix" value=".html"></property>
                        <property name="templateMode" value="HTML5"></property>
                        <property name="characterEncoding" value="UTF-8"></property>
                    </bean>
                </property>
            </bean>
        </property>
    </bean>

</beans>
```

**3. 通过thymeleaf解决浏览器解析的绝对路径**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h1>首页</h1>
    <!-- 通过 th:href="@{/target}" 解决访问的绝对路径问题 -->
    <a th:href="@{/target}">访问目标页面target</a>
</body>
</html>
```

**4.get请求和post请求的区别**
```text
Get: 请求后，请求参数会拼接在请求路径后，不安全，请求快，请求数据量少
Post: 请求参数会放在请求体中，安全，请求慢，请求数据量大
```

**5.RequestMapping中method属性**
```text
如果不设置method属性，则不以请求方式区分请求，post和get均可请求成功
405——请求方式不被支持
```

**6.RequestMapping注解的headers属性**
```text
@RequestMapping注解的headers属性通过请求的请求头信息匹配请求映射
@RequestMapping注解的headers属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系
"header":要求请求映射所匹配的请求必须携带header请求头信息
"!header":要求请求映射所匹配的请求必须不携带header请求头信息
"header=value":要求请求映射所匹配的请求必须携带header请求头信息且header=value
"header!=value":要求请求映射所匹配的请求必须header请求头信息且header!=value

若当前请求满足@RequestMapping注解的value和method属性，但是不满足headers属性，此时页面显示404报错，即资源未找到
```

**7.SpringMVC支持ant风格的路径**
```text
?: 表示任意单个字符
*: 表示任意0个或多个字符
**: 表示一层或多层目录
注意：在使用**时，只能使用/**/xxx的方式
```

**8.SpringMVC中支持路径中的占位符(重点)**
```text
原始方式: /deleteUser?Id=1
rest方式: /deleteUser/1

SpringMVC路径中的占位符常用于restful风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，再通过@PathVariable注解，将占位符所表示的数据赋值给传参控制器方法的形参


```

# 三、注解
## @RequestParam
```java
@RequestParam 是将请求参数和控制器方法的形参创建映射关系
@RequestParam注解一共有三个属性：
    value:指定为形参赋值的请求参数的参数名
    required:设置是否必须传输此参数，默认为true
    defaultValue:当请求体未传对应的参数或为空字符串时，
    使用默认值为形参赋值
```
## @RequestHeader
```java
@RequestHeader是将请求头信息和控制器方法的形参创建映射关系
@RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam
```
## @CookieValue
```java
@CookieValue是将cookie数据和控制器方法的形参创建映射关系
@CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

只有使用HttpSessionServlet请求访问时，并且调用request.getSession()方法时，才会生成cookie

```
## get请求乱码问题解决
```xml
在tomcat安装目录->conf->server.xml中,修改
<Connector port="8080" 
    URIEncoding="UTF-8" 
    protocol="HTTP/1.1"
    connectionTimeout="20000"
    redirectPort="8443"/>

```
## post请求乱码问题解决
```xml
<!--添加编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <!--设置请求编码-->
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <!--设置响应编码-->
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

## 域对象共享数据
### 1. 使用servletAPI向request域对应共享数据
```java
@RequestMapping("/testServletAPI")
public String testServletAPI(HttpServletRequest request){
    
    request.setAttribute("testScope", "hello,servletAPI");
    return "success";
    
}
```
### 2.使用ModelAndView向request域对象共享数据
```java
@RequestMapping("/testModelAndView")
public ModelAndView testModelAndView(){
    
    /**
    * ModelAndView有Model和view的功能
    * Model主要用于向请求域共享数据
    * View 主要用于设置视图，实现页面跳转
    */
    ModelAndView mav = new ModelAndView();
    //向请求域共享数据
    mav.addObject("testScope", hello,ModelAndView);
    //设置视图，实现页面跳转
    mav.setViewName("success");
    return mav;
    
}
```
### 3.使用Model向request域对象共享数据
```java
    @RequestMapping("/testModel")
    public String testModel(Model model){

        model.addAttribute("testRequestScope", "hello, Model");

        return "success";

    }
```

### 4.使用map向request域对象共享数据
```java
    @RequestMapping("/testMap")
    public String testMap(Map map){

        map.put("testRequestScope", "hello, Map");

        return "success";

    }
```

### 5.使用ModelMap向request域共享数据
```java
    @RequestMapping("/testModelMap")
    public String testModelMap(ModelMap modelMap){

        modelMap.addAttribute("testRequestScope","hello, ModelMap");

        return "success";
    }
```

### 6.Model、ModelMap、Map的关系
Model、ModelMap、Map类型的参数其实本质上都是BindingAwareModelMap类型的
```java
public interface Model{}

public class ModelMap extends LinkedHashMap<String, Object>{}

public class ExtendModelMap extends ModelMap implements Model {}

public class BindingAwareModelMap extends ExtendedModelMap{}

```

### 7.向session域共享数据
```java
@RequestMapping("/testSession")
public String testSession(HttpSession session){

    session.setAttribute("testSessionScope", "hello, session");

    return "success";
}
```

### 8.向application域共享数据
```java
    @RequestMapping("/testApplication")
    public String testApplication(HttpSession session){

        ServletContext application = session.getServletContext();
        application.setAttribute("testApplicationScope", "hello, application");

        return "success";
    }
```

## SpringMVC的视图
SpringMVC中视图是View接口，视图的作用是渲染数据，将模型Model中的数据展示给用户
SpringMVC视图种类很多，默认有转发视图InternalResourceView和重定向视图RedirectView
当工程引入jstl依赖，转发视图会自动转换为jstlView
若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析后所得到的是ThymeleafView

### 1.ThymeleafView
当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转
```java
//Thymeleaf视图
@RequestMapping("/testHello")
public String testHello(){
    
    return "hello";
    //转发视图
    return "forward:/testHello";
    //重定向视图，会改变地址栏中的地址
    return "redirect:/testHello";
}
```
### 3.视图控制器view-controller
当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法用view-controller标签进行展示
```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!--
        path:设置处理请求地址
        view-name:设置请求地址所对应的视图名称
     -->
    <!-- 开启后@Controller注解会全部失效 -->
    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>

    <!-- 开启MVC的注解驱动 需要和view-controller标签一起使用 -->
    <mvc:annotation-driven />
```
当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射全部失效，此时需要在SpringMVC.xml文件中添加注解驱动

```java
    @RequestMapping("/testRedirect")
    public String testRedirect(){

        //重定向到testThymeleafView
        return "redirect:/testThymeleafView";
    }
```
重定向视图在解析时，会先将redirect:前缀去掉，然后会判断剩余部分是否为/开头，若是则会自动拼接上下文路径

###4.视图控制器view-controller
在控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示
```xml

    <mvc:view-controller path="/" view-name="index"></mvc:view-controller>
```

# 七、RESTFul
## 1、RESTFul简介
REST: Representational State Transfer，表现层资源状态转移

## 2、RESTFul实现
```java
/**
     * 使用RESTFul模拟用户资源的增删改查
     * /user  GET  查询所有用户信息
     * /user/1  GET  根据用户Id查询所有用户信息
     * /user  POST  添加用户信息
     * /user/1  DELETE  删除用户信息
     * /user  PUT  修改用户信息
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getAllUser(){
        System.out.println("查询所有用户信息");
        return "success";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUserById(){
        System.out.println("根据Id查询用户信息");
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String insertUser(String userName, String passWord){
        System.out.println("添加用户信息:" + userName +"," + passWord);
        return "success";
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String updateUser(String userName, String passWord){
        System.out.println("修改用户信息:" + userName +"," + passWord);
        return "success";
    }

```
## 3、配置HttpMethodFilter
```xml
    <!-- 配置HiddenHttpMethodFilter -->
    <filter>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>HiddenHttpMethodFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
    <!--编码过滤器需要早于HttpMethod过滤器进行配置-->
```
# 八、RESTFul案例
## 1、准备工作
和传统的CRUD一样，实现对员工信息的增删改查
- 搭建环境
- 准备实体类
```java

略
```

略

# 九、文件上传和下载

## 1.文件下载

下载接口

```java
@RequestMapping("/testDown")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {

        //获取servletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/img/clipboard.png");
        System.out.println(realPath);
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //读取流到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置下载方式以及下载文件名字
        headers.add("Content-Disposition", "attachment;filename=1.png");
        //设置相应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();

        return responseEntity;

    }
```

前端页面

```html
<a th:href="@{/testDown}">下载clipboard.png</a>
```

## 2.文件上传

上传接口

```java
@RequestMapping("/testUp")
    public String testUp(MultipartFile photo, HttpSession session){

        //通过ServletContext获取服务器中photo目录的路径
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("photo");
        //获取上传文件的文件名
        String fileName = photo.getOriginalFilename();
        //获取上传文件的后缀名
        String suffixName = fileName.substring(fileName.indexOf("."));
        File file = new File(photoPath);
        //判断photoPath路径是否存在
        if(!file.exists()){
            //不存在则创建目录
            file.mkdir();
        }
        //文件上传
        System.out.println(photo.getName());
        System.out.println(photo.getOriginalFilename());

        String finalPath = photoPath + File.separator + fileName;
        try {
            photo.transferTo(new File(finalPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
```

前端页面

```html
<!-- enctype="multipart/form-data"属性是将文件以二进制形式进行传输 -->
<form th:action="@{/testUp}" method="post" enctype="multipart/form-data">
    头像：<input type="file" name="photo"><br>
    <input type="submit" value="上传">
</form>
```

# 十、拦截器

## 1、拦截器配置

SpringMVC中的拦截器用于拦截控制器方法的执行

SpringMVC中的拦截器需要实现HandlerInterceptor或者继承HandlerInterceptorAdapter类

SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置

```xml
<!-- 配置拦截器 会拦截所有请求 -->
    <mvc:interceptors>
<!--        <bean class="com.solo.dispatcher.interceptors.FirstInterceptor"></bean>-->
<!--        <ref bean="firstInterceptor"/>-->
        <mvc:interceptor>
            <!-- 需要拦截的路径 /*:表示一层目录 /**:表示拦截所有目录-->
            <mvc:mapping path="/**"/>
            <!-- 排除拦截的路径 -->
            <mvc:exclude-mapping path="/"/>
            <ref bean="firstInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>
<!-- 以上配置方式都可以通过ref或bean标签设置拦截器，通过mvc:mapping设置需要拦截的请求，通过mvc:exclude-mapping设置需要排除的请求，即不需要拦截的请求 -->
    
```

## 2.拦截器的三个抽象方法

SpringMVC中的拦截器有三个抽象方法

preHandler: 控制器方法执行之前执行preHandler()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法

postHandler: 控制器发放执行之后执行postHandler()

afterComplation: 处理完视图和模型数据，渲染视图完毕后执行afterComplation()



## 3.多个拦截器的执行顺序



