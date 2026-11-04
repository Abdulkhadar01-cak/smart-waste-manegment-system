/**
 * ==========================================================================
 * Smart Waste Management System - Main JavaScript File
 * Path: backend/src/main/resources/static/js/main.js
 * 
 * Separation of Concerns:
 * All JavaScript logic is isolated in this external file.
 * No inline <script> blocks are embedded inside HTML templates.
 * ==========================================================================
 */

document.addEventListener("DOMContentLoaded", function () {
    console.log("[SmartWaste] Static JavaScript loaded and initialized successfully.");

    // Verify DOM interaction from external JavaScript
    const jsStatusEl = document.getElementById("js-runtime-status");
    const timestampEl = document.getElementById("client-timestamp");

    if (jsStatusEl) {
        jsStatusEl.textContent = "External JavaScript (main.js) loaded & running in browser";
        jsStatusEl.style.color = "#047857";
    }

    if (timestampEl) {
        const now = new Date();
        timestampEl.textContent = "Client render time: " + now.toLocaleTimeString();
    }

    // Attach click feedback to test cards
    const cards = document.querySelectorAll(".check-card");
    cards.forEach((card) => {
        card.addEventListener("mouseenter", function () {
            card.style.borderColor = "#10b981";
        });
        card.addEventListener("mouseleave", function () {
            card.style.borderColor = "#e2e8f0";
        });
    });
});
