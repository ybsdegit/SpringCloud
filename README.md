# SpringCloud Study note

# SpringCloud 常识知识

- springboot 与 Springcloud 关系
1、springboot 专注于快速方便的构建个体微服务框架
2、springcloud 专注于服务治理
3、dubbo的定位是一款RPC框架，
4、Spring Cloud的目标是微服务下的一站式解决方案

- 服务熔断，服务降级


- eureka 与 zookeeper 区别


- 使用RestTemplate 进行服务之间调用
```java
@RestController
public class DeptConsumerController {
    // 消费者，不应该有 service 层
    // 提供多种便捷访问远程http服务的方法，简单的restful服务模板
    // url
    // 实体 Map
    // responseType
    @Autowired
    private RestTemplate restTemplate;

    private static final String REST_URL_PREFIX = "http://localhost:8001";

    @PostMapping("/consumer/dept/add")
    public boolean add(Dept dept){
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class);
    }

    @GetMapping("/consumer/dept/get/{id}")
    public Dept get(@PathVariable Long id){
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/"+id, Dept.class);
    }

    @GetMapping("/consumer/dept/list")
    public List<Dept> list(){
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }


}

```


- eureka


1、服务端
    - <!--导包-->
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```
    - 配置文件

```
server:
  port: 7001
eureka:
  instance:
    hostname: localhost  # Eureka 服务端的实例名车
  client:
    register-with-eureka: false  # 是否向 Eureka 注册中心注册自己
    fetch-registry: false  # 如果为 false，表示自己为注册中心
    service-url:   # 监控页面
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

   - 启动类添加注解
   @EnableEurekaServer


2、客户端
 - <!--导包-->
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
        <!--完善监控信息-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```
 - 配置文件

```
# Eureka 配置，服务注册地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka/

 # info 配置
info:
  app.name: Paulson-springcloud
  company.name: ybs
```
 - 启动类添加注解
    @EnableEurekaClient  // 在服务启动后自动注册到eureka中



