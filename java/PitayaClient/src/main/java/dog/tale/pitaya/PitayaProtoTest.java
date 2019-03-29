package dog.tale.pitaya;

import java.nio.*;
import java.nio.charset.*;
import org.bytedeco.javacpp.*;

public class PitayaProtoTest {
    static {
        //System.load("/Users/arden/data/repository/tale/PitayaClientJava/lib/libpitaya-mac.bundle");
        System.loadLibrary("pitaya-mac");
        System.loadLibrary("jniPitaya");
    }

    public static void main(String...args) {
        Object lock = new Object();

        try {


            Pitaya.pc_lib_set_default_log_level(Pitaya.PC_LOG_DEBUG);

            Pitaya.pc_lib_client_info_t clientInfo = new Pitaya.pc_lib_client_info_t();

            Pitaya.pc_lib_init((Pitaya.Pc_log_int_BytePointer) null, null, null, null, clientInfo);

            Pitaya.pc_client_config_t config = new Pitaya.pc_client_config_t();
            config.transport_name(Pitaya.PC_TR_NAME_UV_TCP);
            config.enable_reconn(1);
            config.conn_timeout(120);
            config.reconn_max_retry(Pitaya.PC_ALWAYS_RETRY);
            config.reconn_delay(30);


            Pitaya.pc_client_init_result_t result = Pitaya.pc_client_init(null, config);
           final Pitaya.pc_client_t client = result.client();

            Pitaya.pc_client_connect(client, "tujiao.co", 30124, null);

            Pitaya.pc_client_add_ev_handler(client, new Pitaya.pc_event_cb_t() {
                @Override
                public void call(Pitaya.pc_client_t client, int ev_type, Pointer ex_data, BytePointer arg1, BytePointer arg2) {
                    System.out.println("ev_type: " + ev_type);
                    if (ev_type == Pitaya.PC_EV_CONNECTED) {
                        System.out.println(ev_type);
                    }
                }
            }, null, null);


            byte[] loignData=gateutil.NewJoinGameRequest();
            Pitaya.pc_binary_request_with_timeout(client,"sys.login",loignData,loignData.length,null,15
                    ,new Pitaya.pc_request_success_cb_t(){
                        @Override
                        public void call(Pitaya.pc_request_t req, Pitaya.pc_buf_t resp) {
                             //success
                            try {
                                byte[] retdata;
                                retdata= resp.base().getStringBytes();
                                taleproto.Game.LoginGameResponse response = taleproto.Game.LoginGameResponse.parseFrom(retdata);
                                if (response.getCode()==0){
                                    System.out.println("Login success");
                                    System.out.println(response.getSessionId());
                                    //create Room
                                    createRoom();

                                }else{
                                    System.out.println("login failure");
                                    System.out.println(response.getMsg());
                                }

                            }catch (Exception ex){
                                System.out.println(ex);
                            }

                        }

                        private void createRoom() {
                            byte[] roomReq= gateutil.CreateRoomRequest();

                            Pitaya.pc_binary_request_with_timeout(client,"RoomHandler.createroom",roomReq,roomReq.length,null
                                    ,15
                                    ,new Pitaya.pc_request_success_cb_t(){
                                        @Override
                                        public void call(Pitaya.pc_request_t req, Pitaya.pc_buf_t resp) {
                                            try {
                                                taleproto.Game.CreateRoomResponse response = taleproto.Game.CreateRoomResponse.parseFrom(resp.base().getStringBytes());
                                                if (response.getCode() == 0) {
                                                    System.out.println("创建房间成功");
                                                    System.out.println(response.getRoomId());
                                                }
                                            }catch (Exception ex){
                                                ex.printStackTrace();
                                            }
                                        }
                                    }

                                    ,new Pitaya.pc_request_error_cb_t(){
                                        @Override
                                        public void call(Pitaya.pc_request_t req, Pitaya.pc_error_t error) {
                                             System.out.println(error);
                                            System.out.println("创建房间出错");

                                        }
                                    }

                                    );
                        }
                    },new Pitaya.pc_request_error_cb_t(){
                        @Override
                        public void call(Pitaya.pc_request_t req, Pitaya.pc_error_t error) {
                            //error
                            System.out.println(error.code());
                        }
                    });



            Pitaya.pc_client_set_push_handler(client, new Pitaya.pc_push_handler_cb_t() {
                @Override
                public void call(Pitaya.pc_client_t client, BytePointer route, Pitaya.pc_buf_t payload)  {

                    String strRoute=route.getString();
                    if(strRoute.equals("onNewMember")){//其他成员加入房间
                        try {
                            taleproto.Game.NewMember m = taleproto.Game.NewMember.parseFrom(payload.base().getStringBytes());
                            System.out.println("玩家加入房间：" +m.getMember().getNickName());

                            byte[] sendText=gateutil.NewTextMessage(123456,"欢迎您：" + m.getMember().getNickName());
                            Pitaya.pc_binary_request_with_timeout(client,"RoomHandler.sendtext",sendText,sendText.length,null,15
                                    ,new Pitaya.pc_request_success_cb_t() {
                                        @Override
                                        public void call(Pitaya.pc_request_t req, Pitaya.pc_buf_t resp) {
                                            System.out.println(resp);
                                        }
                                    }
                                    ,new Pitaya.pc_request_error_cb_t(){
                                        @Override
                                        public void call(Pitaya.pc_request_t req, Pitaya.pc_error_t error) {
                                            System.out.println(error.code());
                                        }
                                    }
                            );

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }


                    }else if(strRoute.equals("onQuitMember")){//其他玩家退出房间

                        try {
                            taleproto.Game.QuitMember m = taleproto.Game.QuitMember.parseFrom(payload.base().getStringBytes());

                            System.out.println("玩家退出房间："+m.getPlayerId());
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }


                    }else if(strRoute.equals("onTextMessage")){//接收文本消息
                        try {

                            byte[] data=payload.base().getStringBytes();
                            int len=Long.valueOf(payload.len()).intValue();
                            if (data !=null) {
                                byte[] bd=java.util.Arrays.copyOf(data,len);
                                taleproto.Game.TextMessage m = taleproto.Game.TextMessage.parseFrom(
                                        bd);
                                System.out.println(String.format("玩家：%d say:%s", m.getPlayerId(), m.getText()));
                            }

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }else{
                        System.out.println("  route: " +strRoute);
                    }



                }
            });

            try {
                Thread.sleep(5000);
            } catch (Exception e) {

            }

            while (true) {
                synchronized (lock) {
                    //System.out.println("无限期等待中...");
                    lock.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
