#include <jni.h>
#include <string.h>
#include <stdio.h>
#include <com_cvsong_study_rice_ndk_JniUtil.h>
/**
 * RSA私钥
 */
const char* AUTH_KEY = "RSA私钥字符串";
/**
 * 发布的app 签名,只有和本签名一致的app 才会返回合法的 授权 key
 */

//Debug签名
const char* SIGN_DEBUG ="3082032f30820217a00302010202045e794a25300d06092a864886f70d01010b05003048310a30080603550406130163310a30080603550408130163310a30080603550407130163310a3008060355040a130163310a3008060355040b130163310a30080603550403130163301e170d3138303531353030353230385a170d3433303530393030353230385a3048310a30080603550406130163310a30080603550408130163310a30080603550407130163310a3008060355040a130163310a3008060355040b130163310a3008060355040313016330820122300d06092a864886f70d01010105000382010f003082010a0282010100b8c5f116cdcaa9d9c26c1fb2e138db55321f0b757ca5ae13697e32f36f8229b3804816c702e94c60a51a45b5b6d80ebdf500612b7649b955f720f0e3a6d3bba78d23439bbcf5bddb00ff1879db33663174e0340071705f0274ac9cd43db8744d8d019ad6591c9e9e7de9c85a5e8d746046380baecda6db21aa0b720bed9c830aa4e24b27edf48d9547f7ccd50e0ec088d8b025bddd736283a95eefa543fbbfa907694aa521fe0b93cd530cd99c6b9255722faaefd507681e4c590103d55904387e2f857c1e0795f472ff60d962cd04db599c92e5747a36470c44c92c52e928fb629af4a50f09cf92739125d051539a07f7f0b9116b3765ac789a67508da468bd0203010001a321301f301d0603551d0e0416041469bb488a1595f438100909daf8cffc4d4c59dd81300d06092a864886f70d01010b05000382010100b2ee8b486f8a29735536a8bb07d703a8d031f470eb62041c3e446e4585972971f1e35269a3beb67404011893ff1badb173ea974d3bee7456f43998ca6bd16f74fad061aa7433f153be965e127dfbf9855ce3c7b936fbb5023118adfb555dacde2ac58e470a4ddb1c0a166e37d04e6df8257b90ef1299d264fb30016e22826b07dc2a27b2b8d20fad1390b4992622701a68a4fd18551c03f6911ab15acaa0d9844dc55aa4d741b1842df575b84f62b88decbdf98620b6dc82ee196be6b4945488bc594a9b26c4fe14a15aa3ffc258b6212bd04bf5295631b8186484a16ee345431b1d765350ee3433bdfc2168cb9c6cd78f29923296cac6de33ead869cf28ce20";
//Release签名信息
const char* SIGN_RELEASE ="3082032f30820217a0030201020204655b336a300d06092a864886f70d01010b05003048310a30080603550406130143310a30080603550408130143310a30080603550407130143310a3008060355040a130143310a3008060355040b130143310a30080603550403130143301e170d3138303531343032313533355a170d3433303530383032313533355a3048310a30080603550406130143310a30080603550408130143310a30080603550407130143310a3008060355040a130143310a3008060355040b130143310a3008060355040313014330820122300d06092a864886f70d01010105000382010f003082010a0282010100ada73981052053dee23c66bf39e71ab24e7a5d907b7798f74d61ce878d8b8b027ee7f98a589131ef93a5fe444edf2ff46008e5a44b499ddda81950936a4e0e828b4468689eb61f6a0e741078582e7a2b3e8c3ee9a3d869e108ad2f5a6238f65e372effc646e90ef56630a4fc8395ecbc3653084c1dae07fff0b0edb68da3f4a7518b52c7a0dff22848994d084c7d1b650e0f21d220a65a811567ead7187f697e5f1a31c7ed5563a3549818c99d271ce568c1130269c75a9155255fcf01c85fdb3d73e8ae846c5a9a9f2c8b61fa4fcb33aa2964d3c42f336d22da34156e498e71eae2696760bcffb22b52d03cddee038333ec10b01f5e2af3f6afd4b7fd74f6b10203010001a321301f301d0603551d0e04160414c3bd4a77fe1e86397bdd319ba10c594d84c491ec300d06092a864886f70d01010b050003820101006959067ab428a2729dafafe1fe8cf5e47b3ec0489dc2fb191b3bf887ec03dbaf0fe10c8cc9c6a99dd977d649d165e6f0e2c60c019cc4c44dd44204058d0f09a1ee12ab06a9158566a9aa3c7073a56494f85a5a9168d0d284514572f51e3f44a439230b17d179a3fbd63e3e0721852fc7f617417b141ed8e0e0d059e16d6160742ee5c7461aec26ba1d288d0bb22ee42d15c05bbfebf46e7545c095e2235b182ed93b42ac729ea35bd9d98f830ad6ee6202f433fbdfa1bd9d25e1295b7445435c21e6b5f3ad11b1470ac7dd31262cf58bd7d37005b750f634d8dae57ba69e8dfbd1f5795a6f0ad27b265967a51854afe96394af8f3997397206381cac04a656ac";

/**
 * 拿到传入的app  的 签名信息，对比合法的app 签名，防止so文件被未知应用盗用
 */
static jclass contextClass;
static jclass signatureClass;
static jclass packageNameClass;
static jclass packageInfoClass;
JNIEXPORT jstring JNICALL Java_com_cvsong_study_rice_ndk_JniUtil_getRsaKey(
        JNIEnv * env, jobject obj, jobject contextObject) {

    jmethodID getPackageManagerId = (env)->GetMethodID(contextClass, "getPackageManager","()Landroid/content/pm/PackageManager;");
    jmethodID getPackageNameId = (env)->GetMethodID(contextClass, "getPackageName","()Ljava/lang/String;");
    jmethodID signToStringId = (env)->GetMethodID(signatureClass, "toCharsString","()Ljava/lang/String;");
    jmethodID getPackageInfoId = (env)->GetMethodID(packageNameClass, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject packageManagerObject =  (env)->CallObjectMethod(contextObject, getPackageManagerId);
    jstring packNameString =  (jstring)(env)->CallObjectMethod(contextObject, getPackageNameId);
    jobject packageInfoObject = (env)->CallObjectMethod(packageManagerObject, getPackageInfoId,packNameString, 64);
    jfieldID signaturefieldID =(env)->GetFieldID(packageInfoClass,"signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatureArray = (jobjectArray)(env)->GetObjectField(packageInfoObject, signaturefieldID);
    jobject signatureObject =  (env)->GetObjectArrayElement(signatureArray,0);

    const char* signStrng =  (env)->GetStringUTFChars((jstring)(env)->CallObjectMethod(signatureObject, signToStringId),0);
    if(strcmp(signStrng,SIGN_DEBUG)==0 || strcmp(signStrng,SIGN_RELEASE)==0)//签名一致  返回合法的 api key，否则返回错误
    {
       return (env)->NewStringUTF(AUTH_KEY);
    }else
    {
       return (env)->NewStringUTF("应用签名信息错误");
    }

}


JNIEXPORT jint JNICALL JNI_OnLoad (JavaVM* vm,void* reserved){

     JNIEnv* env = NULL;
     jint result=-1;
     if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
       return result;

     contextClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/Context"));
     signatureClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/Signature"));
     packageNameClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageManager"));
     packageInfoClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageInfo"));

     return JNI_VERSION_1_4;
 }