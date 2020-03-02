package com.nslb.twipee.communication;
public class DefineValue {

    int LOGIN_PACKET = 1;
    int TRIPTALK_PACKET = 2;
    int FOLLOW_PACKET =3;
    int PAGE_PACKET =4;
    int POST_PACKET = 5;
    int HEADER_SIZE =8;

    int CLEAR=0;
    int CONNECT_CLEAR=0;



    //----------------------------------------------------------서버 -> 클라이언트----------------------------///

    int LOGIN_SUCCESS = 1;
    int LOGOUT_SUCCESS = 2;
    int JOIN_SUCCESS = 3;
    int ID_DUPLICATE_CHECK = 4;
    int ID_SEARCHED = 5;
    int PW_SEARCHED = 6;
    int USER_IMFORMATION_EDIT = 7;

    //----------------종우----------------------//
    // 서버 -> 클라이언트 보내는 데이터 타입(트립톡)

    int PARTICIPATE_NOTICE=8;
    int PARTICIPANTS_NOTICE=9;
    int LEAVE_PARTICIPATE_NOTICE = 10;
    int TRIP_TALK_MESSAGE = 11;
    int TRIP_TALK_DISTRIBUTION = 12;
    int TRIP_TALK_BOARD_CLICK = 19;
    int TRIP_TALK_BOARD_PUBLISH = 20;
    int TRIP_TALK_BOARD_DELETE = 21;
    int TRIP_TALK_BOARD_EDIT = 22;
    int TRIP_TALK_BOARD_REPLY = 23;
    int TRIP_TALK_BOARD_REPLY_EDIT = 24;
    int TRIP_TALK_BOARD_REPLY_DELETE = 25;
    //-----------------리커넥트------------------//
    int RECONNECT = 13;
    //----------------팔로우--------------------------//
    int FOLLOW_APPLY = 26;
    int UNFOLLOW_APLLY = 27;
    //---------------게시물 페북같은------------------------
    int POST_TAB_CLICK = 28;
    int POST_PUBLISH = 29;
    int POST_EDIT = 30;
    int POST_DELETE = 31;
    int POST_REPLY_PUBLISH = 32;
    int POST_REPLY_EDIT = 33;
    int POST_REPLY_DELETE =34;
    int POST_TAB_RESET = 35;
    //------------------------------------------------

    // 클라이언트 -> 서버 인터페이스(페이지 관련)
    //---------------종우----------------------//
    int PAGE_STEP = 14;
    int PAGE_TAB_CLICK = 15;
    int PAGE_INSERT_OTHER_PAGE = 16;
    int PAGE_PERSONAL_MESSAGE = 17;
    int PAGE_EDIT_PROFILE = 18;
    //-----------------------------------------------------------------------------------------------------///
    //-----------------------------------------------------------------------------------------------------///ㅂ





    //----------------종우----------------------//

    int Seoul = 0;
    int Gangwon = 1;
    int Chungbuk = 2;
    int Chungnam = 3;
    int Jeonbuk29254 = 4;
    int Jeonnam = 5;
    int Gyeongnam = 6;
    int Gyeongbuk =7;
    int GangWon = 8;
    int Ulleung = 9;
    int Jeju = 10;






    int LOGIN_FLAG = 1;
    int LOGOUT_FLAG = 2;



}