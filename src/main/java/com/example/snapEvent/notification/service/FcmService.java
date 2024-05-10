package com.example.snapEvent.notification.service;

import com.example.snapEvent.notification.dto.FcmSendDto;

import java.io.IOException;


public interface FcmService {

    int sendMessageTo(FcmSendDto fcmSendDto) throws IOException;

}
