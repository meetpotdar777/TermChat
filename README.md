# 🚀 TermChat

  TermChat is a lightweight, high-performance Java application designed for real-time communication directly through the terminal. Built using Java Sockets and Multi-threading, it offers a secure "Sudo-style" environment with advanced features like private messaging, automated word filtering, and synchronized server logging.

---

## ✨ Key Features

* **Multi-User Support**: Powered by Java Multi-threading, allowing many users to chat simultaneously.
* **Private Whispers**: Use `/msg <username> <message>` to send encrypted-style private texts.
* **Smart Word Filter**: Automatically detects and censors banned words using regex-based filtering.
* **Server History Logs**: Every public message and private whisper is recorded in `chat_history.txt` with timestamps.
* **ANSI Color Styling**: A fully color-coded terminal interface for better readability and a "hacker" aesthetic.
* **Live User List**: Check who is online in real-time with the `/list` command.
* **Automated Build System**: Includes an "Ultimate Launcher" (`run.bat`) that compiles, builds JAR files, and launches the ecosystem in one click.

---

## 🛠️ Tech Stack

| Layer      | Technology                            |
|:-----------|:--------------------------------------|
| Language   | Java 17+ (Core Networking)            |
| Protocol   | TCP/IP (Transmission Control Protocol)|
| Concurrency| Java Threads & Client Handlers        |
| Data       | Java IO (Streams & Buffered Readers)  |
| Automation | Batch & Bash Scripting                |

## 🛠️ Commands

| Command             | Action                                                  |
| :------------------ | :------------------------------------------------------ |
| `/help`             | Displays the command menu and chat rules.               |
| `/list`             | Shows a list of all currently connected usernames.      |
| `/msg <name> <msg>` | Sends a private message to a specific user.             |
| `/quit`             | Safely disconnects from the server.                     |

---

## 🚀 How to Run

### Windows (One-Click)
1. Navigate to the root folder.
2. Double-click `run.bat`.
3. The server will launch in a new window, and your client will start automatically.

### Manual Launch (Cross-Platform)
1. **Compile**:
   `javac -d bin src/com/termchat/shared/*.java src/com/termchat/server/*.java src/com/termchat/client/*.java`
2. **Start Server**:
   `java -cp bin com.termchat.server.TermServer`
3. **Start Client**:
   `java -cp bin com.termchat.client.TermClient`

---

## 📁 Project Structure

```bash
TermChat/
├── src/                  # Java Source Files
│   └── com/termchat/
│       ├── client/       # Client-side logic
│       ├── server/       # Server-side & Multi-threading logic
│       └── shared/       # Constants, Rules, and Word Lists
├── bin/                  # Compiled Bytecode (.class files)
├── chat_history.txt      # Automated Server Logs
├── TermChat_Client.jar   # Executable Client Application
└── run.bat               # Master Build & Launch Script
```

---

* **Developer Note** : This project was designed to demonstrate advanced networking concepts including Socket Programming, Thread Pooling, and Synchronized Collections.

---

👨‍💻 Author
Meet Potdar Backend & Networking Developer

<p align="center"> <img src="Screenshot.png" width="600" title="TermChat Terminal Interface"> </p>

Built with ❤️ using Java Sockets & Multi-threading
