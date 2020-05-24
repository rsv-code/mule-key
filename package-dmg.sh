#!/bin/bash

#
# This file is part of the Mule-Key (https://github.com/rsv-code/mule-key).
# Copyright (c) 2020 Roseville Code Inc
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, version 3.
#
# This program is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program. If not, see <http://www.gnu.org/licenses/>.
#

# I could only get this working using the OpenJDK build avaiable
# from AdoptOpenJDK. https://adoptopenjdk.net
# The one in the standard deb repo creates a
# java.lang.module.FindException: Hash of ... differs to expected hash ... recorded in java.base

# Modules need to be downloaded from here and then
# the PATH_TO_FX_MODS needs to be set to it.
# https://gluonhq.com/products/javafx/

echo "Running the packager for mule-key."
echo "Copyright 2020 Roseville Code Inc"
echo "Licensed under the GNU GPL Version 3"
echo ""
echo "If all goes well resulting .deb will be in the target/ directory."
echo ""

if [ -z "$JAVA_HOME" ]
then
  echo "Error, \$JAVA_HOME is not set. Please set it in your environment or at the top of this script."
  exit 1
else
  echo "\$JAVA_HOME found, continuing."
fi

if [ -z "$PATH_TO_FX" ]
then
  echo "Error, \$PATH_TO_FX is not set. Please set it in your environment or at the top of this script."
  exit 1
else
  echo "\$PATH_TO_FX found, continuing."
fi

if [ -z "$PATH_TO_FX_MODS" ]
then
  echo "Error, \$PATH_TO_FX_MODS is not set. Please set it in your environment or at the top of this script."
  exit 1
else
  echo "\$PATH_TO_FX_MODS found, continuing."
fi

echo "Packaging with maven ...";
mvn clean package
if [[ "$?" -ne 0 ]]
then
  echo "Error, build failure. Cannot package without a successful build. Exiting."
  exit 1
else
  echo "Build successful, running jpackage ..."
  jpackage \
    -t dmg \
    -d target \
    -i target \
    -n "mule-key" \
    --icon "img/mule-key.png" \
    --app-version "1.0.0" \
    --copyright "Copyright 2020 Roseville Code Inc" \
    --description "Mule-Key is a GUI application that provides Mule key encryption functionality." \
    --vendor "Roseville Code Inc" \
    --license-file "LICENSE" \
    --module-path $PATH_TO_FX_MODS \
    --add-modules javafx.controls,javafx.fxml \
    --main-class com.lehman.muleKey.Main \
    --main-jar "mule-key-1.0.jar" \
    # I don't intend to produce signed dmgs at this time.
    #--mac-bundle-identifier "Mule-Key-1.0" \
    #--mac-bundle-name "Mule-Key" \
    #--mac-bundle-signing-prefix "" \
    #--mac-signing-keychain "" \
    #--mac-signing-key-user-name "" \
    --verbose
fi