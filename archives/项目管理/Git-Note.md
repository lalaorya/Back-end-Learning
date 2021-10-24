## Git-Note

![git5](https://i.loli.net/2021/03/31/5EsmtrxXWqfvLJR.png)

#### 1. Git的工作流程

![image-20210307221923071](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307221923071.png)

#### 2. 在当前文件夹创建本地仓库

- 原生gitgui创建

- 原生gitbash创建,命令是`git init`

- totouristGit创建

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307224647009.png" alt="image-20210307224647009" style="zoom:50%;" />

  **得到.git文件夹**

  ![image-20210307224727942](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307224727942.png)

  `.git 是本地仓库，resp1是工作目录workspace`

#### 3. 提交文件到本地仓库

- 首先文件肯定是放在工作目录下的

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307225525761.png" alt="image-20210307225525761" style="zoom:50%;" />

- 把文件add到暂存区

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307225707550.png" alt="image-20210307225707550" style="zoom:67%;" />

- 在暂存区文件提交commit给本地仓库

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307225746137.png" alt="image-20210307225746137" style="zoom:50%;" />

#### 4. 使用乌龟git查看本地仓库的内容和提交日志

<img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307230242380.png" alt="image-20210307230242380" style="zoom:50%;" />

#### 5. 删除本地仓库的文件

需要在工作区删除该文件后，点击提交commit即可，这样本地仓库中的该文件也会被删除

如何需要在工作区保留副本，本地删除，则点击`删除本地副本`

#### 6. 提交Java工程到版本库

- 一般情况一些配置文件和编译完成后的目录(out)不需要添加到本地仓库，所以需要进行忽略

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307232720619.png" alt="image-20210307232720619" style="zoom:67%;" />

  <img src="C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210307232832363.png" alt="image-20210307232832363" style="zoom:50%;" />

- 添加并提交其他目录

#### 7. 推送本地仓库到github

##### ① SSH方式推送

- 在github上创建对应仓库

- 使用`ssh-keygen -t rsa`命令创建ssh密钥，默认目录为用户目录下的.ssh

  ![image-20210308000036732](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308000036732.png)

- 在github上添加公钥

  ![image-20210308000224614](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308000224614.png)

- 使用命令行或者乌龟把本地仓库推送带github

  借助私钥进行推送，保证安全性

  ![image-20210308084802124](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308084802124.png)

- 也可使用乌龟进行推送

##### ② https方式推送

不需要密钥对，通过用户民密码认证

使用乌龟GUI进行推送

#### 8. 从github克隆项目到本地

![image-20210308090008789](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308090008789.png)

#### 9. 分支

![image-20210308091219000](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308091219000.png)

- 新建分支

  ![image-20210308091329729](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308091329729.png)

- 提交的时候可以选择提交到哪个分支

#### 10. 使用idea进行git项目管理

- 配置idea

  Setting-Git-添加cmd/Git.ext

- 创建本地仓库

  ![image-20210308092524654](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308092524654.png)

  创建完可看到自动生成的git工具栏

  ![image-20210308092623414](C:%5CDocuments(%E8%B5%84%E6%96%99)%5CLearning%5C%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C-%E5%B0%8F%E6%B2%88%5Cimg%5Cimage-20210308092623414.png)

- 提交到本地仓库

  右上角第二个

- 推送本地仓库到远程仓库

- 从远程仓库拉取到本地