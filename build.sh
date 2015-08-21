#!/bin/bash

mkdir build
javac -d ./build Main.java
cd build
jar cfe GraphDrawer.jar Main *
mv GraphDrawer.jar ../
cd ..
chmod +x GraphDrawer.jar
rm -R build
         
