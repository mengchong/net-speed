# 网络测速管理系统

## 项目简介

网络测速管理系统是一个基于Spring Boot的Web应用，用于监控和管理网络速度、设备信息、AP信息等网络相关数据。系统提供了丰富的功能模块，包括网络测速、体验测速、Ping测速、设备信息管理、AP信息管理、区域信息管理、信号质量管理等，支持数据统计分析和Excel导出功能。

## 技术栈

### 后端技术
- Spring Boot 2.3.7
- Java 8
- MyBatis-Plus 3.4.2
- MySQL
- Maven
- Swagger (Knife4j)
- Hutool 5.8.0
- Apache POI 3.9
- Jxls 1.0.6

### 前端技术
- HTML5
- CSS3
- JavaScript
- jQuery
- Bootstrap
- ECharts

## 功能模块

### 核心功能
1. **网络测速管理**：记录和分析网络速度数据
2. **体验测速管理**：评估网络使用体验
3. **Ping测速管理**：测试网络延迟
4. **设备信息管理**：管理网络设备信息
5. **AP信息管理**：管理无线接入点信息
6. **区域信息管理**：管理网络覆盖区域
7. **信号质量管理**：监控和分析信号质量
8. **场景管理**：管理不同网络场景
9. **用户管理**：系统用户管理
10. **阈值设置**：设置网络性能阈值
11. **数据统计分析**：提供网络数据统计和分析
12. **Excel导出**：支持数据导出到Excel

## 项目结构

```
net-speed-master/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/suzao/net/speed/netspeed/
│   │   │       ├── base/          # 基础类
│   │   │       ├── config/        # 配置类
│   │   │       ├── interceptor/   # 拦截器
│   │   │       ├── util/          # 工具类
│   │   │       └── NetSpeedApplication.java  # 应用入口
│   │   └── resources/
│   │       ├── excel/            # Excel模板
│   │       ├── mapper/           # MyBatis映射文件
│   │       ├── static/           # 静态资源
│   │       └── application.yaml  # 应用配置
├── .gitignore
├── README.md
└── pom.xml
```

## 快速开始

### 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 5.7+

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <项目地址>
   cd net-speed-master
   ```

2. **配置数据库**
   - 创建数据库 `speed`
   - 修改 `src/main/resources/application.yaml` 中的数据库连接信息

3. **构建项目**
   ```bash
   mvn clean package -DskipTests
   ```

4. **运行项目**
   ```bash
   # 方式1：使用Spring Boot内置Tomcat运行
   mvn spring-boot:run
   
   # 方式2：部署到外部Tomcat
   # 将生成的war包部署到Tomcat的webapps目录
   ```

5. **访问系统**
   - 浏览器访问：`http://localhost:8085`
   - Swagger文档：`http://localhost:8085/doc.html`

## 配置说明

### 主要配置项

- **数据库配置**：在 `application.yaml` 中配置数据库连接信息
- **文件上传路径**：在 `application.yaml` 中配置 `document.upload.path`
- **文件下载路径**：在 `application.yaml` 中配置 `document.download.path`
- **服务端口**：在 `application.yaml` 中配置 `server.port`

## 系统功能

### 1. 网络测速
- 记录网络上传/下载速度
- 支持按时间、设备、区域等维度查询
- 提供数据统计和图表分析

### 2. 体验测速
- 评估网络使用体验
- 记录用户体验数据
- 提供体验质量分析

### 3. Ping测速
- 测试网络延迟
- 记录Ping响应时间
- 分析网络稳定性

### 4. 设备信息管理
- 管理网络设备基本信息
- 支持设备状态监控
- 设备数据统计分析

### 5. AP信息管理
- 管理无线接入点信息
- 监控AP状态
- AP覆盖范围分析

### 6. 区域信息管理
- 管理网络覆盖区域
- 区域网络性能分析
- 区域对比分析

### 7. 信号质量管理
- 监控信号强度
- 分析信号质量
- 提供信号优化建议

### 8. 数据统计分析
- 多维度数据统计
- 可视化图表展示
- 数据导出功能

## 开发指南

### 代码规范
- 遵循Java代码规范
- 使用Lombok简化代码
- 采用MyBatis-Plus进行数据库操作
- 使用Swagger进行API文档管理

### 扩展建议
- 增加更多网络性能指标监控
- 集成告警系统
- 增加历史数据趋势分析
- 支持更多数据源接入

## 注意事项

1. **数据库配置**：请确保数据库连接信息正确，并且数据库已创建
2. **文件路径**：请确保文件上传和下载路径存在且有写入权限
3. **端口冲突**：如端口8085已被占用，请修改配置文件中的端口号
4. **依赖管理**：使用Maven管理依赖，确保所有依赖已正确安装

## 联系方式

- 项目维护：mc
- 邮箱：[请联系项目维护者获取]
- 版本：1.0.1-SNAPSHOT

## 许可证

[请在此添加项目许可证信息]
