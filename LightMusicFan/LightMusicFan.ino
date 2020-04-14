// Initialize pins for buttons to control music, light, and fan
const int LIGHT_BUTTON = 7;
const int FAN_BUTTON = 5;
const int MUSIC_BUTTON = 8;

// Initialize pins for components controlled by buttons (motor-fan and lights)
const int LIGHT_PIN_1 = 6;
const int LIGHT_PIN_2 = 2;
const int FAN_PIN = 12;

// Remember states for components controlled by buttons (motor-fan, lights, music)
bool STARTED_LIGHT = false;
bool STARTED_FAN = false;
bool STARTED_MUSIC = false;

// Initialize counter that will be used to control buttons to some big number
// Users will be able to click on buttons right after the program starts
// counter controls time before button clicks, avoids turn on/off frequently
int count = 200;

void setup() {
  // Set buttons to input
  pinMode (MUSIC_BUTTON, INPUT);
  pinMode (FAN_BUTTON, INPUT);
  pinMode (LIGHT_BUTTON,INPUT);

  // Set components to output
  pinMode (LIGHT_PIN_1, OUTPUT);
  pinMode (LIGHT_PIN_2, OUTPUT);
  pinMode(FAN_PIN, OUTPUT);

  // Start serial
  Serial.begin(9600);

  // Small delay just to make sure everything is ready to start program
  delay(2000);
}

// Loop that checks if there was user input (button click)
// Depending on input and current state it decides what to do next
void loop() {
  // If button the for music was clicked, music has not started, and counter is greater 10
  // then send message to python script to start music, change state to started music and reset counter
  // (music control needs bigger counter than lights, so that python script reads messages properly)
  if ((digitalRead(MUSIC_BUTTON)==HIGH) && (!STARTED_MUSIC) && (count>10) ){
      send("StartBirds");
      STARTED_MUSIC = true;
      count = 0;
  }

  // If button the for music was clicked, music has started, and counter is greater 10
  // then send message to python script to stop music, change state to not started for music and reset counter
  // (music control needs bigger counter than lights, so that python script reads messages properly)
  if ((digitalRead(MUSIC_BUTTON)==HIGH) && (STARTED_MUSIC) && (count>10) ){
      send("StopBirds");
      STARTED_MUSIC = false;
      count = 0;
  }

  // If light button was clicked, light has not started, and counter is greater than 3 (some wait between button clicks)
  // then turn on light, change state to started light, reset counter
  if ((digitalRead(LIGHT_BUTTON)==HIGH) && (!STARTED_LIGHT) && (count>3) ){
      digitalWrite(LIGHT_PIN_1, HIGH);
      digitalWrite(LIGHT_PIN_2, HIGH);
      STARTED_LIGHT = true;
      count = 0;
  }

  // If light button was clicked, light started, and counter is greater than 3 (some wait between button clicks)
  // then turn off light, change state to not started light, reset counter
  if ((digitalRead(LIGHT_BUTTON)==HIGH) && (STARTED_LIGHT) && (count>3) ){
      digitalWrite(LIGHT_PIN_1, LOW);
      digitalWrite(LIGHT_PIN_2, LOW);
      STARTED_LIGHT = false;
      count = 0;
  }


  // If fan button was clicked, fan has not started yet, counter is greater than 3 (some wait between button clicks)
  // then start fan, change state to started fan, reset counter to 0
   if ((digitalRead(FAN_BUTTON)==HIGH) && (!STARTED_FAN) && (count>3) ){
    digitalWrite(FAN_PIN, HIGH);
    STARTED_FAN = true;
    count = 0;
  }


  // If fan button was clicked, fan has started, counter is greater than 3 (some wait between button clicks)
  // then turn fan off, change state to not started fan, reset counter to 0
  if ((digitalRead(FAN_BUTTON)==HIGH) && (STARTED_FAN) && (count>3) ){
    digitalWrite(FAN_PIN, LOW);
    STARTED_FAN = false;
    count = 0;
  }

  // Increment counter to control button clicks
   count =count + 1;

   // Set some delay between button clicks
   delay(100);
}

// Send messages to python script to turn on or off the song (Reference 2, 5)
// Serial helps to communicate with a script
// It prints the message, then flushes it and have a small delay between sending other messages
// to make sure python script reads it properly
void send(String message){
  Serial.println(message);
  Serial.print("\n");
  Serial.flush();
  delay(50);
}

/* REFERENCES:
* 1) Code for Assignment 1:
* https://github.com/Olga-Chitsvarina/Arduino/blob/master/lcd_display/lcd_display.ino
* 2) Code from first trial with Python Script for Assignment 2:
* https://github.com/Olga-Chitsvarina/Arduino-Musical-Instrument/commit/9d6f0b9ff605cd5f3fa7680977aacec84ca2638b
* 3) Build in Arduino IDE examples for
* Servo (Knob, Sweep), Basics (Blink)
* 4) This video that explains basics of working with transistors:
* https://www.youtube.com/watch?v=gEMBXxWKUS0
* 5) Code for Assignment 3:
* https://github.com/Olga-Chitsvarina/Arduino-Weather-Machine/blob/master/weather/weather.ino
*/
