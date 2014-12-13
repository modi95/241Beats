#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>

#define DEBUG_TAG "NDK_AndroidNDK1SampleActivity"

void Java_com_akmodi2_beats241_MainActivity_recieveData(JNIEnv * env, jobject this, jstring logThis)
{
    jboolean isCopy;
    const char * szLogThis = (*env)->GetStringUTFChars(env, logThis, &isCopy);

    __android_log_print(ANDROID_LOG_DEBUG, DEBUG_TAG, "NDK:LC: [%s]", szLogThis);

    (*env)->ReleaseStringUTFChars(env, logThis, szLogThis);
}

int Java_com_akmodi2_beats241_MainActivity_buildClient(JNIEnv * env, jobject this,jstring stURL, jstring stPORT) {
	int s;
	jboolean isCopy;
    int sock_fd = socket(AF_INET, SOCK_STREAM, 0);

    char* URL;
    char* PORT;

    URL = (*env)->GetStringUTFChars(evn, stURL, &isCopy);
    PORT = (*env)->GetStringUTFChars(evn, stPORT, &isCopy);

    struct addrinfo hints, *result;
    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_family = AF_INET; /* IPv4 only */
    hints.ai_socktype = SOCK_STREAM; /* TCP */

    s = getaddrinfo(URL, PORT, &hints, &result);

    /* error checking */
    // if (s != 0) {
    //         fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(s));
    //         exit(1);
    // }

    connect(sock_fd, result->ai_addr, result->ai_addrlen);

    return sock_fd;
}

char* Java_com_akmodi2_beats241_MainActivity_sender(JNIEnv * env, jobject this, int sock_fd, jstring stdata) {
	char* data = (char*)stdata;
	write(sock_fd, data, strlen(data));

    return data;
}

jstring Java_com_akmodi2_beats241_MainActivity_receiver(JNIEnv * env, jobject this, int sock_fd) {
	char buffer[000000000];/* substitute with normal length of data to receive */
	ssize_t n = recv(sock_fd, buffer, 0000000000, 0);
	buffer[n] = '\0';

	// from JNI api documentation
	// jstring NewStringUTF(JNIEnv *env, const char *bytes)
	jstring jstrBuf = (*env)->NewStringUTF(env, buffer); // Maybe need to include something or declare/initialize "env"?

	return jstrBuf;
}
