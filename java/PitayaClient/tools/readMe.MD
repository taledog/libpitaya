步骤：
1. pitaya-cli -docs connector.docsHandler.docs
2. 执行 以下三个命令
    push onNewMember taleproto.NewMember

    push onTextMessage taleproto.TextMessage

    push onQuitMember taleproto.QuitMember

3. 连接服务器
    connect tujiao.co:30124
4. 登录
    request connector.sys.login {"Player":{"UserId":456,"NickName":"风清扬","Locale":"zh-CH"},"Ip":"127.0.0.1"}

5. 加入房间（注意使用实际的房间号）
   request connector.RoomHandler.joinroom {"RoomId":1605461786}
6. 发送文本消息
  request connector.RoomHandler.sendtext {"Text":"好啊，大师哥"}
