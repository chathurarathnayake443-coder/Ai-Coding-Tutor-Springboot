/* =============================================================================
   mockApi.js
   -----------------------------------------------------------------------------
   Front-end-only stand-in for the real AI Coding Tutor REST API.
   Every function here mirrors an endpoint your backend should expose. Swap the
   body of each function for a real `fetch()` call when the API is ready — the
   call sites in each page already treat these as async functions, so nothing
   else needs to change.

   Suggested real endpoints (for reference while wiring the backend):
     POST   /api/auth/signup
     POST   /api/auth/login
     POST   /api/auth/logout
     GET    /api/students                     (admin)
     GET    /api/students/:id/analytics        (admin or self)
     GET    /api/sessions?userId=...           (list, newest first)
     POST   /api/sessions                      (create + question + language)
     POST   /api/sessions/:id/hint             (request next hint)
     POST   /api/sessions/:id/run              (send code to compiler/interpreter)
     POST   /api/sessions/:id/end              (end + receive rating)
     GET    /api/sessions/:id                  (full session incl. chat history)
   ============================================================================= */

const DB_KEY = "act_db_v1";
const SESSION_KEY = "act_current_user";

const TOPIC_POOL = ["Loops", "Conditionals", "Nested Loops", "Arrays", "Functions", "Recursion", "String Handling", "Variables & Scope"];

function seedDb() {
    return {
        users: [
            { id: "u_admin_1", role: "admin", name: "Priyantha Silva", email: "admin@aitutor.dev", password: "admin123", createdAt: "2026-02-01T09:00:00Z" },
            { id: "u_stu_1", role: "student", name: "Dinuka Perera", email: "dinuka@student.dev", password: "student123", phone: "+94 71 234 5678", createdAt: "2026-03-11T09:00:00Z" },
            { id: "u_stu_2", role: "student", name: "Ishara Fernando", email: "ishara@student.dev", password: "student123", phone: "+94 77 998 1122", createdAt: "2026-04-02T09:00:00Z" },
            { id: "u_stu_3", role: "student", name: "Kavindu Jayasuriya", email: "kavindu@student.dev", password: "student123", phone: "+94 76 555 9090", createdAt: "2026-05-19T09:00:00Z" }
        ],
        sessions: [
            {
                id: "s_1001", userId: "u_stu_1", language: "java",
                question: "Print the Fibonacci sequence up to n terms without using recursion.",
                status: "ended", hintsUsed: 2, rating: 4,
                topics: ["Loops", "Variables & Scope"],
                createdAt: "2026-07-30T10:12:00Z", endedAt: "2026-07-30T10:41:00Z",
                code: "public class Main {\n  public static void main(String[] args) {\n    int n = 10, a = 0, b = 1;\n    for (int i = 0; i < n; i++) {\n      System.out.print(a + \" \");\n      int next = a + b;\n      a = b;\n      b = next;\n    }\n  }\n}",
                chat: [
                    { from: "student", text: "I don't know how to keep track of the previous two numbers." },
                    { from: "ai", text: "Think about two variables that always hold the last two terms — update both of them every time your loop runs.", options: ["for loop", "while loop"] },
                    { from: "student", text: "Got it, using a and b now inside a for loop." },
                    { from: "ai", text: "That's the right shape. Update 'a' and 'b' together at the end of each iteration so neither overwrites the other too early." }
                ]
            },
            {
                id: "s_1002", userId: "u_stu_1", language: "python",
                question: "Given a list of numbers, return only the even ones in a new list.",
                status: "ended", hintsUsed: 1, rating: 5,
                topics: ["Loops", "Conditionals"],
                createdAt: "2026-08-02T14:05:00Z", endedAt: "2026-08-02T14:20:00Z",
                code: "def evens(nums):\n    result = []\n    for n in nums:\n        if n % 2 == 0:\n            result.append(n)\n    return result",
                chat: [
                    { from: "student", text: "Not sure how to filter only even numbers." },
                    { from: "ai", text: "You'll need a condition inside your loop that checks divisibility by 2 before adding an item to your result list.", options: ["if statement", "if-else statement"] }
                ]
            },
            {
                id: "s_1003", userId: "u_stu_1", language: "javascript",
                question: "Write a function that reverses a string without using the built-in reverse method.",
                status: "ended", hintsUsed: 3, rating: 3,
                topics: ["Loops", "String Handling"],
                createdAt: "2026-08-06T08:47:00Z", endedAt: "2026-08-06T09:10:00Z",
                code: "function reverseStr(str) {\n  let result = '';\n  for (let i = str.length - 1; i >= 0; i--) {\n    result += str[i];\n  }\n  return result;\n}",
                chat: [
                    { from: "student", text: "I keep getting the string in the same order." },
                    { from: "ai", text: "Check the direction your loop counter moves — right now it likely starts at 0 instead of the last index.", options: ["for loop (reverse)", "while loop (reverse)"] },
                    { from: "student", text: "Changed it to start from length - 1, still stuck on building the result." },
                    { from: "ai", text: "You're close. Concatenate each character onto a result string as you go, rather than trying to modify the original." },
                    { from: "ai", text: "Here is a complete approach: loop from the last index down to 0, appending each character to a new string, then return that string.", isFinal: true }
                ]
            }
        ]
    };
}

function loadDb() {
    const raw = localStorage.getItem(DB_KEY);
    if (!raw) {
        const seeded = seedDb();
        localStorage.setItem(DB_KEY, JSON.stringify(seeded));
        return seeded;
    }
    try { return JSON.parse(raw); } catch (e) { const seeded = seedDb(); localStorage.setItem(DB_KEY, JSON.stringify(seeded)); return seeded; }
}
function saveDb(db) { localStorage.setItem(DB_KEY, JSON.stringify(db)); }
function uid(prefix) { return prefix + "_" + Math.random().toString(36).slice(2, 9); }
function delay(ms) { return new Promise((res) => setTimeout(res, ms)); }

const api = {
    /* ---------------------------------------------------------- auth */
    async signup({ email, password, phone, name }) {
        await delay(350);
        const db = loadDb();
        if (db.users.some((u) => u.email.toLowerCase() === email.toLowerCase())) {
            throw new Error("An account with this email already exists.");
        }
        const user = { id: uid("u"), role: "student", name: name || email.split("@")[0], email, password, phone, createdAt: new Date().toISOString() };
        db.users.push(user);
        saveDb(db);
        return { id: user.id, email: user.email, role: user.role };
    },

    async login({ email, password, role }) {
        await delay(350);
        const db = loadDb();
        const user = db.users.find((u) => u.email.toLowerCase() === email.toLowerCase() && u.password === password && u.role === role);
        if (!user) throw new Error("Invalid email, password, or role selection.");
        const publicUser = { id: user.id, email: user.email, role: user.role, name: user.name };
        sessionStorage.setItem(SESSION_KEY, JSON.stringify(publicUser));
        return publicUser;
    },

    logout() { sessionStorage.removeItem(SESSION_KEY); },

    currentUser() {
        const raw = sessionStorage.getItem(SESSION_KEY);
        return raw ? JSON.parse(raw) : null;
    },

    /* ------------------------------------------------------- students / admin */
    async listStudents() {
        await delay(250);
        const db = loadDb();
        return db.users
            .filter((u) => u.role === "student")
            .map((u) => {
                const sessions = db.sessions.filter((s) => s.userId === u.id);
                const ratings = sessions.filter((s) => s.status === "ended").map((s) => s.rating);
                const avgRating = ratings.length ? (ratings.reduce((a, b) => a + b, 0) / ratings.length) : null;
                const lastActive = sessions.length ? sessions.map((s) => s.createdAt).sort().slice(-1)[0] : null;
                return { ...u, sessionCount: sessions.length, avgRating, lastActive };
            });
    },

    async addAdmin({ email, password, name }) {
        await delay(300);
        const db = loadDb();
        if (db.users.some((u) => u.email.toLowerCase() === email.toLowerCase())) {
            throw new Error("An account with this email already exists.");
        }
        const user = { id: uid("u"), role: "admin", name, email, password, createdAt: new Date().toISOString() };
        db.users.push(user);
        saveDb(db);
        return user;
    },

    /* ------------------------------------------------------------- sessions */
    async listSessions(userId) {
        await delay(200);
        const db = loadDb();
        return db.sessions.filter((s) => s.userId === userId).sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    },

    async getSession(id) {
        await delay(150);
        const db = loadDb();
        const s = db.sessions.find((x) => x.id === id);
        if (!s) throw new Error("Session not found.");
        return s;
    },

    async createSession({ userId, question, language }) {
        await delay(400);
        const db = loadDb();
        const session = {
            id: uid("s"), userId, question, language,
            status: "active", hintsUsed: 0, rating: null, topics: [],
            createdAt: new Date().toISOString(), endedAt: null,
            code: "", chat: []
        };
        db.sessions.push(session);
        saveDb(db);
        return session;
    },

    /* Simulated AI hint generation. A real backend sends `code` + `question`
       to OpenAI / Gemini and returns a contextual next-step suggestion. */
    async requestHint({ sessionId, code }) {
        await delay(900);
        const db = loadDb();
        const s = db.sessions.find((x) => x.id === sessionId);
        if (!s) throw new Error("Session not found.");
        if (s.hintsUsed >= 5) throw new Error("No hints remaining.");

        s.hintsUsed += 1;
        const isFinal = s.hintsUsed === 5;
        const topic = TOPIC_POOL[Math.floor(Math.random() * TOPIC_POOL.length)];
        if (!s.topics.includes(topic)) s.topics.push(topic);

        let text, options;
        if (isFinal) {
            text = "You've used all 5 hints, so here's a full walkthrough: break the problem into the smallest repeatable step, express that step inside a loop, and store any running result in a variable declared before the loop starts. This session will now close automatically.";
            options = [];
        } else {
            const suggestions = [
                { text: "Based on your code so far, the next step looks like a repeating action. Consider adding a loop here.", options: ["for loop", "while loop", "do-while loop"] },
                { text: "You have a value that needs to change depending on a condition. A conditional check would fit well right after this line.", options: ["if statement", "if-else statement", "switch statement"] },
                { text: "It looks like you're working with a collection of values. Think about how you'll access each item one at a time.", options: ["for-each loop", "indexed for loop"] },
                { text: "This logic could be repeated inside another loop for the pattern to work correctly — you may need a nested loop here.", options: ["nested for loop", "nested while loop"] },
                { text: "Consider declaring a variable to hold your running result before the loop starts, so it isn't reset every iteration.", options: ["declare outside loop", "declare inside loop"] }
            ];
            const pick = suggestions[Math.floor(Math.random() * suggestions.length)];
            text = pick.text;
            options = pick.options;
        }

        s.chat.push({ from: "ai", text, options, isFinal });
        if (isFinal) {
            s.status = "ended";
            s.endedAt = new Date().toISOString();
            s.rating = Math.floor(Math.random() * 2) + 3; // 3-4, since all hints were needed
        }
        saveDb(db);
        return { hintsUsed: s.hintsUsed, text, options, isFinal, sessionEnded: s.status === "ended", rating: s.rating };
    },

    /* Simulated code execution. A real backend forwards `code` to a sandboxed
       Java compiler, Python interpreter, or Node/JS runtime and streams stdout/stderr back. */
    async runCode({ language, code }) {
        await delay(700);
        if (!code || !code.trim()) return { ok: false, output: "No code to run." };
        if (language === "javascript") {
            const logs = [];
            const fakeConsole = { log: (...args) => logs.push(args.map(String).join(" ")) };
            try {
                // eslint-disable-next-line no-new-func
                const fn = new Function("console", code);
                fn(fakeConsole);
                return { ok: true, output: logs.length ? logs.join("\n") : "(no output — did you forget console.log?)" };
            } catch (err) {
                return { ok: false, output: err.message };
            }
        }
        // Java / Python have no in-browser runtime — simulate a compiler round-trip.
        return { ok: true, output: `[simulated ${language} run]\nCompiled & executed on tutor sandbox.\n(Connect a real ${language === "java" ? "JDK" : "Python"} execution service to see real output.)` };
    },

    async saveCode({ sessionId, code }) {
        const db = loadDb();
        const s = db.sessions.find((x) => x.id === sessionId);
        if (s) { s.code = code; saveDb(db); }
    },

    async endSession({ sessionId }) {
        await delay(400);
        const db = loadDb();
        const s = db.sessions.find((x) => x.id === sessionId);
        if (!s) throw new Error("Session not found.");
        if (s.status === "ended") return s;
        s.status = "ended";
        s.endedAt = new Date().toISOString();
        // Rating logic: fewer hints + more attempts before asking => higher score.
        s.rating = Math.max(1, 5 - s.hintsUsed + (s.code && s.code.length > 40 ? 1 : 0));
        s.rating = Math.min(s.rating, 5);
        if (!s.topics.length) s.topics.push(TOPIC_POOL[Math.floor(Math.random() * TOPIC_POOL.length)]);
        saveDb(db);
        return s;
    },

    /* ---------------------------------------------------------- analytics */
    async getAnalytics(userId) {
        await delay(250);
        const db = loadDb();
        const sessions = db.sessions.filter((s) => s.userId === userId && s.status === "ended");
        const totalSessions = db.sessions.filter((s) => s.userId === userId).length;
        const avgRating = sessions.length ? sessions.reduce((a, s) => a + s.rating, 0) / sessions.length : 0;
        const avgHints = sessions.length ? sessions.reduce((a, s) => a + s.hintsUsed, 0) / sessions.length : 0;

        const topicCount = {};
        sessions.forEach((s) => (s.topics || []).forEach((t) => { topicCount[t] = (topicCount[t] || 0) + 1; }));
        const totalTags = Object.values(topicCount).reduce((a, b) => a + b, 0) || 1;
        const focusTopics = Object.entries(topicCount)
            .map(([topic, count]) => ({ topic, pct: Math.round((count / totalTags) * 100) }))
            .sort((a, b) => b.pct - a.pct)
            .slice(0, 5);

        if (!focusTopics.length) {
            ["Loops", "Conditionals", "Nested Loops"].forEach((t, i) => focusTopics.push({ topic: t, pct: 0 }));
        }

        return {
            totalSessions, avgRating: Math.round(avgRating * 10) / 10, avgHints: Math.round(avgHints * 10) / 10,
            focusTopics,
            feedback: avgRating >= 4 ? "Strong independent problem-solving — you're leaning on hints less each session." :
                avgRating >= 2.5 ? "Solid progress. Try sketching your loop logic in comments before typing code to cut down hint usage." :
                    "You're taking on challenging problems. Slow down on planning the steps before coding — it'll reduce how many hints you need."
        };
    }
};
