#!/bin/bash

ANDROID_HOME="/Users/arden/Library/Android/sdk"
ANDROID_NDK_HOME="/Users/arden/Library/Android/sdk/ndk-bundle"
ANDROID_NDK_COMPILER="$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/darwin-x86_64/bin/clang++"

#PITAYA_CPP_INCLUDEPATH="/Users/arden/data/repository/tale/PitayaClientJava/src/main/cpp/include"
#PITAYA_CPP_SOURCE_INCLUDEPATH="/Users/arden/data/source/go/game/libpitaya/src"
#CPP_INCLUDEPATH_HOME="$ANDROID_NDK_HOME/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include"
#CPP_INCLUDEPATH="$ANDROID_NDK_HOME/sources/cxx-stl/llvm-libc++/include:$ANDROID_NDK_HOME/sources/cxx-stl/llvm-libc++abi/include:$ANDROID_NDK_HOME/sources/android/support/include:$ANDROID_NDK_HOME/sysroot/usr/include:$ANDROID_NDK_HOME/sysroot/usr/include/arm-linux-androideabi:$PITAYA_CPP_INCLUDEPATH:$PITAYA_CPP_SOURCE_INCLUDEPATH"
#LINK_PATH="$ANDROID_NDK_HOME/sources/cxx-stl/llvm-libc++/libs/armeabi-v7a:/Users/arden/data/source/go/game/libpitaya/_builds/android/:/Users/arden/data/source/go/game/libpitaya/_builds/android/deps/libuv-1.23.0"

#echo "$CPP_INCLUDEPATH"

# for java
#javac -cp ../../../lib/javacpp.jar dog/tale/pitaya/PitayaConfig.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.PitayaConfig
#javac -cp .:../../../lib/javacpp.jar dog/tale/pitaya/Pitaya.java
##java -jar ../../../lib/javacpp.jar dog.tale.pitaya.Pitaya
#java -jar ../../../lib/javacpp.jar -nocompile dog.tale.pitaya.Pitaya
#rm -rf dog/tale/pitaya/*.class

# for android arm
#javac -cp ../../../lib/javacpp.jar dog/tale/pitaya/PitayaConfig.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.PitayaConfig -properties android-arm-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#javac -cp .:../../../lib/javacpp.jar dog/tale/pitaya/Pitaya.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.Pitaya -properties android-arm-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#rm -rf dog/tale/pitaya/*.class

# for android arm64
javac -cp ../../../lib/javacpp.jar dog/tale/pitaya/PitayaConfig.java
java -jar ../../../lib/javacpp.jar dog.tale.pitaya.PitayaConfig -properties android-arm64-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
javac -cp .:../../../lib/javacpp.jar dog/tale/pitaya/Pitaya.java
java -jar ../../../lib/javacpp.jar dog.tale.pitaya.Pitaya -properties android-arm64-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
rm -rf dog/tale/pitaya/*.class


## for android x86
#javac -cp ../../../lib/javacpp.jar dog/tale/pitaya/PitayaConfig.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.PitayaConfig -properties android-x86-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#javac -cp .:../../../lib/javacpp.jar dog/tale/pitaya/Pitaya.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.Pitaya -properties android-x86-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#rm -rf dog/tale/pitaya/*.class
#
#
## for android x86 64
#javac -cp ../../../lib/javacpp.jar dog/tale/pitaya/PitayaConfig.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.PitayaConfig -properties android-x86_64-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#javac -cp .:../../../lib/javacpp.jar dog/tale/pitaya/Pitaya.java
#java -jar ../../../lib/javacpp.jar dog.tale.pitaya.Pitaya -properties android-x86_64-clang -Dplatform.root=$ANDROID_NDK_HOME -Dplatform.compiler=$ANDROID_NDK_COMPILER
#rm -rf dog/tale/pitaya/*.class


#javac -cp ../../../lib/javacpp.jar PitayaConfig.java
#java -jar ../../../lib/javacpp.jar PitayaConfig
#javac -cp .:../../../lib/javacpp.jar Pitaya.java
#java -jar ../../../lib/javacpp.jar Pitaya
#rm -rf *.class



