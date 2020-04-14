#include <M5Stack.h> // For working with m5Stack components
#include <WiFi.h>    // Creation of WiFi access point by ESP 32 microcontroller
#include "WebServer.h"  // Handles HTTP requests
#include <Preferences.h> // Saving WiFi configurations

WebServer webServer(80); // Create webserver at port 80
Preferences preferences; // Creating preferences object


// Define WiFi configuration constants
// IP address of the microcontroller in the network that is created by microcontroller,
// SSID is the name of the WiFi network
// Setting password for security reasons
const IPAddress apIP(192, 168, 4, 1);
const char* apSSID = "Mask";
const char* password = "12345678";


void setup() {
  m5.begin(); // Initialize m5 components
  preferences.begin("wifi-config"); // Setting wifi configurations

  // Workaround to fix connectivity issues, without that access point was not working
  // Android device could find, but could not connect to the network
  WiFi.mode(WIFI_MODE_STA);
  WiFi.disconnect();
  delay(100);

  // Setting WiFi.
  // Set Network IP and Mask
  // Set network name and password
  // Mode to access point (esp controller shares WiFi)
  WiFi.softAPConfig(apIP, apIP, IPAddress(255, 255, 255, 0));
  WiFi.softAP(apSSID, password);
  WiFi.mode(WIFI_MODE_AP);

  // Start webserver on ESP controller
  startWebServer();
}

void loop() {
  // Indefinetly handle client requests
  webServer.handleClient();
}

//Starts Webserver on ESP controller
void startWebServer() {

  // Take WiFi and Password from set preferences
  // Start WiFi sharing with defined settings
  String wifi_ssid = preferences.getString("WIFI_SSID");
  String wifi_password = preferences.getString("WIFI_PASSWD");
  WiFi.begin(wifi_ssid.c_str(), wifi_password.c_str());


  // LCD shows IP of the access point on start
  M5.Lcd.print("Starting Web Server at ");
  M5.Lcd.println(WiFi.softAPIP());

  // Define how webserver handles message intake
  // When get a message of this type /emotion/<EMOTION_NAME>, call this function
  webServer.on("/emotion/{}", []() {
    // Initialize picture path with received emotion name
    String emotionPath = "/Pictures/" + webServer.pathArg(0) + ".jpg";
    // Convert string to char array. Char array has length = emotion path name + 1 because it needs a terminate character (defines end of string)
    char emotion[emotionPath.length() + 1];
    emotionPath.toCharArray(emotion, emotionPath.length() + 1);

    // set brightness of LCD screen
    M5.Lcd.setBrightness(200);

    // Display picture from TF card with specified file name on the LCD screen
    M5.Lcd.drawJpgFile(SD, emotion);

    // Send success response to the client
    webServer.send(200, "text/html", "success " + webServer.pathArg(0));
  });

  // Start webserver
  webServer.begin();
}

/* REFERENCES:
1) Code is based on Arduino IDE Examples for libraries and components:
2) Once standard M5Stack library is included, then: File -> Examples -> M5Stack -> Advanced -> WiFiSetting
3) Examples found in Arduino IDE: File -> WebServer, File -> WebServer -> PathArgServer
4) Sending request from Android: https://developer.android.com/training/volley/simple
5) Examples for M5Stack-Core-ESP32 in Arduino IDE
6) Installation Quick start guide which has been shipped together with ESP32 microcontroller helped to install libraries
7) M5Stack Webserver http://community.m5stack.com/topic/391/i-need-help-with-a-webserver-project/4
8) Getting query params: https://techtutorialsx.com/2017/12/17/esp32-arduino-http-server-getting-query-parameters/
*/
