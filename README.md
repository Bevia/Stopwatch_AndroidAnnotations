# Stopwatch_AndroidAnnotations
Android stopwatch using annotations + butterknife

adding annotations:

gradle-->

        apply plugin: 'android-apt'
        def AAVersion = '3.3.2'
        
        apt {
            arguments {
                androidManifestFile variant.outputs[0].processResources.manifestFile
                resourcePackageName 'corebaseit.com.stopwatchwithannotations'
            }
        }
        
             apt "org.androidannotations:androidannotations:$AAVersion"
             compile "org.androidannotations:androidannotations-api:$AAVersion"
             
              dependencies {
                classpath 'com.neenbedankt.gradle.plugins:android-apt:1.4'
            }
