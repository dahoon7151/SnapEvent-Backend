package com.example.snapEvent.calendar.onboarding;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class GoogleApiClient {

    public GoogleApiClient(@Value("google_calendar/snapevent_api.json") String keyFileName) throws IOException, GeneralSecurityException {
        /*
         * 서비스 계정 인증
         */
        InputStream keyFile = ResourceUtils.getURL("classpath:" + keyFileName).openStream();
        GoogleCredentials credential = GoogleCredentials.fromStream(keyFile).createScoped(List.of(CalendarScopes.CALENDAR)).createDelegated("csz0209@gmail.com");

        NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestInitializer httpRequestInitializer = new HttpCredentialsAdapter(credential);

        Calendar service = new Calendar.Builder(transport, GsonFactory.getDefaultInstance(), httpRequestInitializer).setApplicationName("snapEventCalendar").build();

        String calendarId = "447330148711df0d08c604fa25edc58941dd9b520a841d2c63641e6203d37ec3@group.calendar.google.com";

        /*
         * 캘린더 일정 생성
         */
        Event event = new Event()
                .setSummary("test") // 일정 이름
                .setDescription("teststst"); // 일정 설명

        DateTime startDateTime = new DateTime("2022-05-18T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Seoul");
        event.setStart(start);
        DateTime endDateTime = new DateTime("2022-05-19T09:00:00-07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Seoul");
        event.setEnd(end);

        //이벤트 참석자 추가
        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("minwest61@gmail.com")
        };
        event.setAttendees(Arrays.asList(attendees));

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=1"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        //이벤트 실행
        event = service.events().insert(calendarId, event).execute();
        log.debug("Event created: {}", event.getHtmlLink());
    }
}


