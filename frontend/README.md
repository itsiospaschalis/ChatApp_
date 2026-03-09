# Chat App — Frontend

A modern chat application with AI-powered responses. Built with Next.js and React for a responsive, real-time messaging experience.

> **Note:** This frontend is part of a full-stack application. It communicates with a [Spring Boot backend](../spring-api) and requires the backend to be running.

---

## ✨ Features

- **User authentication** — Login and sign up with JWT-based auth
- **Real-time chat** — Create threads, send messages, view conversation history
- **AI-powered responses** — Integrates with Groq (LLaMA) for smart replies
- **User profile** — View and update your account details
- **Responsive UI** — Clean, modern interface built with Next.js and CSS modules

---

## 🛠 Tech Stack

| Layer       | Technology              |
| ----------- | ----------------------- |
| Framework   | [Next.js](https://nextjs.org) 15 |
| UI          | React 19                |
| HTTP Client | Axios                   |
| Styling     | CSS Modules, custom CSS |

---

## 📋 Prerequisites

- **Node.js** 18+ (or 20+ recommended)
- **npm** or **yarn**
- **Backend API** — The [Spring Boot backend](../spring-api) must be running on `http://localhost:8080`

---

## 🚀 Getting Started

### 1. Install dependencies

```bash
npm install
```

### 2. Start the backend

From the project root, start the Spring Boot API:

```bash
cd ../spring-api
mvn spring-boot:run
```

Ensure PostgreSQL is running and `LLMS_GROQ_KEY` is set if you use AI features.

### 3. Run the frontend

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) in your browser.

---

## 📁 Project Structure

```
src/
├── components/     # Reusable UI (Header, Footer)
├── pages/          # Next.js pages
│   ├── index.js    # Main chat view
│   ├── login/      # Login & signup
│   └── profile/    # User profile
├── services/       # API helpers (userService)
└── styles/         # CSS modules & global styles
```

---

## 🔧 Available Scripts

| Command        | Description                    |
| -------------- | ------------------------------ |
| `npm run dev`  | Start development server       |
| `npm run build`| Build for production           |
| `npm run start`| Start production server        |

---

## 🌐 Deployment (Portfolio)

### Vercel (recommended for Next.js)

1. Push your repo to GitHub
2. Import the project in [Vercel](https://vercel.com)
3. Set **Root Directory** to `frontend`
4. Configure **Environment Variables** if needed
5. Deploy

### Environment variables

If your backend URL differs in production, use:

```env
NEXT_PUBLIC_API_URL=https://your-api.example.com
```

Then update `userService.js` and API calls to use `process.env.NEXT_PUBLIC_API_URL` instead of `http://localhost:8080`.

---

## 📄 License

MIT
