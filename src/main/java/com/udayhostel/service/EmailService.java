package com.udayhostel.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public void sendEnquiryReply(String toEmail, String studentName, String reply)
    {
        String subject = "Reply from Uday Hostel";

        String text =
                "Dear " + studentName + ",\n\n"
                + reply
                + "\n\n"
                + "Regards,\n"
                + "Uday Hostel Management";

        String jsonBody =
                "{"
                + "\"from\":\"Uday Hostel Management <noreply@udayboyshostel.com>\","
                + "\"to\":[\"" + escapeJson(toEmail) + "\"],"
                + "\"subject\":\"" + escapeJson(subject) + "\","
                + "\"text\":\"" + escapeJson(text) + "\""
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.resend.com/emails"))
                .header("Authorization", "Bearer " + resendApiKey)
                .header("Content-Type", "application/json")
                .header("User-Agent", "UdayHostelManagement/1.0")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try
        {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300)
            {
                throw new RuntimeException(
                        "Resend email failed. Status: "
                        + response.statusCode()
                        + ", Response: "
                        + response.body()
                );
            }

            System.out.println("Email sent successfully through Resend.");
            System.out.println("Resend response: " + response.body());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to send email through Resend.", e);
        }
    }

    private String escapeJson(String value)
    {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}