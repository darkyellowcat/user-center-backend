# User-Center 用户中心

## 📖 项目简介 | Project Introduction
**中文**  
User-Center 是一个基于 **Java + Spring Boot + MySQL** 的后端实战项目，主要功能是实现用户注册、登录、信息管理等模块。该项目适合学习过 **JavaWeb** 的同学，通过实战来提升 **后端开发能力、数据库设计能力** 和 **项目工程化能力**。  

**English**  
User-Center is a backend project built with **Java + Spring Boot + MySQL**, focusing on user registration, login, and profile management modules. It is designed for learners who have studied **JavaWeb** and want to gain hands-on experience in **backend development, database design, and project engineering practices**.

---

## 👥 使用人群 | Target Audience
- **中文**：已经学习过 **JavaWeb 基础**，希望通过实战项目进一步提升的同学。  
- **English**: Learners who have studied **JavaWeb basics** and want to improve their skills through practical projects.  

---

## 📂 项目结构 | Project Structure
```plaintext
user-center/
├── src/
│   ├── main/
│   │   ├── java/com/darkyellowcat/usercenter/
│   │   │   ├── common/                # 通用返回类 & 工具 Common response & utils
│   │   │   │   ├── BaseResponse
│   │   │   │   └── ResultUtils
│   │   │   ├── constant/              # 常量 Constants
│   │   │   │   └── UserConstant
│   │   │   ├── controller/            # 控制层 Controllers
│   │   │   │   └── UserController
│   │   │   ├── exception/             # 异常处理 Exception handling
│   │   │   │   ├── BusinessException
│   │   │   │   └── GlobalExceptionHandler
│   │   │   ├── mapper/                # 数据访问层 DAO / Mappers
│   │   │   │   └── UserMapper
│   │   │   ├── model/domain/          # 实体类 Models
│   │   │   │   ├── User
│   │   │   │   └── request/           # 请求对象 Request DTOs
│   │   │   │       ├── UserLoginRequest
│   │   │   │       └── UserRegisterRequest
│   │   │   ├── service/               # 服务层 Services
│   │   │   │   ├── impl/
│   │   │   │   │   └── UserServiceImpl
│   │   │   │   └── UserService
│   │   │   ├── utils/                 # 工具类 Utils
│   │   │   │   └── ErrorCode
│   │   │   └── UserCenterApplication  # 启动类 Application entrypoint
│   │   └── resources/
│   │       ├── gen/mapper/            # MyBatis 映射文件 MyBatis Mappers
│   │       │   └── UserMapper.xml
│   │       ├── application.yml        # 配置文件 Dev config
│   │       └── application-prod.yml   # 配置文件 Prod config
│   └── test/java/                     # 测试代码 Test code
├── pom.xml                            # Maven 配置 Maven config
