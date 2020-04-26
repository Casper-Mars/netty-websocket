package org.r.server.websocket.camera.enums;

/**
 * date 20-4-10 下午4:13
 *
 * @author casper
 **/
public class EventStatusCode {


    public static int EVENT_CONNECTING = 0;
    public static int EVENT_CONNECTOK = 1;
    public static int EVENT_CONNECTFAILED = 2;
    public static int EVENT_SOCKETERROR = 3;
    public static int EVENT_LOGINOK = 4;
    public static int EVENT_LOGINFAILED = 5;
    public static int EVENT_STARTAUDIOOK = 6;
    public static int EVENT_STARTAUDIOFAILED = 7;
    public static int EVENT_STOPAUDIOOK = 8;
    public static int EVENT_STOPAUDIOFAILED = 9;
    public static int EVENT_SENDPTZOK = 10;
    public static int EVENT_SENDPTZFAILED = 11;
    public static int EVENT_SENDAUXOK = 12;
    public static int EVENT_SENDAUXFAILED = 13;
    public static int EVENT_UPLOADOK = 14;
    public static int EVENT_UPLOADFAILED = 15;
    public static int EVENT_DOWNLOADOK = 16;
    public static int EVENT_DOWNLOADFAILED = 17;
    public static int EVENT_REMOVEOK = 18;
    public static int EVENT_REMOVEFAILED = 19;
    public static int EVENT_SENDPTZERROR = 20;
    public static int EVENT_PTZPRESETINFO = 21;
    public static int EVNET_PTZNOPRESETINFO = 22;
    public static int EVENT_PTZALARM = 23;
    public static int EVENT_RECVVIDEOPARAM = 24;
    public static int EVENT_RECVAUDIOPARAM = 25;
    public static int EVENT_CONNECTRTSPERROR = 26;
    public static int EVENT_CONNECTRTSPOK = 27;
    public static int EVENT_RTSPTHREADEXIT = 28;
    public static int EVENT_URLERROR = 29;
    public static int EVENT_RECVVIDEOAUDIOPARAM = 30;
    public static int EVENT_LOGIN_USERERROR = 31;
    public static int EVENT_LOGOUT_FINISH = 32;
    public static int EVENT_LOGIN_RECONNECT = 33;
    public static int EVENT_LOGIN_HEARTBEAT_LOST = 34;
    public static int EVENT_STARTAUDIO_ISBUSY = 35;
    public static int EVENT_STARTAUDIO_PARAMERROR = 36;
    public static int EVENT_STARTAUDIO_AUDIODDISABLED = 37;
    public static int EVENT_CONNECT_RTSPSERVER_ERROR = 38;
    public static int EVENT_CREATE_RTSPCLIENT_ERROR = 39;
    public static int EVENT_GET_RTSP_CMDOPTION_ERROR = 40;
    public static int EVENT_RTSP_AUTHERROR = 41;
    public static int EVNET_RECORD_FILEBEGIN = 42;
    public static int EVENT_RECORD_FILEEND = 43;
    public static int EVENT_RECORD_TASKEND = 44;
    public static int EVENT_RECORD_DISKFREESPACE_TOOLOW = 45;
    public static int EVNET_RECORD_FILEBEGIN_ERROR = 46;
    public static int EVNET_RECORD_WRITE_FILE_ERROR = 47;
    public static int EVENT_RECORD_INITAVIHEAD_ERROR = 48;
    public static int EVENT_RECORD_MEDIA_PARAM_ERROR = 49;
    public static int EVENT_NVR_CHANNELS = 50;
    public static int EVENT_NVR_IPC_STATUS = 51;
    public static int EVENT_SYSTEMREBOOT_ANDRELOGINOK = 52;
    public static int EVENT_NETWORKRESET_ANDRELOGINOK = 53;
    public static int EVENT_UPLOAD_PROCESS = 54;
    public static int EVENT_DOWNLOAD_PROCESS = 55;
    public static int EVENT_DOWNLOAD_RETRY_ANDRESTAR = 56;
    public static int EVENT_LOGOUT_BYUSER = 57;
    public static int EVENT_P2P_CONNECT_STATE_INFO = 58;
    public static int EVNET_INITP2P_OK = 59;
    public static int EVNET_INITP2P_ERROR = 60;
    public static int EVENT_START_CONNECT_DEVICE = 61;
    public static int EVENT_START_CONNECT_DEVICE_ERROR = 62;
    public static int EVENT_P2PSERVER_LOGIN_OK = 63;
    public static int EVENT_P2PSERVER_LOGOUT = 64;
    public static int EVENT_P2PERROR_EVNETINFO = 65;
    public static int EVENT_P2PCONNECT_DEVICEOK = 66;
    public static int EVENT_P2PCONNECT_CLOSE = 67;
    public static int EVENT_P2P_EXIT_CONNECT = 68;
    public static int EVENT_CAPTURE_IMAGE_FINISH = 69;
    public static int EVENT_RECVABLITY_INFO = 70;
    public static int EVENT_P2P_CLINET_CHANNLEINFO = 71;
    public static int EVENT_P2P_STARTSTREAM_ERROR11 = 72;
    public static int EVENT_PTZADV_FUNCLIST = 73;
    public static int EVENT_PTZADV_FUNCRET = 74;
    public static int EVENT_P2PDEVICE_OFFLINE = 75;
    public static int EVENT_P2P_LOGIN_DEVICEOK = 76;
    public static int EVENT_P2P_NVR_LOGIN_FULL = 77;
    public static int EVENT_P2P_DEVICE_NOTIFY = 78;
    public static int EVENT_DOWNLOAD_START = 202;
    public static int EVENT_P2P_DEVICE_ERROR = 203;
    public static int EVENT_RTSP_NVR_IPC_STATUS = 204;
    public static int EVENT_AI_FACEDETECT_REPORT = 200;
    public static int EVENT_AI_FACEDETECT_IMAGEDATA_REPORT = 201;
    public static int EVENT_AI_FACEDETECT_IMAGEDATA_REPORT_ONLYDATA = 205;
    public static int EVENT_AI_FACERECOGNITION_REPORT = 206;


}
