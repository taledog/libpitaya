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
                            "/Users/arden/data/source/go/game/libpitaya/src/"
                    },
                    preloadpath = {

                    },
                    linkpath = {
                            "/Users/arden/data/repository/tale/PitayaClientJava/lib/android/arm64-v8a",
                            "/Users/arden/data/source/go/game/libpitaya/_builds/android/CMakeFiles/pitaya.dir/src"
                    },
                    cinclude = {"pitaya_version.h","pitaya.h"},
                    include = {},
                    library = "jniPitaya",
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