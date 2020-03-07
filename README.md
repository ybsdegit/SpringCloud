# SpringCloud Study note

# SpringCloud 常识知识

- springboot 与 Springcloud 关系
1、springboot 专注于快速方便的构建个体微服务框架
2、springcloud 专注于服务治理
3、dubbo的定位是一款RPC框架，
4、Spring Cloud的目标是微服务下的一站式解决方案

- 服务熔断，服务降级


## eureka 与 zookeeper 区别

- CAP 原则
    - C 强一致性
    - A 可用性
    - P 分区容错性

- ACID
    - 原子性，一致性，隔离性，持久性

- **Zookeeper 保证的是CP**
    - 失去master节点，引起故障
- **Eureka 保证的是AP**
    - 很好的应对因网络故障导致部分节点失去联系的情况，而不会想 zookeeper 那样使整个服务崩掉
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




## Ribbon 负载均衡
进程内LB
LoadBalance （LB，负载均衡）将用户的请求平摊的分配到多个服务上
- 常见的负载均衡：Ngix, Lvs
- 轮询
- 随机

1、导包
```
<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-ribbon -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
    <version>1.4.7.RELEASE</version>
</dependency>

```

2、配置类
```java
@Configuration
public class ConfigBean {

    // 配置负载均衡实现 RestTemplate
    @Bean
    @LoadBalanced  // Ribbon
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


}
```

## Feign 负载均衡

使用Feign 可以代替 restTemplate

1、导包
```
  <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
```

2、service
```java
@Component
@FeignClient(value = "SPRINGCLOUD-PROVIDER-DEPT")
public interface DeptClientService {

    @GetMapping("/dept/get/{id}")
    public Dept queryById(@PathVariable("id") Long id);

    @GetMapping("/dept/list")
    public List<Dept> queryAll();

    @PostMapping("/dept/add")
    public boolean addDept(Dept dept);

}

```

@Autowiredprivate DeptClientService deptClientService;

@EnableFeignClients(basePackages = {"com.ybs.springcloud"})
@ComponentScan("com.ybs.springcloud")


## 服务熔断 Hystrix （断路器）服务端

- 服务雪崩
    对于高流量的应用，单一的后端依赖坑你导致所有服务在几秒内饱和
    我们需要 弃车保帅

- Hystrix
   避免级联故障，提高分布式系统的弹性
   备选响应

- 服务熔断
   熔断机制是应对雪崩效应的一种微服务链路保护机制

   当链路某个微服务不可用获取响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息

## 服务降级 Hystrix （客户端）
- 服务熔断
    - 服务端
    - 某个服务超时或者异常，引起熔断
- 服务降级
    - 客户端
    - 从整体网站请求负载考虑 当某个服务熔断或者关闭之后，服务将不被调用
    - 此时客户端可以准备一个 FallbackFactory， 返回一个默认的值（缺省值）
    - 整体服服务水平下降了。好歹能用，比直接挂掉强


```

@SpringBootApplication
@EnableEurekaClient  // 在服务启动后自动注册到eureka中
@EnableDiscoveryClient  // 服务发现
@EnableCircuitBreaker   // 添加对熔断的支持 断路器
```

```java
@Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        return registrationBean;
    }
```

## Zuul 网关
- 实现外部访问统一入口
- 过滤
- Zuul 和 Eureka 整合

zuul 配置
```
server:
  port: 9527

spring:
  application:
    name: springcloud-zuul



# Eureka 配置，服务注册地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka/
  instance:
    instance-id: springcloud-zuul-9527 # 修改Eureka上的默认，描述信息
    prefer-ip-address: true  # true 可以显示ip地址

info:
  app.name: paulson-springcloud
  company.name: ybs

zuul:
  routes:
    mydept.serviceId: springcloud-provider-dept
    mydept.path: /mydept/**
  ignored-services: springcloud-provider-dept  # 不能再使用这个路径访问了
  prefix: /ybs  # 公共的访问前缀
#  ignored-services: *  # 隐藏全部

```

## Spring Cloud Config


```
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```

```
server:
  port: 3344

spring:
  application:
    name: springcloud-config-server
  cloud:
    config:
      server:
        git:
          uri: https://gitee.com/paulsonwier/springcloud-config.git

# 通过config-server可以连接到git访问其中的资源和配置


```