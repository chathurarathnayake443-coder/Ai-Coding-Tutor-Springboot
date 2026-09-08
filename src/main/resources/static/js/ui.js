/* Shared small helpers used across pages. Load after mockApi.js. */

function showToast(message, type) {
    let el = document.getElementById("global-toast");
    if (!el) {
        el = document.createElement("div");
        el.id = "global-toast";
        el.className = "toast";
        document.body.appendChild(el);
    }
    el.textContent = message;
    el.className = "toast show" + (type ? " toast-" + type : "");
    clearTimeout(el._timer);
    el._timer = setTimeout(() => el.classList.remove("show"), 3200);
}

function initials(name) {
    if (!name) return "?";
    return name.split(" ").map((p) => p[0]).slice(0, 2).join("").toUpperCase();
}

function timeAgo(iso) {
    if (!iso) return "—";
    const diff = Date.now() - new Date(iso).getTime();
    const mins = Math.floor(diff / 60000);
    if (mins < 1) return "just now";
    if (mins < 60) return mins + "m ago";
    const hrs = Math.floor(mins / 60);
    if (hrs < 24) return hrs + "h ago";
    const days = Math.floor(hrs / 24);
    if (days < 30) return days + "d ago";
    return new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" });
}

function formatDate(iso) {
    if (!iso) return "—";
    return new Date(iso).toLocaleDateString(undefined, { year: "numeric", month: "short", day: "numeric" });
}

function starString(n) {
    n = Math.round(n || 0);
    return "★".repeat(n) + "☆".repeat(5 - n);
}

function wireLogout(selector) {
    document.querySelectorAll(selector).forEach((btn) => {
        btn.addEventListener("click", () => { api.logout(); window.location.href = "login.html"; });
    });
}
