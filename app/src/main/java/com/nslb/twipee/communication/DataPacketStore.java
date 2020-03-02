package com.nslb.twipee.communication;

import java.io.File;
import java.io.Serializable;
import java.net.Socket;

//**************************************************************************************************//
//클래스명 : ******************************************************************************************//
//개발자   : ******************************************************************************************//
//수정날짜 : ******************************************************************************************//
//수정내용 : ******************************************************************************************//
//**************************************************************************************************//

// TWIPEE 통신에 사용될 데이터 패킷의 포맷을 선언하고 저장을 담당하는 클래스임 (현재 설계중)
@SuppressWarnings("unused")
public class DataPacketStore {

	/*static public class SubTemp implements Serializable
	{
		String TempString;
		String TempString2;
	}*/

    static public class header //implements Serializable
    {
        int[] header = new int[2];
    }


    // 모든 패킷에 사용될 메인패킷
    static public class MainPacket implements Serializable
    {
        private static final long serialVersionUID = 1L;
        int PacketSize;
        int DataType;
        String UserID;
        String Time;
    }

//메인패킷 뒤에 붙는 서브패킷

    static public class LoginSubPacket implements Serializable
    {
        private static final long serialVersionUID = 1L;
        int SubType;
        String UserPW;
        String UserName;
        String UserEmail;
        String PhoneNumber;
        String BirthDay;
        String SearchedID;
        String SearchedPW;
        boolean SearchedFlag;
        boolean LoginSuccessFlag;
        boolean LogoutSuccessFlag;
        boolean IDDuplicateFlag;
        boolean JoinSuccessFlag;
        boolean EditFlag;
    }

    static public class TripTalkSubPacket implements Serializable
    {//-------종우-------// 패킷 바까주세여
        int SubType;
        int BoardNumber;
        String Participants;
        String ChatRoomTitle;
        String ChatID;
        String InsertLeaveID;
        String ChatMessage;
        String Reply;
        String BoardInfo;
        File ChatImageBuffer;
        File BoardData;
        boolean InsertFlag;
        boolean BoardPublishFlag;
        boolean DeleteFlag;
        boolean EditFlag;
        boolean PublishSuccessFlag;
        boolean TlaverDistributionApply;
        char[] TlaverDistribution;
    }



    static public class FollowSubPacket implements Serializable
    {
        int SubType;
        String FollowID;
        boolean FollowCheck;
    }

    static public class PostSubPacket
    {
        int SubType;
        int PostNumber;
        String PublisherID;
        String Reply;
        String PostInfo;
        File PostData;
        boolean TripStatus;
        boolean PublishFlag;
        boolean EditFlag;
        boolean DeleteFlag;
    }

    static public class PageSubPacket
    {
        int SubType;
        int Step;
        String ChatID;
        String ClickedID;
        String PersonalMessage;
        String ProfileInfo;
        File ProfileData;
        File PostData;
        boolean WhoClicked;
        boolean EditFlag;
    }
}