# Libpitaya

Libpitaya is a SDK for building clients for projects using the pitaya game server framework and is built on top of [libpomelo2](https://github.com/NetEase/libpomelo2)

## How to Use


### Import the pitaya related header file

Create the Bridging Header file, example PitayaClientSwift-Bridging-Header.h then import.

``` c
#import "pitaya.h"
#import "pc_pitaya_i.h"


### Initialize the pitaya lib

The `pc_lib_init` must be called before any clients are created and should only be called once. The `client_info` struct is pased on to the pitaya server in the handshake, you can use this for some clever routing.  

``` swift
// This info is passed on to the pitaya server in the handshake. You can use this for routing

var client_info : pc_lib_client_info_t = pc_lib_client_info_t();
client_info.platform = UnsafePointer("IOS");
client_info.build_number = UnsafePointer("90");
client_info.version = UnsafePointer("1.0");

pc_lib_init(nil, nil, nil, nil, client_info);
pc_lib_set_default_log_level(PC_LOG_ERROR);
```

To update the `client_info` after the initialization you can use this:
``` swift
pc_update_client_info(client_info.version)
```

### Create a pitaya client and start a connection

This config if for connecting with a TCP port
``` swift
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

```

To connect with a TLS port use this example

``` swift
// This certificate is used for all clients, you can set this in your setup function


```

### Making requests


To make a request with a callback use `pc_string_request_with_timeout`
``` swift
pc_string_request_with_timeout(client, UnsafePointer("room.join"), nil, nil, 15, {(req, buf) in
    print("1111111111");
    let content = buf!.pointee.base.unsafelyUnwrapped;
    print("recv \(String(cString: content)) \r\n");
}, { (req, error) in
    print(error?.pointee.payload.base);
})


```
To make a request without a timeout or a callback 

``` swift
pc_string_notify_with_timeout(client, UnsafePointer("room.message"), UnsafePointer("{\"name\":\"arden\", \"content\":\"welcome arden(他乐)\"}"), nil, 15, { (req, error) in
    print(error);
})
```

### Using protobuf

Generate the objc protobuf files

``` bash
protoc --objc_out=. --proto_path=./Protos/ session-data.proto
```

After generating the files you can include then in your project and use it.
``` objc 
SessionData * d = [[SessionData alloc] init];
d.data_p = @"This is a message";
pc_binary_notify_with_timeout(client, "connector.notifysessiondata", [d.data bytes] ,[d.data length], NULL, 15, notify_error_cb);
```

### Listening to server pushes

``` swift
pc_client_set_push_handler(client) { (client, router, buf) in
    let routerStr = String(cString:router.unsafelyUnwrapped);
    let message = String(cString:buf!.pointee.base.unsafelyUnwrapped);
    if (routerStr == "onMessage") {
        print("recv \(routerStr) \r\n")
        print("recv \(message) \r\n")
    }
}
```

### The test server

The source code for the test server used in  this example is [here](https://github.com/topfreegames/libpitaya/tree/master/pitaya-servers)

