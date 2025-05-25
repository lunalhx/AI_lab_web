# 人工智能与算力技术实验室网站系统

## 🚀 项目简介

这是一个完整的实验室网站管理系统，包含若依后端管理系统和前端展示网站，实现了数据的完全同步和自动化管理。

## 📁 项目结构

```
web/
├── RuoYi-Vue-master/          # 若依后端管理系统
│   ├── ruoyi-admin/           # 后端主应用
│   ├── ruoyi-framework/       # 框架核心
│   ├── ruoyi-system/          # 系统模块
│   ├── ruoyi-common/          # 通用模块
│   ├── ruoyi-generator/       # 代码生成器
│   ├── ruoyi-quartz/          # 定时任务
│   └── ruoyi-ui/              # 前端Vue界面
└── web_2/                     # 实验室前端网站
    ├── js/                    # JavaScript文件
    │   ├── config.js          # API配置
    │   ├── homepage-api.js    # 首页API客户端
    │   ├── news-api.js        # 新闻API客户端
    │   ├── notices-api.js     # 通知API客户端
    │   ├── activities-api.js  # 活动API客户端
    │   └── main.js            # 主脚本
    ├── notices_html/          # 自动生成的通知页面
    ├── activities_html/       # 自动生成的活动页面
    ├── images/                # 图片资源
    ├── index.html             # 首页
    ├── news.html              # 新闻页面
    ├── notices.html           # 通知页面
    ├── activities.html        # 活动页面
    ├── test-integration.html  # 集成测试页面
    ├── server.js              # Node.js服务器
    └── styles.css             # 样式文件
```

## ✨ 主要功能

### 🔧 后端管理系统（若依）
- **用户管理**：完整的用户权限管理系统
- **新闻管理**：新闻的增删改查，支持图片和链接
- **活动管理**：学术活动管理，支持多图片上传
- **通知管理**：通知公告管理，支持附件上传
- **自动页面生成**：新增通知和活动时自动生成HTML页面
- **API接口**：提供公开API供前端网站调用

### 🌐 前端展示网站
- **响应式设计**：支持PC和移动端访问
- **动态数据加载**：通过API实时获取最新数据
- **首页三卡片**：新闻、活动、通知动态展示
- **专门页面**：新闻、活动、通知的完整列表页面
- **自动链接**：自动链接到生成的详情页面
- **错误处理**：完善的错误处理和加载状态

### 🔄 数据同步机制
- **实时同步**：管理系统更新后前端立即生效
- **跨域支持**：配置CORS支持外部网站调用
- **数据排序**：按时间倒序显示，最新内容优先
- **页面生成**：通知和活动自动生成对应HTML页面

## 🛠️ 技术栈

### 后端
- **Spring Boot** 2.5.15
- **MyBatis** 数据持久化
- **MySQL** 5.7+ 数据库
- **Redis** 缓存
- **Maven** 项目管理

### 前端管理
- **Vue.js** 2.x
- **Element UI** 组件库
- **Axios** HTTP客户端

### 前端网站
- **原生HTML/CSS/JavaScript**
- **Node.js** 静态服务器
- **Fetch API** 数据请求

## 🚀 快速开始

### 环境要求
- Java 8+
- Node.js 14+
- MySQL 5.7+
- Redis 3.0+
- Maven 3.6+

### 1. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE `ry-vue` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- 导入数据库脚本
-- 执行 RuoYi-Vue-master/sql/ 目录下的SQL文件
```

### 2. 后端启动
```bash
cd RuoYi-Vue-master
mvn clean install
mvn spring-boot:run -pl ruoyi-admin
```

### 3. 前端管理界面启动
```bash
cd RuoYi-Vue-master/ruoyi-ui
npm install
npm run dev
```

### 4. 前端网站启动
```bash
cd web_2
node server.js
```

## 🌐 访问地址

- **若依管理后台**：http://localhost:1024
  - 用户名：admin
  - 密码：admin123

- **若依后端API**：http://localhost:8080

- **实验室网站**：http://localhost:50001
  - 首页：http://localhost:50001/index.html
  - 新闻页面：http://localhost:50001/news.html
  - 活动页面：http://localhost:50001/activities.html
  - 通知页面：http://localhost:50001/notices.html

- **集成测试页面**：http://localhost:50001/test-integration.html

## 📊 API接口

### 公开API（无需认证）
- `GET /api/public/news/list` - 获取新闻列表
- `GET /api/public/news/{id}` - 获取新闻详情
- `GET /api/public/activities/list` - 获取活动列表
- `GET /api/public/activities/{id}` - 获取活动详情
- `GET /api/public/notifications/list` - 获取通知列表
- `GET /api/public/notifications/{id}` - 获取通知详情

### 页面生成API
- `POST /api/public/notifications/generate-page/{id}` - 生成单个通知页面
- `POST /api/public/notifications/generate-all-pages` - 批量生成通知页面
- `POST /api/public/activities/generate-page/{id}` - 生成单个活动页面
- `POST /api/public/activities/generate-all-pages` - 批量生成活动页面

## 🔧 配置说明

### API配置
修改 `web_2/js/config.js` 文件：
```javascript
window.apiConfig = {
    baseURL: 'http://localhost:8080',  // 后端API地址
    prefix: '/api/public',             // API前缀
    requireAuth: false                 // 是否需要认证
};
```

### 数据库配置
修改 `RuoYi-Vue-master/ruoyi-admin/src/main/resources/application-druid.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: your_password
```

## 📝 使用说明

### 1. 内容管理
1. 登录若依管理后台
2. 在"实验室网站系统"菜单下管理新闻、活动、通知
3. 新增或修改内容后，前端网站自动更新

### 2. 页面生成
- 新增通知时自动生成HTML页面（格式：YYYYMMDD_ID.html）
- 新增活动时自动生成HTML页面（格式：YYYY-MM-DD.html）
- 页面保存在对应的目录下，可直接访问

### 3. 数据同步
- 前端网站通过API实时获取最新数据
- 支持跨域访问，可部署在不同服务器
- 数据按时间倒序排列，最新内容优先显示

## 🎯 核心特性

### ✅ 已实现功能
- [x] 若依后端API与前端网站数据同步
- [x] 新闻、活动、通知三大模块完整集成
- [x] 首页三个卡片部分动态加载最新数据
- [x] 专门页面显示完整列表，支持分页和搜索
- [x] 自动生成HTML页面（通知和活动）
- [x] 跨域访问配置，支持外部网站调用
- [x] 数据按时间倒序排列，最新内容优先显示
- [x] 错误处理和加载状态提示
- [x] 响应式设计，支持移动端访问
- [x] 实时数据同步，管理系统更新后立即生效

### 🔄 数据流程
```
若依管理系统 → MySQL数据库 → 公开API → 前端网站 → 用户访问
```

## 🤝 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 📞 联系方式

- 项目维护者：人工智能与算力技术实验室
- 邮箱：contact@ailab.edu.cn
- 网站：http://localhost:50001

## 🙏 致谢

- [若依管理系统](https://gitee.com/y_project/RuoYi-Vue) - 提供了强大的后台管理框架
- [Element UI](https://element.eleme.cn/) - 优秀的Vue组件库
- [Spring Boot](https://spring.io/projects/spring-boot) - 强大的Java开发框架

---

⭐ 如果这个项目对您有帮助，请给我们一个星标！ 