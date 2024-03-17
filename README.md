# MyScoreBoard
让玩家设计自己的 计分板 吧！

---

> 指令
- /mysb reload
  - 权限: OP | myscoreboard.reload
  - 重载创建配置文件 (不建议使用,请重启服务器重载)
- /mysb set <add|set|remove|resetting> [行]
  - 权限: OP | myscoreboard.use
    - add - 向计分板增加内容
    - set - 设置某一行的文字
    - remove - 移除某一行
    - resetting -清空
- /mysb look  
  权限: OP | myscoreboard.use
  - 看自己的计分板文字
- /mysb papi  
  权限: OP | myscoreboard.use
  - 查看允许的papi变量
> 使用 `!:`-表示空格 `%player%`-表示玩家名字  
> 支持 PlaceholderAPI


```yaml
template:
  title: "自定义计分板 - 默认标题"
  text:
    - '&e%player%'
    - "!:"
    - 'by: 3054587546'

#允许玩家使用的的Papi变量
PermittedPapi:
  - "%player_name%"

#配置文件版本
var: 2
```