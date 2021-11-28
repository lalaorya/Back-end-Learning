OAuth2是一种目前最流行的授权机制，用来授权第三方应用，比如微信、QQ、微博等，它可以安全的获取用户的数据。

![image-20211121220617834](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211121220617834.png)

---

![image-20211121220649671](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211121220649671.png)

试想一下，为什么现在很多网站都提供第三方应用登录呢？原因有三：

1. 用户省去了用户注册的流程，打开微信、QQ扫码就能登录，优化了用户体验
2. 网站自身无需实现用户体系，可使用第三方应用的用户信息
3. 第三方应用也可借此提高其他网站对此的依赖性，提升自己影响力

传统的方式如果要获取用户的个人信息，需要提供用户名密码，这样就等同于网站掌握了你的微信账号的用户名和密码，这种操作是十分危险的，而且你一旦修改了密码，就需要同时告诉所有使用第三方登录的网站，十分繁琐。因为OAuth2应运而生，通过在网站与第三方应用间设置一个授权层，网站只需要获得授权token就能访问用户的个人信息，不需要用户名密码了，且授权token是有权限和时间限制的，整个授权过程会更加安全可控.

#### 一、OAuth协议的机制

![image-20211121211852418](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211121211852418.png)

- 调用第三方应用请求用户授权
- 第三方应用同意授权并返回许可证code
- 客户端通过code向认证服务器请求access token
- 第三方应用响应access token
- 客户端通过token向资源服务器请求资源
- 第三方应用响应资源

其中认证服务器和资源服务器可以是同个服务器，这里只是做个逻辑上的区分

#### 二、授权码认证模式

OAuth2有四种授权模式，分别是授权码模式、简化模式、密码模式和客户端模式。我们只介绍最流行的授权码模式，微信、QQ、支付宝、百度等采用的都是授权码模式，事实上另外三种用得很少。

![image-20211121213055413](C:/Documents(%E8%B5%84%E6%96%99)/Learning/%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88/img/image-20211121213055413.png)

- 请求第三方授权url，需要携带以下参数

  - response_type：授权类型，此处为“code”
  - client_id：客户端ID，微信登录为公众号的appkey
  - redirect_uri：同意授权后的重定向url，十分重要，需要从次url获取code
  - state：可选项，如果存在则重定向url也必须包含该参数

- 重定向至redirect_uri，如https://client.example.com/cb?code=SplxlOBeZQQYbYS6WxSbIA&state=xyz，前端或后端均可可以通过此url获取code，code的有效期一般为10min

- 通过code向认证服务器发起请求，获得access_token.需要携带以下参数

  - grant_type：授权模式，此处为“authorization_code”
  - code：上一步获取的code
  - redirect_uri：与上一步保持一致
  - client_id

- 认证服务器响应

  - access_token：访问令牌，有效期一般为5min
  - token_type：令牌类型
  - expires_in：表示过期时间，单位为秒
  - refresh_token：更新令牌，有效期一般为30min。如果access_token过期，可以用来再次快速获取access_token
  - scope：表示权限范围，如果与客户端申请的范围一致，此项可省略。

- 客户端让资源服务器发起请求，响应用户信息

  

  

  

