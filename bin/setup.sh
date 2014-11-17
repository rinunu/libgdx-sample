#!/bin/bash
# プロジェクトを作成する
# 初回に一度だけ実行する
# root で実行してね

root=./

curl http://libgdx.badlogicgames.com/nightlies/dist/gdx-setup.jar > $root/tmp/gdx-setup.jar
java -jar $root/tmp/gdx-setup.jar --dir $root --name mygame --package nu.rinu.mygame --mainClass MyGame --sdkLocation mySdkLocation