# ruoyi-admin 接口测试工程

## 项目介绍

本工程用于对 ruoyi-admin 模块中的所有接口进行单元测试。测试工程采用 Spring Boot Test + JUnit 5 + Mockito
的组合，提供了完整的接口测试框架。

## 技术栈

- Spring Boot Test
- JUnit 5
- Mockito
- MockMVC
- H2 数据库（用于数据层测试）

## 使用方法

### 运行所有测试

```bash
cd d:\应用系统\教培行业\resbai\resbai-api
mvn test -pl ruoyi-admin-test
```

### 运行指定测试类

```bash
mvn test -pl ruoyi-admin-test -Dtest=com.ruoyi.test.controller.system.SysLoginControllerTest
```

## 测试类结构

- 所有 Controller 测试类都放在 `com.ruoyi.test.controller` 包下，按照原项目的包结构组织
- 所有测试类都继承自 `BaseControllerTest` 基类
- 测试方法按照被测接口命名，清晰反映测试目的

## 创建新的测试类

1. 在对应的包下创建测试类，继承 `BaseControllerTest`
2. 为每个接口创建对应的测试方法
3. 使用 MockMvc 发送请求并验证响应

## 示例测试代码

```java
@Test
void testInterface() throws Exception {
    // 准备请求数据
    String requestBody = "{\"param\":\"value\"}";
    
    // 执行请求
    mockMvc.perform(MockMvcRequestBuilders.post("/api/endpoint")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200));
}
```

## 注意事项

1. 测试工程使用了模拟对象，不会实际连接数据库
2. 对于需要数据库操作的接口，需要使用 Mockito 模拟 Service 层
3. 测试前确保主项目已编译完成
4. 建议为每个接口编写正向、逆向、边界值等多种测试用例