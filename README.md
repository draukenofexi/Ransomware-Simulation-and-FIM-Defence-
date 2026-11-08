# Ransomware-Simulation-and-FIM-Defence-
A Java-based Red Team vs Blue Team simulation featuring AES encryption and real-time File Integrity Monitoring (FIM).

# 🛡️ Ransomware Simulation & File Integrity Monitoring (FIM) in Java

## 🛑 STRICT LEGAL & ETHICAL DISCLAIMER (MUST READ)
**FOR EDUCATIONAL AND RESEARCH PURPOSES ONLY.**

1. **No Malicious Use:** This project is created strictly to understand the underlying mechanics of ransomware and defensive programming. It is **NOT** meant to be used for scamming, extortion, or any malicious activities. Any misuse of this code is strictly against ethical hacking guidelines.
2. **DO NOT RUN ON PERSONAL FILES:** Never run the `Ransomware.java` script on your main documents, photos, or personal folders. It will permanently encrypt and delete your original files. **Only use a dedicated dummy folder for testing.**
3. **Responsibility:** The author of this repository is not responsible for any data loss or legal consequences caused by the misuse of this code.

---

## 📖 Project Overview

This project is a complete Red Team vs Blue Team simulation built in Java. It demonstrates:

* **The Attack:** How ransomware uses AES Cryptography to lock files and make them unreadable.
* **The Defense:** How to build a "WatchDog" using Java's File Integrity Monitoring (FIM) to detect unauthorized file encryptions in real-time.
* **The Recovery:** How to reverse-engineer the attack and decrypt the data using the exact secret key.

---

## ⚙️ Setup & Configuration (Do this first!)

Before running the project, you MUST configure it according to your system:

1. **Set Your Target Folder:**
   Open all three `.java` files and change the `targetPath` variable to a dummy folder on your computer. 
   ```java
   String targetPath = "C:\\Path\\To\\Your\\Dummy_Folder";

2. Create Your Own Password (Secret Key):
In Ransomware.java and Decryptor.java, change the SECRET_KEY to your own 16-character password.

for example - private static final String SECRET_KEY = "Your16CharKey123";

🚀 Step-by-Step Execution & Live Testing

Step 1: Create a Dummy Environment
First, we created a dummy folder and added some sample files (like .txt and .jpg) to test our attack safely.

Step 2: Activate the WatchDog (Blue Team Defense)
We run the FolderWatcher.java script. It uses WatchService to monitor the folder 24/7 for any suspicious activity.

Step 3: Launch the Ransomware Attack (Red Team)
We run Ransomware.java. It scans the folder, reads the bytes, encrypts them using AES, creates new .locked files, and deletes the originals.

Encrypted Garbage Value Result:

Step 4: Real-Time Detection!
As soon as the .locked files are created, our WatchDog immediately detects the ransomware signature and throws a DANGER ALERT in the terminal!

Step 5: Data Recovery
Finally, we run Decryptor.java. It uses our Secret Key to unlock the .locked files, restores the original data, and cleans up the encrypted files.

​🎓 Conclusion
​This project successfully demonstrates the dual nature of cryptography. The exact same AES algorithm used to destroy and lock data can be reversed to restore it, provided you have the right keys. Furthermore, it highlights the importance of active defensive mechanisms (FIM) in modern cybersecurity infrastructures.
