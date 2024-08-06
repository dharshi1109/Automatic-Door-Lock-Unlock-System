#include <SoftwareSerial.h>

// Define 
 module pins
#define BT_RX 8
#define BT_TX 9

// Define motor control pins

#define MOTOR1_IN1 A0
#define MOTOR1_IN2 A1

#define MOTOR2_IN1 A2
#define MOTOR2_IN2 A3

#define MOTOR3_IN1 2
#define MOTOR3_IN2 3

#define MOTOR4_IN1 4
#define MOTOR4_IN2 5

SoftwareSerial bluetooth(BT_RX, BT_TX);

void setup() {
  // Set motor control pins as output

  pinMode(MOTOR1_IN1, OUTPUT);
  pinMode(MOTOR1_IN2, OUTPUT);

  pinMode(MOTOR2_IN1, OUTPUT);
  pinMode(MOTOR2_IN2, OUTPUT);

  pinMode(MOTOR3_IN1, OUTPUT);
  pinMode(MOTOR3_IN2, OUTPUT);

  pinMode(MOTOR4_IN1, OUTPUT);
  pinMode(MOTOR4_IN2, OUTPUT);

  digitalWrite(MOTOR1_IN1, LOW);
  digitalWrite(MOTOR1_IN2, LOW);

  digitalWrite(MOTOR2_IN1, LOW);
  digitalWrite(MOTOR2_IN2, LOW);

  digitalWrite(MOTOR3_IN1, LOW);
  digitalWrite(MOTOR3_IN2, LOW);

  digitalWrite(MOTOR4_IN1, LOW);
  digitalWrite(MOTOR4_IN2, LOW);
  
  bluetooth.begin(9600);
}

void loop() {
  if (bluetooth.available() > 0) {
    char command = bluetooth.read();
    // Motor 1 control
    if (command == '1') {
      digitalWrite(MOTOR1_IN1, HIGH);
      digitalWrite(MOTOR1_IN2, LOW);delay(30000);
      digitalWrite(MOTOR1_IN1, LOW);
      digitalWrite(MOTOR1_IN2, LOW);
      
      
    }
    else if (command == '2') {
      digitalWrite(MOTOR1_IN1, LOW);
      digitalWrite(MOTOR1_IN2, HIGH);delay(30000);
      digitalWrite(MOTOR1_IN1, LOW);
      digitalWrite(MOTOR1_IN2, LOW);
    }
    else {
     
    }
    
    // Motor 2 control
    if (command == '3') {
     
      digitalWrite(MOTOR2_IN1, HIGH);
      digitalWrite(MOTOR2_IN2, LOW);delay(30000);
      digitalWrite(MOTOR2_IN1, LOW);
      digitalWrite(MOTOR2_IN2, LOW);
    }
    else if (command == '4') {
     
      digitalWrite(MOTOR2_IN1, LOW);
      digitalWrite(MOTOR2_IN2, HIGH);delay(30000);
      digitalWrite(MOTOR2_IN1, LOW);
      digitalWrite(MOTOR2_IN2, LOW);
    }
    else {
     
    }
    
    // Motor 3 control
    if (command == '5') {
     
      digitalWrite(MOTOR3_IN1, HIGH);
      digitalWrite(MOTOR3_IN2, LOW);delay(30000);
      digitalWrite(MOTOR3_IN1, LOW);
      digitalWrite(MOTOR3_IN2, LOW);
    }
    else if (command == '6') {
     
      digitalWrite(MOTOR3_IN1, LOW);
      digitalWrite(MOTOR3_IN2, HIGH);delay(30000);
      digitalWrite(MOTOR3_IN1, LOW);
      digitalWrite(MOTOR3_IN2, LOW);
    }
    else {
      
    }
    
    // Motor 4 control
    if (command == '7') {
     
      digitalWrite(MOTOR4_IN1, HIGH);
      digitalWrite(MOTOR4_IN2, LOW);delay(30000);
      digitalWrite(MOTOR4_IN1, LOW);
      digitalWrite(MOTOR4_IN2, LOW);
    }
    else if (command == '8') {
     
      digitalWrite(MOTOR4_IN1, LOW);
      digitalWrite(MOTOR4_IN2, HIGH);delay(30000);
      digitalWrite(MOTOR4_IN1, LOW);
      digitalWrite(MOTOR4_IN2, LOW);
    }
    else {
      
    }
  }
}
