**QCBM (Q-Cloud Book Mall)** 是一个采用微服务架构，并使用 dubbo-2.7.8 框架开发的一个网上书城 Demo 项目，具体组成如下：

- **qcbm-front**：前端工程，使用 react 开发的前端项目。

- **qcbm-backend**：后端工程，包括如下微服务：
  - **qcbm-gateway** ：API 网关，接受前端的 http 请求，并转化为后台的 dubbo 请求。
  - **user-service** ：基于 dubbo 的微服务，提供用户注册、登录、鉴权等功能。
  - **favorites-service** ：基于 dubbo 的微服务，提供用户图书收藏功能。
  - **order-service** ：基于 dubbo 的微服务，提供用户订单生成和查询等功能。
  - **store-service** ：基于 dubbo 的微服务，提供图书信息的存储等功能。