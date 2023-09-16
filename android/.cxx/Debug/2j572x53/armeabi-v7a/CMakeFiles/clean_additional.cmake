# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "/Users/coink/Desktop/projects/oink-hardware-plugins/capacitor-pelicano/android/src/main/java/hardware/pelicano/PelicanoControl.java"
  "/Users/coink/Desktop/projects/oink-hardware-plugins/capacitor-pelicano/android/src/main/java/hardware/pelicano/PelicanoControlJAVA_wrap.cxx"
  "/Users/coink/Desktop/projects/oink-hardware-plugins/capacitor-pelicano/android/src/main/java/hardware/pelicano/PelicanoControlJNI.java"
  )
endif()
