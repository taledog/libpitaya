//
//  ViewController.swift
//  PitayaClientSwift
//
//  Created by arden on 2019/2/26.
//  Copyright © 2019 arden. All rights reserved.
//


import UIKit
import Alamofire

class ViewController: UIViewController {


    override func viewDidLoad() {
        super.viewDidLoad()
        
        var blockSelf = self
        weak var weakSelf = self
        //let controllerPoint = withUnsafeMutablePointer(to: &blockSelf) { return $0};
        let mySelf = UnsafeRawPointer(Unmanaged.passUnretained(self).toOpaque())
        
        var client_info : pc_lib_client_info_t = pc_lib_client_info_t();
        client_info.platform = UnsafePointer("IOS");
        client_info.build_number = UnsafePointer("90");
        client_info.version = UnsafePointer("1.0");
        
        pc_lib_init(nil, nil, nil, nil, client_info);
        pc_lib_set_default_log_level(PC_LOG_ERROR);
        
        var config : pc_client_config_t = pc_client_config_t(conn_timeout: 30, enable_reconn: 1, reconn_max_retry: PC_ALWAYS_RETRY, reconn_delay: 30, reconn_delay_max: 1, reconn_exp_backoff: 1, enable_polling: 0, local_storage_cb: nil, ls_ex_data: nil, transport_name: PC_TR_NAME_UV_TCP, disable_compression: 0)
        
        config.transport_name = PC_TR_NAME_UV_TCP;
        config.enable_reconn = 1;
        
        let result : pc_client_init_result_t = pc_client_init(nil, UnsafePointer([config]));
        let client = result.client;
        client?.pointee.config.reconn_delay = 10;
        
        pc_client_connect(client, UnsafePointer("tujiao.co"), 3252, nil);
        
        pc_client_add_ev_handler(client, {(client, ev_type, ex_data, arg1, arg2) in
        
            if (ev_type == PC_EV_USER_DEFINED_PUSH) {
                print("===========");
            }
            
            if (ev_type == PC_EV_CONNECTED) {
                print("pitaya server is connected.");
                
                pc_string_request_with_timeout(client, UnsafePointer("room.join"), nil, nil, 15, {(req, buf) in
                    print("1111111111");
                    let content = buf!.pointee.base.unsafelyUnwrapped;
                    print("recv \(String(cString: content)) \r\n");
                }, { (req, error) in
                    print(error?.pointee.payload.base);
                })

                
                pc_string_notify_with_timeout(client, UnsafePointer("room.message"), UnsafePointer("{\"name\":\"arden\", \"content\":\"welcome arden(他乐)\"}"), nil, 15, { (req, error) in
                    print(error);
                })
                
            }
            print("test: get event %s, arg1: %s, arg2: %s\n", pc_client_ev_str(ev_type), arg1, arg2);
        }, nil, nil);
        
        
        
        pc_client_set_push_handler(client) { (client, router, buf) in
            let routerStr = String(cString:router.unsafelyUnwrapped);
            let message = String(cString:buf!.pointee.base.unsafelyUnwrapped);
            if (routerStr == "onMessage") {
                print("recv \(routerStr) \r\n")
                print("recv \(message) \r\n")
            }
        }
        
    }
    
    func test() {
        print("aaaaaa");
    }
    
}

func UInt8ToStr(bytes:[UInt8]) -> String {
    var hexStr = ""
    for index in 0 ..< bytes.count {
        var Str = bytes[index].description
        if Str.count == 1 {
            Str = "0 "+Str;
        }else {
            let low = Int(Str)!%16
            let hight = Int(Str)!/16
            Str = hexIntToStr(HexInt: hight) + hexIntToStr(HexInt: low)
        }
        hexStr += Str
    }
    return hexStr
}

func hexIntToStr(HexInt:Int) -> String {
    var Str = ""
    if HexInt>9 {
        switch HexInt{
        case 10:
            Str = "A"
            break
        case 11:
            Str = "B"
            break
        case 12:
            Str = "C"
            break
        case 13:
            Str = "D"
            break
        case 14:
            Str = "E"
            break
        case 15:
            Str = "F"
            break
        default:
            Str = "0"
        }
    }else {
        Str = String(HexInt)
    }
    
    return Str
}
