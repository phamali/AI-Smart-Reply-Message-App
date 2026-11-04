# **AIChat** 🤖💬

**AIChat** is a real-time desktop messaging application built using Clean Architecture. It allows users to manage accounts, connect with friends, and exchange messages seamlessly.

✨ **Key Highlight:** AIChat features an **AI-powered smart response generator**. By analyzing your recent conversation history, the app suggests intelligent, context-aware replies that you can send with a single click.

---

## **Table of Contents** 
- [Features](#features) 
- [Tech Stack & Architecture](#tech-stack--architecture) 

---

## **Features** 

* **Authentication**: Secure Sign Up, Log In, Password Reset, and Logout.
* **Profile Customization**: Choose and update profile avatars.
* **Friend System**: Add users by username to instantly initialize chat rooms.
* **Real-Time Messaging**: Exchange messages with automatic timestamping and smooth scroll behavior.
* **AI Smart Replies**: Leverage GPT-4 integration to generate contextually accurate message suggestions.

---

## **Tech Stack & Architecture**
* **Language:** Java 17
* **UI Framework:** Java Swing styled with **FlatLaf** for modern light/dark themes.
* **Architecture:** Clean Architecture (Entities, Use Cases, Interface Adapters, Frameworks/Drivers).
* **APIs & Libraries:** OkHttp3, Gson, OpenAI Chat Completions API, JUnit 5.

---

## **Table of Contents**  
- [Features of Software](#features-of-software)  
- [Installation Instructions](#installation-instructions)  
- [Usage Guide](#usage-guide)  
- [License](#license)  
- [Section for Feedback](#section-for-feedback)  
- [Section for Contributions](#section-for-contributions)  

---

## **Installation Instructions**

1. **Prerequisites:** Install **Java 11+** (JDK 17 recommended) and **IntelliJ IDEA**.
2. **Clone:** Run `git clone https://github.com/your-username/AIChat.git` in your terminal.
3. **Import:** Open the project folder in IntelliJ as a **Maven** project and let `pom.xml` sync dependencies.
4. **Run:** Open `src/main/java/app/Main.java`, right-click, and select **Run 'Main.main()'**.

## **Installation Instructions**  

### **Install IntelliJ Ultimate IDEA**  
Visit the [JetBrains website](https://www.jetbrains.com/idea/) for instructions on how to download IntelliJ Ultimate IDEA.  
- **Students**: You have access to a student license for IntelliJ Ultimate IDEA. Sign up on [JetBrains Students](https://www.jetbrains.com/student/).  
- Follow the JetBrains official installation guide for further details.

### **Install Java**  
Download a JDK from version 11 or higher. Version 17 is recommended.  
- Follow instructions for installing Java [here](https://www.oracle.com/java/technologies/javase-downloads.html).  

### **Install Git**  
IntelliJ provides version control integration, including Git.  
- Follow the [JetBrains Git Integration Guide](https://www.jetbrains.com/help/idea/enabling-version-control.html).  
- If Git isn't installed on your computer, download it from [Git SCM](https://git-scm.com/downloads).  

### **Get a GitHub Account**  
1. Sign up at [GitHub](https://github.com/).  
2. Fork this repository.  
3. Copy the HTTPS link to clone the repository.

---

## **Usage Guide**  

1. **Landing Page**:  
   - If you do not have an account, press **Sign Up**.  
   - If you have an account, press **Login**.  
<img src="https://github.com/user-attachments/assets/61e26abe-c49d-4860-a578-afa8e2326b92" alt="Image 1" width="300" />

2. **Sign Up**:  
   - Create an account with a username, password, and email. After signing up, log in using your credentials.  

<img src="https://github.com/user-attachments/assets/9cfa9f04-5057-4488-a983-4b57bcfbf0a7" alt="Image 3" width="300" />
<img src="https://github.com/user-attachments/assets/de753d83-c7dd-4c4a-bb1d-42a51c6ffbbc" alt="Image 2" width="300" />

3. **Home Page**:  
   - Once logged in, you will be redirected to the home page.  
<img src="https://github.com/user-attachments/assets/0571e4dd-4db8-4b3a-b2e3-89da75c28047" alt="Home Page" width="300" />

4. **Add Friend**:  
   - Press the **Add Friend** button at the top right of the home page.  
   - Enter the username of an existing user to start a conversation.  
<img src="https://github.com/user-attachments/assets/13e68920-4568-4060-8f72-2ccd1f9b4e65" alt="Add Friend 1" width="300" />
<img src="https://github.com/user-attachments/assets/203d8d91-4fe4-4cf2-9981-d92c77279701" alt="Add Friend 2" width="300" />

5. **View Conversations**:  
   - The homepage displays previews of conversations.  
   - Double-click a conversation preview to view or enter the chat with another user.  
<img src="https://github.com/user-attachments/assets/752e4865-61ff-4927-9abe-42c975750460" alt="View Conversations" width="300" />

6. **Send Messages**:  
   - Type your message at the bottom of the chat screen and press **Send**.  
   - Incoming messages from the other user will appear in the chat.  
<img src="https://github.com/user-attachments/assets/737b47e3-ce7b-4525-976f-43e1f0959e5f" alt="Send Message 1" width="300" />
<img src="https://github.com/user-attachments/assets/232f0092-f198-4971-b7c8-2a89f4bbfd8d" alt="Send Message 2" width="300" />

7. **AI Suggestions**:  
   - Press the **AI Suggestion** button to generate context-aware responses for your conversation.  
<img src="https://github.com/user-attachments/assets/996d0062-2164-4df8-b02e-dd77f2e7bef5" alt="AI Suggestions 1" width="300" />
<img src="https://github.com/user-attachments/assets/f963c607-df38-4b1d-8309-b9e57473871f" alt="AI Suggestions 2" width="300" />

8. **Settings Page**:  
   - Access settings via the **Settings** button on the homepage.  
   - Change your profile picture, password, or logout.  
<img src="https://github.com/user-attachments/assets/67a021b4-4cc9-4a12-99df-e1655304c0c1" alt="Settings Page" width="300" />

9. **Profile Picture**:  
   - Press **Upload Profile Picture** and select an image.  
<img src="https://github.com/user-attachments/assets/7cbcaa49-dbc5-4728-89e7-e9cd16b6a554" alt="Profile Picture 1" width="300" />
<img src="https://github.com/user-attachments/assets/d3deeb75-4264-4f82-930a-69a0f683c19f" alt="Profile Picture 2" width="300" />

10. **Change Password**:  
    - Input your current password and confirm the new password.  
<img src="https://github.com/user-attachments/assets/a197801f-a14a-4b4e-9f9a-b2bd58a818fb" alt="Change Password 1" width="300" />
<img src="https://github.com/user-attachments/assets/3b3cfdd4-242c-4346-b938-7d28e4f90ab6" alt="Change Password 2" width="300" />

11. **Logout**:  
    - Press the **Logout** button to sign out.  
<img src="https://github.com/user-attachments/assets/ccc198bf-982b-4467-9b33-a981087d6650" alt="Logout 1" width="300" />
<img src="https://github.com/user-attachments/assets/bfa73419-f03d-4b9a-bdd5-9090646aa265" alt="Logout 2" width="300" />

---

## Demo

[![Watch the Demo](https://via.placeholder.com/640x360.png?text=Click+to+Watch+Demo)](https://streamable.com/2jplu5)

---
## **License**  

This project is licensed under the terms of the [CC0 1.0 Universal (Public Domain Dedication)](./LICENSE). See the `LICENSE` file for more details.

---

### **Authors**  
Alisha, Eddie, Tiffany, Julian, Dareenat
