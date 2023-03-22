echo "sdk.dir=/data/android-sdk-linux" > local.properties
echo "ndk.dir=/data/android-sdk-linux/ndk/android-ndk-r21" >> local.properties
chmod +x ./gradlew
python3 script/module_manager.py -v all
gradle app:analyzeOfficialPhoneReleaseApk -Dbranch=${BUILD_BRANCH}
cd  tools
java -jar matrix-apk-canary-2.0.8.jar --config  apk-checker-config.json

echo "apk报表上传到apm server"
cd ${WORKSPACE}/android/app/build/outputs/
A=`find apk/officialPhone/release/ -name "*.apk"`
curl  -X  POST  \
'http://10.114.54.124:9911/api/apkstatement/apk' \
 --form statement=@apk-checker-result.json \
  --form "package=@$A"


#curl  -X  POST  \
#'http://10.114.54.124:9911/api/apkstatement/apk' \
#--header 'Content-Type: application/json'  -d \
#@apk-checker-result.json

