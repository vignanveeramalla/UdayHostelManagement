const token = localStorage.getItem("token");


if (!token)
{
    window.location.href = "login.html";
}



// =====================================================
// LOAD ALL COMPLAINTS
// =====================================================

async function loadComplaints()
{
    try
    {
        const response = await fetch(
            "https://udayhostelmanagement-production.up.railway.app/complaints",
            {
                method: "GET",

                headers:
                {
                    "Authorization":
                        "Bearer " + token
                }
            }
        );


        const result = await response.json();


        console.log(
            "Complaints response:",
            result
        );


        if (response.ok)
        {
            displayComplaints(result.data);
        }
        else
        {
            showMessage(result.message);
        }

    }
    catch(error)
    {
        console.error(error);

        showMessage(
            "Unable to connect to server."
        );
    }
}



// =====================================================
// DISPLAY COMPLAINTS
// =====================================================

function displayComplaints(complaints)
{
    const tableBody =
        document.getElementById(
            "complaintTableBody"
        );


    tableBody.innerHTML = "";


    if (!complaints || complaints.length === 0)
    {
        tableBody.innerHTML = `
            <tr>
                <td colspan="7">
                    No complaints found.
                </td>
            </tr>
        `;

        return;
    }


    complaints.forEach(complaint =>
    {
        const row =
            document.createElement("tr");


        let actionButtons = `

            <button
                onclick="updateComplaintStatus(
                    ${complaint.complaintId}
                )">

                Update Status

            </button>

        `;


        // Delete only RESOLVED complaints

        if (
            complaint.status &&
            complaint.status.toUpperCase() === "RESOLVED"
        )
        {
            actionButtons += `

                <button
                    onclick="deleteComplaint(
                        ${complaint.complaintId}
                    )">

                    Delete

                </button>

            `;
        }


        row.innerHTML = `

            <td>
                ${complaint.complaintId}
            </td>

            <td>
                ${complaint.studentId}
            </td>

            <td>
                ${complaint.subject}
            </td>

            <td>
                ${complaint.description}
            </td>

            <td>
                ${complaint.complaintDate}
            </td>

            <td>

                <strong>
                    ${complaint.status}
                </strong>

            </td>

            <td>

                ${actionButtons}

            </td>

        `;


        tableBody.appendChild(row);

    });
}


// =====================================================
// OPEN UPDATE STATUS MODAL
// =====================================================

function updateComplaintStatus(complaintId)
{
    const rows =
        document.querySelectorAll(
            "#complaintTableBody tr"
        );

    let currentStatus = "";


    rows.forEach(row =>
    {
        const cells = row.querySelectorAll("td");

        if (
            cells.length >= 6 &&
            Number(cells[0].textContent.trim()) === complaintId
        )
        {
            currentStatus =
                cells[5].textContent.trim();
        }
    });


    document.getElementById(
        "statusComplaintId"
    ).value = complaintId;


    document.getElementById(
        "currentComplaintStatus"
    ).value = currentStatus;


    document.getElementById(
        "newComplaintStatus"
    ).value = currentStatus;


    document.getElementById(
        "statusModal"
    ).style.display = "block";
}



// =====================================================
// CLOSE UPDATE STATUS MODAL
// =====================================================

function closeStatusModal()
{
    document.getElementById(
        "statusModal"
    ).style.display = "none";
}



// =====================================================
// SAVE COMPLAINT STATUS
// =====================================================

async function saveComplaintStatus()
{
    const complaintId =
        document.getElementById(
            "statusComplaintId"
        ).value;


    const newStatus =
        document.getElementById(
            "newComplaintStatus"
        ).value;


    if (!complaintId)
    {
        showMessage(
            "Complaint ID is required."
        );

        return;
    }


    try
    {
        const response =
            await fetch(
                "https://udayhostelmanagement-production.up.railway.app/complaints/"
                + complaintId
                + "/status?status="
                + newStatus,
                {
                    method: "PUT",

                    headers:
                    {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const result =
            await response.json();


        console.log(
            "Update status response:",
            result
        );


        if (response.ok)
        {
            closeStatusModal();


            showMessage(
                "Complaint status updated successfully."
            );


            loadComplaints();
        }
        else
        {
            showMessage(
                result.message ||
                "Status update failed."
            );
        }

    }
    catch(error)
    {
        console.error(error);

        showMessage(
            "Unable to connect to server."
        );
    }
}

// =====================================================
// DELETE COMPLAINT
// =====================================================

async function deleteComplaint(
    complaintId
)
{

    const confirmDelete =
        confirm(
            "This complaint is RESOLVED.\n\n"
            + "Are you sure you want to delete complaint ID "
            + complaintId
            + "?"
        );


    if (!confirmDelete)
    {
        return;
    }


    try
    {

        const response =
            await fetch(
                "https://udayhostelmanagement-production.up.railway.app/complaints/"
                + complaintId,
                {
                    method: "DELETE",

                    headers:
                    {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const result =
            await response.json();


        console.log(
            "Delete complaint response:",
            result
        );


        if (response.ok)
        {
            showMessage(
                "Resolved complaint deleted successfully."
            );


            loadComplaints();
        }
        else
        {
            showMessage(
                result.message
            );
        }

    }
    catch(error)
    {
        console.error(error);

        showMessage(
            "Unable to connect to server."
        );
    }
}



// =====================================================
// SEARCH BY STUDENT ID
// =====================================================

async function searchComplaintsByStudent()
{

    const studentId =
        Number(
            document.getElementById(
                "searchStudentId"
            ).value
        );


    if (!studentId)
    {
        showMessage(
            "Please enter Student ID."
        );

        return;
    }


    try
    {

        const response =
            await fetch(
                "https://udayhostelmanagement-production.up.railway.app/complaints/student/"
                + studentId,
                {
                    method: "GET",

                    headers:
                    {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const result =
            await response.json();


        if (response.ok)
        {
            displayComplaints(
                result.data
            );
        }
        else
        {
            showMessage(
                result.message
            );
        }

    }
    catch(error)
    {
        console.error(error);

        showMessage(
            "Unable to connect to server."
        );
    }
}



// =====================================================
// FILTER BY STATUS
// =====================================================

async function filterByStatus()
{

    const status =
        document.getElementById(
            "statusFilter"
        ).value;


    if (status === "")
    {
        loadComplaints();

        return;
    }


    try
    {

        const response =
            await fetch(
                "https://udayhostelmanagement-production.up.railway.app/complaints/status/"
                + status,
                {
                    method: "GET",

                    headers:
                    {
                        "Authorization":
                            "Bearer " + token
                    }
                }
            );


        const result =
            await response.json();


        if (response.ok)
        {
            displayComplaints(
                result.data
            );
        }
        else
        {
            showMessage(
                result.message
            );
        }

    }
    catch(error)
    {
        console.error(error);

        showMessage(
            "Unable to connect to server."
        );
    }
}



// =====================================================
// MESSAGE
// =====================================================

function showMessage(message)
{
    document.getElementById(
        "message"
    ).textContent = message;
}



// =====================================================
// LOGOUT
// =====================================================

function logout()
{
    localStorage.removeItem("token");

    window.location.href =
        "login.html";
}

// =====================================================
// CLOSE MODAL WHEN CLICKING OUTSIDE
// =====================================================

window.onclick = function(event)
{
    const modal =
        document.getElementById("statusModal");


    if (event.target === modal)
    {
        closeStatusModal();
    }
};

// =====================================================
// INITIAL LOAD
// =====================================================

loadComplaints();