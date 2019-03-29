package dog.tale.pitaya;

import org.bytedeco.javacpp.annotation.*;
import org.bytedeco.javacpp.tools.*;

@Properties(
    target="dog.tale.pitaya.Pitaya",
    value = {
            @Platform(
                    value={""},
                    includepath = {
                            "/Users/arden/data/repository/tale/PitayaClientJava/src/main/cpp/include/",
                            "/Users/arden/data/repository/tale/libpitaya/src"
                    },
                    preloadpath = {

                    },
                    linkpath = {
                            //"/Users/arden/data/repository/tale/PitayaClientJava/lib"
                            //"/Users/arden/data/repository/tale/PitayaClientJava/lib/android/armeabi-v7a"
                            "/Users/arden/data/repository/tale/PitayaClientJava/lib/android/arm64-v8a"
                    },
                    cinclude = {"pitaya_version.h","pitaya.h"},
                    include = {},
                    library = "jniPitaya",
                    //link = "pitaya-mac"
                    link = "pitaya-android"
            )
    }

)
public class PitayaConfig implements InfoMapper {
    public void map(InfoMap infoMap) {
        //infoMap.put(new Info("std::string").annotations("@StdString").valueTypes("BytePointer", "String").pointerTypes("@Cast({\"char*\", \"std::string*\"}) BytePointer"));
        infoMap.put(new Info("PC_CLIENT_CONFIG_DEFAULT", "PC_EXPORT", "PC_VERSION_STR", "pc_lib_version_str()").cppTypes().annotations());
    }
}