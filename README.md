# Git-Clone

```
  ____ ___ _____      ____ _     ___  _   _ _____
 / ___|_ _|_   _|    / ___| |   / _ \| \ | | ____|
| |  _ | |  | |_____| |   | |  | | | |  \| |  _|
| |_| || |  | |_____| |___| |__| |_| | |\  | |___
 \____|___| |_|      \____|_____\___/|_| \_|_____|

```

### 描述
一个 Git Clone 的插件，适用于 FlowCi

### 输入参数
>- PLUGIN_API `Api的服务地址` `必填` Example: `http://127.0.0.1:8080/flow-api`
>- PLUGIN_GIT_URL `Git的克隆地址` `选填`
>- PLUGIN_GIT_BRANCH `Git的分支` `选填`
>- PLUGIN_GIT_WORKSPACE `Git的克隆的目录` `选填`

### 输出环境变量
>- PLUGIN_GIT_WORKSPACE
>- PLUGIN_GIT_PROJECT `项目的目录`
>- PLUGIN_API
>- PLUGIN_GIT_URL
>- PLUGIN_GIT_BRANCH
>- PLUGIN_GIT_RSA_PUB `Flow 的 公钥文件地址`
>- PLUGIN_GIT_RSA_PRI `Flow 的 私钥文件地址`

>如果在下个step使用输出的环境变量 请在ENV中加上 FLOW_ENV_OUTPUT_PREFIX: PLUGIN_， 这样的话就会把PLUGIN_前缀的环境变量传递到下个step
### 反馈
> 欢迎提[Issue](https://github.com/FlowCI/plugin-gitclone/issues)