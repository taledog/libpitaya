package dog.tale.pitaya;

import taleproto.*;
public class gateutil {

    public static byte[]  NewJoinGameRequest() {
        Game.LoginGameRequest.Builder r=taleproto.Game.LoginGameRequest.newBuilder();

        Game.Player.Builder p=taleproto.Game.Player.newBuilder();
        p.setUserId(1234);
        p.setNickName("zhengji");

        r.setIp("localhost");
        r.setToken("token");
        r.setPlayer(p);

        return r.build().toByteArray();

    }
    public static byte[]  CreateRoomRequest() {
        Game.CreateRoomRequest.Builder r=Game.CreateRoomRequest.newBuilder();

        r.setKind(1);
        r.setRoomTitle("Tale Room");
        return r.build().toByteArray();
    }

    public static byte[]  NewTextMessage(int msgId,String text) {
        Game.TextMessage.Builder r=Game.TextMessage.newBuilder();

        r.setMsgId(msgId);
        r.setText(text);

        return r.build().toByteArray();
    }

}
