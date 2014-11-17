# libGDX sample

# セットアップ
local.properties を設定する

# 起動

## PC
./gradlew desktop:run
./gradlew run

## HTML
./gradlew html:superDev
open http://localhost:8080/html

## Android

# ligGDX プロジェクトの生成
curl -O http://libgdx.badlogicgames.com/nightlies/dist/gdx-setup.jar
java -jar gdx-setup.jar --dir . --name mygame --package nu.rinu.mygame --mainClass MyGame --sdkLocation mySdkLocation

# IDE で開く

## IntelliJ IDEA
Import Project で Gradle プロジェクトとして読み込む
