#include <ESP8266WiFi.h>
//How it works: The Arduino (with WiFi) acts as a server or client, and the Python app sends HTTP requests or opens sockets.

// HTTP Server on Arduino

//Arduino runs a web server.
//Java sends HTTP requests to configure it.

const char* ssid = "AndroidAPFB58";  // Replace with your WiFi SSID
const char* password = "nikoletakiwow";  // Replace with your WiFi password

// Define LED pins
const int ledRed = D1;  // GPIO5 (D1 on NodeMCU)
const int ledBlue = D2; // GPIO4 (D2 on NodeMCU)

WiFiServer server(80);

void setup() {
  // Initialize serial communication
  Serial.begin(9600);
  //disable sleep mode
  WiFi.setSleep(false);
  
  // Set LED pins as output
  pinMode(ledRed, OUTPUT);
  pinMode(ledBlue, OUTPUT);
  
  // Connect to Wi-Fi
  Serial.println("Connecting to Wi-Fi...");
  WiFi.begin(ssid, password);

  int attempts = 0;
  //try to connect to WiFi until 20 attempts
  while (WiFi.status() != WL_CONNECTED && attempts < 20) {
    delay(1000);
    Serial.print(".");
    attempts++;
  }

    if (WiFi.status() == WL_CONNECTED) {
    Serial.println("Connected!");
    Serial.print("IP Address: ");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("Failed to connect to Wi-Fi.");
  }

  // Start the server
  server.begin();
  Serial.println("Server started.");
  Serial.print("Use this URL to connect: "); //use this URL to AndroidStudio
  Serial.print("http://");
  Serial.println(WiFi.localIP());
}

void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  // Wait until the client sends some data
  while (!client.available()) {
    delay(1);
  }

  // Read the request
  String request = client.readStringUntil('\r');
  Serial.println(request);
  client.flush();

  // Control LEDs based on the request from the android app
  if (request.indexOf("/red/on") != -1) {
    digitalWrite(ledRed, HIGH);  // Turn on the red LED
  }
  if (request.indexOf("/red/off") != -1) {
    digitalWrite(ledRed, LOW);   // Turn off the red LED
  }
  if (request.indexOf("/blue/on") != -1) {
    digitalWrite(ledBlue, HIGH);  // Turn on the blue LED
  }
  if (request.indexOf("/blue/off") != -1) {
    digitalWrite(ledBlue, LOW);   // Turn off the blue LED
  }

  // Send a response to the client
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println("");
  client.println("<!DOCTYPE HTML>");
  client.println("<html>");
  client.println("<h1>ESP8266 LED Control</h1>");
  client.println("<a href=\"/red/on\">Turn Red LED ON</a><br>");
  client.println("<a href=\"/red/off\">Turn Red LED OFF</a><br>");
  client.println("<a href=\"/blue/on\">Turn Blue LED ON</a><br>");
  client.println("<a href=\"/blue/off\">Turn Blue LED OFF</a><br>");
  client.println("</html>");
}
