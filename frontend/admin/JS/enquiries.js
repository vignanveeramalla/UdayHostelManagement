// ================= TOKEN =================

const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


// ================= LOAD ENQUIRIES =================

async function loadEnquiries() {

    const tableBody =
        document.getElementById("enquiryTableBody");

    try {

        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/enquiries",
            {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );

        const result = await response.json();

        console.log("Enquiries response:", result);

        if (!response.ok) {

            document.getElementById("message").textContent =
                result.message || "Unable to load enquiries.";

            return;
        }

        const enquiries = result.data || [];

        tableBody.innerHTML = "";


        if (enquiries.length === 0) {

            tableBody.innerHTML = `
                <tr>
                    <td colspan="7">
                        No enquiries found.
                    </td>
                </tr>
            `;

            return;
        }


        // ================= DISPLAY EACH ENQUIRY =================

        enquiries.forEach(enquiry => {

            const row =
                document.createElement("tr");


            row.innerHTML = `

                <td>
                    ${enquiry.enquiryId}
                </td>

                <td>
                    ${escapeHTML(enquiry.name)}
                </td>

                <td>
                    ${escapeHTML(enquiry.email)}
                </td>

                <td>
                    ${escapeHTML(enquiry.message)}
                </td>

                <td>
                    ${
                        enquiry.adminReply
                        ? escapeHTML(enquiry.adminReply)
                        : "No reply yet"
                    }
                </td>

                <td>
                    ${escapeHTML(enquiry.status)}
                </td>

                <td>

                    <button
                        onclick="replyEnquiry(${enquiry.enquiryId})">

                        📧 Reply

                    </button>


                    <button
                        onclick="updateStatus(${enquiry.enquiryId})">

                        Status

                    </button>


                    <button
                        onclick="deleteEnquiry(${enquiry.enquiryId})">

                        Delete

                    </button>

                </td>

            `;

            tableBody.appendChild(row);

        });

    }

    catch (error) {

        console.error("Error:", error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }
}



// ================= OPEN REPLY MODAL =================

async function replyEnquiry(enquiryId) {

    try {

        // Get the complete enquiry
        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/enquiries/${enquiryId}`,
            {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            }
        );


        const result = await response.json();


        if (!response.ok) {

            document.getElementById("message").textContent =
                result.message || "Unable to load enquiry.";

            return;
        }


        const enquiry = result.data;


        // Store enquiry ID for sendReply()
        document.getElementById("replyModal")
            .dataset.enquiryId = enquiry.enquiryId;


        // Fill modal
        document.getElementById("replyStudentName").value =
            enquiry.name || "";


        document.getElementById("replyEmail").value =
            enquiry.email || "";


        document.getElementById("replyOriginalMessage").value =
            enquiry.message || "";


        // Clear previous reply
        document.getElementById("replyText").value = "";


        // Show modal
        document.getElementById("replyModal").style.display =
            "block";

    }

    catch (error) {

        console.error("Reply modal error:", error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }
}



// ================= CLOSE REPLY MODAL =================

function closeReplyModal() {

    document.getElementById("replyModal").style.display =
        "none";

}



// ================= SEND EMAIL REPLY =================

async function sendReply() {

    const modal =
        document.getElementById("replyModal");


    const enquiryId =
        modal.dataset.enquiryId;


    const reply =
        document.getElementById("replyText").value.trim();


    if (!reply) {

        alert("Reply cannot be empty.");

        return;
    }


    try {

        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/enquiries/${enquiryId}/reply`,
            {
                method: "PUT",

                headers: {

                    "Content-Type":
                        "application/json",

                    "Authorization":
                        "Bearer " + token
                },

                body: JSON.stringify({
                    reply: reply
                })
            }
        );


        const result =
            await response.json();


        console.log("Reply response:", result);


        if (response.ok) {

            // Close modal
            closeReplyModal();


            // Show success message
            document.getElementById("message").textContent =
                "Email sent successfully and reply saved.";


            // Reload table
            loadEnquiries();

        }

        else {

            document.getElementById("message").textContent =
                result.message || "Email sending failed.";

        }

    }

    catch (error) {

        console.error("Send reply error:", error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }
}



// ================= UPDATE STATUS =================

async function updateStatus(enquiryId) {

    const status =
        prompt(
            "Enter status:\n\nPENDING\nCONTACTED\nCLOSED"
        );


    if (status === null) {
        return;
    }


    const newStatus =
        status.trim().toUpperCase();


    if (
        newStatus !== "PENDING" &&
        newStatus !== "CONTACTED" &&
        newStatus !== "CLOSED"
    ) {

        alert(
            "Invalid status.\n\nUse PENDING, CONTACTED or CLOSED."
        );

        return;
    }


    try {

        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/enquiries/${enquiryId}/status?status=${newStatus}`,
            {
                method: "PUT",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result =
            await response.json();


        if (response.ok) {

            document.getElementById("message").textContent =
                "Enquiry status updated successfully.";

            loadEnquiries();

        }

        else {

            document.getElementById("message").textContent =
                result.message || "Status update failed.";

        }

    }

    catch (error) {

        console.error(error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }
}



// ================= DELETE ENQUIRY =================

async function deleteEnquiry(enquiryId) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete enquiry ID "
            + enquiryId
            + "?"
        );


    if (!confirmDelete) {
        return;
    }


    try {

        const response = await fetch(
            `https://udayhostelmanagement-production.up.railway.app/enquiries/${enquiryId}`,
            {
                method: "DELETE",

                headers: {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result =
            await response.json();


        if (response.ok) {

            document.getElementById("message").textContent =
                "Enquiry deleted successfully.";

            loadEnquiries();

        }

        else {

            document.getElementById("message").textContent =
                result.message || "Delete failed.";

        }

    }

    catch (error) {

        console.error(error);

        document.getElementById("message").textContent =
            "Unable to connect to server.";

    }
}



// ================= ESCAPE HTML =================

function escapeHTML(value) {

    if (value === null || value === undefined) {
        return "";
    }


    return String(value)

        .replace(/&/g, "&amp;")

        .replace(/</g, "&lt;")

        .replace(/>/g, "&gt;")

        .replace(/"/g, "&quot;")

        .replace(/'/g, "&#039;");
}



// ================= LOGOUT =================

function logout() {

    localStorage.removeItem("token");

    window.location.href = "login.html";

}



// ================= CLOSE MODAL WHEN CLICKING OUTSIDE =================

window.onclick = function(event) {

    const modal =
        document.getElementById("replyModal");


    if (event.target === modal) {

        closeReplyModal();

    }

};



// ================= INITIAL LOAD =================

loadEnquiries();