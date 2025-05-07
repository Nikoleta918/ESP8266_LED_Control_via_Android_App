## 📱 Overview

- The **ESP8266** connects to a Wi-Fi network and runs a basic HTTP server.
- The **Android app** sends GET requests to the ESP8266's IP address to control the LEDs:
  - One button turns on the **red** LED.
  - Another button turns on the **blue** LED.
  
## 🔌 Hardware Requirements

- NodeMCU ESP8266 board
- Two LEDs (e.g., red and blue)
- Two resistors (220Ω recommended)
- Breadboard and jumper wires
- Android device with the app installed

## ⚙️ ESP8266 Code

- Connects to Wi-Fi
- Hosts a web server on port 80
- Listens for specific URLs:
  - `/red/on`, `/red/off` → control red LED
  - `/blue/on`, `/blue/off` → control blue LED
- Responds with a simple HTML page (useful for testing via browser)

**Libraries Used**:  
`ESP8266WiFi.h`

**LED Pin Configuration:**
- Red LED → GPIO5 (D1)
- Blue LED → GPIO4 (D2)

## 📲 Android App

- Built with Java and Android Studio
- Contains two buttons:
  - **Water** → sends `/blue/on` request
  - **Food** → sends `/red/on` request
- Uses `HttpURLConnection` for networking
- Displays Toast messages for feedback

## 🚀 How to Use

### 1. Upload Arduino Code

- Replace the Wi-Fi credentials:
  ```cpp
  const char* ssid = "YOUR_SSID";
  const char* password = "YOUR_PASSWORD";
Upload the code to your NodeMCU.

Open the Serial Monitor to find the IP address of your ESP8266.

2. Update Android App
Replace the IP in MainActivity.java with the ESP8266 IP:

java
Copy
Edit
private String esp8266IP = "http://192.168.X.X";
Build and run the app on your Android device.

3. Press Buttons
Press Water to turn on the blue LED.

Press Food to turn on the red LED.

🛠 Project Structure
markdown
Copy
Edit
├── arduino/
│   └── esp8266_led_control.ino
└── android/
    └── MainActivity.java
📝 Notes
Ensure both devices (ESP8266 and Android) are on the same Wi-Fi network.

You can test the server via browser by visiting: http://<ESP_IP>/red/on
